package com.ebay.cart.Runner;


import com.ebay.cart.Base.BrowserManager;
import com.vimalselvam.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterClass;

import java.io.File;

@CucumberOptions(
        features = {"src/test/java/com/telstra/cwp/features"},glue = {"com.telstra.cwp.Steps"},
        plugin = {"com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:target/extent-reports/report.html",
                "pretty","html:target/cucumber-html/cucumber-pretty","json:target/cucumber.json"},
        tags = "@Galen")


public class RunnerTestNg extends AbstractTestNGCucumberTests {

    @AfterClass
    public static void writeReport() {

        final String browser = BrowserManager.prop.getProperty("browser").toUpperCase();
        final String env = BrowserManager.prop.getProperty("env").toUpperCase();

        // Loads the extent config xml to customize on the report.
        Reporter.loadXMLConfig(new File(System.getProperty("user.dir")+"//src//main//resources//config//extent-report.xml"));
        // User can add the system information as follows
        Reporter.setSystemInfo("Browser", browser);
        Reporter.setSystemInfo("Environment", env);

    }

   /* @BeforeClass
    public static void intReport(){
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmmss");
        Date curDate = new Date();
        String strDate = sdf.format(curDate);
        String fileName = System.getProperty("user.dir")+"\\target\\Extent_Reports\\" + strDate+".html";
        ExtentReports extentReports=new ExtentReports();
        ExtentHtmlReporter htmlReporter=new ExtentHtmlReporter(fileName);

    }*/
}