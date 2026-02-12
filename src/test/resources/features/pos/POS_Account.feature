@Positive @Account
Feature: Positive Account Management Scenarios
  As a logged-in user
  I want to manage my accounts
  So that I can perform account operations and view account details

  Background:
    Given User is logged into the application
    And User is on the home page

  @Regression
  Scenario: Open new account successfully
    When User navigates to "Open New Account" page
    And User selects a base account from available accounts
    And User selects account type "CHECKING" or "SAVINGS"
    Then New account should be created successfully
    And Created account number should be visible

  @Regression
  Scenario: Verify new account details
    Given User has created a new account
    When User navigates to "Accounts Overview" page
    And User selects the created account
    Then Account details should match the creation parameters
      | Account Type | CHECKING |
      | Balance      | $100.00  |

  @Regression
  Scenario: View all accounts overview
    When User navigates to "Accounts Overview" page
    Then All user accounts should be displayed
    And Account information should include:
      | Account Number |
      | Account Type   |
      | Balance        |
      | Available      |

  @Regression
  Scenario: Verify default minimum balance
    Given User has created a new account
    When User checks the account balance
    Then Balance should equal the minimum balance set in Admin panel
    And Balance should be "$100.00" by default

