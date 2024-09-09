Feature: Get Change for Bill

  Background:
    Given the coin inventory is initialized
      | 0.25 | 100 |
      | 0.10 | 100 |
      | 0.05 | 100 |
      | 0.01 | 100 |

  Scenario: Get change for the bill given by user - success
    When the user requests change for the bill with the amount "5.0"
    Then the user should receive the response with "0.25" coins with count "20"
    And the inventory should be updated with the following coins:
      | 0.25 | 80  |
      | 0.10 | 100 |
      | 0.05 | 100 |
      | 0.01 | 100 |


  Scenario: Get change for the bill given by user multiple times - success
    When the user requests change for the bill with the amount "10.0"
    Then the user should receive the response with "0.25" coins with count "40"
    And the inventory should be updated with the following coins:
      | 0.25 | 60  |
      | 0.10 | 100 |
      | 0.05 | 100 |
      | 0.01 | 100 |
    When the user requests change for the bill with the amount "5.0"
    Then the user should receive the response with "0.25" coins with count "20"
    And the inventory should be updated with the following coins:
      | 0.25 | 40  |
      | 0.10 | 100 |
      | 0.05 | 100 |
      | 0.01 | 100 |

  Scenario: Get change for the bill given by user - invalid bill amount
    When the user requests change for the bill with the amount "15.0"
    Then we expect 400 response with message "Bill amount not supported."

  Scenario: Get change for the bill given by user - insufficient coins
    When the user requests change for the bill with the amount "100.0"
    Then we expect 422 response with message "Not enough coins to make exact change."



