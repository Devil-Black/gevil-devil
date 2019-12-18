package com.gevil.webTest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;
import org.testng.annotations.Test;


import java.io.File;
import java.io.IOException;

public class readXmlTest {

    @Test
    public void readXml() throws IOException {
        String xmlPath = this.getClass().getClassLoader().getResource("PageXml.xml").getPath();
        Document document = (Document) Jsoup.parse(new File(xmlPath),"utf-8");
        Elements element = document.getElementsByTag("page");
        //Elements element = document.getElementsBy("page");
        String str = element.attr("pageName");
        System.out.println(str);

    }


}
