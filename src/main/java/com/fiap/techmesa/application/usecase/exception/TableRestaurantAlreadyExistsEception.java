package com.fiap.techmesa.application.usecase.exception;

import static java.lang.String.format;

import com.fiap.techmesa.application.domain.Reserve;

public class TableRestaurantAlreadyExistsEception extends BusinessException {

	private static final String ERROR_CODE = "already_exists";
	  private static final String MESSAGE = "Table restaurant [%s] with id [%s] already exists.";

	  public TableRestaurantAlreadyExistsEception(String tableIdentification, Reserve reserve) {
	    super(format(MESSAGE, tableIdentification, reserve), ERROR_CODE);
	  }
}