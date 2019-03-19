package com.ebay.cart.Base;


import com.ebay.cart.util.util;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteDriverManager extends DriverManager{

    private static final String USERNAME = "";
    private static final String ACCESS_KEY = "";
    private static final String sauceUrl="https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
    public static String sauceTunnel_name = "";
    DesiredCapabilities capability = new DesiredCapabilities();
    @Override
    protected void startService() {

        //util util=new util();
        util.readConfigfile();
        capability.setBrowserName(util.prop.getProperty("RemoteBrowser"));
        capability.setCapability("version",util.prop.getProperty("version"));
        capability.setCapability("platform",util.prop.getProperty("platform"));
        capability.setCapability("screenResolution", util.prop.getProperty("resolution"));
        capability.setCapability("parentTunnel", sauceTunnel_name);

    }

    @Override
    protected void stopService() {

    }

    @Override
    protected void createDriver() throws MalformedURLException {

        driver = new RemoteWebDriver(new URL(sauceUrl),capability);


    }
}
