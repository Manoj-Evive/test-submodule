package com.evive.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.evive.enums.TestCaseStatus;
import com.evive.services.annotations.Blocked;
import com.evive.services.annotations.Manual;
import com.evive.services.annotations.Skipped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import java.lang.annotation.Annotation;

//Extent Report Declarations

@Service
@Slf4j
public class ExtentTestNGITestListenerAPI implements ITestListener {

    public static final ExtentReports extent = ExtentManager.createInstance();
    public static final ThreadLocal<ExtentTest> parentTest = new ThreadLocal<>();
    protected static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    protected static final ThreadLocal<ExtentTest> grandChild = new ThreadLocal<>();

    @Override
    public synchronized void onStart(ITestContext iTestContext) {
        if (parentTest.get() == null) {
            ExtentTest parent = ExtentTestNGITestListenerAPI.extent.createTest(iTestContext.getSuite().getName());
            ExtentTestNGITestListenerAPI.parentTest.set(parent);
        }
        ExtentTest child = parentTest.get().createNode(iTestContext.getName());
        test.set(child);
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult iTestResult) {
        ExtentTest child = test.get()
                .createNode(iTestResult.getMethod().getDescription());
        grandChild.set(child);

        Annotation skippedAnnotation = getAnnotation(iTestResult, Skipped.class);
        Annotation blockedAnnotation = getAnnotation(iTestResult, Blocked.class);
        Annotation manualAnnotation = getAnnotation(iTestResult, Manual.class);
        if ((manualAnnotation != null || skippedAnnotation != null || blockedAnnotation != null) && null == iTestResult.getThrowable()) {
            throw new SkipException("");
        }
    }

    @Override
    public synchronized void onTestSuccess(ITestResult iTestResult) {
        grandChild.get().pass("Test passed");
    }

    @Override
    public synchronized void onTestFailure(ITestResult iTestResult) {
        grandChild.get().fail(iTestResult.getThrowable().getMessage());
    }

    @Override
    public synchronized void onTestSkipped(ITestResult iTestResult) {
        Annotation skippedAnnotation = getAnnotation(iTestResult, Skipped.class);
        Annotation blockedAnnotation = getAnnotation(iTestResult, Blocked.class);
        Annotation manualAnnotation = getAnnotation(iTestResult, Manual.class);
        if (manualAnnotation != null) {
            Manual manual = (Manual) manualAnnotation;
            TestCaseStatus status = manual.status();
            String message = manual.message();

            switch (status) {
                case PASSED:
                    grandChild.get().pass(message);
                    break;
                case FAILED:
                    grandChild.get().fail(message);
                    break;
                case SKIPPED:
                    grandChild.get().skip(message);
                    break;
                case BLOCKED:
                    grandChild.get().warning(message);
                    break;
            }
        } else if (skippedAnnotation != null) {
            Skipped skipped = (Skipped) skippedAnnotation;
            String message = skipped.reason();
            getGrandChild().get().skip(message);
        } else if (blockedAnnotation != null) {
            Blocked blocked = (Blocked) blockedAnnotation;
            String message = blocked.reason();
            getGrandChild().get().warning(message);
        } else {
            grandChild.get().pass("Test passed");
        }
    }

    @Override
    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.info(("onTestFailedButWithinSuccessPercentage for " + result.getMethod()
                .getMethodName()));
    }

    private Annotation getAnnotation(ITestResult iTestResult, Class tClass) {
        return iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(tClass);
    }

    public ThreadLocal<ExtentTest> getGrandChild() {
        return grandChild;
    }
}