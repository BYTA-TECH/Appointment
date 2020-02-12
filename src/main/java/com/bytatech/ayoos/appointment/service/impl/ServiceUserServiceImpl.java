package com.bytatech.ayoos.appointment.service.impl;

import com.bytatech.ayoos.appointment.service.ServiceUserService;
import com.bytatech.ayoos.appointment.domain.ServiceUser;
import com.bytatech.ayoos.appointment.repository.ServiceUserRepository;
import com.bytatech.ayoos.appointment.repository.search.ServiceUserSearchRepository;
import com.bytatech.ayoos.appointment.service.dto.ServiceUserDTO;
import com.bytatech.ayoos.appointment.service.mapper.ServiceUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ServiceUser}.
 */
@Service
@Transactional
public class ServiceUserServiceImpl implements ServiceUserService {

    private final Logger log = LoggerFactory.getLogger(ServiceUserServiceImpl.class);

    private final ServiceUserRepository serviceUserRepository;

    private final ServiceUserMapper serviceUserMapper;

    private final ServiceUserSearchRepository serviceUserSearchRepository;

    public ServiceUserServiceImpl(ServiceUserRepository serviceUserRepository, ServiceUserMapper serviceUserMapper, ServiceUserSearchRepository serviceUserSearchRepository) {
        this.serviceUserRepository = serviceUserRepository;
        this.serviceUserMapper = serviceUserMapper;
        this.serviceUserSearchRepository = serviceUserSearchRepository;
    }

    /**
     * Save a serviceUser.
     *
     * @param serviceUserDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceUserDTO save(ServiceUserDTO serviceUserDTO) {
        log.debug("Request to save ServiceUser : {}", serviceUserDTO);
        ServiceUser serviceUser = serviceUserMapper.toEntity(serviceUserDTO);
        serviceUser = serviceUserRepository.save(serviceUser);
        ServiceUserDTO result = serviceUserMapper.toDto(serviceUser);
        serviceUserSearchRepository.save(serviceUser);
        return result;
    }

    /**
     * Get all the serviceUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceUsers");
        return serviceUserRepository.findAll(pageable)
            .map(serviceUserMapper::toDto);
    }


    /**
     * Get one serviceUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceUserDTO> findOne(Long id) {
        log.debug("Request to get ServiceUser : {}", id);
        return serviceUserRepository.findById(id)
            .map(serviceUserMapper::toDto);
    }

    /**
     * Delete the serviceUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceUser : {}", id);
        serviceUserRepository.deleteById(id);
        serviceUserSearchRepository.deleteById(id);
    }

    /**
     * Search for the serviceUser corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceUserDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ServiceUsers for query {}", query);
        return serviceUserSearchRepository.search(queryStringQuery(query), pageable)
            .map(serviceUserMapper::toDto);
    }
}
