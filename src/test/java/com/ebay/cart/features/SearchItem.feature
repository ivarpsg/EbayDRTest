Feature: Add and search item in ebay
@Searchproduct1 @search
  Scenario Outline: search and add iphone & sony TV to cart
    Given user launches url "ebay"
    When user searches for "<item1>"
    Then user adds the best match from the list of search results to the cart
    And user searches for "<item2>"
    And user adds the best match from the list of search results to the cart
    And user confirms the products added is displayed in view cart page

    Examples:
    |item1|item2|
    |iphone 64GB|sony tv|

  @Searchproduct2 @search
  Scenario Outline:To validate user is able to search and add iphone & sony TV to cart
    Given user launches url "ebay"
    When user searches for "<item1>"
    Then user adds the best match from the list of search results to the cart
    And user searches for "<item2>"
    And user adds the best match from the list of search results to the cart
    And user confirms the products added is displayed in view cart page

    Examples:
      |item1|item2|
      |tupperware|cricket bat|