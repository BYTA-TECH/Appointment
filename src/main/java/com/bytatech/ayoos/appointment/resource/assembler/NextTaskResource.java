package com.bytatech.ayoos.appointment.resource.assembler;

public class NextTaskResource {
	
	private String nextTaskId;
	private String nextTaskName;
	 
	private String processId;
	/**
	 * @return the nextTaskId
	 */
	public String getNextTaskId() {
		return nextTaskId;
	}
	/**
	 * @return the selfId
	 */
	 
	@Override
	public String toString() {
		return String.format(
				"CommandResource [nextTaskId=%s,\n nextTaskName=%s, \n processId=%s]",
				nextTaskId, nextTaskName,  processId);
	}
	/**
	 * @param nextTaskId the nextTaskId to set
	 */
	public void setNextTaskId(String nextTaskId) {
		this.nextTaskId = nextTaskId;
	}
	/**
	 * @return the nextTaskName
	 */
	public String getNextTaskName() {
		return nextTaskName;
	}
	/**
	 * @param nextTaskName the nextTaskName to set
	 */
	public void setNextTaskName(String nextTaskName) {
		this.nextTaskName = nextTaskName;
	}
 
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}

}
