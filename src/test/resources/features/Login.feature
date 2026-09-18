Feature: Login into application

  @Smoke
  Scenario Outline: Login functionality
    When user enters "<username>" and "<password>"
    Then user should see "<result>"

    Examples:
      | username        | password     | result                                               |
      | standard_user   | secret_sauce | Products                                             |
      | locked_out_user | secret_sauce | Epic sadface: Sorry, this user has been locked out.  |
