Feature: User Login

  Scenario: Successful Login
    Given the user provides the following credentials:
      | loginType   | PHONE_NUMBER_AND_PASSWORD |
      | phoneCode   | +91                       |
      | phoneNumber | 7000017679                |
      | password    | 1234                      |
      | deviceId    | RZ8T81Q01DA               |
      | language    | en                        |
      | fcmToken    | FCM TOKEN                 |
      | appVersion  | 12.12                     |
      | osVersion   |                           |
      | platform    | android                   |
    When user send a POST request to "/customers/api/v1/auth/login"
    Then the response status code should be 200
    And  the response should contain data


  Scenario: verify the code
    Given user provides the following  details
      | sessionId |                           |
      | code      | 1234                      |
      | type      | PHONE_NUMBER_VERIFICATION |
    When  user send a post request to "/customers/api/v1/auth/verifyCode"
    Then  the response status coe should be 200
    And   user valid the contain data
#
#  Scenario: home page
#    Given user provides the following  details for home page
#      | userId |  |
#


