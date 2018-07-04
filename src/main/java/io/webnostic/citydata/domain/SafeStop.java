package io.webnostic.citydata.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SafeStop.
 */
@Entity
@Table(name = "safe_stop")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SafeStop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "violent_crime_count")
    private Long violentCrimeCount;

    @Column(name = "property_crime_count")
    private Long propertyCrimeCount;

    @Column(name = "other_crime_count")
    private Long otherCrimeCount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SafeStop name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public SafeStop latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public SafeStop longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getViolentCrimeCount() {
        return violentCrimeCount;
    }

    public SafeStop violentCrimeCount(Long violentCrimeCount) {
        this.violentCrimeCount = violentCrimeCount;
        return this;
    }

    public void setViolentCrimeCount(Long violentCrimeCount) {
        this.violentCrimeCount = violentCrimeCount;
    }

    public Long getPropertyCrimeCount() {
        return propertyCrimeCount;
    }

    public SafeStop propertyCrimeCount(Long propertyCrimeCount) {
        this.propertyCrimeCount = propertyCrimeCount;
        return this;
    }

    public void setPropertyCrimeCount(Long propertyCrimeCount) {
        this.propertyCrimeCount = propertyCrimeCount;
    }

    public Long getOtherCrimeCount() {
        return otherCrimeCount;
    }

    public SafeStop otherCrimeCount(Long otherCrimeCount) {
        this.otherCrimeCount = otherCrimeCount;
        return this;
    }

    public void setOtherCrimeCount(Long otherCrimeCount) {
        this.otherCrimeCount = otherCrimeCount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public SafeStop() {
    }

    public SafeStop(String name, Double latitude, Double longitude, Long violentCrimeCount, Long propertyCrimeCount, Long otherCrimeCount) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.violentCrimeCount = violentCrimeCount;
        this.propertyCrimeCount = propertyCrimeCount;
        this.otherCrimeCount = otherCrimeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SafeStop safeStop = (SafeStop) o;
        if (safeStop.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), safeStop.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SafeStop{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", violentCrimeCount=" + getViolentCrimeCount() +
            ", propertyCrimeCount=" + getPropertyCrimeCount() +
            ", otherCrimeCount=" + getOtherCrimeCount() +
            "}";
    }
}
