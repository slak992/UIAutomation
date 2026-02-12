@Positive @Login
Feature: Positive Login Scenarios
  As a user
  I want to register and login to ParaBank
  So that I can access my account and perform transactions

  Background:
    Given User navigates to ParaBank application
    And ParaBank login page is displayed

  @Regression @Mobile
  Scenario: Register new user successfully
    When User registers a new account with valid details
      | firstName | lastName | address    | city    | state | zipCode | phoneNumber | ssn         | username | password |
      | testUser  | userTest | 123 Main   | NewYork | NY    | 10001   | 2125551234  | 123-45-6789 | testuser | Test@123 |
    Then User registration succeeds with success message displayed
    And User should be able to logout successfully

  @Regression
  Scenario: Login with valid credentials
    When User enters valid username and password
      | username | password |
      | demo1    | demo1    |
    Then User should be successfully logged into the application
    And User dashboard is displayed with account options

  @Regression @DataDriven
  Scenario Outline: Login with different user credentials
    When User enters username "<user>" and password "<password>"
    Then User should be successfully logged in
    Examples:
      | user  | password |
      | demo1 | demo1    |
      | demo2 | demo2    |
      | john  | john     |

  @Regression
  Scenario: Logout successfully
    Given User is logged into the application
    When User clicks logout link
    Then User should be logged out successfully
    And User is redirected to login page

