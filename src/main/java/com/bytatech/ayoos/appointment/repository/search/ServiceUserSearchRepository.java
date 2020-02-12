package com.bytatech.ayoos.appointment.repository.search;

import com.bytatech.ayoos.appointment.domain.ServiceUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ServiceUser} entity.
 */
public interface ServiceUserSearchRepository extends ElasticsearchRepository<ServiceUser, Long> {
}
