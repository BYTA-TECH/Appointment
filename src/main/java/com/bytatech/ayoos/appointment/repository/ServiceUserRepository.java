package com.bytatech.ayoos.appointment.repository;

import com.bytatech.ayoos.appointment.domain.ServiceUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceUserRepository extends JpaRepository<ServiceUser, Long> {

}
