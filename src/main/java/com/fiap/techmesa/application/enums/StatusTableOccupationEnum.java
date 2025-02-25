package com.fiap.techmesa.application.enums;

public enum StatusTableOccupationEnum {

	AVAILABLE("AVAILABLE"),
	BUSY("BUSY"), OCCUPIED("OCCUPIED");
	
	private final String status;
	
	StatusTableOccupationEnum(String status) {
		this.status = status;
	}
}
