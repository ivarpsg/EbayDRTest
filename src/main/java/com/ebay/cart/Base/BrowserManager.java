package com.ebay.cart.Base;

import com.ebay.cart.util.util;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.util.Properties;

import static com.ebay.cart.util.util.readConfigfile;


public class BrowserManager extends util {

    private static DriverManager driverManager;
    public static WebDriver driver;

    public BrowserManager() {
        readConfigfile();
    }


    public void launchBrowser() {
        String BrowserType = prop.getProperty("browser").toUpperCase();
        driverManager = DriveraManagerFactory.getManager(DriverType.valueOf(BrowserType));

        driver = driverManager.getDriver();
        driver.manage().window().maximize();


    }

    public WebDriver getDriver() {
        if (driver == null) {
            launchBrowser();
        }
        return driver;
    }

    protected void teardown() {
        if (driver != null)
            driver.quit();
    }

    protected void navigate(String propURL) {
        String env = prop.getProperty("env");
        if (env.equalsIgnoreCase("prod")) {
            readpropfile("prod");
            driver.get(properties.getProperty(propURL));
        } else if (env.equalsIgnoreCase("uat")) {
            readpropfile("uat");
            driver.get(properties.getProperty(propURL));
        } else {
            readpropfile("fast");
            driver.get(properties.getProperty(propURL));
        }
    }

    protected void navigateTo(String url) {
        driver.get(url);
    }

}


