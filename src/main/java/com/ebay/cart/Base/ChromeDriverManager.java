package com.ebay.cart.Base;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

import static org.openqa.selenium.ie.InternetExplorerDriver.IGNORE_ZOOM_SETTING;

public class ChromeDriverManager extends DriverManager {

    private ChromeDriverService chService;

    @Override
    public void startService() {
        if (null == chService) {
            try {
                chService = new ChromeDriverService.Builder()
                        .usingDriverExecutable(new File("src/main/resources/Drivers/chromedriver.exe"))
                        .usingAnyFreePort()
                        .build();
                chService.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stopService() {
        if (null != chService && chService.isRunning())
            chService.stop();
    }

    @Override
    public void createDriver() {
        //DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.setCapability(IGNORE_ZOOM_SETTING,true);
        options.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(chService, options);
    }

}
