package com.bytatech.ayoos.appointment.domain;

public class SlotAndProvider {

	private Slot slot;
    private ServiceProvider serviceProvider;
     
    public ServiceProvider getProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}
}
