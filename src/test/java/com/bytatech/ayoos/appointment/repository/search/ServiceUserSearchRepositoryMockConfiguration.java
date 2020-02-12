package com.bytatech.ayoos.appointment.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ServiceUserSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ServiceUserSearchRepositoryMockConfiguration {

    @MockBean
    private ServiceUserSearchRepository mockServiceUserSearchRepository;

}
