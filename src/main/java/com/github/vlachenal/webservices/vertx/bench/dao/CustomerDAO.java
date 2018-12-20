/*
 * Copyright © 2018 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservices.vertx.bench.dao;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.vlachenal.sql.Clauses;
import com.github.vlachenal.sql.SQL;
import com.github.vlachenal.sql.SQLQuery;
import com.github.vlachenal.webservices.vertx.bench.dto.CustomerDTO;
import com.github.vlachenal.webservices.vertx.bench.dto.SearchRequestDTO;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.UpdateResult;


/**
 * Customer DAO
 *
 * @author Vincent Lachenal
 */
public class CustomerDAO {

  // Attributes +
  /** {@link CustomerDAO} logger instance */
  private static final Logger LOG = LoggerFactory.getLogger(CustomerDAO.class);

  /** MySQL and PostgreSQL date format ... */
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  // SQL requests +
  /** Get customer details SQL request */
  private static final String REQ_GET_CUST = "SELECT id,first_name,last_name,birth_date,email FROM Customer WHERE id = ?";

  /** Delete all customer SQL request */
  private static final String REQ_DELETE_ALL = "DELETE FROM Customer";

  /** Delete customer SQL request */
  private static final String REQ_DELETE = "DELETE FROM Customer WHERE id = ?";

  /** Insert customer in database */
  private static final String REQ_ADD_CUSTOMER = "INSERT INTO Customer "
      + "(id,first_name,last_name,birth_date,email) "
      + "VALUES (?,?,?,?,?)";

  /** Customer exists */
  private static final String REQ_CUSTOMER_EXISTS = "SELECT 1 FROM Customer WHERE id = ?";

  /** Vacuum requests */
  private String vacuumReqs;
  // SQL requests -

  /** SQL client */
  private final SQLClient jdbc;
  // Attributes -


  // Constructors +
  /**
   * {@link CustomerDAO} constructor
   *
   * @param jdbc the SQl client to use
   */
  public CustomerDAO(final SQLClient jdbc) {
    this.jdbc = jdbc;
  }
  // Constructors -


  // Methods +
  /**
   * Handle customer exists result
   *
   * @param result the SQL result
   * @param destination the destination result
   */
  private void handleCustomerExistsRes(final AsyncResult<JsonArray> result, final Handler<AsyncResult<Boolean>> destination) {
    if(result.failed()) {
      destination.handle(Future.failedFuture(result.cause()));
    } else {
      destination.handle(Future.succeededFuture(result.result().getBoolean(0)));
    }
  }

  /**
   * Check if customer exists
   *
   * @param customerId the customer's identifier
   * @param destination the destination result
   */
  public void customerExists(final UUID customerId, final Handler<AsyncResult<Boolean>> destination) {
    jdbc.querySingleWithParams(REQ_CUSTOMER_EXISTS,
                               new JsonArray(Collections.singletonList(customerId)),
                               res -> handleCustomerExistsRes(res, destination));
  }

  /**
   * Handle search result
   *
   * @param result the SQL result
   * @param destination the destination result
   */
  private void handleSearchRes(final AsyncResult<ResultSet> result, final Handler<AsyncResult<List<CustomerDTO>>> destination) {
    if(result.failed()) {
      destination.handle(Future.failedFuture(result.cause()));
    } else {
      destination.handle(result.map(res -> res.getResults().stream()
                                    .map(json -> new CustomerDTO(json.getString(0), json.getString(1), json.getString(3)))
                                    .collect(Collectors.toList())));
    }
  }

  /**
   * List all customers in database
   *
   * @param request the search request
   * @param destination the destination result
   */
  public void search(final SearchRequestDTO request, final Handler<AsyncResult<List<CustomerDTO>>> destination) {
    final SQLQuery query = SQL.select().field("id").field("first_name").field("last_name")
        .from("Customer")
        .where(SQL.clauses("first_name", Clauses::like, request.getFirstName())
               .and("last_name", Clauses::like, request.getLastName())
               .and("email", Clauses::equalsTo, request.getEmail())
               .and("birth_date", Clauses::equalsTo, request.getBirthDate())
               .and("birth_date", Clauses::greaterEquals, request.getBornAfter())
               .and("birth_date", Clauses::lesserEquals, request.getBornBefore()))
        .build();
    jdbc.queryWithParams(query.getQuery(), new JsonArray(query.getValues()),
                         result -> handleSearchRes(result, destination));
  }

  /**
   * Handle get customer details result
   *
   * @param result the SQL result
   * @param destination the destination result
   */
  private void handleGetDetailsRes(final AsyncResult<JsonArray> result, final Handler<AsyncResult<CustomerDTO>> destination) {
    if(result.failed()) {
      destination.handle(Future.failedFuture(result.cause()));
    } else {
      destination.handle(result.map(res -> {
        final CustomerDTO cust = new CustomerDTO();
        cust.setId(res.getString(0));
        cust.setFirstName(res.getString(1));
        cust.setLastName(res.getString(2));
        cust.setBirthDate(Date.from(DATE_FORMAT.parse(res.getString(3), ZonedDateTime::from).toInstant()));
        cust.setEmail(res.getString(4));
        return cust;
      }));
    }
  }

  /**
   * Get customer details
   *
   * @param customerId the customer identifier
   * @param destination the destination result
   */
  public void getDetails(final UUID customerId, final Handler<AsyncResult<CustomerDTO>> destination) {
    jdbc.querySingleWithParams(REQ_GET_CUST, new JsonArray(Collections.singletonList(customerId)), res -> handleGetDetailsRes(res, destination));
//    if(customer != null) {
//      customer.setAddress(addressDAO.getAddress(customerId));
//      customer.setPhones(phoneDAO.getPhones(customerId));
//    }
  }

  /**
   * Handle create customer result
   *
   * @param result the SQL result
   * @param destination the destination result
   * @param uuid the new customer UUID
   */
  private void handleCreateCustomerRes(final AsyncResult<UpdateResult> result, final Handler<AsyncResult<String>> destination, final UUID uuid) {
    if(result.failed()) {
      destination.handle(Future.failedFuture(result.cause()));
    } else {
      destination.handle(Future.succeededFuture(uuid.toString()));
    }
  }

  /**
   * Create customer in database
   *
   * @param customer the customer to create
   * @param destination the destination result
   */
  public void createCustomer(final CustomerDTO customer, final Handler<AsyncResult<String>> destination) {
    final UUID customerId = UUID.randomUUID();
    jdbc.updateWithParams(REQ_ADD_CUSTOMER,
                          new JsonArray(Arrays.asList(customerId,
                                                      customer.getFirstName(),
                                                      customer.getLastName(),
                                                      customer.getBirthDate(),
                                                      customer.getEmail())),
                          res -> handleCreateCustomerRes(res, destination, customerId));
//    if(customer.getAddress() != null) {
//      jdbc.update(AddressDAO.REQ_ADD_ADDRESS, stmt -> addressDAO.setAddressValues(stmt, customerId, UUID.randomUUID(), customer.getAddress()));
//    }
//    if(customer.getPhones() != null && !customer.getPhones().isEmpty()) {
//      jdbc.batchUpdate(PhoneDAO.REQ_ADD_PHONE, customer.getPhones(), customer.getPhones().size(), (stmt, phone) -> phoneDAO.setPhoneValues(stmt, customerId, phone));
//    }
  }

  /**
   * Delete customer.<br>
   * Address and phones will be deleted throught foreign key usage (ON DELETE CASCADE).
   *
   * @param customerId the customer's identifier
   * @param destination the destination result
   */
  public void deleteCustomer(final UUID customerId, final Handler<AsyncResult<CustomerDTO>> destination) {
    jdbc.updateWithParams(REQ_DELETE, new JsonArray(Collections.singletonList(customerId)), res -> {
      if(res.failed()) {
        destination.handle(Future.failedFuture(res.cause()));
      } else {
        destination.handle(Future.succeededFuture());
      }
    });
  }

  /**
   * Handle delete all result
   *
   * @param result the result
   */
  private void handleDeleteAll(final AsyncResult<UpdateResult> result) {
    if(result.failed()) {
      LOG.error("Unable to clear database: " + result.cause().getMessage(), result.cause());
      return;
    }
    if(vacuumReqs != null && !vacuumReqs.isEmpty()) {
      for(final String req : vacuumReqs.split(";")) {
        jdbc.update(req, res -> {
          if(res.failed()) {
            LOG.error("Unable to clear database: " + res.cause().getMessage(), res.cause());
          }
        });
      }
    }
  }

  /**
   * Delete all customers in database
   */
  public void deleteAll() {
    jdbc.update(REQ_DELETE_ALL, this::handleDeleteAll);
  }
  // Methods -

}
