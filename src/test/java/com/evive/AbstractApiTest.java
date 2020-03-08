package com.evive;

import com.aventstack.extentreports.ExtentTest;
import com.evive.config.ConfigProperties;
import com.evive.config.PropertiesInitializer;
import com.evive.reports.ExtentTestNGITestListenerAPI;
import com.evive.services.ApiService;
import com.evive.services.JsonUtil;
import com.evive.services.TestResetApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;

@ContextConfiguration(classes = Application.class, initializers = PropertiesInitializer.class)
@Slf4j
public abstract class AbstractApiTest extends AbstractTestNGSpringContextTests {
    public final String SMOKE = "SMOKE";
    public final String ACCEPTANCE = "ACCEPTANCE";

    @Autowired
    protected JsonUtil jsonUtil;
    @Autowired
    protected ConfigProperties configProperties;
    @Autowired
    protected ApiService apiService;
    @Autowired
    protected ExtentTestNGITestListenerAPI extentTestNGITestListenerAPI;
    @Autowired
    protected TestResetApiService testResetApiService;


    protected abstract void setBaseUri();

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext iTestContext) {
        ExtentTest parent = ExtentTestNGITestListenerAPI.extent.createTest(iTestContext.getSuite().getName());
        ExtentTestNGITestListenerAPI.parentTest.set(parent);
    }

}
