/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (3.0.0-SNAPSHOT).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.bytatech.ayoos.appointment.client.activiti.api;

import com.bytatech.ayoos.appointment.client.activiti.model.SignalEventReceivedRequest;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-02-11T11:47:40.380+05:30[Asia/Colombo]")

@Api(value = "Runtime", description = "the Runtime API")
public interface RuntimeApi {

    @ApiOperation(value = "Signal event received", nickname = "signalEventReceived", notes = "", authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Runtime", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Indicated signal has been processed and no errors occurred."),
        @ApiResponse(code = 202, message = "Indicated signal processing is queued as a job, ready to be executed."),
        @ApiResponse(code = 400, message = "Signal not processed. The signal name is missing or variables are used together with async, which is not allowed. Response body contains additional information about the error.") })
    @RequestMapping(value = "/runtime/signals",
        method = RequestMethod.POST)
    ResponseEntity<Void> signalEventReceived(@ApiParam(value = ""  )  @Valid @RequestBody SignalEventReceivedRequest signalEventReceivedRequest);

}
