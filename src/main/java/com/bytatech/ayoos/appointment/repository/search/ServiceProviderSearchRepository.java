package com.bytatech.ayoos.appointment.repository.search;

import com.bytatech.ayoos.appointment.domain.ServiceProvider;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ServiceProvider} entity.
 */
public interface ServiceProviderSearchRepository extends ElasticsearchRepository<ServiceProvider, Long> {
}
