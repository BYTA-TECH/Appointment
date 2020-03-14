package com.bytatech.ayoos.appointment.service;
  
import com.bytatech.ayoos.appointment.resource.assembler.NextTaskResource;
import com.bytatech.ayoos.appointment.service.dto.AppointmentDTO;

public interface AppointmentCommandService {
	public NextTaskResource initiate();

	public NextTaskResource chooseTime(String processId,AppointmentDTO appointmentDTO);

	public NextTaskResource paymentProcess(String processId,String paymentStatus);

	public NextTaskResource appointmentApproval(String processId,String approval);
}
