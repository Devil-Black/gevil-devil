package com.gevil.excutor.impl;

import com.gevil.Utils.LogUtil;
import com.gevil.position.Position;
import com.gevil.excutor.UIExcutor;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import java.util.Set;

public class UIExcutorImpl implements UIExcutor {

    private WebDriver webDriver;
    LogUtil logUtil = new LogUtil(UIExcutor.class);

    public UIExcutorImpl(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    //对接口方法进行重写，使用log记录，并加入report中

    /**
     * 点击事件
     * @param position
     */
    @Override
    public void click(Position position) {

        WebElement webElement = getElement(position);
        webElement.click();
        logUtil.info("click元素"+position.getPositionName()+"["+position.getType()+":"+position.getPath()+"] success!");
        //在report中显示响应的log
        Reporter.log("click元素"+position.getPositionName()+"["+position.getType()+":"+position.getPath()+"] success!");
    }

    /**
     * 进行文本输入
     * @param position
     * @param value
     */
    @Override
    public void sendKeys(Position position,String value) {

        WebElement webElement = getElement(position);
        webElement.sendKeys(value);
        logUtil.info("input输入："+position.getPositionName()+"["+position.getType()+":"+position.getPath()+",value:"+value+"]");
        Reporter.log("input输入："+position.getPositionName()+"["+position.getType()+":"+position.getPath()+",value:"+value+"]");
    }

    /**
     * 获取元素文本
     * @param position
     * @return
     */
    @Override
    public String getElementText(Position position) {
        WebElement webElement = getElement(position);
        String text = webElement.getText();
        return text;
    }

    /**
     * 获取页面元素
     * @param position
     * @return
     */
    @Override
    public WebElement getElement(Position position) {
        WebElement webElement = null;
        String path = position.getPath();
        //这里指定3秒等待，用于加载页面元素，需要根据实际情况进行配置
        WebDriverWait wait = new WebDriverWait(webDriver,3);
        logUtil.info("查找元素");
        //采用switch-case 通过WebDriver的8中元素定位方法查找元素
        switch (position.getType()){
            case xpath:
                try {
                    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(path)));
                    webElement = webDriver.findElement(By.xpath(path));
                } catch (Exception e) {
                    logUtil.info("findElement ByXpath "+path+" failed! NoSuchElement !");
                    Reporter.log("findElement ByXpath "+path+" failed! NoSuchElement !");
                }
                break;
            case id:
                try {
                    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(path)));
                    webElement = webDriver.findElement(By.id(path));
                } catch (Exception e) {
                    logUtil.info("findElement ById "+path+" failed! NoSuchElement !");
                    Reporter.log("findElement ById "+path+" failed! NoSuchElement !");
                }
                break;
            case className:
                try {
                    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className(path)));
                    webElement = webDriver.findElement(By.className(path));
                } catch (Exception e) {
                    logUtil.info("findElement ByClassName "+path+" failed! NoSuchElement !");
                    Reporter.log("findElement ByClassName "+path+" failed! NoSuchElement !");
                }
                break;
            case linkText:
                try {
                    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(path)));
                    webElement = webDriver.findElement(By.linkText(path));
                } catch (Exception e) {
                    logUtil.info("findElement ByLinkText "+path+" failed! NoSuchElement !");
                    Reporter.log("findElement ByLinkText "+path+" failed! NoSuchElement !");
                }
                break;
            case name:
                try {
                    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(path)));
                    webElement = webDriver.findElement(By.name(path));
                } catch (Exception e) {
                    logUtil.info("findElement ByName "+path+" failed! NoSuchElement !");
                    Reporter.log("findElement ByName "+path+" failed! NoSuchElement !");
                }
                break;
            case cssSelector:
                try {
                    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(path)));
                    webElement = webDriver.findElement(By.cssSelector(path));
                } catch (Exception e) {
                    logUtil.info("findElement ByCssSelector "+path+" failed! NoSuchElement !");
                    Reporter.log("findElement ByCssSelector "+path+" failed! NoSuchElement !");
                }
                break;
            case tagName:
                try {
                    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName(path)));
                    webElement = webDriver.findElement(By.tagName(path));
                } catch (Exception e) {
                    logUtil.info("findElement ByTagName "+path+" failed! NoSuchElement !");
                    Reporter.log("findElement ByTagName "+path+" failed! NoSuchElement !");
                }
                break;
            case partialLinkText:
                try {
                    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.partialLinkText(path)));
                    webElement = webDriver.findElement(By.partialLinkText(path));
                } catch (Exception e) {
                    logUtil.info("findElement ByPartialLinkText "+path+" failed! NoSuchElement !");
                    Reporter.log("findElement ByPartialLinkText "+path+" failed! NoSuchElement !");
                }
                break;
            default:
                break;
        }
        return webElement;
    }

    /**
     * 判断元素是否存在
     * @param position
     * @return
     */
    @Override
    public boolean isElementDisplayed(Position position) {
        WebElement webElement = getElement(position);
        boolean flag = webElement.isDisplayed();
        return flag;
    }

    /**
     * 切换Windows窗口
     * @param WinTitle
     */
    @Override
    public void switchWindow(String WinTitle) {
        logUtil.info("切换Windows窗口：" + WinTitle);
        Reporter.log("切换Windows窗口：" + WinTitle);
        Set<String> handles = webDriver.getWindowHandles();
        for (String handle : handles){

            if (WinTitle.contains(webDriver.switchTo().window(handle).getTitle())){
                break;
            }else {
                continue;
            }
            //if (handle.equals(webDriver.getWindowHandle())) {
            //    continue;
            //} else {
            //    webDriver.switchTo().window(handle);
            //    if (WinTitle.contains(webDriver.getTitle())) {
            //        break;
            //    } else {
            //        continue;
            //    }
            //}
        }
    }

    /**
     * 切换frame
     * @param position
     */
    @Override
    public void switchFrame(Position position) {
        webDriver.switchTo().frame(position.getPath());
    }

    /**
     * 智能等待
     * @param position
     * @param esc
     */
    @Override
    public void waitElement(Position position, int esc) {
        WebDriverWait wait = new WebDriverWait(webDriver,esc);
        String add = position.getPath();
        switch (position.getType()){
            case id:
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(add)));
                break;
            case xpath:
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(add)));
                break;
            case name:
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(add)));
                break;
            case linkText:
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(add)));
                break;
            case className:
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className(add)));
                break;
            case tagName:
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName(add)));
                break;
            case partialLinkText:
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.partialLinkText(add)));
                break;
            case cssSelector:
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(add)));
                break;
            default:
                break;

        }
    }

    /**
     * 获取弹窗文字（并关闭弹窗）
     * @return
     * @throws InterruptedException
     */
    @Override
    public String getAlertText() throws InterruptedException {
        String AlertText = "";
        try {
            Thread.sleep(2000);
            Alert alert = webDriver.switchTo().alert();
            AlertText = alert.getText();
            alert.accept();
        } catch (InterruptedException e) {
            logUtil.info("no alert open,switch to alert failed");
            Reporter.log("no alert open,switch to alert failed");
        }

        return AlertText;
    }

    /**
     * 获取元素属性
     * @param position
     * @param attributeName
     * @return
     */
    @Override
    public String getAttribute(Position position, String attributeName) {
        WebElement webElement = getElement(position);
        String value = webElement.getAttribute(attributeName);
        return value;
    }

    /**
     * JavaScript强制点击
     * @param position
     */
    @Override
    public void jsClick(Position position) {
        WebElement webElement = getElement(position);
        ((JavascriptExecutor)webDriver).executeScript("arguments[0].click();",webElement);
        logUtil.info("JavaScript-click元素："+position.getPositionName()+"["+position.getType()+":"+position.getPath()+"] success!");
        Reporter.log("JavaScript-click元素："+position.getPositionName()+"["+position.getType()+":"+position.getPath()+"] success!");
    }

    /**
     * 清楚文本框用户输入
     * @param position
     */
    @Override
    public void clear(Position position) {
        WebElement webElement = getElement(position);
        webElement.clear();
        logUtil.info("清除文本框:"+position.getPositionName()+"内容");
        Reporter.log("清除文本框:"+position.getPositionName()+"内容");
    }
}
