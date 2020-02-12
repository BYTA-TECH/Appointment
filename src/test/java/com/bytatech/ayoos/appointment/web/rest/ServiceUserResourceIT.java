package com.bytatech.ayoos.appointment.web.rest;

import com.bytatech.ayoos.appointment.AppointmentApp;
import com.bytatech.ayoos.appointment.config.TestSecurityConfiguration;
import com.bytatech.ayoos.appointment.domain.ServiceUser;
import com.bytatech.ayoos.appointment.repository.ServiceUserRepository;
import com.bytatech.ayoos.appointment.repository.search.ServiceUserSearchRepository;
import com.bytatech.ayoos.appointment.service.ServiceUserService;
import com.bytatech.ayoos.appointment.service.dto.ServiceUserDTO;
import com.bytatech.ayoos.appointment.service.mapper.ServiceUserMapper;
import com.bytatech.ayoos.appointment.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.bytatech.ayoos.appointment.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ServiceUserResource} REST controller.
 */
@SpringBootTest(classes = {AppointmentApp.class, TestSecurityConfiguration.class})
public class ServiceUserResourceIT {

    private static final String DEFAULT_IDP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IDP_CODE = "BBBBBBBBBB";

    @Autowired
    private ServiceUserRepository serviceUserRepository;

    @Autowired
    private ServiceUserMapper serviceUserMapper;

    @Autowired
    private ServiceUserService serviceUserService;

    /**
     * This repository is mocked in the com.bytatech.ayoos.appointment.repository.search test package.
     *
     * @see com.bytatech.ayoos.appointment.repository.search.ServiceUserSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServiceUserSearchRepository mockServiceUserSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restServiceUserMockMvc;

    private ServiceUser serviceUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceUserResource serviceUserResource = new ServiceUserResource(serviceUserService);
        this.restServiceUserMockMvc = MockMvcBuilders.standaloneSetup(serviceUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceUser createEntity(EntityManager em) {
        ServiceUser serviceUser = new ServiceUser()
            .idpCode(DEFAULT_IDP_CODE);
        return serviceUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceUser createUpdatedEntity(EntityManager em) {
        ServiceUser serviceUser = new ServiceUser()
            .idpCode(UPDATED_IDP_CODE);
        return serviceUser;
    }

    @BeforeEach
    public void initTest() {
        serviceUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceUser() throws Exception {
        int databaseSizeBeforeCreate = serviceUserRepository.findAll().size();

        // Create the ServiceUser
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);
        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceUser in the database
        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceUser testServiceUser = serviceUserList.get(serviceUserList.size() - 1);
        assertThat(testServiceUser.getIdpCode()).isEqualTo(DEFAULT_IDP_CODE);

        // Validate the ServiceUser in Elasticsearch
        verify(mockServiceUserSearchRepository, times(1)).save(testServiceUser);
    }

    @Test
    @Transactional
    public void createServiceUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceUserRepository.findAll().size();

        // Create the ServiceUser with an existing ID
        serviceUser.setId(1L);
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceUserMockMvc.perform(post("/api/service-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceUser in the database
        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeCreate);

        // Validate the ServiceUser in Elasticsearch
        verify(mockServiceUserSearchRepository, times(0)).save(serviceUser);
    }


    @Test
    @Transactional
    public void getAllServiceUsers() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get all the serviceUserList
        restServiceUserMockMvc.perform(get("/api/service-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].idpCode").value(hasItem(DEFAULT_IDP_CODE)));
    }
    
    @Test
    @Transactional
    public void getServiceUser() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        // Get the serviceUser
        restServiceUserMockMvc.perform(get("/api/service-users/{id}", serviceUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceUser.getId().intValue()))
            .andExpect(jsonPath("$.idpCode").value(DEFAULT_IDP_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingServiceUser() throws Exception {
        // Get the serviceUser
        restServiceUserMockMvc.perform(get("/api/service-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceUser() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        int databaseSizeBeforeUpdate = serviceUserRepository.findAll().size();

        // Update the serviceUser
        ServiceUser updatedServiceUser = serviceUserRepository.findById(serviceUser.getId()).get();
        // Disconnect from session so that the updates on updatedServiceUser are not directly saved in db
        em.detach(updatedServiceUser);
        updatedServiceUser
            .idpCode(UPDATED_IDP_CODE);
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(updatedServiceUser);

        restServiceUserMockMvc.perform(put("/api/service-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceUser in the database
        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeUpdate);
        ServiceUser testServiceUser = serviceUserList.get(serviceUserList.size() - 1);
        assertThat(testServiceUser.getIdpCode()).isEqualTo(UPDATED_IDP_CODE);

        // Validate the ServiceUser in Elasticsearch
        verify(mockServiceUserSearchRepository, times(1)).save(testServiceUser);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceUser() throws Exception {
        int databaseSizeBeforeUpdate = serviceUserRepository.findAll().size();

        // Create the ServiceUser
        ServiceUserDTO serviceUserDTO = serviceUserMapper.toDto(serviceUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceUserMockMvc.perform(put("/api/service-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceUser in the database
        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ServiceUser in Elasticsearch
        verify(mockServiceUserSearchRepository, times(0)).save(serviceUser);
    }

    @Test
    @Transactional
    public void deleteServiceUser() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);

        int databaseSizeBeforeDelete = serviceUserRepository.findAll().size();

        // Delete the serviceUser
        restServiceUserMockMvc.perform(delete("/api/service-users/{id}", serviceUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceUser> serviceUserList = serviceUserRepository.findAll();
        assertThat(serviceUserList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ServiceUser in Elasticsearch
        verify(mockServiceUserSearchRepository, times(1)).deleteById(serviceUser.getId());
    }

    @Test
    @Transactional
    public void searchServiceUser() throws Exception {
        // Initialize the database
        serviceUserRepository.saveAndFlush(serviceUser);
        when(mockServiceUserSearchRepository.search(queryStringQuery("id:" + serviceUser.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(serviceUser), PageRequest.of(0, 1), 1));
        // Search the serviceUser
        restServiceUserMockMvc.perform(get("/api/_search/service-users?query=id:" + serviceUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].idpCode").value(hasItem(DEFAULT_IDP_CODE)));
    }
}
