package com.ebay.cart.Steps;

import com.ebay.cart.Base.BrowserManager;
import com.ebay.cart.pages.CommonPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;

public class Steps extends BrowserManager{

    CommonPage commonPage=new CommonPage();
    @Given("^User is navigated to \"([^\"]*)\" page$")
    public void user_is_navigated_to_page(String page) {
        commonPage.GotoPage(page);
    }

    @And("^user launches url \"([^\"]*)\"$")
    public void userLaunchesUrl(String url){

        if (verifyUrl(url)){
            navigateTo(url);
        }else{
            navigate(url);

        }
    }

    @When("^user launches the application with dimension$")
    public void userLaunchesTheApplicationWithDimension() throws Throwable {

    }


    @Given("^user launches browser with url \"([^\"]*)\"$")
    public void userLaunchesBrowserWithUrl(String arg0) throws Throwable {

    }

    @Given("^user launches \"([^\"]*)\" with version \"([^\"]*)\" in a os \"([^\"]*)\"$")
    public void userLaunchesWithVersionInAOs(String browser, String version, String os)  {


    }
}

