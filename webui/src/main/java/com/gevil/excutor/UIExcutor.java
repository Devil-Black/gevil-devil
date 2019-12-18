package com.gevil.excutor;

import com.gevil.position.Position;
import org.openqa.selenium.WebElement;

public interface UIExcutor {

    //点击事件
    void click(Position position);
    //输入文本
    void sendKeys(Position position,String value);
    //获取元素文本
    String getElementText(Position position);
    //获取元素
    WebElement getElement(Position position);
    //判断元素是否存在
    boolean isElementDisplayed(Position position);
    //切换页面
    void switchWindow(String WinTitle);
    //切换frame
    void switchFrame(Position position);
    //智能等待
    void waitElement(Position position,int esc);
    //获取弹窗文字（并关闭弹窗）
    String getAlertText() throws InterruptedException;
    //获取元素属性
    String getAttribute(Position position,String attributeName);
    //JavaScript强制点击
    void jsClick(Position position);
    void clear(Position position);


}
