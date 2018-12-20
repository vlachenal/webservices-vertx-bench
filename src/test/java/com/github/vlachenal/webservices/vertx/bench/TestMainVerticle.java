package com.github.vlachenal.webservices.vertx.bench;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.vertx.core.Vertx;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;


/**
 * {@link MainVerticle} unit tests
 *
 * @author Vincent Lachenal
 */
@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

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
   * Start HTTP server
   *
   * @param vertx the Vert.x instance
   * @param testContext the unit test context
   *
   * @throws Throwable any error
   */
  @Test
  @DisplayName("Should start a Web Server on port 8080")
  @Timeout(value = 10, timeUnit = TimeUnit.SECONDS)
  void startHttpServer(final Vertx vertx, final VertxTestContext testContext) throws Throwable {
    vertx.createHttpClient().getNow(8080, "localhost", "/", response -> testContext.verify(() -> {
      assertTrue(response.statusCode() == 200);
      response.handler(body -> {
        assertTrue(body.toString().contains("Hello from Vert.x!"));
        testContext.completeNow();
      });
    }));
  }

}
