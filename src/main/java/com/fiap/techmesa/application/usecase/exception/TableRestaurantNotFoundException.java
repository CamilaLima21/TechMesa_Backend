package com.fiap.techmesa.application.usecase.exception;

import static java.lang.String.format;

public class TableRestaurantNotFoundException extends BusinessException {

	private static final String ERROR_CODE = "not_found";
	  private static final String MESSAGE = "Table restaurant with tableIdentification [%s] not found.";

	  public TableRestaurantNotFoundException(String tableIdentification) {
	    super(format(MESSAGE, tableIdentification), ERROR_CODE);
	  }
}
