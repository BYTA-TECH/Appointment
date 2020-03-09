package com.bytatech.ayoos.appointment.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AppointmentMapperTest {

    private AppointmentMapper appointmentMapper;

    @BeforeEach
    public void setUp() {
        appointmentMapper = new AppointmentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(appointmentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(appointmentMapper.fromId(null)).isNull();
    }
}
