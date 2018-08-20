package com.ebay.cart.Steps;

import com.ebay.cart.Base.BrowserManager;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import javafx.scene.layout.BackgroundRepeat;

public class Hooks extends BrowserManager {

    @Before
    public void openBrowser(){

        launchBrowser();

    }

    @After
    public void quitdriver(){
        teardown();
    }
}
