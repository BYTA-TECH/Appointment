package com.bytatech.ayoos.appointment.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bytatech.ayoos.appointment.domain.ServiceProvider} entity.
 */
public class ServiceProviderDTO implements Serializable {

    private Long id;

    private String idpCode;

    private String type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdpCode() {
        return idpCode;
    }

    public void setIdpCode(String idpCode) {
        this.idpCode = idpCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceProviderDTO serviceProviderDTO = (ServiceProviderDTO) o;
        if (serviceProviderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceProviderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceProviderDTO{" +
            "id=" + getId() +
            ", idpCode='" + getIdpCode() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
