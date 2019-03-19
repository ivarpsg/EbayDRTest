package com.ebay.cart.Galen;


import com.ebay.cart.Base.BrowserManager;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;

public class GalenTestPage extends BrowserManager {

@Test
    public void SetupScreensize(){

    driver.manage().window().setSize(new Dimension(1500,200));
}

}
