/*
 * Copyright © 2018 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservices.vertx.bench;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.vlachenal.webservices.vertx.bench.business.CustomerBusiness;
import com.github.vlachenal.webservices.vertx.bench.dao.CustomerDAO;
import com.github.vlachenal.webservices.vertx.bench.dto.CustomerDTO;
import com.github.vlachenal.webservices.vertx.bench.dto.ErrorResponseDTO;
import com.github.vlachenal.webservices.vertx.bench.dto.SearchRequestDTO;
import com.github.vlachenal.webservices.vertx.bench.errors.ClientException;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.PostgreSQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.api.contract.RouterFactoryOptions;
import io.vertx.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;


/**
 * Application entry point
 *
 * @author Vincent Lachenal
 */
public class MainVerticle extends AbstractVerticle {

  // Attributes +
  /** {@link MainVerticle} logger instance */
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  /** MySQL and PostgreSQL date format ... */
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  /** Customer business */
  private CustomerBusiness customer;
  // Attributes -


  // Methods +
  /**
   * Handle error
   *
   * @param context the routing context
   */
  private static void handleError(final RoutingContext context) {
    context.response().putHeader("Content-Type", "application/json");
    LOG.error("Handle error: " + context.failure().getMessage(), context.failure());
    ErrorResponseDTO error = null;
    if(context.failure() instanceof ClientException) {
      error = new ErrorResponseDTO((ClientException)context.failure());
    } else {
      error = new ErrorResponseDTO();
      error.setCode(500);
      error.setStatus("INTERNAL_SERVER_ERROR");
      error.setMessage("Unexpected error: " + context.failure().getMessage());
    }
    context.response().setStatusCode(error.getCode()).end(Json.encode(error));
  }

  /**
   * Find customer handler
   *
   * @param context the routing context
   */
  private void findCustomers(final RoutingContext context) {
    LOG.debug("Enter in findCustomers");
    final SearchRequestDTO req = new SearchRequestDTO();
    req.setFirstName(context.request().getParam("first_name"));
    req.setLastName(context.request().getParam("last_name"));
    final String birthDate = context.request().getParam("birth_name");
    if(birthDate != null) {
      req.setBirthDate(java.util.Date.from(DATE_FORMAT.parse(birthDate, ZonedDateTime::from).toInstant()));
    }
    customer.search(new SearchRequestDTO(), res -> {
      LOG.debug("Manage find customers result! {}", res);
      if(res.succeeded()) {
        final List<CustomerDTO> customers = res.result();
        if(customers == null || customers.isEmpty()) {
          final ErrorResponseDTO error = new ErrorResponseDTO();
          error.setCode(404);
          error.setStatus("NOT_FOUND");
          error.setMessage("No customer has been found");
          context.response().setStatusCode(404)
          .putHeader("Content-Type", "application/json")
          .end(Json.encode(error));
        } else {
          context.response().setStatusCode(200)
          .putHeader("Content-Type", "application/json")
          .end(Json.encode(customers));
        }
      } else {
        context.fail(new ClientException(500, "INTERNAL_SERVER_ERROR", "plop"));
      }
    });
    LOG.debug("Exit findCustomers");
  }

  /**
   * Initialize SQL client, DAOs and businesses instances.<br>
   * {@inheritDoc}
   *
   * @see io.vertx.core.AbstractVerticle#init(io.vertx.core.Vertx, io.vertx.core.Context)
   */
  @Override
  public void init(final Vertx vertx, final Context context) {
    super.init(vertx, context);
//    final JDBCClient client = JDBCClient.createShared(vertx, new JsonObject()
//                                                      .put("url", "jdbc:postgresql://localhost:5432/apibenchmark")
//                                                      .put("user", "apibenchmark")
//                                                      .put("password", "apibenchmark")
//                                                      .put("driver_class", "org.postgresql.Driver"),
//              "ApiBenchmark");
    final SQLClient client = PostgreSQLClient.createShared(vertx, new JsonObject()
                                                           .put("host", "localhost")
                                                           .put("port", 5432)
                                                           .put("database", "apibenchmark")
                                                           .put("username", "apibenchmark")
                                                           .put("password", "apibenchmark"),
        "ApiBenchmark");
    customer = new CustomerBusiness(new CustomerDAO(client));
  }

  /**
   * Parse OAS 3.0 YAML file to create router and start HTTP server with it.<br>
   * {@inheritDoc}
   *
   * @see io.vertx.core.AbstractVerticle#start(io.vertx.core.Future)
   */
  @Override
  public void start(final Future<Void> startFuture) throws Exception {
    InternalLoggerFactory.setDefaultFactory(Log4J2LoggerFactory.INSTANCE);
    LOG.debug("Create resources from OpenAPI specifications");
    OpenAPI3RouterFactory.create(vertx, "src/main/resources/customers.yaml", ar -> {
      if(ar.succeeded()) {
        // Spec loaded with success
        final OpenAPI3RouterFactory routerFactory = ar.result();
        LOG.info("Router has been successfully created: {}", routerFactory);
        // Create and mount options to router factory
        routerFactory.setOptions(new RouterFactoryOptions()
                                 .setMountNotImplementedHandler(true)
                                 .setMountValidationFailureHandler(false));

        LOG.debug("Manage customers resource endpoints");
        routerFactory.addHandlerByOperationId("findCustomers", this::findCustomers)
        .addFailureHandlerByOperationId("findCustomers", MainVerticle::handleError);

        LOG.debug("Create and start HTTP server");
        vertx.createHttpServer(new HttpServerOptions().setPort(8080)/*.setHost("localhost")*/)
            .requestHandler(routerFactory.getRouter()).listen();

      } else {
        // Something went wrong during router factory initialization
        LOG.error(ar.cause().getMessage(), ar.cause());
        throw new RuntimeException(ar.cause().getMessage(), ar.cause());
      }
    });
  }
  // Methods -

}
