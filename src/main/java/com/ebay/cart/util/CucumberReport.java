package com.ebay.cart.util;


import com.cucumber.listener.ExtentCucumberFormatter;
import com.cucumber.listener.Reporter;
import com.ebay.cart.Base.BrowserManager;
import com.relevantcodes.extentreports.DisplayOrder;
import com.cucumber.listener.ExtentCucumberFormatter;
import com.relevantcodes.extentreports.NetworkMode;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CucumberReport extends BrowserManager {

    public static String getReportConfigPath() {
        String reportConfigPath = System.getProperty("user.dir") + "//src//main//resources//extent-config.xml";
        if (reportConfigPath != null) return reportConfigPath;
        else
            throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
    }

    static File reportOutputDirectory = new File("target/cucumber-parallel/consolidated-report");
    static File reportDirectory = new File("target/cucumber-parallel");


    public static void main(String[] args) {


        ArrayList<String> jsonFiles = new ArrayList<>();
        File[] files = reportDirectory.listFiles((d, name) -> name.endsWith(".json"));
        for (File s : files) {
            jsonFiles.add(s.toString());
        }

        String projecName = "Ebay";
        Configuration configuration = new Configuration(reportOutputDirectory, projecName);
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);

        try {
            Reportable result = reportBuilder.generateReports();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


}