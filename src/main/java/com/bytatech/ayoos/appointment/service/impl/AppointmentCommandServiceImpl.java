package com.bytatech.ayoos.appointment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bytatech.ayoos.appointment.client.activiti.model.RestVariable;
import com.bytatech.ayoos.appointment.client.activiti.model.SubmitFormRequest;
import com.bytatech.ayoos.appointment.domain.SlotAndProvider;
import com.bytatech.ayoos.appointment.client.activiti.api.FormsApi;
import com.bytatech.ayoos.appointment.client.activiti.api.ProcessInstancesApi;
import com.bytatech.ayoos.appointment.client.activiti.model.ProcessInstanceCreateRequest;
import com.bytatech.ayoos.appointment.client.activiti.model.ProcessInstanceResponse;
import com.bytatech.ayoos.appointment.client.activiti.model.RestFormProperty;
import com.bytatech.ayoos.appointment.resource.assembler.NextTaskAssembler;
import com.bytatech.ayoos.appointment.resource.assembler.NextTaskResource;
import com.bytatech.ayoos.appointment.service.AppointmentCommandService;

@Service
@Transactional
public class AppointmentCommandServiceImpl implements AppointmentCommandService {
	private final Logger log = LoggerFactory.getLogger(AppointmentCommandServiceImpl.class);
	@Autowired
	private ProcessInstancesApi processInstanceApi;
	@Autowired
	private NextTaskAssembler resourceAssembler;
	@Autowired
	private FormsApi formsApi;

	@Override
	public NextTaskResource initiate() {
		log.info("into ====================initiate()");
		ProcessInstanceCreateRequest processInstanceCreateRequest = new ProcessInstanceCreateRequest();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		processInstanceCreateRequest.setProcessDefinitionId("Appointment:1:52504");
		log.info("*****************************************************"
				+ processInstanceCreateRequest.getProcessDefinitionId());
		RestVariable driverRestVariable = new RestVariable();
		driverRestVariable.setName("doctor");
		driverRestVariable.setType("string");
		driverRestVariable.setValue("doctor");
		RestVariable driverRestVariable2 = new RestVariable();
		driverRestVariable2.setName("patient");
		driverRestVariable2.setType("string");
		driverRestVariable2.setValue("patient");
		variables.add(driverRestVariable);
		variables.add(driverRestVariable2);
		processInstanceCreateRequest.setVariables(variables);
		ResponseEntity<ProcessInstanceResponse> processInstanceResponse = processInstanceApi
				.createProcessInstance(processInstanceCreateRequest);
		String processInstanceId = processInstanceResponse.getBody().getId();
		String processDefinitionId = processInstanceCreateRequest.getProcessDefinitionId();
		log.info("++++++++++++++++processDefinitionId++++++++++++++++++" + processDefinitionId);
		log.info("++++++++++++++++ProcessInstanceId is+++++++++++++ " + processInstanceId);

		processInstanceApi.createProcessInstance(processInstanceCreateRequest);
		NextTaskResource nextTaskResource = resourceAssembler.toResource(processInstanceId);
		return nextTaskResource;

	}

	@Override
	public NextTaskResource chooseTime(String processId,SlotAndProvider slotAndProvider) {
		log.info("into ====================chooseTime()");
		List<RestFormProperty> formProperties = new ArrayList<RestFormProperty>();
		SubmitFormRequest submitFormRequest = new SubmitFormRequest();
		submitFormRequest.setAction("completed");
		NextTaskResource nextTaskResource = resourceAssembler.toResource(processId);
		submitFormRequest.setTaskId(nextTaskResource.getNextTaskId());
        String settingStatus="manual";
        //provide the doctor's setting option to settingStatus
		RestFormProperty checkUpStatusFormProperty = new RestFormProperty();
		checkUpStatusFormProperty.setId("settingStatus");
		checkUpStatusFormProperty.setName("settingStatus");
		checkUpStatusFormProperty.setType("String");
		checkUpStatusFormProperty.setReadable(true);
		checkUpStatusFormProperty.setValue(settingStatus);
		formProperties.add(checkUpStatusFormProperty);

		submitFormRequest.setProperties(formProperties);
		formsApi.submitForm(submitFormRequest);
 
		System.out.println("Task id=##" + nextTaskResource.getNextTaskId());
		nextTaskResource = resourceAssembler.toResource(processId);
		return nextTaskResource;
 
	}

	@Override
	public NextTaskResource paymentProcess(String processId) {
		log.info("into ====================paymentProcess()");
		List<RestFormProperty> formProperties = new ArrayList<RestFormProperty>();
		SubmitFormRequest submitFormRequest = new SubmitFormRequest();
		submitFormRequest.setAction("completed");
		NextTaskResource nextTaskResource = resourceAssembler.toResource(processId);
		submitFormRequest.setTaskId(nextTaskResource.getNextTaskId());
        String paymentStatus="false";
		RestFormProperty checkUpStatusFormProperty = new RestFormProperty();
		checkUpStatusFormProperty.setId("paymentStatus");
		checkUpStatusFormProperty.setName("paymentStatus");
		checkUpStatusFormProperty.setType("String");
		checkUpStatusFormProperty.setReadable(true);
		checkUpStatusFormProperty.setValue(paymentStatus);
		formProperties.add(checkUpStatusFormProperty);

		submitFormRequest.setProperties(formProperties);
		formsApi.submitForm(submitFormRequest);
 
		System.out.println("Task id=##" + nextTaskResource.getNextTaskId());
		nextTaskResource = resourceAssembler.toResource(processId);
		return nextTaskResource;
	}

	@Override
	public NextTaskResource appointmentApproval(String processId,String approval) {
		log.info("into ====================appointmentApproval()");
		List<RestFormProperty> formProperties = new ArrayList<RestFormProperty>();
		SubmitFormRequest submitFormRequest = new SubmitFormRequest();
		submitFormRequest.setAction("completed");
		NextTaskResource nextTaskResource = resourceAssembler.toResource(processId);
		submitFormRequest.setTaskId(nextTaskResource.getNextTaskId());
        String approvalStatus=approval;
        //provide the doctor's  approvalStatus
		RestFormProperty checkUpStatusFormProperty = new RestFormProperty();
		checkUpStatusFormProperty.setId("approvalStatus");
		checkUpStatusFormProperty.setName("approvalStatus");
		checkUpStatusFormProperty.setType("String");
		checkUpStatusFormProperty.setReadable(true);
		checkUpStatusFormProperty.setValue(approvalStatus);
		formProperties.add(checkUpStatusFormProperty);

		submitFormRequest.setProperties(formProperties);
		formsApi.submitForm(submitFormRequest);
 
		System.out.println("Task id=##" + nextTaskResource.getNextTaskId());
		nextTaskResource = resourceAssembler.toResource(processId);
		return nextTaskResource;
	}

 

}
