package com.bytatech.ayoos.appointment.client.activiti.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.bytatech.ayoos.appointment.client.activiti.ClientConfiguration;

@FeignClient(name="${activiti.name:activiti}", url="${activiti.url:http://localhost:8080/activiti-rest/service}", configuration = ClientConfiguration.class)
public interface JobsApiClient extends JobsApi {
}