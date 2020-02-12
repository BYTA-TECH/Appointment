package com.bytatech.ayoos.appointment.service;

import com.bytatech.ayoos.appointment.domain.SlotAndProvider;
import com.bytatech.ayoos.appointment.resource.assembler.NextTaskResource;

public interface AppointmentCommandService {
	public NextTaskResource initiate();

	public NextTaskResource chooseTime(String processId,SlotAndProvider slotAndProvider);

	public NextTaskResource paymentProcess(String processId);

	public NextTaskResource appointmentApproval(String processId,String approval);
}
