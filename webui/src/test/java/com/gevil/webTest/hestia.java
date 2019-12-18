package com.gevil.webTest;

import com.gevil.Utils.BrowserUtil;
import com.gevil.Utils.LogUtil;
import com.gevil.Utils.TestNGListener;
import com.gevil.excutor.BasePageX;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.*;

public class hestia {
    String browserName = "";
    String url = "";
    WebDriver webDriver;
    protected LogUtil logUtil = new LogUtil(BasePageX.class);
    @BeforeClass
    public void login() throws Exception {
        browserName = "chrome";
        url = "https://t-hestia.gaodun.com/login";
        webDriver = BrowserUtil.setDriver(browserName,url);
        BasePageX basePageX = new BasePageX(webDriver,"loginPage", "hestia.xml");
        basePageX.sendKeys("账户","gongqiangwei@gaodun.com");
        basePageX.sendKeys("密码","123456");
        basePageX.click("登录按钮");

    }
    @Test(dataProvider = "addQuestions")
    public void addQuestion(String question,String sortIndex) throws Exception {

        BasePageX basePageX = questionQa("新增按钮","新增确定按钮",question,sortIndex,"新增问题","新增排序");
        //BasePageX basePageX = new BasePageX(webDriver,"indexPage", "hestia.xml");
        //basePageX.click("新增按钮");
        //basePageX.clear("新增问题");
        //basePageX.clear("新增排序");
        //basePageX.sendKeys("新增问题",question);
        //basePageX.sendKeys("新增排序",sortIndex);
        //basePageX.click("新增确定按钮");
        try {
            //basePageX.waitElement("alert", 3);
            //basePageX.getText("小提示");
            //basePageX.isElementDisplayed("小提示");
            if (!"添加成功".equals(basePageX.getText("alert"))){
                logUtil.info("添加失败:"+basePageX.getText("alert"));
                Reporter.log("添加失败:"+basePageX.getText("alert"));
                basePageX.click("新增取消按钮");
            }
        } catch (Exception e) {
            logUtil.info("添加失败:"+basePageX.getText("小提示"));
            Reporter.log("添加失败:"+basePageX.getText("小提示"));
            basePageX.click("新增取消按钮");
        } finally {
            Thread.sleep(2000);
        }


    }
    @Test(dataProvider = "updateQuestions")
    public void updateQuestion(String question,String sortIndex) throws Exception {
        BasePageX basePageX = new BasePageX(webDriver,"indexPage", "hestia.xml");
        basePageX.click("编辑按钮");
        basePageX.clear("编辑问题");
        basePageX.clear("编辑排序");
        basePageX.sendKeys("编辑问题",question);
        basePageX.sendKeys("编辑排序",sortIndex);
        basePageX.click("编辑确定按钮");
        try {
            //basePageX.waitElement("alert", 3);
            //basePageX.getText("小提示");
            //basePageX.isElementDisplayed("小提示");
            if (!"更新成功".equals(basePageX.getText("alert"))){
                logUtil.info("添加失败:"+basePageX.getText("alert"));
                Reporter.log("添加失败:"+basePageX.getText("alert"));
                basePageX.click("编辑取消按钮");
            }
        } catch (Exception e) {
            logUtil.info("添加失败:"+basePageX.getText("小提示"));
            Reporter.log("添加失败:"+basePageX.getText("小提示"));
            basePageX.click("编辑取消按钮");
        } finally {
            Thread.sleep(2000);
        }

    }

    @DataProvider(name = "addQuestions")
    public Object[][] addQuestions(){
        return new Object[][]{
                {"回归测试：正常添加题","1"},
                {"回归测试：正常添加问题","1"},//重复添加
                {" ","1"},//问题为空格
                {"回归测试：排序为负数","-3"},
                {"回归测试：排序为零","0"},
                {"回归测试：超长问题大于40个字符；回归测试：超长问题大于40个字符；回归测试：超长问题大于40个字符；回归测试：超长问题大于40个字符","1"},
                {"回归测试：排序为字母","dsddss"}
        };
    }
    @DataProvider(name = "updateQuestions")
    public Object[][] updateQuestions(){
        return new Object[][]{
                {"编辑问题：修改后的问题","4"},
                {"编辑问题：修改后的问题","6"},//重复名称编辑
                {" ","1"},//编辑问题为空格
                {"编辑问题：排序为负数","-3"},
                {"编辑问题：排序为零","0"},
                {"编辑问题：超长问题大于40个字符；回归测试：超长问题大于40个字符；回归测试：超长问题大于40个字符；回归测试：超长问题大于40个字符","1"},
                {"编辑问题：排序为字母","dsddss"}
        };
    }


    //@BeforeMethod
    //public void beforeMethod(){
    //        TestNGListener.setDriver(webDriver);
    //    }

    //@AfterClass
    //public void afterClass(){
    //    BrowserUtil.quit();
    //}

    /**
     * 用于点出问题弹窗的操作，例如新增编辑
     * @param buttonA
     * @param buttonB
     * @param question
     * @param sortIndex
     * @param positionQuestion
     * @param positionSort
     * @return
     * @throws Exception
     */
    public BasePageX questionQa(String buttonA,String buttonB,String question,String sortIndex,String positionQuestion,String positionSort) throws Exception {
        BasePageX basePageX = new BasePageX(webDriver,"indexPage", "hestia.xml");
        basePageX.click(buttonA);
        basePageX.clear(positionQuestion);
        basePageX.clear(positionSort);
        basePageX.sendKeys(positionQuestion,question);
        basePageX.sendKeys(positionSort,sortIndex);
        basePageX.click(buttonB);
        return basePageX;
    }

}
