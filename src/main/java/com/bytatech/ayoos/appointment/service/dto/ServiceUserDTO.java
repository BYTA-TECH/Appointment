package com.bytatech.ayoos.appointment.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bytatech.ayoos.appointment.domain.ServiceUser} entity.
 */
public class ServiceUserDTO implements Serializable {

    private Long id;

    private String idpCode;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceUserDTO serviceUserDTO = (ServiceUserDTO) o;
        if (serviceUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceUserDTO{" +
            "id=" + getId() +
            ", idpCode='" + getIdpCode() + "'" +
            "}";
    }
}
