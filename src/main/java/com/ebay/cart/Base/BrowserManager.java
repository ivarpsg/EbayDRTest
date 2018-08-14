package com.ebay.cart.Base;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.util.Properties;


public class BrowserManager {


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

    protected void launchBrowser() {
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
    protected void navigateTo(String url){
        driver.get(url);
    }
    protected boolean isElementPresent(WebElement element) {
        boolean value=false;
        try {
            if(element.isDisplayed()){
                value=true;
            }} catch(NoSuchElementException e) {
            e.printStackTrace();
        }
        return value;
    }




}

