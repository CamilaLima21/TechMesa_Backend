package com.fiap.techmesa.application.enums;

public enum TypeKitchenEnum {

	JAPANESE("JP"),
	ITALIAN("IT"),
	CHINESE("CN"),
	BRAZILIAN("BR"),
	INDIAN("IN"),
	THAI("TH"),
	PERUVIAN("PE"),
	FRENCH("FR"),
	ORIENTAL("ORI"),
	MEDITERRANEAN("MED"),
	INTERNATIONAL("INT"),
	OTHERS("OTH");
	
	private final String acronym;
	
	TypeKitchenEnum(String acronym) {
		this.acronym = acronym;
	}
	
	public String getAcronym() {
		return acronym;
	}
}
