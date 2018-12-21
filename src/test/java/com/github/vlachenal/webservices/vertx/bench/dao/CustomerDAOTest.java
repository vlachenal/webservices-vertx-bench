package com.github.vlachenal.webservices.vertx.bench.dao;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.vlachenal.webservices.vertx.bench.MainVerticle;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.PostgreSQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;


/**
 * Customer DAO unit tests
 *
 * @author Vincent Lachenal
 */
@ExtendWith(VertxExtension.class)
class CustomerDAOTest {

  /** {@link CustomerDAOTest} logger instance */
  private static final Logger LOG = LoggerFactory.getLogger(CustomerDAOTest.class);

  /** Customer DAO */
  private static CustomerDAO dao;

  /**
   * @throws java.lang.Exception
   */
  @BeforeAll
  static void setUpBeforeClass(final Vertx vertx, final VertxTestContext testContext) {
//    vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
    final SQLClient client = PostgreSQLClient.createShared(vertx, new JsonObject()
                                                           .put("host", "localhost")
                                                           .put("port", 5432)
                                                           .put("database", "apibenchmark")
                                                           .put("username", "apibenchmark")
                                                           .put("password", "apibenchmark"),
        "ApiBenchmark");
    dao = new CustomerDAO(client);
  }

  /**
   * Deploy verticle before each test
   *
   * @param vertx the Vert.x instance
   * @param testContext the unit test context
   */
  @BeforeEach
  void deployVerticle(final Vertx vertx, final VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }

  /**
   * Test method for {@link com.github.vlachenal.webservices.vertx.bench.dao.CustomerDAO#customerExists(java.util.UUID, io.vertx.core.Handler)}.
   *
   * @throws InterruptedException timeout ...
   */
  @DisplayName("Customer exists")
  @Test
  void testCustomerExists() throws InterruptedException {
    LOG.error("Enter in testCustomerExists");
    LOG.error("tutu");
    final Lock lock = new ReentrantLock();
//    final Condition isFinished = lock.newCondition();
    dao.customerExists(UUID.randomUUID(), res -> {
      LOG.error("titi");
      lock.unlock();
      if(res.failed()) {
        fail(res.cause().getMessage());
      }
//      isFinished.signalAll();
    });
    LOG.error("tata");
    lock.lock();
    LOG.error("toto");
//    isFinished.await(3, TimeUnit.SECONDS);
    lock.tryLock(3, TimeUnit.SECONDS);
  }

  /**
   * Test method for {@link com.github.vlachenal.webservices.vertx.bench.dao.CustomerDAO#search(com.github.vlachenal.webservices.vertx.bench.dto.SearchRequestDTO, io.vertx.core.Handler)}.
   */
  @Test
  void testSearch() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link com.github.vlachenal.webservices.vertx.bench.dao.CustomerDAO#getDetails(java.util.UUID, io.vertx.core.Handler)}.
   */
  @Test
  void testGetDetails() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link com.github.vlachenal.webservices.vertx.bench.dao.CustomerDAO#createCustomer(com.github.vlachenal.webservices.vertx.bench.dto.CustomerDTO, io.vertx.core.Handler)}.
   */
  @Test
  void testCreateCustomer() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link com.github.vlachenal.webservices.vertx.bench.dao.CustomerDAO#deleteCustomer(java.util.UUID, io.vertx.core.Handler)}.
   */
  @Test
  void testDeleteCustomer() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link com.github.vlachenal.webservices.vertx.bench.dao.CustomerDAO#deleteAll()}.
   */
  @Test
  void testDeleteAll() {
    fail("Not yet implemented");
  }

}
