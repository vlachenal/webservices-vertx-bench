/*
 * Copyright © 2018 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservices.vertx.bench.errors;


/**
 * Invalid parameter(s) error which can be thrown by business classes
 *
 * @author Vincent Lachenal
 */
@SuppressWarnings("serial")
public class InvalidParametersException extends ClientException {

  /**
   * {@link InvalidParametersException} constructor
   *
   * @param message the error message
   */
  public InvalidParametersException(final String message) {
    super(400, "INVALID_PARAMETER", message);
  }

}
