package com.bytatech.ayoos.appointment.service.impl;

import com.bytatech.ayoos.appointment.service.AppointmentService;
import com.bytatech.ayoos.appointment.domain.Appointment;
import com.bytatech.ayoos.appointment.repository.AppointmentRepository;
import com.bytatech.ayoos.appointment.repository.search.AppointmentSearchRepository;
import com.bytatech.ayoos.appointment.service.dto.AppointmentDTO;
import com.bytatech.ayoos.appointment.service.mapper.AppointmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Appointment}.
 */
@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

	private final Logger log = LoggerFactory.getLogger(AppointmentServiceImpl.class);

	private final AppointmentRepository appointmentRepository;

	private final AppointmentMapper appointmentMapper;

	private final AppointmentSearchRepository appointmentSearchRepository;

	public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper,
			AppointmentSearchRepository appointmentSearchRepository) {
		this.appointmentRepository = appointmentRepository;
		this.appointmentMapper = appointmentMapper;
		this.appointmentSearchRepository = appointmentSearchRepository;
	}

	/**
	 * Save a appointment.
	 *
	 * @param appointmentDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public AppointmentDTO save(AppointmentDTO appointmentDTO) {
		log.debug("Request to save Appointment : {}", appointmentDTO);
		Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
		appointment = appointmentRepository.save(appointment);
		AppointmentDTO result = appointmentMapper.toDto(appointment);
		appointmentSearchRepository.save(appointment);
		return result;
	}

	/**
	 * Get all the appointments.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<AppointmentDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Appointments");
		return appointmentRepository.findAll(pageable).map(appointmentMapper::toDto);
	}

	/**
	 * Get one appointment by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<AppointmentDTO> findOne(Long id) {
		log.debug("Request to get Appointment : {}", id);
		return appointmentRepository.findById(id).map(appointmentMapper::toDto);
	}

	/**
	 * Delete the appointment by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Appointment : {}", id);
		appointmentRepository.deleteById(id);
		appointmentSearchRepository.deleteById(id);
	}

	/**
	 * Search for the appointment corresponding to the query.
	 *
	 * @param query    the query of the search.
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<AppointmentDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Appointments for query {}", query);
		return appointmentSearchRepository.search(queryStringQuery(query), pageable).map(appointmentMapper::toDto);
	}

	/**
	 * @author ajay
	 * 
	 * @return the appointmentDto with given appointmentId
	 */
	public AppointmentDTO findByAppointmentId(String appointmentId) {
		// TODO Auto-generated method stub
		Appointment resultantAppointment = null;
		List<Appointment> appointmentList = appointmentRepository.findAll();
		for (Appointment appointment : appointmentList) {
			 if (appointment.getAppointmentId() == Long.parseLong(appointmentId)) {
				resultantAppointment = appointment;
			}
		}
		return appointmentMapper.toDto(resultantAppointment);
	}

}
