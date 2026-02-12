@Negative @LoanProcessing
Feature: Negative Loan Processing Scenarios
  As a logged-in user
  I want validation for invalid loan applications
  So that the system prevents invalid loan requests

  Background:
    Given User is logged into the application
    And User has created a new account
    And User is on the home page

  @Regression
  Scenario: Loan application fails with invalid loan amount
    When User navigates to "Request Loan" page
    And User enters loan amount as invalid value
    Then Loan application should fail
    And Error message should be displayed

  @Regression
  Scenario: Loan application fails with insufficient funds
    Given Account balance is "$100.00"
    When User applies for loan with amount "$100000.00"
    Then Loan application should be rejected
    And Error message should indicate insufficient account balance

  @Regression
  Scenario: Loan application fails with down payment greater than loan amount
    When User navigates to "Request Loan" page
    And User enters loan amount "$5000.00"
    And User enters down payment "$6000.00"
    Then Loan application should fail
    And Error message should indicate invalid down payment amount

  @Regression
  Scenario: Loan application fails with blank loan amount
    When User navigates to "Request Loan" page
    And User leaves loan amount blank
    And User enters down payment "$1000.00"
    And User submits loan application
    Then Loan application should fail
    And Error message should display: "Loan amount is required."

  @Regression
  Scenario: Loan application fails with blank down payment
    When User navigates to "Request Loan" page
    And User enters loan amount "$5000.00"
    And User leaves down payment blank
    And User submits loan application
    Then Loan application should fail
    And Error message should display: "Down payment is required."

  @Regression
  Scenario: Loan application fails with invalid account selection
    When User navigates to "Request Loan" page
    And User enters valid loan amount "$5000.00"
    And User enters valid down payment "$1000.00"
    And User does not select an account for deposit
    And User submits loan application
    Then Loan application should fail
    And Error message should display: "Please select an account."

  @Regression
  Scenario: Loan application fails with negative loan amount
    When User navigates to "Request Loan" page
    And User enters loan amount "-$5000.00"
    Then Loan application should fail
    And Error message should indicate invalid amount

  @Regression
  Scenario: Loan balance does not increase without successful application
    Given Loan application was rejected
    When User checks accounts overview
    Then No new loan account should be created
    And Original account balance should remain unchanged

