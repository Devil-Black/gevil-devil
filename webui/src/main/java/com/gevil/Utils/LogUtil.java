package com.gevil.Utils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class LogUtil {

    private final Class<?> aClass;
    private Logger logger;

    //这里定义LogUtil的构造函数，因为不明确需要记录log的类是什么样，所有使用泛型Class<?>
    public LogUtil(Class<?> aClass) {
        this.aClass = aClass;
        this.logger = Logger.getLogger(this.aClass);
        LogUtil.initLog4j();
    }

    /**
     * 定义记录log的方法
     */
    private static void initLog4j() {
        //创建Properties对象
        Properties properties = new Properties();
        properties.setProperty("log4j.rootLogger","INFO,CONSOLE,E,F");
        properties.setProperty("log4j.appender.CONSOLE","org.apache.log4j.ConsoleAppender");
        properties.setProperty("log4j.appender.CONSOLE.layout","org.apache.log4j.PatternLayout");
        properties.setProperty("log4j.appender.CONSOLE.layout.ConversionPattern","[%d{YYYY-MM-dd HH:mm:ss}] %-5p %c %m%n");

        //设置日志输出路径
        String src = "test-output/log";
        //设置日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //获取当前时间
        String localDateTime = LocalDateTime.now().format(formatter);

        File dir = new File(src + "/" + localDateTime);
        if (!dir.exists()){
            dir.mkdirs();
        }

        String filePath = "/"+dir.getAbsolutePath() + "/" + "log_" + localDateTime + ".log";

        properties.setProperty("log4j.appender.E","org.apache.log4j.FileAppender");
        properties.setProperty("log4j.appender.E.file",filePath);
        properties.setProperty("log4j.appender.E.layout","org.apache.log4j.PatternLayout");
        properties.setProperty("log4j.appender.E.layout.ConversionPattern", "[%d{YYYY-MM-dd HH:mm:ss,SSS}] %-5p %c %m%n");
        properties.setProperty("log4j.appender.F","org.apache.log4j.FileAppender");
        properties.setProperty("log4j.appender.file.encoding","UTF-8");
        //生成Html格式的日志，并将生成的.html的日志文件放入当前日期的文件夹。
        String filepathHtml=dir.getAbsolutePath()+"/"+"log_"+localDateTime+".html";
        properties.setProperty("log4j.appender.F.file",filepathHtml);
        properties.setProperty("log4j.appender.F.layout","org.apache.log4j.HTMLLayout");
        PropertyConfigurator.configure(properties);

    }
    public void info(String message) {
        logger.info(message);
    }

    public void debug(String message) {
        logger.debug(message);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void trace(String message) {
        logger.trace(message);
    }
}
