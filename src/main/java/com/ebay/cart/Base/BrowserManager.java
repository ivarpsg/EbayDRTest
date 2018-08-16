package com.ebay.cart.Base;

import com.ebay.cart.util.Wrapper;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.FileInputStream;
import java.util.Properties;


public class BrowserManager extends Wrapper {


    private DriverManager driverManager;
    public static WebDriver driver;
    private Properties prop;


    public BrowserManager() {

        try {
            prop = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//main//resources//config.properties");
            prop.load(fis);
        } catch (Exception e) {
            System.out.println("Error in initializing Properties file");


        }
    }

    public void launchBrowser() {
        String BrowserType = prop.getProperty("browser").toUpperCase();


            driverManager = DriveraManagerFactory.getManager(DriverType.valueOf(BrowserType));
            driver = driverManager.getDriver();
            driver.manage().window().maximize();
        }

    public WebDriver getDriver(){
        if(driver==null){
            launchBrowser();}
        return driver;
    }

    protected void teardown() {

        driverManager.quitDriver();
    }

    protected void navigate(String URL) {
        driver.get(prop.getProperty(URL));
    }
    protected void navigateToURL(String url){
        driver.get(url);
    }






}

