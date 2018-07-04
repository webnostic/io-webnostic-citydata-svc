package io.webnostic.citydata.web.rest;

import io.webnostic.citydata.CityDataServiceApp;

import io.webnostic.citydata.domain.SafeStop;
import io.webnostic.citydata.repository.SafeStopRepository;
import io.webnostic.citydata.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static io.webnostic.citydata.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SafeStopResource REST controller.
 *
 * @see SafeStopResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CityDataServiceApp.class)
public class SafeStopResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Long DEFAULT_VIOLENT_CRIME_COUNT = 1L;
    private static final Long UPDATED_VIOLENT_CRIME_COUNT = 2L;

    private static final Long DEFAULT_PROPERTY_CRIME_COUNT = 1L;
    private static final Long UPDATED_PROPERTY_CRIME_COUNT = 2L;

    private static final Long DEFAULT_OTHER_CRIME_COUNT = 1L;
    private static final Long UPDATED_OTHER_CRIME_COUNT = 2L;

    @Autowired
    private SafeStopRepository safeStopRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSafeStopMockMvc;

    private SafeStop safeStop;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SafeStopResource safeStopResource = new SafeStopResource(safeStopRepository);
        this.restSafeStopMockMvc = MockMvcBuilders.standaloneSetup(safeStopResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SafeStop createEntity(EntityManager em) {
        SafeStop safeStop = new SafeStop()
            .name(DEFAULT_NAME)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .violentCrimeCount(DEFAULT_VIOLENT_CRIME_COUNT)
            .propertyCrimeCount(DEFAULT_PROPERTY_CRIME_COUNT)
            .otherCrimeCount(DEFAULT_OTHER_CRIME_COUNT);
        return safeStop;
    }

    @Before
    public void initTest() {
        safeStop = createEntity(em);
    }

    @Test
    @Transactional
    public void createSafeStop() throws Exception {
        int databaseSizeBeforeCreate = safeStopRepository.findAll().size();

        // Create the SafeStop
        restSafeStopMockMvc.perform(post("/api/safe-stops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeStop)))
            .andExpect(status().isCreated());

        // Validate the SafeStop in the database
        List<SafeStop> safeStopList = safeStopRepository.findAll();
        assertThat(safeStopList).hasSize(databaseSizeBeforeCreate + 1);
        SafeStop testSafeStop = safeStopList.get(safeStopList.size() - 1);
        assertThat(testSafeStop.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSafeStop.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testSafeStop.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testSafeStop.getViolentCrimeCount()).isEqualTo(DEFAULT_VIOLENT_CRIME_COUNT);
        assertThat(testSafeStop.getPropertyCrimeCount()).isEqualTo(DEFAULT_PROPERTY_CRIME_COUNT);
        assertThat(testSafeStop.getOtherCrimeCount()).isEqualTo(DEFAULT_OTHER_CRIME_COUNT);
    }

    @Test
    @Transactional
    public void createSafeStopWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = safeStopRepository.findAll().size();

        // Create the SafeStop with an existing ID
        safeStop.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSafeStopMockMvc.perform(post("/api/safe-stops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeStop)))
            .andExpect(status().isBadRequest());

        // Validate the SafeStop in the database
        List<SafeStop> safeStopList = safeStopRepository.findAll();
        assertThat(safeStopList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSafeStops() throws Exception {
        // Initialize the database
        safeStopRepository.saveAndFlush(safeStop);

        // Get all the safeStopList
        restSafeStopMockMvc.perform(get("/api/safe-stops?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(safeStop.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].violentCrimeCount").value(hasItem(DEFAULT_VIOLENT_CRIME_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].propertyCrimeCount").value(hasItem(DEFAULT_PROPERTY_CRIME_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].otherCrimeCount").value(hasItem(DEFAULT_OTHER_CRIME_COUNT.intValue())));
    }

    @Test
    @Transactional
    public void getSafeStop() throws Exception {
        // Initialize the database
        safeStopRepository.saveAndFlush(safeStop);

        // Get the safeStop
        restSafeStopMockMvc.perform(get("/api/safe-stops/{id}", safeStop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(safeStop.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.violentCrimeCount").value(DEFAULT_VIOLENT_CRIME_COUNT.intValue()))
            .andExpect(jsonPath("$.propertyCrimeCount").value(DEFAULT_PROPERTY_CRIME_COUNT.intValue()))
            .andExpect(jsonPath("$.otherCrimeCount").value(DEFAULT_OTHER_CRIME_COUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSafeStop() throws Exception {
        // Get the safeStop
        restSafeStopMockMvc.perform(get("/api/safe-stops/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSafeStop() throws Exception {
        // Initialize the database
        safeStopRepository.saveAndFlush(safeStop);
        int databaseSizeBeforeUpdate = safeStopRepository.findAll().size();

        // Update the safeStop
        SafeStop updatedSafeStop = safeStopRepository.findOne(safeStop.getId());
        // Disconnect from session so that the updates on updatedSafeStop are not directly saved in db
        em.detach(updatedSafeStop);
        updatedSafeStop
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .violentCrimeCount(UPDATED_VIOLENT_CRIME_COUNT)
            .propertyCrimeCount(UPDATED_PROPERTY_CRIME_COUNT)
            .otherCrimeCount(UPDATED_OTHER_CRIME_COUNT);

        restSafeStopMockMvc.perform(put("/api/safe-stops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSafeStop)))
            .andExpect(status().isOk());

        // Validate the SafeStop in the database
        List<SafeStop> safeStopList = safeStopRepository.findAll();
        assertThat(safeStopList).hasSize(databaseSizeBeforeUpdate);
        SafeStop testSafeStop = safeStopList.get(safeStopList.size() - 1);
        assertThat(testSafeStop.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSafeStop.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testSafeStop.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testSafeStop.getViolentCrimeCount()).isEqualTo(UPDATED_VIOLENT_CRIME_COUNT);
        assertThat(testSafeStop.getPropertyCrimeCount()).isEqualTo(UPDATED_PROPERTY_CRIME_COUNT);
        assertThat(testSafeStop.getOtherCrimeCount()).isEqualTo(UPDATED_OTHER_CRIME_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingSafeStop() throws Exception {
        int databaseSizeBeforeUpdate = safeStopRepository.findAll().size();

        // Create the SafeStop

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSafeStopMockMvc.perform(put("/api/safe-stops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeStop)))
            .andExpect(status().isCreated());

        // Validate the SafeStop in the database
        List<SafeStop> safeStopList = safeStopRepository.findAll();
        assertThat(safeStopList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSafeStop() throws Exception {
        // Initialize the database
        safeStopRepository.saveAndFlush(safeStop);
        int databaseSizeBeforeDelete = safeStopRepository.findAll().size();

        // Get the safeStop
        restSafeStopMockMvc.perform(delete("/api/safe-stops/{id}", safeStop.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SafeStop> safeStopList = safeStopRepository.findAll();
        assertThat(safeStopList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SafeStop.class);
        SafeStop safeStop1 = new SafeStop();
        safeStop1.setId(1L);
        SafeStop safeStop2 = new SafeStop();
        safeStop2.setId(safeStop1.getId());
        assertThat(safeStop1).isEqualTo(safeStop2);
        safeStop2.setId(2L);
        assertThat(safeStop1).isNotEqualTo(safeStop2);
        safeStop1.setId(null);
        assertThat(safeStop1).isNotEqualTo(safeStop2);
    }
}
