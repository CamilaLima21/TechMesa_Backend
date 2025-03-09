package com.fiap.techmesa.application.enums;

public enum StatusReserveEnum {

	PENDING("0"), CONFIRMED("1"), CANCELLED("2");
	
	private final String code;
	
	private StatusReserveEnum(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
