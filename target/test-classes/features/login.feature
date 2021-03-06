Feature: Login functionality
  As a user
  I want to sign in to the site
  In order to make online orders

  Scenario: Login with valid credentials
    Given User opens Home page
    When User signs in with login luckyinvestor.info@gmail.com and password 9119112
    Then User signs in successfully

  Scenario Outline: Login with invalid credentials
    Given User opens Home page
    When User signs in with login <login> and password <password>
    Then Error message <error message> appears
    Examples:
      | login                | password | error message                                               |
      | olegmarket@gmail.com | -        | Password is required.                                       |
      |                      | lucky125 | Email address is required.                                  |
      | 123qwer.com          | lucky125 | Please enter a valid email address.                         |
      |                      | -        | Email address is required. * Password is required.          |
      | 123qwer.com          | -        | Please enter a valid email address. * Password is required. |
