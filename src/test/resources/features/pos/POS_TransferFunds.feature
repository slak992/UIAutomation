@Positive @TransferFunds
Feature: Positive Transfer Funds Scenarios
  As a logged-in user
  I want to transfer funds between my accounts
  So that I can manage my money effectively

  Background:
    Given User is logged into the application
    And User has multiple accounts created
    And User is on the home page

  @Regression
  Scenario: Transfer funds successfully
    Given Source account has sufficient balance
    When User navigates to "Transfer Funds" page
    And User selects source account from dropdown
    And User selects destination account from dropdown
    And User enters transfer amount
    Then Transfer should be completed successfully
    And Transfer confirmation message should be displayed

  @Regression
  Scenario: Verify balance after transfer
    Given User has transferred funds between accounts
    And Source account balance before transfer was "$500.00"
    And Transfer amount was "$100.00"
    When User checks the source account balance
    Then Source account balance should be reduced by transfer amount
    And Source account balance should be "$400.00"

  @Regression
  Scenario: Verify destination account balance after transfer
    Given User has transferred funds to destination account
    And Destination account balance before transfer was "$100.00"
    And Transfer amount was "$100.00"
    When User checks the destination account balance
    Then Destination account balance should be increased by transfer amount
    And Destination account balance should be "$200.00"

  @Regression
  Scenario: Transfer with formatted decimal amounts
    When User enters a transfer amount "99.99"
    And User clicks Transfer button
    Then Transfer should be processed with correct decimal formatting
    And Balance should reflect correct calculation

