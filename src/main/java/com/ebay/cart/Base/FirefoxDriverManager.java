package com.ebay.cart.Base;



import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;

import java.io.File;

import static org.openqa.selenium.ie.InternetExplorerDriver.IGNORE_ZOOM_SETTING;

public class FirefoxDriverManager extends DriverManager {

    private GeckoDriverService gkService;

    @Override
    public void startService() {
        if (null == gkService) {
            try {
                gkService = new GeckoDriverService.Builder()
                        .usingDriverExecutable(new File("src/main/resources/Drivers/geckodriver.exe"))
                        .usingAnyFreePort()
                        .build();
                gkService.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stopService() {
        if (null != gkService && gkService.isRunning())
            gkService.stop();
    }

    @Override
    public void createDriver() {
        //DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("start-maximized");
        options.setCapability(IGNORE_ZOOM_SETTING,true);
        driver = new FirefoxDriver(gkService, options);
    }
}
