package com.bytatech.ayoos.appointment.client.activiti.model;

import java.util.Objects;
import com.bytatech.ayoos.appointment.client.activiti.model.RestVariable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TaskActionRequest
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-02-11T11:47:40.380+05:30[Asia/Colombo]")

public class TaskActionRequest   {
  @JsonProperty("action")
  private String action = null;

  @JsonProperty("assignee")
  private String assignee = null;

  @JsonProperty("variables")
  @Valid
  private List<RestVariable> variables = null;

  @JsonProperty("transientVariables")
  @Valid
  private List<RestVariable> transientVariables = null;

  public TaskActionRequest action(String action) {
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

  public TaskActionRequest assignee(String assignee) {
    this.assignee = assignee;
    return this;
  }

  /**
   * Get assignee
   * @return assignee
  **/
  @ApiModelProperty(value = "")


  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public TaskActionRequest variables(List<RestVariable> variables) {
    this.variables = variables;
    return this;
  }

  public TaskActionRequest addVariablesItem(RestVariable variablesItem) {
    if (this.variables == null) {
      this.variables = new ArrayList<RestVariable>();
    }
    this.variables.add(variablesItem);
    return this;
  }

  /**
   * Get variables
   * @return variables
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<RestVariable> getVariables() {
    return variables;
  }

  public void setVariables(List<RestVariable> variables) {
    this.variables = variables;
  }

  public TaskActionRequest transientVariables(List<RestVariable> transientVariables) {
    this.transientVariables = transientVariables;
    return this;
  }

  public TaskActionRequest addTransientVariablesItem(RestVariable transientVariablesItem) {
    if (this.transientVariables == null) {
      this.transientVariables = new ArrayList<RestVariable>();
    }
    this.transientVariables.add(transientVariablesItem);
    return this;
  }

  /**
   * Get transientVariables
   * @return transientVariables
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<RestVariable> getTransientVariables() {
    return transientVariables;
  }

  public void setTransientVariables(List<RestVariable> transientVariables) {
    this.transientVariables = transientVariables;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskActionRequest taskActionRequest = (TaskActionRequest) o;
    return Objects.equals(this.action, taskActionRequest.action) &&
        Objects.equals(this.assignee, taskActionRequest.assignee) &&
        Objects.equals(this.variables, taskActionRequest.variables) &&
        Objects.equals(this.transientVariables, taskActionRequest.transientVariables);
  }

  @Override
  public int hashCode() {
    return Objects.hash(action, assignee, variables, transientVariables);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskActionRequest {\n");
    
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    assignee: ").append(toIndentedString(assignee)).append("\n");
    sb.append("    variables: ").append(toIndentedString(variables)).append("\n");
    sb.append("    transientVariables: ").append(toIndentedString(transientVariables)).append("\n");
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

