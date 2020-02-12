package com.bytatech.ayoos.appointment.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytatech.ayoos.appointment.web.rest.TestUtil;

public class ServiceUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceUserDTO.class);
        ServiceUserDTO serviceUserDTO1 = new ServiceUserDTO();
        serviceUserDTO1.setId(1L);
        ServiceUserDTO serviceUserDTO2 = new ServiceUserDTO();
        assertThat(serviceUserDTO1).isNotEqualTo(serviceUserDTO2);
        serviceUserDTO2.setId(serviceUserDTO1.getId());
        assertThat(serviceUserDTO1).isEqualTo(serviceUserDTO2);
        serviceUserDTO2.setId(2L);
        assertThat(serviceUserDTO1).isNotEqualTo(serviceUserDTO2);
        serviceUserDTO1.setId(null);
        assertThat(serviceUserDTO1).isNotEqualTo(serviceUserDTO2);
    }
}
