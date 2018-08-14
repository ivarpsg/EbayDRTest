package com.ebay.cart.pages;

import com.ebay.cart.Base.BrowserManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CommonPage extends BrowserManager {

    public CommonPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver,this);
    }
}
