package com.gevil.webTest;

import com.gevil.excutor.BasePageX;
import com.gevil.Utils.BrowserUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class goldenfinanceTest {
    String browserName = "";
    String url = "";
    WebDriver webDriver;

    @Test
    public void goldenEvent() throws Exception {
        browserName = "chrome";
        url = "https://www.goldenfinance.com.cn/home/events/index.html";
        webDriver = BrowserUtil.setDriver(browserName,url);
        BasePageX basePageX = new BasePageX(webDriver,"golenEvents", "PageXml.xml");
        basePageX.click("首页");
        basePageX.click("选课中心");
        basePageX.click("学习平台");
        basePageX.click("研究中心");
        basePageX.click("高顿活动");
        basePageX.click("财税资讯");
        basePageX.click("关于高顿");

    }


}
