package com.fiap.techmesa.application.usecase.exception;

import static java.lang.String.format;

import java.time.LocalDate;

import com.fiap.techmesa.application.domain.Client;
import com.fiap.techmesa.application.domain.Restaurant;

public class ReserveAlreadyExistsException extends BusinessException {

	private static final String ERROR_CODE = "already_exists";
	  private static final String MESSAGE = "Reserve [%s] with id [%s] already exists.";

	  public ReserveAlreadyExistsException(Restaurant restaurant, Client client, LocalDate dateReserve) {
	    super(format(MESSAGE, restaurant, client, dateReserve), ERROR_CODE);
	  }
}
