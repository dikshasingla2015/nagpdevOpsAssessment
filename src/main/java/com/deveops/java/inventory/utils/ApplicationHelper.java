package com.deveops.java.inventory.utils;

import com.deveops.java.inventory.dto.InventoryBEObjectBaseResponse;;

public class ApplicationHelper {

	private ApplicationHelper() {

	}

	public static InventoryBEObjectBaseResponse getSuccessResponse(final Object msg) {
		InventoryBEObjectBaseResponse response = new InventoryBEObjectBaseResponse();
		response.setError(Boolean.FALSE);
		response.setCode("200");
		response.setData(msg);
		return response;
	}

	public static InventoryBEObjectBaseResponse getErrorResponse(String errorMessage) {
		InventoryBEObjectBaseResponse response = new InventoryBEObjectBaseResponse();
		response.setError(Boolean.TRUE);
		response.setCode("101");
		response.setErrorMessage(errorMessage);
		return response;
	}

}
