package com.magic.wow.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.magic.wow.model.User;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/3 17:42
 *          ExcelUtils
 */
public class ExcelUtils {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @author Phil
     */
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\zhaoxf\\Desktop\\DKP23170305.xlsx");
        List<User> users = read(file);
//        List<List<Object>> lists=read(file);
        System.out.println(JSON.toJSONString(users));
    }

    /**
     * 读取excel 兼容97-2013
     *
     * @param file  excel文件
     * @param sheet 工作表索引,从0开始
     * @return List<List<Object>> 行<列>
     */
    public static List<User> read(File file, int sheet) {
        List<User> data = Lists.newArrayList();
        if (file == null) {
            return data;
        }
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(file);
            Sheet s = wb.getSheetAt(sheet);
            toLoadData(data, s);//装载数据
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            close(wb);
        }
        return data;
    }

    /**
     * 读取excel 兼容97-2013
     *
     * @param file excel文件
     * @return List<List<Object>> 行<列>
     */
    public static List<User> read(File file) {
        return read(file, 0);
    }

    /**
     * 装载数据到list中
     *
     * @param data  List<List<Object>> 行<列>
     * @param sheet 当前工作表索引 从0开始
     */
    private static void toLoadData(List<User> data, Sheet sheet) {
        int firstRowNum = sheet.getFirstRowNum() + 1;// 第一行
        int lastRowNum = sheet.getLastRowNum();// 最后一行
        for (int rIndex = firstRowNum; rIndex <= lastRowNum; rIndex++) {// 遍历第一个有效行和最后一行之间的值
            Row row = sheet.getRow(rIndex);
            User u = new User();
            u.setUsername(row.getCell(0).toString().trim().replace(new String(new char[]{160}), ""));
            u.setType(row.getCell(1).toString().trim().replace(new String(new char[]{160}), ""));

            u.setDynamic(getInt(row, 4));
            u.setConsume(getInt(row, 5));
//            u.setSurplus(getInt(row, 6));
            data.add(u);
//            List<Object> cellList=Lists.newArrayList();
//            int firstCellNum = row.getFirstCellNum();
//            int lastCellNum = row.getLastCellNum() - 1;//从0开始
//            for (int cIndex = firstCellNum; cIndex <= lastCellNum; cIndex++) {//遍历第一个有效列和最后一列之间的值
//                Cell cell = row.getCell(cIndex);
//            }
//            if(cellList.size()>0){
//                data.add(cellList);
//            }
        }
    }

    private static int getInt(Row row, int cellIndex) {
        String s = row.getCell(cellIndex).toString().trim().replace(new String(new char[]{160}), "");
        System.out.println(s);
        return Integer.valueOf(s.substring(0, s.lastIndexOf(".")));
    }

    /**
     * @param path
     * @param fileName
     * @param headAndFiled
     * @param objectList
     */
    public static void writeExcel(String path, String fileName, String[] headAndFiled, List<? extends Object> objectList) {//TODO 未测试

        //创建目录
        if (!(new File(path).isDirectory())) {
            new File(path).mkdirs();
        }

        HSSFWorkbook wb = new HSSFWorkbook();
        FileOutputStream out = null;
        try {
            //创建工作簿
            HSSFSheet hssfSheet = wb.createSheet();
            String[] heads = new String[headAndFiled.length];
            String[] fields = new String[headAndFiled.length];
            for (int i = 0; i < headAndFiled.length; i++) {
                String[] vs = headAndFiled[i].split("=");
                heads[i] = vs[0].trim();
                fields[i] = vs[1].trim();
            }
            //单行表头
            HSSFRow row = hssfSheet.createRow(0);
            for (int i = 0; i < heads.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(heads[i]);
            }

            //数据
            for (int i = 0; i < objectList.size(); i++) {
                HSSFRow hssfRow = hssfSheet.createRow(i + 1);//从第二行开始是实体
                Object user = objectList.get(i);
                for (int j = 0; j < fields.length; j++) {
                    HSSFCell hssfCell = hssfRow.createCell(j);
                    Object value = getFieldValue(user, fields[j]);
                    hssfCell.setCellValue(value == null ? "" : value.toString());
                }
            }
            //自动列宽
            for (int i = 0; i < heads.length; i++) {
                hssfSheet.autoSizeColumn(i);
            }
            File file = new File(path, fileName);
            //创建文件
            out = new FileOutputStream(file);
            out.flush();
            //写入磁盘
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(wb);
        }
    }

    private static void close(Workbook wb) {
        try {
            if (wb != null) {
                wb.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 把一个字符串的第一个字母大写、效率是最高的、
    private static String getMethodName(String fieldName) {
        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    private static Object getFieldValue(Object object, String fieldName) {
        try {
            return object.getClass().getMethod("get" + getMethodName(fieldName)).invoke(object);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("无效的字段");
        return null;
    }
}
