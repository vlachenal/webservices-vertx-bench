/*
 * Copyright © 2018 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservices.vertx.bench.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Customer
 *
 * @author Vincent Lachenal
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {

  // Attributes +
  /** Customer identifier */
  private String id;

  /** Customer first name */
  @JsonProperty(value="first_name")
  private String firstName;

  /* Customer last name */
  @JsonProperty(value="last_name")
  private String lastName;

  /** Customer brith date */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @JsonProperty(value="birth_date")
  private Date birthDate;

  /** Customer email address */
  private String email;
  // Attributes -


  // Constructors +
  /**
   * {@link CustomerDTO} default constructor
   */
  public CustomerDTO() {
    // Nothing to do
  }

  /**
   * {@link CustomerDTO} constructor
   *
   * @param id the identifier
   * @param firstName the first name
   * @param lastName the last name
   */
  public CustomerDTO(final String id, final String firstName, final String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }
  // Constructors -


  // Accessors +
  /**
   * Identifier getter
   *
   * @return the identifier
   */
  public String getId() {
    return id;
  }

  /**
   * Identifier setter
   *
   * @param id the identifier to set
   */
  public void setId(final String id) {
    this.id = id;
  }

  /**
   * First name getter
   *
   * @return the first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * First name setter
   *
   * @param firstName the first name to set
   */
  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  /**
   * Last name getter
   *
   * @return the last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Last name setter
   *
   * @param lastName the last name to set
   */
  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  /**
   * Birth date getter
   *
   * @return the birth date
   */
  public Date getBirthDate() {
    return birthDate;
  }

  /**
   * Birth date setter
   *
   * @param birthDate the birth date
   */
  public void setBirthDate(final Date birthDate) {
    this.birthDate = birthDate;
  }

  /**
   * Email address getter
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Email address setter
   *
   * @param email the email to set
   */
  public void setEmail(final String email) {
    this.email = email;
  }
  // Accessors -

}
