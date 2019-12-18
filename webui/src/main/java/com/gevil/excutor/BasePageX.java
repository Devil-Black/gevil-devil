package com.gevil.excutor;

import com.gevil.Utils.LogUtil;
import com.gevil.Utils.XmlReadUtil;
import com.gevil.position.Position;
import com.gevil.excutor.impl.UIExcutorImpl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.HashMap;

public class BasePageX extends UIExcutorImpl {
    protected WebDriver webDriver;
    protected String pageName;
    protected String xmlPath;
    protected HashMap<String, Position> positionMap;
    protected LogUtil logUtil = new LogUtil(BasePageX.class);
    Position position = null;
    public BasePageX(WebDriver webDriver,String pageName,String xmlName) throws Exception {
        super(webDriver);
        this.webDriver = webDriver;
        this.pageName = pageName;
        //xmlPath = this.getClass().getResource(xmlName).getPath();
        //InputStream in = this.getClass().getClassLoader().getResourceAsStream(pageName);
        xmlPath = this.getClass().getClassLoader().getResource(xmlName).getPath();
        positionMap = XmlReadUtil.readXmlDocument(xmlPath,pageName);
        logUtil.info("成功获取" + pageName + "页面信息");
        Reporter.log("成功获取"+pageName+"页面信息");
    }

    /**
     * 点击事件
     * @param positionName
     */
    public void click(String positionName){
        waitElement(getPosition(positionName), getPosition(positionName).getWaitSec());
        super.click(getPosition(positionName));

    }

    /**
     * 输入文本
     * @param positionName
     * @param value
     */
    public void sendKeys(String positionName,String value){
        waitElement(getPosition(positionName), getPosition(positionName).getWaitSec());
        super.sendKeys(getPosition(positionName),value);

    }

    /**
     * 获取元素文本
     * @param positionName
     * @return
     */
    public String getText(String positionName){
        waitElement(getPosition(positionName), getPosition(positionName).getWaitSec());
        String Text = super.getElementText(getPosition(positionName));
        return Text;

    }

    /**
     * 获取元素属性
     * @param positionName
     * @param value
     * @return
     */
    public String getAttribute(String positionName,String value){
        waitElement(getPosition(positionName), getPosition(positionName).getWaitSec());
        String Attribute = super.getAttribute(getPosition(positionName),value);
        return Attribute;
    }

    /**
     * 获取元素
     * @param positionName
     * @return
     */
    public WebElement getElement(String positionName){
        waitElement(getPosition(positionName), getPosition(positionName).getWaitSec());
        WebElement Element = super.getElement(getPosition(positionName));
        return Element;
    }

    /**
     * 判断元素是否存在
     * @param positionName
     * @return
     */
    public boolean isElementDisplayed(String positionName){
        waitElement(getPosition(positionName), getPosition(positionName).getWaitSec());
        return super.isElementDisplayed(getPosition(positionName));
    }

    /**
     * 切换页面
     * @param title
     */
    public void switchWindow(String title){
        super.switchWindow(title);
        logUtil.info("切换窗口");
        Reporter.log("切换窗口");
    }

    /**
     * 切换frame
     * @param positionName
     */
    public void switchFrame(String positionName){
        waitElement(getPosition(positionName), getPosition(positionName).getWaitSec());
        super.switchFrame(getPosition(positionName));
        logUtil.info("切换frame至："+positionName);
        Reporter.log("切换frame至："+positionName);
    }

    /**
     * 获取弹窗文字（并关闭弹窗）
     * @return
     * @throws InterruptedException
     */
    public String getAlertText() throws InterruptedException {

        return super.getAlertText();
    }

    //使用Robot强制点击某处坐标，用户无法定位的元素，比如（Object类型的元素）
    public void mouseMoveClick(int x,int y) throws AWTException {
        Robot robot = new Robot();
        robot.mouseMove(x,y);
        robot.delay(500);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(500);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(500);
        logUtil.info("将鼠标移动至："+"("+x+","+y+")");
        Reporter.log("将鼠标移动至："+"("+x+","+y+")");
    }

    /**
     * JavaScript强制点击
     * @param positionName
     */
    public void jsClick(String positionName){
        waitElement(getPosition(positionName), getPosition(positionName).getWaitSec());
        super.jsClick(getPosition(positionName));

    }

    /**
     * 智能等待
     * @param positionName
     * @param sec
     */
    public void waitElement(String positionName,int sec){
        super.waitElement(getPosition(positionName),sec);
    }

    /**
     * 清楚文本框用户输入
     * @param positionName
     */
    public void clear(String positionName) {
        waitElement(getPosition(positionName), getPosition(positionName).getWaitSec());

        super.clear(getPosition(positionName));
    }


    private Position getPosition(String positionName) {
        Position position = null;
        if (positionMap == null){
            logUtil.info("没有找到"+positionName+"页面元素");
            Reporter.log("没有找到"+positionName+"页面元素");
        }else {
            position = positionMap.get(positionName);
        }
        return position;
    }
}
