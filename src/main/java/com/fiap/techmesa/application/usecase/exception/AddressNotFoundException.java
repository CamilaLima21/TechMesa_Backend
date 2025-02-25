package com.fiap.techmesa.application.usecase.exception;


import static java.lang.String.format;

import lombok.Getter;

@Getter
public class AddressNotFoundException extends BusinessException {

	private static final String ERROR_CODE = "not_found";
	  private static final String MESSAGE = "Address with id [%s] not found.";

	  public AddressNotFoundException(final int id) {
	    super(format(MESSAGE, id), ERROR_CODE);
	  }
}
