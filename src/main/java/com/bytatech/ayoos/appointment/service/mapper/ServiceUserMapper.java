package com.bytatech.ayoos.appointment.service.mapper;

import com.bytatech.ayoos.appointment.domain.*;
import com.bytatech.ayoos.appointment.service.dto.ServiceUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceUser} and its DTO {@link ServiceUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceUserMapper extends EntityMapper<ServiceUserDTO, ServiceUser> {



    default ServiceUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceUser serviceUser = new ServiceUser();
        serviceUser.setId(id);
        return serviceUser;
    }
}
