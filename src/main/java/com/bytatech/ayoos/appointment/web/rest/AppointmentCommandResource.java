package com.bytatech.ayoos.appointment.web.rest;

import org.springframework.beans.factory.annotation.Autowired; 

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytatech.ayoos.appointment.service.AppointmentCommandService;
import com.bytatech.ayoos.appointment.service.dto.AppointmentDTO;
import com.bytatech.ayoos.appointment.resource.assembler.NextTaskResource;

@RestController
@RequestMapping("/api")
public class AppointmentCommandResource {

	@Autowired
	private final AppointmentCommandService appointmentCommandService;

	public AppointmentCommandResource(AppointmentCommandService appointmentCommandService) {
		this.appointmentCommandService = appointmentCommandService;
	}

	@PostMapping("/initiate")
	public NextTaskResource initiateConsultation() {
		NextTaskResource task = appointmentCommandService.initiate();
		return task;
	}

	@PostMapping("/chooseTime/{processId}")
	public NextTaskResource chooseTime(@PathVariable String processId, @RequestBody AppointmentDTO appointmentDTO) {
		NextTaskResource task = appointmentCommandService.chooseTime(processId, appointmentDTO);
		return task;
	}

	@PostMapping("/paymentProcess/{paymentStatus}/{processId}")
	public NextTaskResource paymentProcess(@PathVariable String processId ,@PathVariable String paymentStatus) {
		NextTaskResource task = appointmentCommandService.paymentProcess(processId,paymentStatus);
		return task;
	}

	@PostMapping("/appointmentApproval/{approval}/{processId}")
	public NextTaskResource appointmentApproval(@PathVariable String processId, @PathVariable String approval) {
		NextTaskResource task = appointmentCommandService.appointmentApproval(processId, approval);
		return task;
	}
}
