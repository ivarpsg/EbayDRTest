Feature: Add and search item in ebay

  Scenario:To validate user is able to search and add iphone & sony TV to cart
    Given user launches url "ebay"
    When user searches for "iphone 64GB"
    Then user adds the best match from the list of search results to the cart
    And user searches for "cricket bat"
    And user adds the best match from the list of search results to the cart
    And user confirms the products added is displayed in view cart page
