package com.fiap.techmesa.application.enums;

public enum StatusRestaurantEnum {

	ACTIVE("0"),
	SUSPENDED("1"),
	DELETED("2"), 
	OPEN("3");
	
	private final String code;
	
	private StatusRestaurantEnum(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
