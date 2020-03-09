package com.bytatech.ayoos.appointment.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Appointment.
 */
@Entity
@Table(name = "appointment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "appointment")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "appointment_id")
    private Long appointmentId;

    @Column(name = "service_provider")
    private String serviceProvider;

    @Column(name = "service_user")
    private String serviceUser;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "from_time")
    private Instant fromTime;

    @Column(name = "to_time")
    private Instant toTime;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public Appointment appointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
        return this;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public Appointment serviceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
        return this;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getServiceUser() {
        return serviceUser;
    }

    public Appointment serviceUser(String serviceUser) {
        this.serviceUser = serviceUser;
        return this;
    }

    public void setServiceUser(String serviceUser) {
        this.serviceUser = serviceUser;
    }

    public LocalDate getDate() {
        return date;
    }

    public Appointment date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Instant getFromTime() {
        return fromTime;
    }

    public Appointment fromTime(Instant fromTime) {
        this.fromTime = fromTime;
        return this;
    }

    public void setFromTime(Instant fromTime) {
        this.fromTime = fromTime;
    }

    public Instant getToTime() {
        return toTime;
    }

    public Appointment toTime(Instant toTime) {
        this.toTime = toTime;
        return this;
    }

    public void setToTime(Instant toTime) {
        this.toTime = toTime;
    }

    public String getStatus() {
        return status;
    }

    public Appointment status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appointment)) {
            return false;
        }
        return id != null && id.equals(((Appointment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Appointment{" +
            "id=" + getId() +
            ", appointmentId=" + getAppointmentId() +
            ", serviceProvider='" + getServiceProvider() + "'" +
            ", serviceUser='" + getServiceUser() + "'" +
            ", date='" + getDate() + "'" +
            ", fromTime='" + getFromTime() + "'" +
            ", toTime='" + getToTime() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
