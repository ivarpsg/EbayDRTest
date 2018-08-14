package com.ebay.cart.Base;

public class DriveraManagerFactory {


    public static DriverManager getManager(DriverType type) {

        DriverManager driverManager;

        switch (type) {

            case FIREFOX:
                driverManager = new FirefoxDriverManager();
                break;
            default:
                driverManager = new ChromeDriverManager();
                break;
        }
        return driverManager;

    }
}
