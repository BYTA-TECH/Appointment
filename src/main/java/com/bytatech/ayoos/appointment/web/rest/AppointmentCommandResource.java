package com.bytatech.ayoos.appointment.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytatech.ayoos.appointment.service.AppointmentCommandService;
import com.bytatech.ayoos.appointment.domain.Slot;
import com.bytatech.ayoos.appointment.domain.SlotAndProvider;
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
	NextTaskResource task=appointmentCommandService.initiate();
	return task;
}
@PostMapping("/chooseTime/{processId}")
public NextTaskResource chooseTime(@PathVariable String processId ,@RequestBody SlotAndProvider slotAndProvider  ) {
	NextTaskResource task=appointmentCommandService.chooseTime(processId ,slotAndProvider);
	return task;
}
@PostMapping("/paymentProcess/{processId}")
public NextTaskResource paymentProcess(@PathVariable String processId  ) {
	NextTaskResource task=appointmentCommandService.paymentProcess(processId );
	return task;
}
@PostMapping("/appointmentApproval/{approval}/{processId}")
public NextTaskResource appointmentApproval(@PathVariable String processId , @PathVariable String approval ) {
	NextTaskResource task=appointmentCommandService.appointmentApproval(processId,approval );
	return task;
}
}
