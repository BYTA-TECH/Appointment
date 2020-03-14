package com.bytatech.ayoos.appointment.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.bytatech.ayoos.appointment.client.activiti.model.RestVariable;
import com.bytatech.ayoos.appointment.client.activiti.model.SubmitFormRequest;
import com.bytatech.ayoos.appointment.domain.SessionStatus;
import com.bytatech.ayoos.appointment.client.activiti.api.FormsApi;
import com.bytatech.ayoos.appointment.client.activiti.api.HistoryApi;
import com.bytatech.ayoos.appointment.client.activiti.api.ProcessInstancesApi;
import com.bytatech.ayoos.appointment.client.activiti.api.TasksApi;
import com.bytatech.ayoos.appointment.client.activiti.model.DataResponse;
import com.bytatech.ayoos.appointment.client.activiti.model.FormDataResponse;
import com.bytatech.ayoos.appointment.client.activiti.model.HistoricProcessInstanceResponse;
import com.bytatech.ayoos.appointment.client.activiti.model.ProcessInstanceCreateRequest;
import com.bytatech.ayoos.appointment.client.activiti.model.ProcessInstanceResponse;
import com.bytatech.ayoos.appointment.client.activiti.model.RestFormProperty;
import com.bytatech.ayoos.appointment.resource.assembler.NextTaskAssembler;
import com.bytatech.ayoos.appointment.resource.assembler.NextTaskResource;
import com.bytatech.ayoos.appointment.service.AppointmentCommandService;
import com.bytatech.ayoos.appointment.service.dto.AppointmentDTO;

import io.swagger.annotations.ApiParam;

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
	@Autowired
	private AppointmentServiceImpl appointmentServiceImpl;
	@Autowired
	private HistoryApi historyApi;

	 
	String deploymentId = "Appointment:5:62504";

	@Override
	public NextTaskResource initiate() {
		log.info("into ====================initiate()");
		ProcessInstanceCreateRequest processInstanceCreateRequest = new ProcessInstanceCreateRequest();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		processInstanceCreateRequest.setProcessDefinitionId(deploymentId);
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
	public NextTaskResource chooseTime(String processId, AppointmentDTO appointmentDTO) {

		log.info("into ====================chooseTime()" + appointmentDTO.toString());
		NextTaskResource nextTaskResource = resourceAssembler.toResource(processId);
		String taskId = nextTaskResource.getNextTaskId();

		List<RestFormProperty> formProperties = new ArrayList<RestFormProperty>();
		SubmitFormRequest submitFormRequest = new SubmitFormRequest();
		submitFormRequest.setAction("completed");

		submitFormRequest.setTaskId(taskId);
		String settingStatus = getServiceProviderSetting(appointmentDTO.getServiceProvider());

		// provide the doctor's setting option to settingStatus
		// Generate a long appointment id
		Instant instant = Instant.now();
		long timeMillis = instant.toEpochMilli();
		appointmentDTO.setAppointmentId(timeMillis);
		appointmentDTO.setStatus(SessionStatus.PENDING.toString());
		appointmentServiceImpl.save(appointmentDTO);

		RestFormProperty checkUpStatusFormProperty = new RestFormProperty();
		checkUpStatusFormProperty.setId("settingStatus");
		checkUpStatusFormProperty.setName("settingStatus");
		checkUpStatusFormProperty.setType("String");
		checkUpStatusFormProperty.setReadable(true);
		checkUpStatusFormProperty.setValue(settingStatus);
		formProperties.add(checkUpStatusFormProperty);
		RestFormProperty appointmentIdProperty = new RestFormProperty();
		appointmentIdProperty.setId("appointmentId");
		appointmentIdProperty.setName("appointmentId");
		appointmentIdProperty.setType("String");
		appointmentIdProperty.setReadable(true);
		appointmentIdProperty.setValue(appointmentDTO.getAppointmentId().toString());
		formProperties.add(appointmentIdProperty);
		submitFormRequest.setProperties(formProperties);
		formsApi.submitForm(submitFormRequest);

		System.out.println("Task id=##" + nextTaskResource.getNextTaskId());
		nextTaskResource = resourceAssembler.toResource(processId);

		return nextTaskResource;

	}

	// Get doctor setting from doctor idpcode
	private String getServiceProviderSetting(String serviceProvider) {
		// TODO Auto-generated method stub
		String setting = "AUTOMATIC";
		return setting;
	}

	@Override
	public NextTaskResource paymentProcess(String processId, String paymentStatus) {

		String appointmentId = getAppointmentId(processId);

		/*
		 * get appointment with given appointment id update it's status
		 */
		AppointmentDTO appointmentDTO = appointmentServiceImpl.findByAppointmentId(appointmentId);
		
		if (paymentStatus.equals("SUCESS"))
			appointmentDTO.setStatus(SessionStatus.RESERVED.toString());
		else
			appointmentDTO.setStatus(SessionStatus.CANCELLED.toString());
		appointmentServiceImpl.save(appointmentDTO);
		List<RestFormProperty> formProperties = new ArrayList<RestFormProperty>();
		SubmitFormRequest submitFormRequest = new SubmitFormRequest();
		submitFormRequest.setAction("completed");
		NextTaskResource nextTaskResource = resourceAssembler.toResource(processId);
		submitFormRequest.setTaskId(nextTaskResource.getNextTaskId());

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
	public NextTaskResource appointmentApproval(String processId, String approval) {
		log.info("into ====================appointmentApproval()" + approval);
		List<RestFormProperty> formProperties = new ArrayList<RestFormProperty>();
		SubmitFormRequest submitFormRequest = new SubmitFormRequest();
		submitFormRequest.setAction("completed");
		NextTaskResource nextTaskResource = resourceAssembler.toResource(processId);
		submitFormRequest.setTaskId(nextTaskResource.getNextTaskId());

		// provide the doctor's approvalStatus
		RestFormProperty approvalStatusFormProperty = new RestFormProperty();
		approvalStatusFormProperty.setId("approvalStatus");
		approvalStatusFormProperty.setName("approvalStatus");
		approvalStatusFormProperty.setType("String");
		approvalStatusFormProperty.setReadable(true);
		approvalStatusFormProperty.setValue(approval);
		formProperties.add(approvalStatusFormProperty);

		submitFormRequest.setProperties(formProperties);
		formsApi.submitForm(submitFormRequest);

		System.out.println("Task id=##" + nextTaskResource.getNextTaskId());
		nextTaskResource = resourceAssembler.toResource(processId);
		return nextTaskResource;
	}

	public ResponseEntity<DataResponse> getHistoricTaskusingProcessIdAndName(String processId, String name) {

		return historyApi.listHistoricTaskInstances(null, processId, null, null, deploymentId, null, null, null, null,
				null, null, name, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

	}

	public String getAppointmentId(String processId) {

		List<LinkedHashMap<String, String>> taskResponseCollectInfo = (List<LinkedHashMap<String, String>>) getHistoricTaskusingProcessIdAndName(
				processId, "Choose Time").getBody().getData();
		String oldTaskId = taskResponseCollectInfo.get(0).get("id");
		ResponseEntity<DataResponse> requestDetails = historyApi.getHistoricDetailInfo(null, processId, null, null,
				oldTaskId, true, false);
		List<LinkedHashMap<String, String>> requestFormProperties = (List<LinkedHashMap<String, String>>) requestDetails
				.getBody().getData();
		String propertyId = null, source = null;
		for (LinkedHashMap<String, String> requestMap : requestFormProperties) {
			propertyId = requestMap.get("propertyId");
			if (propertyId.equals("appointmentid")) {
				source = requestMap.get("propertyValue");
			}
		}
		return source;
	}
}
