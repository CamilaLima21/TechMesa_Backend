package com.fiap.techmesa.application.usecase.exception;

import static java.lang.String.format;

public class RestaurantNotFoundException extends BusinessException{

	private static final String ERROR_CODE = "not_found";
	  private static final String MESSAGE = "Restaurant with id [%s] not found.";

	  public RestaurantNotFoundException(Integer id) {
	    super(format(MESSAGE, id), ERROR_CODE);
	  }
}
