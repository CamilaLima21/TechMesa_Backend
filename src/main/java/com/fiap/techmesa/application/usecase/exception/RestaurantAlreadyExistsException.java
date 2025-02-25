package com.fiap.techmesa.application.usecase.exception;

import static java.lang.String.format;

public class RestaurantAlreadyExistsException extends BusinessException {

	 private static final String ERROR_CODE = "already_exists";
	  private static final String MESSAGE = "Restaurant [%s] with id [%s] already exists.";

	  public RestaurantAlreadyExistsException(Integer id, String name) {
	    super(format(MESSAGE, id, name), ERROR_CODE);
	  }
}
