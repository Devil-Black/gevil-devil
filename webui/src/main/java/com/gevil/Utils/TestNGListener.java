package com.gevil.Utils;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;


public class TestNGListener extends TestListenerAdapter {

    private static WebDriver driver;
    LogUtil logUtil = new LogUtil(TestNGListener.class);
    private ITestResult testResult;

    public static void setDriver(WebDriver driver){
        TestNGListener.driver = driver;
    }

    /**
     * 用例执行结束后，用例执行成功时调用
     * @param testResult
     */
    @Override
    public void onTestSuccess(ITestResult testResult){
        logUtil.info("测试步骤成功完成。"+"------Test Success!");
        Reporter.log("测试步骤成功完成。"+"------Test Success!");
        super.onTestSuccess(testResult);
    }

    /**
     * 用例执行结束后，用例执行失败时调用
     * @param testResult
     */
    @Override
    public void onTestFailure(ITestResult testResult){
        logUtil.info("测试步骤执行失败。"+"------Test Success!");
        Reporter.log("测试步骤执行失败。"+"------Test Success!");
        super.onTestFailure(testResult);
    }

    /**
     * 用例执行结束后，用例执行skip时调用
     * @param testResult
     */
    @Override
    public void onTestSkipped(ITestResult testResult) {
        logUtil.error("测试执行步骤跳过。"+"------Test Skipped！");
        Reporter.log("测试执行步骤跳过。"+"------Test Skipped！");
        super.onTestSkipped(testResult);
    }

    public void onTestStart(ITestResult testResult){
        logUtil.error("------测试开始。内容："+testResult.getMethod().getDescription()+"------Start!");
        Reporter.log("------测试开始。内容："+testResult.getMethod().getDescription()+"------Start!");
        super.onTestStart(testResult);
    }
    public void onFinish(ITestContext testContext){
        logUtil.error("------结束"+"------Test Finish");
        Reporter.log("------结束"+"------Test Finish");
        super.onFinish(testContext);
    }

}
