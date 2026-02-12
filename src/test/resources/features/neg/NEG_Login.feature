@Negative @Login
Feature: Negative Login Scenarios
  As a user
  I want validation for invalid registration and login attempts
  So that the system prevents invalid data entry and unauthorized access

  Background:
    Given User navigates to ParaBank application
    And ParaBank login page is displayed

  @Regression
  Scenario: Registration fails with first name blank
    When User attempts to register with first name blank
      | firstName | lastName | address    | city    | state | zipCode |
      | [BLANK]   | userTest | 123 Main   | NewYork | NY    | 10001   |
    Then Registration should fail
    And Error message should display: "First name is required."

  @Regression
  Scenario: Registration fails with last name blank
    When User attempts to register with last name blank
      | firstName | lastName | address    | city    | state | zipCode |
      | testUser  | [BLANK]  | 123 Main   | NewYork | NY    | 10001   |
    Then Registration should fail
    And Error message should display: "Last name is required."

  @Regression
  Scenario: Registration fails with address blank
    When User attempts to register with address blank
      | firstName | lastName | address | city    | state | zipCode |
      | testUser  | userTest | [BLANK] | NewYork | NY    | 10001   |
    Then Registration should fail
    And Error message should display: "Address is required."

  @Regression
  Scenario: Registration fails with city blank
    When User attempts to register with city blank
      | firstName | lastName | address  | city    | state | zipCode |
      | testUser  | userTest | 123 Main | [BLANK] | NY    | 10001   |
    Then Registration should fail
    And Error message should display: "City is required."

  @Regression
  Scenario: Registration fails with state blank
    When User attempts to register with state blank
      | firstName | lastName | address  | city    | state   | zipCode |
      | testUser  | userTest | 123 Main | NewYork | [BLANK] | 10001   |
    Then Registration should fail
    And Error message should display: "State is required."

  @Regression
  Scenario: Registration fails with zip code blank
    When User attempts to register with zip code blank
      | firstName | lastName | address  | city    | state | zipCode |
      | testUser  | userTest | 123 Main | NewYork | NY    | [BLANK] |
    Then Registration should fail
    And Error message should display: "Zip code is required."

  @Regression
  Scenario: Registration fails with SSN blank
    When User attempts to register with SSN blank
      | firstName | lastName | ssn     | username | password |
      | testUser  | userTest | [BLANK] | testuser | Test@123 |
    Then Registration should fail
    And Error message should display: "Social Security Number is required."

  @Regression
  Scenario: Registration fails with password blank
    When User attempts to register with password blank
      | firstName | lastName | username | password |
      | testUser  | userTest | testuser | [BLANK]  |
    Then Registration should fail
    And Error message should display: "Password is required."

  @Regression
  Scenario: Registration fails with password mismatch
    When User attempts to register with mismatched passwords
      | firstName | lastName | username | password     | confirmPassword |
      | testUser  | userTest | testuser | Test@123     | Test@456        |
    Then Registration should fail
    And Error message should display: "Passwords do not match."

  @Regression
  Scenario: Login fails with invalid username
    When User enters invalid username "invaliduser"
    And User enters valid password "password"
    And User clicks Login button
    Then Login should fail
    And Error message should display: "The username and password could not be verified."

  @Regression
  Scenario: Login fails with invalid password
    When User enters valid username "demo1"
    And User enters invalid password "wrongpassword"
    And User clicks Login button
    Then Login should fail
    And Error message should display: "The username and password could not be verified."

  @Regression
  Scenario: Login fails with both username and password invalid
    When User enters invalid username "invaliduser"
    And User enters invalid password "wrongpassword"
    And User clicks Login button
    Then Login should fail
    And Error message should display: "The username and password could not be verified."

  @Regression
  Scenario: Login fails with blank username
    When User enters username as blank
    And User enters password "password"
    And User clicks Login button
    Then Login should fail
    And Error message should display appropriate validation error

  @Regression
  Scenario: Login fails with blank password
    When User enters username "demo1"
    And User enters password as blank
    And User clicks Login button
    Then Login should fail
    And Error message should display appropriate validation error

