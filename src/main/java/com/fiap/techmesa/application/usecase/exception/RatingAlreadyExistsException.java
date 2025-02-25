package com.fiap.techmesa.application.usecase.exception;

import static java.lang.String.format;

public class RatingAlreadyExistsException extends BusinessException {

	private static final String ERROR_CODE = "already_exists";
	  private static final String MESSAGE = "Rating [%s] with id [%s] already exists.";

	  public RatingAlreadyExistsException(Integer id, String title) {
	    super(format(MESSAGE, id, title), ERROR_CODE);
	  }
}
