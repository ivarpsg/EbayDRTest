package com.ebay.cart.Steps;

import com.ebay.cart.Base.BrowserManager;
import org.openqa.selenium.WebDriver;

public class Steps extends BrowserManager{

    WebDriver driver;
    public Steps() {
        this.driver=BrowserManager.driver;
    }

}

