/*
package com.ebay.cart.Runner;


import com.cucumber.listener.Reporter;
import com.telstra.vantage.util.CucumberReport;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/java/com/telstra/vantage/features"},glue = {"com.telstra.vantage.Steps"},
        plugin = {"pretty","html:target/cucumber-html-report","html:target/site/cucumber-pretty","json:target/cucumber.json",
                "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"})
public class Runner {
    @AfterClass
    public static void writeExtentReport() {
        Reporter.loadXMLConfig(new File(CucumberReport.getReportConfigPath()));
        CucumberReport.main(null);
    }
}
*/
