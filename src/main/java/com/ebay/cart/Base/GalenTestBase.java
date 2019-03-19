package com.ebay.cart.Base;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.TestReport;
import com.galenframework.reports.model.LayoutReport;
import com.galenframework.speclang2.pagespec.SectionFilter;
import com.galenframework.support.GalenReportsContainer;
import com.galenframework.support.LayoutValidationException;
import com.galenframework.utils.GalenUtils;
import cucumber.api.Scenario;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public abstract class GalenTestBase extends Galen{

    protected ThreadLocal<WebDriver> driver = new ThreadLocal();
    protected ThreadLocal<TestReport> report = new ThreadLocal();
    protected ThreadLocal<GalenTestInfo> testInfo = new ThreadLocal();
    Scenario scenario;
    public GalenTestBase() {
        WebDriver driver=BrowserManager.driver;
        this.driver.set(driver);
        this.scenario=BrowserManager.scenario;
    }

    public void initDriver(Object[] args) {


    }


    public TestReport getReport() {
        TestReport report = this.report.get();
        if (report == null) {
            throw new RuntimeException("The report is not instantiated yet");
        } else {
            return report;
        }
    }

    public GalenTestInfo createTestInfo(Method method, Object[] arguments) {
        return GalenTestInfo.fromMethod(method, arguments);
    }

    public void load(String url) {
        this.getDriver().get(url);
    }

    public void load(String url, int width, int height) {
        this.load(url);
        this.resize(width, height);
    }
    public void resize(int width, int height) {
        this.getDriver().manage().window().setSize(new Dimension(width, height));
    }
    public void inject(String javaScript) {
        GalenUtils.injectJavascript(this.getDriver(), javaScript);
    }


    public void checkLayout(String spec, List<String> includedTags,String fileName) throws IOException {
        String title = "Layout Validated in page " +fileName ;
        this.initReport();
        LayoutReport layoutReport = Galen.checkLayout(this.getDriver(), spec,includedTags);
        this.getReport().layout(layoutReport, title);
        if (layoutReport.errors() > 0) {
            throw new LayoutValidationException(spec, layoutReport, null);
        }
    }

    public void checkLayout(String specPath, SectionFilter sectionFilter, Properties properties, Map<String, Object> vars) throws IOException {
        String title = "Check layout " + specPath;
        this.initReport();
        LayoutReport layoutReport = Galen.checkLayout(this.getDriver(), specPath, sectionFilter, properties, vars);
        this.getReport().layout(layoutReport, title);
        if (layoutReport.errors() > 0) {
            throw new LayoutValidationException(specPath, layoutReport, sectionFilter);
        }
    }
    public WebDriver getDriver() {
        WebDriver driver = this.driver.get();
        if (driver == null) {
            throw new RuntimeException("The driver is not instantiated yet");
        }
        return driver;
    }

    public void initReport(){
        GalenTestInfo ti = GalenTestInfo.fromString(scenario.getName());
        testInfo.set(ti);
        report.set(GalenReportsContainer.get().registerTest(ti));

    }
}
