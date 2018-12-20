/*
 * Copyright © 2018 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservices.vertx.bench.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.github.vlachenal.webservices.vertx.bench.dto.AddressDTO;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.UpdateResult;


/**
 * Address DAO
 *
 * @author Vincent Lachenal
 */
public class AddressDAO {

  // Attributes +
  // SQL requests +
  /** Insert address in database */
  public static final String REQ_ADD_ADDRESS = "INSERT INTO Address "
      + "(line1,line2,line3,line4,line5,line6,zip_code,city,country,customer_id,id) "
      + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

  /** Get customer address SQL request */
  private static final String REQ_GET_ADDRESS = "SELECT line1,line2,line3,line4,line5,line6,zip_code,city,country FROM Address WHERE id = ?";

  /** List customer addresses SQL request */
  private static final String REQ_LIST_ADDRESS = "SELECT line1,line2,line3,line4,line5,line6,zip_code,city,country FROM Address WHERE customer_id = ?";

  /** Delete customer address SQL request */
  private static final String REQ_DELETE_ADDRESS = "DELETE FROM Address WHERE customer_id = ?";
  // SQL requests -

  /** JDBC template */
  private final SQLClient jdbc;
  // Attributes -


  // Constructors +
  /**
   * {@link AddressDAO} constructor
   *
   * @param jdbc the SQl client to use
   */
  public AddressDAO(final SQLClient jdbc) {
    this.jdbc = jdbc;
  }
  // Constructors -


  // Methods +
  /**
   * Get address line value to insert
   *
   * @param lines the address lines
   * @param idx the line index
   *
   * @return {@code true} if line exists, {@code false} otherwise
   */
  private String getLine(final List<String> lines, final int idx) {
    String line = null;
    if(lines != null && lines.size() > idx) {
      line = lines.get(idx);
    }
    return line;
  }

  /**
   * Get address as JSON array
   *
   * @param customerId the customer identifier
   * @param addressId the address identifier
   * @param address the address
   */
  public JsonArray getAddressValues(final UUID customerId, final UUID addressId, final AddressDTO address) {
    return new JsonArray(Arrays.asList(getLine(address.getLines(), 0),
                                       getLine(address.getLines(), 1),
                                       getLine(address.getLines(), 2),
                                       getLine(address.getLines(), 3),
                                       getLine(address.getLines(), 4),
                                       getLine(address.getLines(), 6),
                                       address.getZipCode(),
                                       address.getCity(),
                                       address.getCountry(),
                                       customerId,
                                       addressId));
  }

  /**
   * Handle register address SQL result
   *
   * @param result the SQL result
   * @param destination the destination
   * @param uuid the new address UUID
   */
  private void handleRegisterAddressRes(final AsyncResult<UpdateResult> result, final Handler<AsyncResult<String>> destination, final UUID uuid) {
    if(result.failed()) {
      destination.handle(Future.failedFuture(result.cause()));
    } else {
      destination.handle(Future.succeededFuture(uuid.toString()));
    }
  }

  /**
   * Add address to customer
   *
   * @param customerId the customer identifier
   * @param address the customer's address to add
   * @param destination the destination result
   */
  public void registerAddress(final UUID customerId, final AddressDTO address, final Handler<AsyncResult<String>> destination) {
    final UUID addressId = UUID.randomUUID();
    jdbc.updateWithParams(REQ_ADD_ADDRESS, getAddressValues(customerId, addressId, address),
                          res -> handleRegisterAddressRes(res, destination, addressId));
  }

  /**
   * Add line to lines if not {@code null} nor empty
   *
   * @param lines the lines
   * @param line the line to add
   */
  private void addLine(final List<String> lines, final String line) {
    if(line != null && !line.isEmpty()) {
      lines.add(line);
    }
  }

  /**
   * Handle get address SQL result
   *
   * @param result the SQL result
   * @param destination the destination result
   */
  private void handleGetAddressRes(final AsyncResult<JsonArray> result, final Handler<AsyncResult<AddressDTO>> destination) {
    if(result.failed()) {
      destination.handle(Future.failedFuture(result.cause()));
    } else {
      destination.handle(result.map(res -> {
        final AddressDTO addr = new AddressDTO();
        final ArrayList<String> lines = new ArrayList<>();
        addLine(lines, res.getString(0));
        addLine(lines, res.getString(1));
        addLine(lines, res.getString(2));
        addLine(lines, res.getString(3));
        addLine(lines, res.getString(4));
        addLine(lines, res.getString(5));
        addr.setLines(lines);
        addr.setZipCode(res.getString(6).trim());
        addr.setCity(res.getString(7));
        addr.setCountry(res.getString(8));
        return addr;
      }));
    }
  }

  /**
   * Get customer's addresses
   *
   * @param addressId the customer identifier
   * @param destination the destination result
   */
  public void getAddress(final UUID addressId, final Handler<AsyncResult<AddressDTO>> destination) {
    jdbc.querySingleWithParams(REQ_GET_ADDRESS, new JsonArray(Collections.singletonList(addressId)),
                               res -> handleGetAddressRes(res, destination));
  }

  /**
   * Handle list customer's addresses SQL result
   *
   * @param result the SQL result
   * @param destination the destination result
   */
  private void handleListAddressesRes(final AsyncResult<ResultSet> result, final Handler<AsyncResult<List<AddressDTO>>> destination) {
    if(result.failed()) {
      destination.handle(Future.failedFuture(result.cause()));
    } else {
      destination.handle(result.map(res -> res.getResults().stream().map(entry -> {
        final AddressDTO addr = new AddressDTO();
        final ArrayList<String> lines = new ArrayList<>();
        addLine(lines, entry.getString(0));
        addLine(lines, entry.getString(1));
        addLine(lines, entry.getString(2));
        addLine(lines, entry.getString(3));
        addLine(lines, entry.getString(4));
        addLine(lines, entry.getString(5));
        addr.setLines(lines);
        addr.setZipCode(entry.getString(6).trim());
        addr.setCity(entry.getString(7));
        addr.setCountry(entry.getString(8));
        return addr;
      }).collect(Collectors.toList())));
    }
  }

  /**
   * List customer's addresses
   *
   * @param customerId the customer UUID
   * @param destination thre destination result
   */
  public void listAddresses(final UUID customerId, final Handler<AsyncResult<List<AddressDTO>>> destination) {
    jdbc.queryWithParams(REQ_LIST_ADDRESS, new JsonArray(Collections.singletonList(customerId)),
                         res -> handleListAddressesRes(res, destination));
  }

  /**
   * Handle delete address SQL result
   *
   * @param result the SQL result
   * @param destination the destination result
   */
  private void handleDeleteAddressRes(final AsyncResult<UpdateResult> result, final Handler<AsyncResult<Void>> destination) {
    if(result.failed()) {
      destination.handle(Future.failedFuture(result.cause()));
    } else {
      destination.handle(result.mapEmpty());
    }
  }

  /**
   * Delete address
   *
   * @param customerId the customer's identifier
   * @param destination the destination result
   */
  public void deleteAddress(final UUID customerId, final Handler<AsyncResult<Void>> destination) {
    jdbc.updateWithParams(REQ_DELETE_ADDRESS, new JsonArray(Collections.singletonList(customerId)), res -> handleDeleteAddressRes(res, destination));
  }
  // Methods -

}
