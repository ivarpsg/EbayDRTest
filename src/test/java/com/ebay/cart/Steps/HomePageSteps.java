package com.ebay.cart.Steps;

import com.ebay.cart.pages.HomePage;
import com.ebay.cart.pages.SearchResultsPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HomePageSteps extends Steps{
   HomePage homePage=new HomePage(driver);
   SearchResultsPage searchResultsPage=new SearchResultsPage(driver);

    @Given("^user launches url \"([^\"]*)\"$")
    public void userLaunchesUrl(String url)  {
    navigate(url);

    }

    @Then("^user adds the best match from the list of search results to the cart$")
    public void userAddsTheBestMatchFromTheListOfSearchResultsToTheCart()  {
        searchResultsPage.clickFirstelement();
        searchResultsPage.ClickAddCartButton();
    }

    @And("^user confirms the products added is displayed in view cart page$")
    public void userConfirmsTheProductsAddedIsDisplayedInViewCartPage() {
        searchResultsPage.verifyItemsAdded();
    }

    @When("^user searches for \"([^\"]*)\"$")
    public void userSearchesFor(String product)  {
    homePage.searchItem(product);
    }
}
