package com.fiap.techmesa.application.usecase.exception;

import static java.lang.String.format;

import lombok.Getter;

@Getter
public class ClientAlreadyExistsException extends BusinessException {

	private static final String ERROR_CODE = "already_exists";
	  private static final String MESSAGE = "Client [%s] with id [%s] already exists.";

	  public ClientAlreadyExistsException(final Integer id, final String name) {
	    super(format(MESSAGE, name, id), ERROR_CODE);
	  }
}
