/*
 * Copyright © 2018 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservices.vertx.bench.business;

import java.util.List;

import com.github.vlachenal.webservices.vertx.bench.dao.CustomerDAO;
import com.github.vlachenal.webservices.vertx.bench.dto.CustomerDTO;
import com.github.vlachenal.webservices.vertx.bench.dto.SearchRequestDTO;
import com.github.vlachenal.webservices.vertx.bench.errors.InvalidParametersException;
import com.github.vlachenal.webservices.vertx.bench.errors.NotFoundException;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


/**
 * Customer business component.<br>
 * Check business rules and call DAO.
 *
 * @author Vincent Lachenal
 */
public class CustomerBusiness extends AbstractBusiness {

  // Attributes +
  /** Customer DAO */
  private final CustomerDAO dao;
  // Attributes -


  // Constructors +
  /**
   * {@link CustomerBusiness} constructor
   *
   * @param dao the customer DAO to use
   */
  public CustomerBusiness(final CustomerDAO dao) {
    this.dao = dao;
  }
  // Constructors -


  // Methods +
  /**
   * List all customers
   *
   * @param request the search request
   * @param destination the destination result
   */
  public void search(final SearchRequestDTO request, final Handler<AsyncResult<List<CustomerDTO>>> destination) {
    dao.search(request, destination);
    // TODO manage not found error
  }

  /**
   * Get customer's details
   *
   * @param id the customer's identifier
   *
   * @return the customer's details
   *
   * @throws InvalidParametersException invalid or missing parameter
   * @throws NotFoundException customer has not been found
   */
  public CustomerDTO getDetails(final String id) throws InvalidParametersException, NotFoundException {
//    final CustomerDTO customers = dao.getDetails(toUUID(id));
//    if(customers == null) {
//      throw new NotFoundException("Customer " + id + " does not exist");
//    }
//    return customers;
    return null;
  }

  /**
   * Create new customer
   *
   * @param customer the customer to create
   * @param destination thre destination result
   *
   * @throws InvalidParametersException missing or invalid parameters
   */
  public void create(final CustomerDTO customer, final Handler<AsyncResult<String>> destination) throws InvalidParametersException {
//    // Customer structure checks +
//    checkParameters("Customer is null", customer);
//    checkParameters("Customer first_name, last_name and brith_date has to be set", customer.getFirstName(), customer.getLastName(), customer.getBirthDate());
//    // Customer structure checks -
//    return dao.createCustomer(customer);
    dao.createCustomer(customer, destination);
  }

  /**
   * Delete all customers
   */
  public void deleteAll() {
    dao.deleteAll();
  }
  // Methods -

}
