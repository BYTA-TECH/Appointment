package com.bytatech.ayoos.appointment.service.impl;

import com.bytatech.ayoos.appointment.service.ServiceProviderService;
import com.bytatech.ayoos.appointment.domain.ServiceProvider;
import com.bytatech.ayoos.appointment.repository.ServiceProviderRepository;
import com.bytatech.ayoos.appointment.repository.search.ServiceProviderSearchRepository;
import com.bytatech.ayoos.appointment.service.dto.ServiceProviderDTO;
import com.bytatech.ayoos.appointment.service.mapper.ServiceProviderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ServiceProvider}.
 */
@Service
@Transactional
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private final Logger log = LoggerFactory.getLogger(ServiceProviderServiceImpl.class);

    private final ServiceProviderRepository serviceProviderRepository;

    private final ServiceProviderMapper serviceProviderMapper;

    private final ServiceProviderSearchRepository serviceProviderSearchRepository;

    public ServiceProviderServiceImpl(ServiceProviderRepository serviceProviderRepository, ServiceProviderMapper serviceProviderMapper, ServiceProviderSearchRepository serviceProviderSearchRepository) {
        this.serviceProviderRepository = serviceProviderRepository;
        this.serviceProviderMapper = serviceProviderMapper;
        this.serviceProviderSearchRepository = serviceProviderSearchRepository;
    }

    /**
     * Save a serviceProvider.
     *
     * @param serviceProviderDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceProviderDTO save(ServiceProviderDTO serviceProviderDTO) {
        log.debug("Request to save ServiceProvider : {}", serviceProviderDTO);
        ServiceProvider serviceProvider = serviceProviderMapper.toEntity(serviceProviderDTO);
        serviceProvider = serviceProviderRepository.save(serviceProvider);
        ServiceProviderDTO result = serviceProviderMapper.toDto(serviceProvider);
        serviceProviderSearchRepository.save(serviceProvider);
        return result;
    }

    /**
     * Get all the serviceProviders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceProviderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceProviders");
        return serviceProviderRepository.findAll(pageable)
            .map(serviceProviderMapper::toDto);
    }


    /**
     * Get one serviceProvider by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceProviderDTO> findOne(Long id) {
        log.debug("Request to get ServiceProvider : {}", id);
        return serviceProviderRepository.findById(id)
            .map(serviceProviderMapper::toDto);
    }

    /**
     * Delete the serviceProvider by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceProvider : {}", id);
        serviceProviderRepository.deleteById(id);
        serviceProviderSearchRepository.deleteById(id);
    }

    /**
     * Search for the serviceProvider corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceProviderDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ServiceProviders for query {}", query);
        return serviceProviderSearchRepository.search(queryStringQuery(query), pageable)
            .map(serviceProviderMapper::toDto);
    }
}
