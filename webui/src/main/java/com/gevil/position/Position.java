package com.gevil.position;

/**
 * 寻找的页面元素
 */

public class Position {
    private String path; //路径
    private String positionName; //位置名字，就是我们通常意义上叫的名字。例如：用户名输入框
    private ByType type;//定位方法
    private int waitSec;//等待时间
    /**
     * xpath:需要被查找的元素的xpath
     * id:需要被查找的元素的ID
     * name:需要被查找的元素的名称
     * className:需要被查找的元素的类名
     * cssSelector:需要被查找的元素的cssSelector
     * tagName:需要被查找的元素的标签名称
     * partialLinkText:需要被查找的元素的部分链接文字
     * linkText:需要被查找的元素的链接文字
     */
    public enum ByType {
        xpath,id,name,className, cssSelector,tagName, partialLinkText,linkText
    }

    public Position(String path, String positionName, ByType type) {
        super();
        this.path = path;
        this.positionName = positionName;
        this.type = type;
    }

    //这种可以看作是针对iframe的情况
    public Position(String path, String positionName) {
        super();
        this.path = path;
        this.positionName = positionName;
    }

    public Position(String path, String positionName, ByType type, int waitSec) {
        super();
        this.path = path;
        this.positionName = positionName;
        this.type = type;
        this.waitSec = waitSec;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public ByType getType() {
        return type;
    }

    public void setType(ByType type) {
        this.type = type;
    }

    public int getWaitSec() {
        return waitSec;
    }

    public void setWaitSec(int waitSec) {
        this.waitSec = waitSec;
    }
}

