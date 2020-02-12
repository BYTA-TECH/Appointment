package com.bytatech.ayoos.appointment.service;

import com.bytatech.ayoos.appointment.service.dto.ServiceUserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bytatech.ayoos.appointment.domain.ServiceUser}.
 */
public interface ServiceUserService {

    /**
     * Save a serviceUser.
     *
     * @param serviceUserDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceUserDTO save(ServiceUserDTO serviceUserDTO);

    /**
     * Get all the serviceUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceUserDTO> findAll(Pageable pageable);


    /**
     * Get the "id" serviceUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceUserDTO> findOne(Long id);

    /**
     * Delete the "id" serviceUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the serviceUser corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceUserDTO> search(String query, Pageable pageable);
}
