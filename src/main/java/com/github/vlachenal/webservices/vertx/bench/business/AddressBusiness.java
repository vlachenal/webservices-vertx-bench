/*
 * Copyright © 2018 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservices.vertx.bench.business;

import com.github.vlachenal.webservices.vertx.bench.dao.AddressDAO;
import com.github.vlachenal.webservices.vertx.bench.dao.CustomerDAO;
import com.github.vlachenal.webservices.vertx.bench.dto.AddressDTO;
import com.github.vlachenal.webservices.vertx.bench.errors.InvalidParametersException;
import com.github.vlachenal.webservices.vertx.bench.errors.NotFoundException;


/**
 * Address business
 *
 * @author Vincent Lachenal
 */
public class AddressBusiness extends AbstractBusiness {

  // Attributes +
  /** Address DAO */
  final AddressDAO addressDAO;

  /** Customer DAO */
  final CustomerDAO custumerDAO;
  // Attributes -


  // Constructors +
  /**
   * {@link AddressBusiness} constructor
   *
   * @param phoneDAO the address DAO to use
   * @param custumerDAO the customer DAO to use
   */
  public AddressBusiness(final AddressDAO addressDAO, final CustomerDAO custumerDAO) {
    this.addressDAO = addressDAO;
    this.custumerDAO = custumerDAO;
  }
  // Constructors -


  // Methods +
  /**
   * Get address
   *
   * @param customerId the customer identifier
   *
   * @return the address
   *
   * @throws InvalidParametersException customer idenfier is not an UUID
   * @throws NotFoundException address has not been found
   */
  public AddressDTO getAddress(final String customerId) throws InvalidParametersException, NotFoundException {
//    final AddressDTO address = addressDAO.getAddress(toUUID(customerId));
//    if(address == null) {
//      throw new NotFoundException("Customer " + customerId + " is hommeless");
//    }
//    return address;
    return null;
  }

  /**
   * Register address
   *
   * @param customerId the customer identifier
   * @param address the address to register
   *
   * @throws InvalidParametersException customer idenfier is not an UUID
   * @throws NotFoundException customer has not been found
   */
  public void registerAddress(final String customerId, final AddressDTO address) throws InvalidParametersException, NotFoundException {
//    final UUID custId = toUUID(customerId);
//    if(!custumerDAO.customerExists(custId)) {
//      throw new NotFoundException("Customer " + customerId + " does not exist");
//    }
//    addressDAO.registerAddress(custId, address);
  }

  /**
   * Delete address
   *
   * @param customerId the customer identifier
   *
   * @throws InvalidParametersException customer idenfier is not an UUID
   * @throws NotFoundException address has not been found
   */
  public void deleteAddress(final String customerId) throws InvalidParametersException, NotFoundException {
//    final UUID custId = toUUID(customerId);
//    if(addressDAO.getAddress(custId) == null) {
//      throw new NotFoundException("Customer " + customerId + " does not exist");
//    }
//    addressDAO.deleteAddress(custId);
  }
  // Methods -

}
