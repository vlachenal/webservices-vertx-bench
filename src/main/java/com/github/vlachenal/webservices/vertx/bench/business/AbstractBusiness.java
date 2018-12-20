/*
 * Copyright © 2018 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservices.vertx.bench.business;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import com.github.vlachenal.webservices.vertx.bench.errors.InvalidParametersException;


/**
 * Abstract business class
 *
 * @author Vincent Lachenal
 */
public abstract class AbstractBusiness {

  /**
   * Check string UUID format and return the UUID object
   *
   * @param id the identifier
   *
   * @return the UUID
   *
   * @throws InvalidParametersException invalid UUID
   */
  protected UUID toUUID(final String id) throws InvalidParametersException {
    try {
      return UUID.fromString(id);
    } catch(final IllegalArgumentException e) {
      throw new InvalidParametersException(id + " is not an UUID");
    }
  }

  /**
   * Check parameters
   *
   * @param errorMsg the error message
   * @param params the parameters
   *
   * @throws InvalidParametersException missing or invalid parameters
   */
  protected void checkParameters(final String errorMsg, final Object... params) throws InvalidParametersException {
    for(final Object param : params) {
      if(param == null) {
        throw new InvalidParametersException(errorMsg + ": " + Arrays.asList(params));
      }
    }
  }

  /**
   * Check collection parameter (null or empty collection is consederd as error)
   *
   * @param errorMsg the error message
   * @param list the collection to check
   *
   * @throws InvalidParametersException missing or invalid parameter
   */
  protected <T> void checkParameter(final String errorMsg, final Collection<T> list) throws InvalidParametersException {
    if(list == null || list.isEmpty()) {
      throw new InvalidParametersException(errorMsg);
    }
  }

}
