@Positive @Transaction
Feature: Positive Transaction Scenarios
  As a logged-in user
  I want to view and manage my transactions
  So that I can track my account activity

  Background:
    Given User is logged into the application
    And User has performed multiple transactions
    And User is on the home page

  @Regression
  Scenario: View transaction history
    When User navigates to "Accounts Overview" page
    And User selects an account with transactions
    Then Transaction history should be displayed
    And Transaction details should include:
      | Transaction Date |
      | Amount          |
      | Debit/Credit    |
      | Balance         |

  @Regression
  Scenario: Find transactions by date range
    When User navigates to "Find Transactions" page
    And User selects date range
    And User enters start date "01/01/2025"
    And User enters end date "12/31/2025"
    And User clicks Find Transactions button
    Then Transactions within date range should be displayed
    And Total transaction count should be accurate

  @Regression
  Scenario: Find transactions by amount
    When User navigates to "Find Transactions" page
    And User searches for transactions with amount "$100.00"
    Then Matching transactions should be displayed
    And Each transaction should show exact amount

  @Regression
  Scenario: Verify transaction balance calculation
    Given Account initial balance is "$500.00"
    And Transaction amount is "$100.00"
    When Transaction is processed
    Then Account balance should be updated correctly
    And Transaction history should reflect the change

