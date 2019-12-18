package com.gevil.Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Reporter;

import java.util.concurrent.TimeUnit;

public class BrowserUtil {

    static WebDriver webDriver;
    static LogUtil logUtil = new LogUtil(BrowserUtil.class);

    public static WebDriver setDriver(String browserName,String url){
        logUtil.info("读取执行xml配置"+browserName+"浏览器初始化");
        Reporter.log("读取执行xml配置"+browserName+"浏览器初始化");
        switch (browserName){
            case "firefox":
                //此处设置Firefox的webdriver地址
                System.setProperty("webdriver.gecko.driver",".\\libs\\webdriver\\geckodriver.exe");
                FirefoxProfile profile = new FirefoxProfile();
                //设置0代表下载到浏览器默认下载路径，设置2 则保存到指定路径
                profile.setPreference("browser.download.folderList",2);
                profile.setPreference("browser.download.dir",".\\firefox-download");
                //browser.helperApps.neverAsk.saveToDisk
                //指定要下载页面的 Content-type 值， “binary/octet-stream” 为文件的类型。
                //下载的文件不同，这里的类型也会有所不一样。如果不清楚你下载的文件什么类型，请用Fiddler抓包。
                profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.ms-excel");
                profile.setPreference("plugin.state.flash", 2);
                FirefoxOptions options = new FirefoxOptions();
                options.setProfile(profile);
                webDriver = new FirefoxDriver(options);
                webDriver.manage().window().maximize();
                //隐式等待
//              driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                logUtil.info("打开浏览器，访问"+url+"网址!");
                Reporter.log("打开浏览器，访问"+url+"网址!");
                webDriver.get(url);
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
                webDriver = new ChromeDriver();
                webDriver.manage().window().maximize();
                webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                logUtil.info("打开浏览器，访问"+url+"网址!");
                Reporter.log("打开浏览器，访问"+url+"网址!");
                webDriver.get(url);
                break;
            default:
                break;
        }
        return webDriver;

    }
    public static void quit() {
        webDriver.quit();
    }
}
