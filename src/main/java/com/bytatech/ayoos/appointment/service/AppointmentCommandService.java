package com.bytatech.ayoos.appointment.service;
 
import com.bytatech.ayoos.appointment.domain.SlotDetail;
import com.bytatech.ayoos.appointment.resource.assembler.NextTaskResource;

public interface AppointmentCommandService {
	public NextTaskResource initiate();

	public NextTaskResource chooseTime(String processId,SlotDetail slotDetail);

	public NextTaskResource paymentProcess(String processId);

	public NextTaskResource appointmentApproval(String processId,String approval);
}
