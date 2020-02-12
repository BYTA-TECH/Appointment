package com.bytatech.ayoos.appointment.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytatech.ayoos.appointment.web.rest.TestUtil;

public class ServiceProviderDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceProviderDTO.class);
        ServiceProviderDTO serviceProviderDTO1 = new ServiceProviderDTO();
        serviceProviderDTO1.setId(1L);
        ServiceProviderDTO serviceProviderDTO2 = new ServiceProviderDTO();
        assertThat(serviceProviderDTO1).isNotEqualTo(serviceProviderDTO2);
        serviceProviderDTO2.setId(serviceProviderDTO1.getId());
        assertThat(serviceProviderDTO1).isEqualTo(serviceProviderDTO2);
        serviceProviderDTO2.setId(2L);
        assertThat(serviceProviderDTO1).isNotEqualTo(serviceProviderDTO2);
        serviceProviderDTO1.setId(null);
        assertThat(serviceProviderDTO1).isNotEqualTo(serviceProviderDTO2);
    }
}
