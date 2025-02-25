package com.fiap.techmesa.application.usecase.exception;

import static java.lang.String.format;

public class OpeningHoursNotFoundException extends BusinessException {

	private static final String ERROR_CODE = "not_found";
	  private static final String MESSAGE = "Opening hours with id [%s] not found.";

	  public OpeningHoursNotFoundException(Integer id) {
	    super(format(MESSAGE, id), ERROR_CODE);
	  }
}
