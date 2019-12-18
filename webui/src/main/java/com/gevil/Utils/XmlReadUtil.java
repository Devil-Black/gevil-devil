package com.gevil.Utils;

import com.gevil.position.Position;
import com.gevil.position.Position.ByType;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.testng.Reporter;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

public class XmlReadUtil {

    static LogUtil logUtil = new LogUtil(XmlReadUtil.class);

    /**
     * 读取页面配置文件
     * @param xmlUrl 页面配置文件路径
     * @param pageName 页面名称
     * @return
     * @throws Exception
     */
    public static HashMap<String, Position> readXmlDocument(String xmlUrl,String pageName) throws Exception {
        //创建一个HashMap，里面的键值对为（名字，元素）
        HashMap<String,Position> positionMap = new HashMap<>();
        //判断xml是否存在
        File file = new File(xmlUrl);
        if (!file.exists()){
            logUtil.info("can't find " + xmlUrl);
            Reporter.log("can't find " + xmlUrl);
        }else {
            //创建SAXReader对象
            SAXReader saxReader = new SAXReader();
            //读取xml转换为Document
            Document document = saxReader.read(file);
            //获取所有根节点元素对象
            Element root = document.getRootElement();
            Iterator<?> rootIte = root.elementIterator();
            Position position = null;
            //遍历根节点
            while (rootIte.hasNext()){
                Element page = (Element) rootIte.next();
                //节点属性忽略大小写
                if (page.attribute(0).getValue().equalsIgnoreCase(pageName)){
                    Iterator<?> pageIte = page.elementIterator();
                    //找到pageName后遍历该page内的各个节点
                    while (pageIte.hasNext()){
                        String type = "";
                        String timeOut = "";
                        String value = "";
                        String positionName = "";
                        Element element = (Element) pageIte.next();
                        positionName = element.getText();
                        Iterator<?> positionIte = element.attributeIterator();
                        //遍历单个便签里面的内容
                        while (positionIte.hasNext()){
                            Attribute attribute = (Attribute) positionIte.next();
                            String attributeName = attribute.getName();
                            switch (attributeName){
                                case "type":
                                    type = attribute.getValue();
                                    break;
                                case "value":
                                    value = attribute.getValue();
                                    break;
                                case "timeOut":
                                    timeOut = attribute.getValue();
                                    break;
                                default:
                                    break;
                            }
                    }

                        position = new Position(value,positionName.trim(), getType(type),Integer.parseInt(timeOut));
                        //将页面元素加入哈希Map的集合
                        positionMap.put(positionName.trim(), position);
                }
                    break;
            }
        }
    }
        return positionMap;
}

    /**
     * 获取ByType枚举封装的元素定位方法
     * @param type
     * @return
     */
    private static ByType getType(String type) {
            ByType byType = ByType.xpath;
            if (type != null){
                switch (type){
                    case "xpath":
                        byType = ByType.xpath;
                        break;
                    case "id":
                        byType = ByType.id;
                        break;
                    case "name":
                        byType = ByType.name;
                        break;
                    case "className":
                        byType = ByType.className;
                        break;
                    case "cssSelector":
                        byType = ByType.cssSelector;
                        break;
                    case "tagName":
                        byType = ByType.tagName;
                        break;
                    case "partialLinkText":
                        byType = ByType.partialLinkText;
                        break;
                    case "linkText":
                        byType = ByType.linkText;
                        break;
                    default:
                        break;
                }
            }
            return byType;
        }
    }
