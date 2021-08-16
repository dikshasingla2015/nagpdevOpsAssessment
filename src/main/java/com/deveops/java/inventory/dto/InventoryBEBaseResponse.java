package com.deveops.java.inventory.dto;

/**
 * 
 * @author dikshasingla
 *
 */
public abstract class InventoryBEBaseResponse implements DomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3414309655411886020L;

	private boolean error;
	private String code;
	private String errorMessage;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
