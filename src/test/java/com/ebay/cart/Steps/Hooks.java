package com.ebay.cart.Steps;

import com.ebay.cart.Base.BrowserManager;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import javafx.scene.layout.BackgroundRepeat;

import java.io.IOException;

public class Hooks extends BrowserManager {

    @Before("~@Ec2")
    public void openBrowser(){

        launchBrowser();

    }

    @After()
    public void clean() throws IOException {
        /*System.out.println(scenario.isFailed());
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            //File screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            scenario.embed(screenshot, "image/png");

            //File destinationPath = new File(System.getProperty("user.dir") + "/target/extent-reports/screenshots/" + scenario.getName()+ ".png");
            //copyFile(screenshot, destinationPath);

        }*/
        teardown();
    }
}
