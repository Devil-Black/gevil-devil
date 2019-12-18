package com.gevil.Utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.Reporter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReadUtil {
    static LogUtil logUtil = new LogUtil(ExcelReadUtil.class);

     private static String getValue(Cell cell){
         if (cell != null){
             switch (cell.getCellType()){
                 case BOOLEAN:
                     return String.valueOf(cell.getBooleanCellValue()); //boolean型
                 case NUMERIC:
                     return String.valueOf(cell.getNumericCellValue()); //number型
                 case STRING:
                     return String.valueOf(cell.getStringCellValue()); //String型
                 default:
                     break;
             }
         }
         return "";

     }

     private static List<Object> getRow(Row row){
         List<Object> cells = new ArrayList<Object>();
         if (row != null){
             for (short cellNum = 0;cellNum < row.getLastCellNum();cellNum++){
                 Cell cell = row.getCell(cellNum); //从0开始获取指定列的单元格
                 cells.add(getValue(cell));
             }
         }
         return cells;
     }

     private static Map<String, Method> getSetMethod(Class<?> cla,List<Object> heads){
         Map<String,Method> map = new HashMap<String,Method>();
         Method[] methods = cla.getMethods();
         for(Object head : heads){
             for (Method method : methods){
                 if (method.getName().toLowerCase().equals("set"+head.toString().toLowerCase()) && method.getParameterTypes().length == 1){
                     map.put(head.toString(),method);
                     break;
                 }
             }
         }
         return map;

     }

     private static void setValue(Object object,List<Object> data,List<Object> heads,Map<String,Method> methods) throws InvocationTargetException, IllegalAccessException {
         //获取对应关系的HashMap，对Map进行遍历
         for(Map.Entry<String,Method> entry : methods.entrySet()){
            Object value = "";
            int dataIndex = heads.indexOf(entry.getKey());//返回列第一次出现的位置序号
            //按照当前列的序号小于数据List的长度（例如List的长度为5，当前为0~4）
             if(dataIndex < data.size()){
                 value = data.get(heads.indexOf(entry.getKey()));
             }
             //获取HashMap中对应的方法
             Method method = entry.getValue();
             //获取方法中的参数类型
             Class<?> parameter = method.getParameterTypes()[0];
             switch (parameter.toString()){
                 case "Integer":
                 case "int":
                     if (value == ""){
                         value = 0;
                     }
                     //将value转换成String类型后，通过BigDecimal转换成int类型，更精确
                     method.invoke(object,new BigDecimal(value.toString()).intValue());
                     break;
                 case "Long":
                     if (value == ""){
                         value = 0;
                     }
                     //将value转换成String类型后，通过BigDecimal转换成long类型，更精确
                     method.invoke(object,new BigDecimal(value.toString()).longValue());
                     break;
                 case "Short":
                     if (value == ""){
                         value = 0;
                     }
                     //将value转换成String类型后，通过BigDecimal转换成short类型，更精确
                     method.invoke(object,new BigDecimal(value.toString()).shortValue());
                     break;
                 default:
                     //方法反射，将具体的值赋给列名所代表的的obj
                     method.invoke(object,value);
                     break;

             }
         }
     }


     private static <T> List<T> TransToObject(Class<T> cls, Workbook workbook,String sheetName){
         List<T> list = new ArrayList<T>();
         Sheet sheet = workbook.getSheet(sheetName);
         Row firstRow = sheet.getRow(0);
         if (firstRow == null){
             return list;
         }
         List<Object> heads = getRow(firstRow);
         //添加sheetName字段，用于封转bean中，与bean中的字段相匹配
         heads.add("sheetName");
         Map<String,Method> headMethod = getSetMethod(cls,heads);
         for (int rowNum = 1;rowNum < sheet.getLastRowNum();rowNum++){
             try {
                 Row row = sheet.getRow(rowNum);
                 if (row == null){
                     continue;
                 }
                 T t = cls.newInstance();
                 List<Object> data = getRow(row);
                 //如果发现表数据的列小于表头的列数，则自动填充为null，最后一位不动，用于添加sheetName数据
                 while (data.size()+1<heads.size()){
                     data.add("");
                 }
                 data.add(sheetName);
                 setValue(t,data,heads,headMethod);
                 list.add(t);
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
         return list;
     }



     public static <T> List<T> readExcel(Class<T> cls,String path,String sheetName){
         if (path == null || "".equals(path)){
             return null;
         }
         InputStream in;
         Workbook workbook = null;
         try {
             in  = new FileInputStream(path);
             if (path.endsWith(".xls") || path.endsWith(".xlsx") || path.endsWith(".csv")){
                 workbook = new HSSFWorkbook(in);
             }else {
                 logUtil.info("文件格式非Excel");
                 Reporter.log("文件格式非Excel");
             }
             in.close();
         } catch (Exception e) {
             e.printStackTrace();
             throw new RuntimeException("文件转换失败：" + e.getMessage());
         }
         return TransToObject(cls,workbook,sheetName);
     }

}
