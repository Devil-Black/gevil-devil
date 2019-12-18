package com.gevil.webTest;


import com.gevil.excutor.BasePageX;
import com.gevil.Utils.BrowserUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaiduTest {
    String browserName = "";
    String url = "";
    WebDriver webDriver;
    @Test
    public void MenuDemo() throws Exception {
        browserName = "chrome";
        url = "https://t-hestia.gaodun.com/login";
        webDriver = BrowserUtil.setDriver(browserName,url);
        BasePageX basePageX = new BasePageX(webDriver,"loginPage", "hestia.xml");
        basePageX.sendKeys("账户","gongqiangwei@gaodun.com");
        basePageX.sendKeys("密码","123456");
        basePageX.click("");

    }




}
