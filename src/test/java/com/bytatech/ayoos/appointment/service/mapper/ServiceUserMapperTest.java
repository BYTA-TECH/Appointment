package com.bytatech.ayoos.appointment.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ServiceUserMapperTest {

    private ServiceUserMapper serviceUserMapper;

    @BeforeEach
    public void setUp() {
        serviceUserMapper = new ServiceUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(serviceUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(serviceUserMapper.fromId(null)).isNull();
    }
}
