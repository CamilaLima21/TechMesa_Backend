package com.fiap.techmesa.application.usecase.exception;

import static java.lang.String.format;

public class ReserveNotFoundException extends BusinessException{

	private static final String ERROR_CODE = "not_found";
	  private static final String MESSAGE = "Reserve with id [%s] not found.";

	  public ReserveNotFoundException(Integer id) {
	    super(format(MESSAGE, id), ERROR_CODE);
	  }
}
