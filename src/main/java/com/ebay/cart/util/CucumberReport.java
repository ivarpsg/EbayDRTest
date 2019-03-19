package com.ebay.cart.util;



import com.ebay.cart.Base.BrowserManager;
import com.galenframework.config.GalenConfig;
import com.galenframework.config.GalenProperty;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.FileTempStorage;
import com.galenframework.support.GalenReportsContainer;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class CucumberReport extends BrowserManager {

    public static String getReportConfigPath(){
        String reportConfigPath = System.getProperty("user.dir")+"//src//main//resources//config//extent-report.xml";
        if(reportConfigPath!= null) return reportConfigPath;
        else throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
    }


    static File reportOutputDirectory=new File("target/cucumber-parallel/consolidated-report");
    static File reportDirectory=new File("target/cucumber-parallel");
    private static final Logger LOGGER=Logger.getLogger(CucumberReport.class.getName());
    String Browser;
    public static void main(String [] args){


        ArrayList<String> jsonFiles=new ArrayList<>();
        File[] files=reportDirectory.listFiles((d,name) -> name.endsWith(".json"));
        for(File s:files){
            jsonFiles.add(s.toString());
        }

        try {
            prop = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//main//resources//config//config.properties");
            prop.load(fis);
        } catch (Exception e) {
            System.out.println("Error in initializing Properties file");
        }
        String projecName="CWP Report";
        final String BrowserName = "Browser";
        final String Environment="Environment";
        final String env=prop.getProperty("env").toUpperCase();
        final String browser = prop.getProperty("browser").toUpperCase();
        Configuration configuration=new Configuration(reportOutputDirectory,projecName);
        configuration.addClassifications(BrowserName,browser);
        configuration.addClassifications(Environment,env);
        ReportBuilder reportBuilder=new ReportBuilder(jsonFiles,configuration);

        try {
            Reportable result = reportBuilder.generateReports();
            TestReports();
            LOGGER.debug("Report generated");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void TestReports(){
        List<GalenTestInfo> objGalentestsList= GalenReportsContainer.get().getAllTests();
        try {
            System.out.println(objGalentestsList);
            new HtmlReportBuilder().build(objGalentestsList, GalenConfig.getConfig().readProperty(GalenProperty.TEST_JAVA_REPORT_OUTPUTFOLDER));
            cleanData(objGalentestsList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static void cleanData(List<GalenTestInfo> testInfos) {
        for (GalenTestInfo testInfo : testInfos) {
            if (testInfo.getReport() != null) {
                try {
                    FileTempStorage storage = testInfo.getReport().getFileStorage();
                    if (storage != null) {
                        storage.cleanup();
                    }
                } catch (Exception e) {
                    System.out.println("Unknown error during report cleaning");
                }
            }
        }
    }
}


