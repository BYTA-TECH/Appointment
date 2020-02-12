package com.bytatech.ayoos.appointment.resource.assembler;

import java.util.LinkedHashMap;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bytatech.ayoos.appointment.client.activiti.api.TasksApi;
import com.bytatech.ayoos.appointment.client.activiti.model.DataResponse;

import org.springframework.http.ResponseEntity; 
 

@Component
public class NextTaskAssembler {

	@Autowired
	TasksApi tasksApi;

	public NextTaskResource toResource(String processInstanceId) {
		@SuppressWarnings("unchecked")
		ResponseEntity<DataResponse> taskResponse=tasksApi.getTasks(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, processInstanceId, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);    
 	
 	String taskId=null,taskName=null;
		if (!(taskResponse.equals(null))) {
			 taskId = ((List<LinkedHashMap<String, String>>) taskResponse.getBody().getData()).get(0).get("id");
			  taskName = ((List<LinkedHashMap<String, String>>) taskResponse.getBody().getData()).get(0).get("name");
			  
		}
		NextTaskResource nextTaskResource = new NextTaskResource();
		nextTaskResource.setNextTaskId(taskId);
		nextTaskResource.setNextTaskName(taskName);
		nextTaskResource.setProcessId(processInstanceId);
		return nextTaskResource;
	}

}
