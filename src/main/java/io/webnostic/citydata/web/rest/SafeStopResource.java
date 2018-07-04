package io.webnostic.citydata.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.webnostic.citydata.domain.SafeStop;

import io.webnostic.citydata.model.CrimeType;
import io.webnostic.citydata.model.GoTriangleStops;
import io.webnostic.citydata.model.PoliceCrimeReports;
import io.webnostic.citydata.repository.SafeStopRepository;
import io.webnostic.citydata.web.rest.errors.BadRequestAlertException;
import io.webnostic.citydata.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.Produces;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * REST controller for managing SafeStop.
 */
@RestController
@RequestMapping("/api")
public class SafeStopResource {

    private final Logger log = LoggerFactory.getLogger(SafeStopResource.class);

    private static final String ENTITY_NAME = "safeStop";

    private final SafeStopRepository safeStopRepository;

    private static final String OPENDURHAM_BASE_URI_V1 = "https://opendurham.nc.gov/api/records/1.0/";

    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;

    public SafeStopResource(SafeStopRepository safeStopRepository) {
        this.safeStopRepository = safeStopRepository;

        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
    }

    /**
     * POST  /safe-stops : Create a new safeStop.
     *
     * @param safeStop the safeStop to create
     * @return the ResponseEntity with status 201 (Created) and with body the new safeStop, or with status 400 (Bad Request) if the safeStop has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/safe-stops")
    @Timed
    public ResponseEntity<SafeStop> createSafeStop(@RequestBody SafeStop safeStop) throws URISyntaxException {
        log.debug("REST request to save SafeStop : {}", safeStop);
        if (safeStop.getId() != null) {
            throw new BadRequestAlertException("A new safeStop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SafeStop result = safeStopRepository.save(safeStop);
        return ResponseEntity.created(new URI("/api/safe-stops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /safe-stops : Updates an existing safeStop.
     *
     * @param safeStop the safeStop to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated safeStop,
     * or with status 400 (Bad Request) if the safeStop is not valid,
     * or with status 500 (Internal Server Error) if the safeStop couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/safe-stops")
    @Timed
    public ResponseEntity<SafeStop> updateSafeStop(@RequestBody SafeStop safeStop) throws URISyntaxException {
        log.debug("REST request to update SafeStop : {}", safeStop);
        if (safeStop.getId() == null) {
            return createSafeStop(safeStop);
        }
        SafeStop result = safeStopRepository.save(safeStop);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, safeStop.getId().toString()))
            .body(result);
    }

    /**
     * GET  /safe-stops : get all the safeStops.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of safeStops in body
     */
    @GetMapping("/safe-stops")
    @Timed
    public List<SafeStop> getAllSafeStops() {
        log.debug("REST request to get all SafeStops");
        return safeStopRepository.findAll();
        }

    /**
     * GET  /safe-stops/:id : get the "id" safeStop.
     *
     * @param id the id of the safeStop to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the safeStop, or with status 404 (Not Found)
     */
    @GetMapping("/safe-stops/{id}")
    @Timed
    public ResponseEntity<SafeStop> getSafeStop(@PathVariable Long id) {
        log.debug("REST request to get SafeStop : {}", id);
        SafeStop safeStop = safeStopRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(safeStop));
    }

    /**
     * DELETE  /safe-stops/:id : delete the "id" safeStop.
     *
     * @param id the id of the safeStop to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/safe-stops/{id}")
    @Timed
    public ResponseEntity<Void> deleteSafeStop(@PathVariable Long id) {
        log.debug("REST request to delete SafeStop : {}", id);
        safeStopRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @RequestMapping(method = RequestMethod.GET, path="/searchSafeStops/")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<List<SafeStop>> searchSafeStops(@RequestParam(value = "streetName") String streetName) throws IOException {
        String uri = "search/?dataset=gotriangle-stops-cary-ch-duke-durham-raleigh-wofline&q=" + streetName + "&facet=municipality";
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(OPENDURHAM_BASE_URI_V1 + uri, HttpMethod.GET, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());

        GoTriangleStops goTriangleStops = new ObjectMapper().readValue(responseEntity.getBody(), GoTriangleStops.class);

        ExecutorService executor = Executors.newFixedThreadPool(4);

        List<CompletableFuture<SafeStop>> busStops = Arrays.stream(goTriangleStops.getRecords()).
            map(record -> CompletableFuture.supplyAsync(() ->
                findCrime(record.getFields().getStop_name(), record.getFields().getStop_lat(), record.getFields().getStop_lon()), executor)).
            collect(Collectors.toList());

        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(busStops.toArray(new CompletableFuture[busStops.size()]));

        return allDoneFuture.thenApply(v -> busStops.stream().
            map(crimeReport -> crimeReport.join()).
            collect(Collectors.<SafeStop>toList()));
    }

    private SafeStop findCrime (String busStopName, double latitude, double longitude) {
        SafeStop result = null;
        try{
            result = findCrime(busStopName,latitude, longitude, 50);
        }catch (IOException e){
            System.out.println(e);
        }
        return result;
    }

    private SafeStop findCrime (String busStopName, double latitude, double longitude, int distance) throws IOException {
        String uri = "search/?dataset=durham-police-crime-reports&facet=date_rept&facet=dow1&facet=reportedas&facet=chrgdesc&facet=big_zone&geofilter.distance=" + latitude + "," + longitude + "," + distance;
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(OPENDURHAM_BASE_URI_V1 + uri, HttpMethod.GET, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());

        PoliceCrimeReports policeCrimeReport = new ObjectMapper().readValue(responseEntity.getBody(), PoliceCrimeReports.class);

        Arrays.stream(policeCrimeReport.getRecords()).forEach(record -> {
            log.info("Found crime near \'"+ busStopName + "\' of type " + record.getFields().getReportedas() + " within " + record.getFields().getDist() + " meter. Description: " + (record.getFields()).getChrgdesc());
        });

        Map<CrimeType, Long> grouped = Arrays.stream(policeCrimeReport.getRecords()).
            collect(groupingBy(record -> (record.getFields()).getCrimeType(), counting()));


        //return busStopService.save(safeStop);
        SafeStop safeStop = safeStopRepository.findByName(busStopName);
        if(safeStop != null){
            log.info("Safe stop \'" + busStopName + "\' already exists.");
            return safeStop;
        } else{
            return safeStopRepository.save(
                new SafeStop(busStopName, latitude, longitude,
                grouped.get(CrimeType.VIOLENT_CRIME),
                grouped.get(CrimeType.PROPERTY_CRIME),
                grouped.get(CrimeType.OTHER_CRIME)));
        }
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
