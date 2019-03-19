package com.ebay.cart.Base;

import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public abstract class DriverManager {

    protected WebDriver driver;
    protected abstract void startService();
    protected abstract void stopService();
    protected abstract void createDriver() throws MalformedURLException;

    public void quitDriver() {
        if (null != driver) {
            driver.quit();
            driver = null;
        }

    }
    public WebDriver getDriver() {
        if (null == driver) {
            startService();
            try {
                createDriver();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return driver;
    }
}
