package com.fiap.techmesa.application.enums;

public enum DayWeekEnum {

	SUNDAY(1),
	MONDAY(2),
	TUESDAY(3),
	WEDNESDAY(4),
	THURSDAY(5),
	FRIDAY(6),
	SATURDAY(7);
	
	private final int code;
	
	private DayWeekEnum(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
