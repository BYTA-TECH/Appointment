package com.bytatech.ayoos.appointment.client.activiti.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ProcessDefinitionActionRequest
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-02-11T11:47:40.380+05:30[Asia/Colombo]")

public class ProcessDefinitionActionRequest   {
  @JsonProperty("action")
  private String action = null;

  @JsonProperty("includeProcessInstances")
  private Boolean includeProcessInstances = false;

  @JsonProperty("date")
  private OffsetDateTime date = null;

  @JsonProperty("category")
  private String category = null;

  public ProcessDefinitionActionRequest action(String action) {
    this.action = action;
    return this;
  }

  /**
   * Get action
   * @return action
  **/
  @ApiModelProperty(value = "")


  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public ProcessDefinitionActionRequest includeProcessInstances(Boolean includeProcessInstances) {
    this.includeProcessInstances = includeProcessInstances;
    return this;
  }

  /**
   * Whether or not to suspend/activate running process-instances for this process-definition. If omitted, the process-instances are left in the state they are
   * @return includeProcessInstances
  **/
  @ApiModelProperty(value = "Whether or not to suspend/activate running process-instances for this process-definition. If omitted, the process-instances are left in the state they are")


  public Boolean isIncludeProcessInstances() {
    return includeProcessInstances;
  }

  public void setIncludeProcessInstances(Boolean includeProcessInstances) {
    this.includeProcessInstances = includeProcessInstances;
  }

  public ProcessDefinitionActionRequest date(OffsetDateTime date) {
    this.date = date;
    return this;
  }

  /**
   * Date (ISO-8601) when the suspension/activation should be executed. If omitted, the suspend/activation is effective immediately.
   * @return date
  **/
  @ApiModelProperty(value = "Date (ISO-8601) when the suspension/activation should be executed. If omitted, the suspend/activation is effective immediately.")

  @Valid

  public OffsetDateTime getDate() {
    return date;
  }

  public void setDate(OffsetDateTime date) {
    this.date = date;
  }

  public ProcessDefinitionActionRequest category(String category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   * @return category
  **/
  @ApiModelProperty(value = "")


  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProcessDefinitionActionRequest processDefinitionActionRequest = (ProcessDefinitionActionRequest) o;
    return Objects.equals(this.action, processDefinitionActionRequest.action) &&
        Objects.equals(this.includeProcessInstances, processDefinitionActionRequest.includeProcessInstances) &&
        Objects.equals(this.date, processDefinitionActionRequest.date) &&
        Objects.equals(this.category, processDefinitionActionRequest.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(action, includeProcessInstances, date, category);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProcessDefinitionActionRequest {\n");
    
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    includeProcessInstances: ").append(toIndentedString(includeProcessInstances)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

