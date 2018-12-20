/*
 * Copyright © 2018 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservices.vertx.bench.dto;

import java.util.Date;


/**
 * Search request. Contains possible filters.
 *
 * @author Vincent Lachenal
 */
public class SearchRequestDTO {

  // Attributes +
  /** First name */
  private String firstName;

  /** Last name */
  private String lastName;

  /** Email */
  private String email;

  /** Birth date */
  private Date birthDate;

  /** Maximum birth date */
  private Date bornBefore;

  /** Minimum birth date */
  private Date bornAfter;
  // Attributes -


  // Accessors +
  /**
   * First name getter
   *
   * @return the first name
   */
  public final String getFirstName() {
    return firstName;
  }

  /**
   * First name setter
   *
   * @param firstName the first name to set
   */
  public final void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  /**
   * Last name getter
   *
   * @return the last name
   */
  public final String getLastName() {
    return lastName;
  }

  /**
   * Last name setter
   *
   * @param lastName the last name to set
   */
  public final void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  /**
   * Email getter
   *
   * @return the email
   */
  public final String getEmail() {
    return email;
  }

  /**
   * Email setter
   *
   * @param email the email to set
   */
  public final void setEmail(final String email) {
    this.email = email;
  }

  /**
   * Birth date getter
   *
   * @return the birth date
   */
  public final Date getBirthDate() {
    return birthDate;
  }

  /**
   * Birth date setter
   *
   * @param birthDate the birth date to set
   */
  public final void setBirthDate(final Date birthDate) {
    this.birthDate = birthDate;
  }

  /**
   * Maximum birth date getter
   *
   * @return the date
   */
  public final Date getBornBefore() {
    return bornBefore;
  }

  /**
   * Maximum birth date setter
   *
   * @param bornBefore the date to set
   */
  public final void setBornBefore(final Date bornBefore) {
    this.bornBefore = bornBefore;
  }

  /**
   * Minimum birth date getter
   *
   * @return the date
   */
  public final Date getBornAfter() {
    return bornAfter;
  }

  /**
   * Minimum birth date setter
   *
   * @param bornAfter the date to set
   */
  public final void setBornAfter(final Date bornAfter) {
    this.bornAfter = bornAfter;
  }
  // Accessors -

}
