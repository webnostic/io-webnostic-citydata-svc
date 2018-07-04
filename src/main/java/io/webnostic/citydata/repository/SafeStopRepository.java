package io.webnostic.citydata.repository;

import io.webnostic.citydata.domain.SafeStop;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SafeStop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SafeStopRepository extends JpaRepository<SafeStop, Long> {
    public SafeStop findByName(String name);

}
