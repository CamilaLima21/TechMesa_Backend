package com.fiap.techmesa.application.usecase.exception;

import static java.lang.String.format;

public class RatingNotFoundException extends BusinessException {

	private static final String ERROR_CODE = "not_found";
	  private static final String MESSAGE = "Rating with id [%s] not found.";

	  public RatingNotFoundException(Integer id) {
	    super(format(MESSAGE, id), ERROR_CODE);
	  }
}
