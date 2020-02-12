package com.bytatech.ayoos.appointment.service.mapper;

import com.bytatech.ayoos.appointment.domain.*;
import com.bytatech.ayoos.appointment.service.dto.ServiceProviderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceProvider} and its DTO {@link ServiceProviderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceProviderMapper extends EntityMapper<ServiceProviderDTO, ServiceProvider> {



    default ServiceProvider fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(id);
        return serviceProvider;
    }
}
