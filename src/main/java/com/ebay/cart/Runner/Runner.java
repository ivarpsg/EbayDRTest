package com.ebay.cart.Runner;


import com.ebay.cart.Base.BrowserManager;
import com.ebay.cart.util.CucumberReport;
import com.vimalselvam.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/java/com/ebay/cart/features"},glue = {"com.ebay.cart.Steps"},
        plugin = {"com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:target/extent-reports/report.html",
                "pretty","html:target/cucumber-html/cucumber-pretty","json:target/cucumber.json"},tags = "@regression")

public class Runner {
    @AfterClass
    public static void writeExtentReport() {
        Reporter.loadXMLConfig(new File(CucumberReport.getReportConfigPath()));
        CucumberReport.main(null);

       /* final String browser = BrowserManager.prop.getProperty("browser").toUpperCase();
        final String env = BrowserManager.prop.getProperty("env").toUpperCase();

        // Loads the extent config xml to customize on the report.
        Reporter.loadXMLConfig(new File(System.getProperty("user.dir")+"//src//main//resources//config//extent-report.xml"));
        // User can add the system information as follows
        Reporter.setSystemInfo("Browser", browser);
        Reporter.setSystemInfo("Environment", env);
        //Reporter.addScreenCaptureFromPath("./screenshots/" + scenario.getName()+ ".png");
        CucumberReport.TestReports();
        // Reporter.loadXMLConfig(new File(CucumberReport.getReportConfigPath()));*/
    }
}
