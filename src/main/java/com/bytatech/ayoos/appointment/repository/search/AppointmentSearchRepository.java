package com.bytatech.ayoos.appointment.repository.search;

import com.bytatech.ayoos.appointment.domain.Appointment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Appointment} entity.
 */
public interface AppointmentSearchRepository extends ElasticsearchRepository<Appointment, Long> {
}
