package com.evive.reports;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.springframework.stereotype.Service;

@Service
public class ExtentManager {

    static ExtentReports createInstance() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent.html");
        htmlReporter.config().enableTimeline(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("extent.html");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("extent.html");
        htmlReporter.config().setCSS(".test-status.skip {color : #009dff ;}");
        htmlReporter.config().setProtocol(Protocol.HTTPS);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
        ExtentReports extent = new ExtentReports();
        extent.setAnalysisStrategy(AnalysisStrategy.SUITE);
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Tester Name", "Shubham Purwar");
        extent.setSystemInfo("environment", "Prod");
        extent.setSystemInfo("Browser", "Chrome");
        return extent;

    }
}
