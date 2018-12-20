/*
 * Copyright © 2018 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservices.vertx.bench.errors;


/**
 * Data not found exception
 *
 * @author Vincent Lachenal
 */
@SuppressWarnings("serial")
public class NotFoundException extends ClientException {

  /**
   * {@link NotFoundException} constructor
   *
   * @param messages the error message
   */
  public NotFoundException(final String messages) {
    super(404, "NOT_FOUND", messages);
  }

}
