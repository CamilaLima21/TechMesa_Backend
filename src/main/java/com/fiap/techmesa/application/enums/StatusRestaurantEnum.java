package com.fiap.techmesa.application.enums;

public enum StatusRestaurantEnum {

	ACTIVE("0"),
	DEACTIVED("1");
	
	private final String code;
	
	private StatusRestaurantEnum(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
