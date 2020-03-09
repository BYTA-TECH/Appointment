package com.bytatech.ayoos.appointment.domain;

import com.bytatech.ayoos.appointment.service.dto.ServiceProviderDTO;
import com.bytatech.ayoos.appointment.service.dto.ServiceUserDTO;

public class SlotDetail {

	private Slot slot;
    private ServiceProviderDTO serviceProvider;
    private ServiceUserDTO serviceUser;
    
    public ServiceProviderDTO getProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProviderDTO serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

	public ServiceUserDTO getServiceUser() {
		return serviceUser;
	}

	public void setServiceUser(ServiceUserDTO serviceUser) {
		this.serviceUser = serviceUser;
	}
}
