package com.fiap.techmesa.application.enums;

public enum StatusTableOccupationEnum {

	AVAILABLE("AVAILABLE"),
	BUSY("BUSY");
	
	private final String status;
	
	StatusTableOccupationEnum(String status) {
		this.status = status;
	}
}
