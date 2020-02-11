package com.bytatech.ayoos.appointment.client.activiti.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * MembershipResponse
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-02-11T11:47:40.380+05:30[Asia/Colombo]")

public class MembershipResponse   {
  @JsonProperty("userId")
  private String userId = null;

  @JsonProperty("url")
  private String url = null;

  @JsonProperty("groupId")
  private String groupId = null;

  public MembershipResponse userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  **/
  @ApiModelProperty(value = "")


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public MembershipResponse url(String url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
  **/
  @ApiModelProperty(value = "")


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public MembershipResponse groupId(String groupId) {
    this.groupId = groupId;
    return this;
  }

  /**
   * Get groupId
   * @return groupId
  **/
  @ApiModelProperty(value = "")


  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MembershipResponse membershipResponse = (MembershipResponse) o;
    return Objects.equals(this.userId, membershipResponse.userId) &&
        Objects.equals(this.url, membershipResponse.url) &&
        Objects.equals(this.groupId, membershipResponse.groupId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, url, groupId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MembershipResponse {\n");
    
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    groupId: ").append(toIndentedString(groupId)).append("\n");
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

