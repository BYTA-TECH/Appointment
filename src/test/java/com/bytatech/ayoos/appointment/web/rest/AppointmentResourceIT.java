package com.bytatech.ayoos.appointment.web.rest;

import com.bytatech.ayoos.appointment.AppointmentApp;
import com.bytatech.ayoos.appointment.config.TestSecurityConfiguration;
import com.bytatech.ayoos.appointment.domain.Appointment;
import com.bytatech.ayoos.appointment.repository.AppointmentRepository;
import com.bytatech.ayoos.appointment.repository.search.AppointmentSearchRepository;
import com.bytatech.ayoos.appointment.service.AppointmentService;
import com.bytatech.ayoos.appointment.service.dto.AppointmentDTO;
import com.bytatech.ayoos.appointment.service.mapper.AppointmentMapper;
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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link AppointmentResource} REST controller.
 */
@SpringBootTest(classes = {AppointmentApp.class, TestSecurityConfiguration.class})
public class AppointmentResourceIT {

    private static final Long DEFAULT_APPOINTMENT_ID = 1L;
    private static final Long UPDATED_APPOINTMENT_ID = 2L;

    private static final String DEFAULT_SERVICE_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_PROVIDER = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_USER = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_USER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_FROM_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TO_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TO_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private AppointmentService appointmentService;

    /**
     * This repository is mocked in the com.bytatech.ayoos.appointment.repository.search test package.
     *
     * @see com.bytatech.ayoos.appointment.repository.search.AppointmentSearchRepositoryMockConfiguration
     */
    @Autowired
    private AppointmentSearchRepository mockAppointmentSearchRepository;

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

    private MockMvc restAppointmentMockMvc;

    private Appointment appointment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppointmentResource appointmentResource = new AppointmentResource(appointmentService);
        this.restAppointmentMockMvc = MockMvcBuilders.standaloneSetup(appointmentResource)
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
    public static Appointment createEntity(EntityManager em) {
        Appointment appointment = new Appointment()
            .appointmentId(DEFAULT_APPOINTMENT_ID)
            .serviceProvider(DEFAULT_SERVICE_PROVIDER)
            .serviceUser(DEFAULT_SERVICE_USER)
            .date(DEFAULT_DATE)
            .fromTime(DEFAULT_FROM_TIME)
            .toTime(DEFAULT_TO_TIME)
            .status(DEFAULT_STATUS);
        return appointment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appointment createUpdatedEntity(EntityManager em) {
        Appointment appointment = new Appointment()
            .appointmentId(UPDATED_APPOINTMENT_ID)
            .serviceProvider(UPDATED_SERVICE_PROVIDER)
            .serviceUser(UPDATED_SERVICE_USER)
            .date(UPDATED_DATE)
            .fromTime(UPDATED_FROM_TIME)
            .toTime(UPDATED_TO_TIME)
            .status(UPDATED_STATUS);
        return appointment;
    }

    @BeforeEach
    public void initTest() {
        appointment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppointment() throws Exception {
        int databaseSizeBeforeCreate = appointmentRepository.findAll().size();

        // Create the Appointment
        AppointmentDTO appointmentDTO = appointmentMapper.toDto(appointment);
        restAppointmentMockMvc.perform(post("/api/appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeCreate + 1);
        Appointment testAppointment = appointmentList.get(appointmentList.size() - 1);
        assertThat(testAppointment.getAppointmentId()).isEqualTo(DEFAULT_APPOINTMENT_ID);
        assertThat(testAppointment.getServiceProvider()).isEqualTo(DEFAULT_SERVICE_PROVIDER);
        assertThat(testAppointment.getServiceUser()).isEqualTo(DEFAULT_SERVICE_USER);
        assertThat(testAppointment.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAppointment.getFromTime()).isEqualTo(DEFAULT_FROM_TIME);
        assertThat(testAppointment.getToTime()).isEqualTo(DEFAULT_TO_TIME);
        assertThat(testAppointment.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Appointment in Elasticsearch
        verify(mockAppointmentSearchRepository, times(1)).save(testAppointment);
    }

    @Test
    @Transactional
    public void createAppointmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appointmentRepository.findAll().size();

        // Create the Appointment with an existing ID
        appointment.setId(1L);
        AppointmentDTO appointmentDTO = appointmentMapper.toDto(appointment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppointmentMockMvc.perform(post("/api/appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeCreate);

        // Validate the Appointment in Elasticsearch
        verify(mockAppointmentSearchRepository, times(0)).save(appointment);
    }


    @Test
    @Transactional
    public void getAllAppointments() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList
        restAppointmentMockMvc.perform(get("/api/appointments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appointment.getId().intValue())))
            .andExpect(jsonPath("$.[*].appointmentId").value(hasItem(DEFAULT_APPOINTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].serviceProvider").value(hasItem(DEFAULT_SERVICE_PROVIDER)))
            .andExpect(jsonPath("$.[*].serviceUser").value(hasItem(DEFAULT_SERVICE_USER)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fromTime").value(hasItem(DEFAULT_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].toTime").value(hasItem(DEFAULT_TO_TIME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getAppointment() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get the appointment
        restAppointmentMockMvc.perform(get("/api/appointments/{id}", appointment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appointment.getId().intValue()))
            .andExpect(jsonPath("$.appointmentId").value(DEFAULT_APPOINTMENT_ID.intValue()))
            .andExpect(jsonPath("$.serviceProvider").value(DEFAULT_SERVICE_PROVIDER))
            .andExpect(jsonPath("$.serviceUser").value(DEFAULT_SERVICE_USER))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.fromTime").value(DEFAULT_FROM_TIME.toString()))
            .andExpect(jsonPath("$.toTime").value(DEFAULT_TO_TIME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingAppointment() throws Exception {
        // Get the appointment
        restAppointmentMockMvc.perform(get("/api/appointments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppointment() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        int databaseSizeBeforeUpdate = appointmentRepository.findAll().size();

        // Update the appointment
        Appointment updatedAppointment = appointmentRepository.findById(appointment.getId()).get();
        // Disconnect from session so that the updates on updatedAppointment are not directly saved in db
        em.detach(updatedAppointment);
        updatedAppointment
            .appointmentId(UPDATED_APPOINTMENT_ID)
            .serviceProvider(UPDATED_SERVICE_PROVIDER)
            .serviceUser(UPDATED_SERVICE_USER)
            .date(UPDATED_DATE)
            .fromTime(UPDATED_FROM_TIME)
            .toTime(UPDATED_TO_TIME)
            .status(UPDATED_STATUS);
        AppointmentDTO appointmentDTO = appointmentMapper.toDto(updatedAppointment);

        restAppointmentMockMvc.perform(put("/api/appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentDTO)))
            .andExpect(status().isOk());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeUpdate);
        Appointment testAppointment = appointmentList.get(appointmentList.size() - 1);
        assertThat(testAppointment.getAppointmentId()).isEqualTo(UPDATED_APPOINTMENT_ID);
        assertThat(testAppointment.getServiceProvider()).isEqualTo(UPDATED_SERVICE_PROVIDER);
        assertThat(testAppointment.getServiceUser()).isEqualTo(UPDATED_SERVICE_USER);
        assertThat(testAppointment.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAppointment.getFromTime()).isEqualTo(UPDATED_FROM_TIME);
        assertThat(testAppointment.getToTime()).isEqualTo(UPDATED_TO_TIME);
        assertThat(testAppointment.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Appointment in Elasticsearch
        verify(mockAppointmentSearchRepository, times(1)).save(testAppointment);
    }

    @Test
    @Transactional
    public void updateNonExistingAppointment() throws Exception {
        int databaseSizeBeforeUpdate = appointmentRepository.findAll().size();

        // Create the Appointment
        AppointmentDTO appointmentDTO = appointmentMapper.toDto(appointment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppointmentMockMvc.perform(put("/api/appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Appointment in Elasticsearch
        verify(mockAppointmentSearchRepository, times(0)).save(appointment);
    }

    @Test
    @Transactional
    public void deleteAppointment() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        int databaseSizeBeforeDelete = appointmentRepository.findAll().size();

        // Delete the appointment
        restAppointmentMockMvc.perform(delete("/api/appointments/{id}", appointment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Appointment in Elasticsearch
        verify(mockAppointmentSearchRepository, times(1)).deleteById(appointment.getId());
    }

    @Test
    @Transactional
    public void searchAppointment() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);
        when(mockAppointmentSearchRepository.search(queryStringQuery("id:" + appointment.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(appointment), PageRequest.of(0, 1), 1));
        // Search the appointment
        restAppointmentMockMvc.perform(get("/api/_search/appointments?query=id:" + appointment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appointment.getId().intValue())))
            .andExpect(jsonPath("$.[*].appointmentId").value(hasItem(DEFAULT_APPOINTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].serviceProvider").value(hasItem(DEFAULT_SERVICE_PROVIDER)))
            .andExpect(jsonPath("$.[*].serviceUser").value(hasItem(DEFAULT_SERVICE_USER)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fromTime").value(hasItem(DEFAULT_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].toTime").value(hasItem(DEFAULT_TO_TIME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
}
