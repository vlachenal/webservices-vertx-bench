/*
 * Copyright © 2018 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservices.vertx.bench.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * Address
 *
 * @author Vincent Lachenal
 */
@ApiModel(description="Customer's address")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {

  // Attributes +
  /** Customer identifier */
  @ApiModelProperty(notes="Address' UUID",example="4064dddd-19b2-435d-97bd-6851ff728821")
  private String id;

  /** Address lines */
  @ApiModelProperty(notes="Address lines",required=true,example="[1 rue des Nuages]")
  private List<String> lines;

  /** Address ZIP code */
  @ApiModelProperty(notes="ZIP code",required=true,example="33500")
  @JsonProperty(value="zip_code")
  private String zipCode;

  /** City */
  @ApiModelProperty(notes="City",required=true,example="Libourne")
  private String city;

  /** Country */
  @ApiModelProperty(notes="Country",required=true,example="France")
  private String country;

  /** Customer's identifier: used for HATEOAS only */
  @JsonIgnore
  private String customerId;
  // Attributes -


  // Accessors +
  /**
   * Phone identifier getter
   *
   * @return the identifier
   */
  public final String getId() {
    return id;
  }

  /**
   * Phone identifier setter
   *
   * @param id the identifier to set
   */
  public final void setId(final String id) {
    this.id = id;
  }

  /**
   * Address lines getter
   *
   * @return the lines
   */
  public List<String> getLines() {
    return lines;
  }

  /**
   * Address lines setter
   *
   * @param lines the lines to set
   */
  public void setLines(final List<String> lines) {
    this.lines = lines;
  }

  /**
   * ZIP code getter
   *
   * @return the ZIP code
   */
  public String getZipCode() {
    return zipCode;
  }

  /**
   * ZIP code setter
   *
   * @param zipCode the ZIP code to set
   */
  public void setZipCode(final String zipCode) {
    this.zipCode = zipCode;
  }

  /**
   * City getter
   *
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * City setter
   *
   * @param city the city to set
   */
  public void setCity(final String city) {
    this.city = city;
  }

  /**
   * Country getter
   *
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * Country setter
   *
   * @param country the country to set
   */
  public void setCountry(final String country) {
    this.country = country;
  }

  /**
   * Customer's identifier getter
   *
   * @return the identifier
   */
  public final String getCustomerId() {
    return customerId;
  }

  /**
   * Customer's identifier setter
   *
   * @param customerId the identifier to set
   */
  public final void setCustomerId(final String customerId) {
    this.customerId = customerId;
  }
  // Accessors -

}
