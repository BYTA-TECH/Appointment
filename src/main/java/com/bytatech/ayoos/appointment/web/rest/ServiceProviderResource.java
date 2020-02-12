package com.bytatech.ayoos.appointment.web.rest;

import com.bytatech.ayoos.appointment.service.ServiceProviderService;
import com.bytatech.ayoos.appointment.web.rest.errors.BadRequestAlertException;
import com.bytatech.ayoos.appointment.service.dto.ServiceProviderDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.bytatech.ayoos.appointment.domain.ServiceProvider}.
 */
@RestController
@RequestMapping("/api")
public class ServiceProviderResource {

    private final Logger log = LoggerFactory.getLogger(ServiceProviderResource.class);

    private static final String ENTITY_NAME = "appointmentServiceProvider";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceProviderService serviceProviderService;

    public ServiceProviderResource(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    /**
     * {@code POST  /service-providers} : Create a new serviceProvider.
     *
     * @param serviceProviderDTO the serviceProviderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceProviderDTO, or with status {@code 400 (Bad Request)} if the serviceProvider has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-providers")
    public ResponseEntity<ServiceProviderDTO> createServiceProvider(@RequestBody ServiceProviderDTO serviceProviderDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceProvider : {}", serviceProviderDTO);
        if (serviceProviderDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceProvider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceProviderDTO result = serviceProviderService.save(serviceProviderDTO);
        return ResponseEntity.created(new URI("/api/service-providers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-providers} : Updates an existing serviceProvider.
     *
     * @param serviceProviderDTO the serviceProviderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceProviderDTO,
     * or with status {@code 400 (Bad Request)} if the serviceProviderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceProviderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-providers")
    public ResponseEntity<ServiceProviderDTO> updateServiceProvider(@RequestBody ServiceProviderDTO serviceProviderDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceProvider : {}", serviceProviderDTO);
        if (serviceProviderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceProviderDTO result = serviceProviderService.save(serviceProviderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceProviderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-providers} : get all the serviceProviders.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceProviders in body.
     */
    @GetMapping("/service-providers")
    public ResponseEntity<List<ServiceProviderDTO>> getAllServiceProviders(Pageable pageable) {
        log.debug("REST request to get a page of ServiceProviders");
        Page<ServiceProviderDTO> page = serviceProviderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-providers/:id} : get the "id" serviceProvider.
     *
     * @param id the id of the serviceProviderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceProviderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-providers/{id}")
    public ResponseEntity<ServiceProviderDTO> getServiceProvider(@PathVariable Long id) {
        log.debug("REST request to get ServiceProvider : {}", id);
        Optional<ServiceProviderDTO> serviceProviderDTO = serviceProviderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceProviderDTO);
    }

    /**
     * {@code DELETE  /service-providers/:id} : delete the "id" serviceProvider.
     *
     * @param id the id of the serviceProviderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-providers/{id}")
    public ResponseEntity<Void> deleteServiceProvider(@PathVariable Long id) {
        log.debug("REST request to delete ServiceProvider : {}", id);
        serviceProviderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/service-providers?query=:query} : search for the serviceProvider corresponding
     * to the query.
     *
     * @param query the query of the serviceProvider search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/service-providers")
    public ResponseEntity<List<ServiceProviderDTO>> searchServiceProviders(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ServiceProviders for query {}", query);
        Page<ServiceProviderDTO> page = serviceProviderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
