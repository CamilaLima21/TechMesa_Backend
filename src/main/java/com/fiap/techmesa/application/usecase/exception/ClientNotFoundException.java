package com.fiap.techmesa.application.usecase.exception;

import static java.lang.String.format;

public class ClientNotFoundException extends BusinessException {

	private static final String ERROR_CODE = "not_found";
	  private static final String MESSAGE = "Client with id [%s] not found.";

	  public ClientNotFoundException(final Integer id) {
	    super(format(MESSAGE, id), ERROR_CODE);
	  }
}
