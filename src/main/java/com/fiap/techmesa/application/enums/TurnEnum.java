package com.fiap.techmesa.application.enums;

public enum TurnEnum {

	COFEE("C"),
	LUNCH("L"),
	DINNER("D");
	
	private final String code;
	
	private TurnEnum(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
