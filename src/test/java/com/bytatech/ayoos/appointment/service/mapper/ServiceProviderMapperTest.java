package com.bytatech.ayoos.appointment.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ServiceProviderMapperTest {

    private ServiceProviderMapper serviceProviderMapper;

    @BeforeEach
    public void setUp() {
        serviceProviderMapper = new ServiceProviderMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(serviceProviderMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(serviceProviderMapper.fromId(null)).isNull();
    }
}
