/*
 * Copyright © 2018 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservices.vertx.bench.dto;

import com.github.vlachenal.webservices.vertx.bench.errors.ClientException;

/**
 * Error response
 *
 * @author Vincent Lachenal
 */
public class ErrorResponseDTO {

  // Attributes +
  /** Error status label */
  private String status;

  /** Error message */
  private String message;

  /** HTTP return code */
  private int code;
  // Attributes -


  // Constructors +
  /**
   * {@link ErrorResponseDTO} default constructor
   */
  public ErrorResponseDTO() {
    // Nothing to do
  }

  /**
   * {@link ErrorResponseDTO} constructor from {@link ClientException}
   *
   * @param error the {@link ClientException}
   */
  public ErrorResponseDTO(final ClientException error) {
    status = error.getStatus();
    message = error.getMessage();
    code = error.getCode();
  }
  // Constructors -


  // Accessors +
  /**
   * Error status label getter
   *
   * @return the status
   */
  public final String getStatus() {
    return status;
  }

  /**
   * Error status label setter
   *
   * @param status the status to set
   */
  public final void setStatus(final String status) {
    this.status = status;
  }

  /**
   * Error message getter
   *
   * @return the message
   */
  public final String getMessage() {
    return message;
  }

  /**
   * Error message setter
   *
   * @param message the message to set
   */
  public final void setMessage(final String message) {
    this.message = message;
  }

  /**
   * HTTP return code getter
   *
   * @return the code
   */
  public final int getCode() {
    return code;
  }

  /**
   * HTTP return code setter
   *
   * @param code the code to set
   */
  public final void setCode(final int code) {
    this.code = code;
  }
  // Accessors -

}
