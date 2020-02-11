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
 * UserInfoResponse
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-02-11T11:47:40.380+05:30[Asia/Colombo]")

public class UserInfoResponse   {
  @JsonProperty("key")
  private String key = null;

  @JsonProperty("value")
  private String value = null;

  @JsonProperty("url")
  private String url = null;

  public UserInfoResponse key(String key) {
    this.key = key;
    return this;
  }

  /**
   * Get key
   * @return key
  **/
  @ApiModelProperty(value = "")


  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public UserInfoResponse value(String value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
  **/
  @ApiModelProperty(value = "")


  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public UserInfoResponse url(String url) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInfoResponse userInfoResponse = (UserInfoResponse) o;
    return Objects.equals(this.key, userInfoResponse.key) &&
        Objects.equals(this.value, userInfoResponse.value) &&
        Objects.equals(this.url, userInfoResponse.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value, url);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInfoResponse {\n");
    
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
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

