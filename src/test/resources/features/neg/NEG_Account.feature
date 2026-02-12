@Negative @Account
Feature: Negative Account Management Scenarios
  As a logged-in user
  I want validation for invalid account operations
  So that the system prevents invalid account creation and manipulation

  Background:
    Given User is logged into the application
    And User is on the home page

  @Regression
  Scenario: Cannot create account without base account selection
    When User navigates to "Open New Account" page
    And User does not select a base account
    And User selects account type "CHECKING"
    And User clicks Create button
    Then Account creation should fail
    And Error message should display: "Please select a base account."

  @Regression
  Scenario: Cannot create account without account type selection
    When User navigates to "Open New Account" page
    And User selects a base account
    And User does not select account type
    And User clicks Create button
    Then Account creation should fail
    And Error message should display: "Please select an account type."

  @Regression
  Scenario: Account details should not match with different parameters
    Given User has created account with type "CHECKING"
    When User views account details
    And Account type is different from created type
    Then System should show correct account type
    And Details should match actual creation parameters

  @Regression
  Scenario: Duplicate account creation should be prevented
    Given User has created an account
    When User attempts to create account with same parameters again
    Then System should handle duplicate creation appropriately
    And Should either prevent or flag the duplicate

  @Regression
  Scenario: Cannot view account without valid account selection
    When User navigates to "Accounts Overview" page
    And User does not select any account
    And User clicks View Details button
    Then View Details should fail or show no information
    And Error message or no data should be displayed

  @Regression
  Scenario: Account balance should not be negative without loan
    Given User has created a regular account
    When User checks the account balance
    Then Account balance should be equal to minimum balance
    And Account balance should not be negative

