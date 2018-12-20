/*
 * Copyright © 2018 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservices.vertx.bench.errors;


/**
 * HTTP client error
 *
 * @author Vincent Lachenal
 */
@SuppressWarnings("serial")
public class ClientException extends RuntimeException {

  // Attributes +
  /** HTTP return code */
  private final int code;

  /** Error status label */
  private final String status;
  // Attributes -


  // Constructors +
  /**
   * {@link ClientException} constructor
   *
   * @param code the HTTP code
   * @param status the HTTP status label
   * @param message the error message
   */
  public ClientException(final int code, final String status, final String message) {
    super(message);
    this.code = code;
    this.status = status;
  }
  // Constructors -


  // Accessors +
  /**
   * HTTP return code getter
   *
   * @return the code
   */
  public final int getCode() {
    return code;
  }

  /**
   * HTTP status label getter
   *
   * @return the status
   */
  public final String getStatus() {
    return status;
  }
  // Accessors -

}
