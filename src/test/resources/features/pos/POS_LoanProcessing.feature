@Positive @LoanProcessing
Feature: Positive Loan Processing Scenarios
  As a logged-in user
  I want to apply for a loan
  So that I can get financial assistance

  Background:
    Given User is logged into the application
    And User has created a new account
    And User is on the home page

  @Regression
  Scenario: Apply for loan successfully
    When User navigates to "Request Loan" page
    And User enters loan amount "50000"
    And User enters down payment "10000"
    And User selects created account for loan deposit
    And User submits loan application
    Then Loan application should be processed successfully
    And Loan processed confirmation message should be displayed

  @Regression
  Scenario: Verify loan account created after application
    Given User has successfully applied for a loan
    When User navigates to "Accounts Overview" page
    Then A new loan account should be created
    And Loan account balance should equal the loan amount

  @Regression
  Scenario: Verify loan account balance matches loan amount
    Given User has applied for loan with amount "$50000.00"
    When User checks the created loan account details
    Then Loan account balance should be "$50000.00"
    And Loan account available balance should be "$50000.00"

  @Regression
  Scenario: Verify source account balance updated after loan application
    Given Original account balance was "$100.00"
    And User applied for loan with down payment "$10000.00"
    When User checks the original account balance
    Then Account balance should be reduced by down payment amount
    And Account balance should be "$-9900.00"
    And Loan status should be "Approved"
    And Loan processed date should match system date

  @Regression
  Scenario: Verify loan account type created
    Given User has applied for a loan
    When User navigates to loan account details
    Then Loan account type should be "SAVINGS"
    And Loan account should be visible in accounts overview

