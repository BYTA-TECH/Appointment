package com.bytatech.ayoos.appointment.web.rest;

import com.bytatech.ayoos.appointment.service.ServiceUserService;
import com.bytatech.ayoos.appointment.web.rest.errors.BadRequestAlertException;
import com.bytatech.ayoos.appointment.service.dto.ServiceUserDTO;

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
 * REST controller for managing {@link com.bytatech.ayoos.appointment.domain.ServiceUser}.
 */
@RestController
@RequestMapping("/api")
public class ServiceUserResource {

    private final Logger log = LoggerFactory.getLogger(ServiceUserResource.class);

    private static final String ENTITY_NAME = "appointmentServiceUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceUserService serviceUserService;

    public ServiceUserResource(ServiceUserService serviceUserService) {
        this.serviceUserService = serviceUserService;
    }

    /**
     * {@code POST  /service-users} : Create a new serviceUser.
     *
     * @param serviceUserDTO the serviceUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceUserDTO, or with status {@code 400 (Bad Request)} if the serviceUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-users")
    public ResponseEntity<ServiceUserDTO> createServiceUser(@RequestBody ServiceUserDTO serviceUserDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceUser : {}", serviceUserDTO);
        if (serviceUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceUserDTO result = serviceUserService.save(serviceUserDTO);
        return ResponseEntity.created(new URI("/api/service-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-users} : Updates an existing serviceUser.
     *
     * @param serviceUserDTO the serviceUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceUserDTO,
     * or with status {@code 400 (Bad Request)} if the serviceUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-users")
    public ResponseEntity<ServiceUserDTO> updateServiceUser(@RequestBody ServiceUserDTO serviceUserDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceUser : {}", serviceUserDTO);
        if (serviceUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceUserDTO result = serviceUserService.save(serviceUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-users} : get all the serviceUsers.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceUsers in body.
     */
    @GetMapping("/service-users")
    public ResponseEntity<List<ServiceUserDTO>> getAllServiceUsers(Pageable pageable) {
        log.debug("REST request to get a page of ServiceUsers");
        Page<ServiceUserDTO> page = serviceUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-users/:id} : get the "id" serviceUser.
     *
     * @param id the id of the serviceUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-users/{id}")
    public ResponseEntity<ServiceUserDTO> getServiceUser(@PathVariable Long id) {
        log.debug("REST request to get ServiceUser : {}", id);
        Optional<ServiceUserDTO> serviceUserDTO = serviceUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceUserDTO);
    }

    /**
     * {@code DELETE  /service-users/:id} : delete the "id" serviceUser.
     *
     * @param id the id of the serviceUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-users/{id}")
    public ResponseEntity<Void> deleteServiceUser(@PathVariable Long id) {
        log.debug("REST request to delete ServiceUser : {}", id);
        serviceUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/service-users?query=:query} : search for the serviceUser corresponding
     * to the query.
     *
     * @param query the query of the serviceUser search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/service-users")
    public ResponseEntity<List<ServiceUserDTO>> searchServiceUsers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ServiceUsers for query {}", query);
        Page<ServiceUserDTO> page = serviceUserService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
