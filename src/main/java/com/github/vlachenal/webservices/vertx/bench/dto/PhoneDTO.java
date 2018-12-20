/*
 * Copyright © 2018 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservices.vertx.bench.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * Phone number
 *
 * @author Vincent Lachenal
 */
@ApiModel(description="Customer's phone")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneDTO {

  /**
   * Phone type
   *
   * @author Vincent Lachenal
   */
  public enum Type {

    // Values +
    /** Landline phone */
    LANDLINE(1),

    /** Mobile phone */
    MOBILE(2);
    // Values -

    // Attributes +
    /** SQL code */
    private final int code;
    // Attributes -

    // Constructors +
    /**
     * {@link Type} constructor
     *
     * @param code the code
     */
    private Type(final int code) {
      this.code = code;
    }
    // Constructors -

    // Methods +
    /**
     * Code getter
     *
     * @return the code
     */
    public int getCode() {
      return code;
    }

    /**
     * Get type from code
     *
     * @param code the code
     *
     * @return the type
     */
    public static Type fromCode(final int code) {
      switch(code) {
        case 1:
          return LANDLINE;
        case 2:
          return MOBILE;
        default:
          return null;
      }
    }
    // Methods -

  }

  // Attributes +
  /** Customer identifier */
  @ApiModelProperty(notes="Phone's UUID",example="4064dddd-19b2-435d-97bd-6851ff728821")
  private String id;

  /** Phone type */
  @ApiModelProperty(notes="Phone type (LANDLINE or MOBILE)",required=true,example="LANDLINE")
  private Type type;

  /** Phone number */
  @ApiModelProperty(notes="Phone number",required=true,example="+33636656565")
  private String number;

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
   * Phone type getter
   *
   * @return the type
   */
  public Type getType() {
    return type;
  }

  /**
   * Phone type setter
   *
   * @param type the type to set
   */
  public void setType(final Type type) {
    this.type = type;
  }

  /**
   * Phone number getter
   *
   * @return the phone number
   */
  public String getNumber() {
    return number;
  }

  /**
   * Phone number setter
   *
   * @param number the phone number to set
   */
  public void setNumber(final String number) {
    this.number = number;
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

