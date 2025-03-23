package com.fiap.techmesa.application.usecase.exception;

import static java.lang.String.format;

import java.time.LocalDate;

public class ReserveAlreadyExistsException extends BusinessException {

	private static final String ERROR_CODE = "already_exists";
	  private static final String MESSAGE = "Reserve [%s] with id [%s] already exists.";

	  public ReserveAlreadyExistsException(Integer restaurantId, Integer clientId, LocalDate dateReserve) {
	    super(format(MESSAGE, restaurantId, clientId, dateReserve), ERROR_CODE);
	  }
}