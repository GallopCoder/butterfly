package com.ali.retail.common.file;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class ExcelUtil {

    //log日志
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * poi导出excel
     * 备注：导出excel HSSFWorkbook 只能最多导出65535条数据
     * @param response
     * @param list
     * @param fileName
     * @param sheetName
     * @param clazz
     * @param <T>
     */
    public static <T> void downLoadExcel(HttpServletResponse response, List<T> list, String fileName, String sheetName,
                                         Class<T> clazz) {
        // 第一步，创建一个webbook，对应一个Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet,自定义sheetName
        XSSFSheet sheet = wb.createSheet(sheetName);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        XSSFRow row = sheet.createRow(0);
        // 第四步，创建表头样式
        XSSFCellStyle headStyle = setHeadStyle(wb);
        // 给单元格内容设置另一个样式
        XSSFCellStyle cellStyle = setCellStyle(wb);

        //自定义表头英文值
        List<String> titlesNew = new ArrayList<>();

        //循环表头
        XSSFCell cell;
        for (int i = 0; i < titlesNew.size(); i++) {
            //获得单元格
            cell = row.createCell(i);
            //给单元格设置样式
            cell.setCellStyle(headStyle);
            //设置单元格为富文本类型
            XSSFRichTextString text = new XSSFRichTextString(titlesNew.get(i));
            //给单元格设置值
            cell.setCellValue(text);
            //设置自动列宽(必须在单元格设值以后进行)
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
        }
        //循环单元格(采用反射机制)
        for (int m = 0; m < list.size(); m++) {
            //从第二行开始写数据（注意下标）
            row = sheet.createRow(m + 1);
            for (int i = 0; i < titlesNew.size(); i++) {
                //获取方法名称
                String name = toUpperCaseFirstOne(titlesNew.get(m));
                Method getMoth;
                try {
                    getMoth = clazz.getMethod(name);
                    //获取方法值
                    String value = getMoth.invoke(list.get(m)) == null ? "" : (String) getMoth.invoke(list.get(m));
                    //创建单元格
                    cell = row.createCell(i);
                    //给每个单元格内容设置样式
                    cell.setCellStyle(cellStyle);
                    //设置单元格为字符串类型
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    //给每个单元格设置值
                    cell.setCellValue(value);

                } catch (Exception e) {
                    LOGGER.error("ExcelUtil->downLoad exception", e);
                }
            }
        }

        // 必须在单元格设值以后进行
        // 设置为根据内容自动调整列宽
        for (int k = 0; k < list.size(); k++) {
            sheet.autoSizeColumn(k);
        }
        // 处理中文不能自动调整列宽的问题
        setSizeColumn(sheet, list.size());

        //指定名称和路径
        setReportNameAndLoad(response, fileName, wb);
    }

    /**
     * 设置表头样式
     * @param wb
     * @return
     */
    private static XSSFCellStyle setHeadStyle(XSSFWorkbook wb) {
        XSSFCellStyle headStyle = wb.createCellStyle();
        // 设置背景颜色白色
        headStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        // 设置填充颜色
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 设置上下左右边框
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);
        headStyle.setBorderTop(BorderStyle.THIN);
        // 设置水平居中
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置标题字体
        XSSFFont headFont = wb.createFont();
        // 设置字体大小
        headFont.setFontHeightInPoints((short) 14);
        // 设置字体
        headFont.setFontName("宋体");
        // 设置字体粗体
        headFont.setBold(true);
        // 把字体应用到当前的样式
        headStyle.setFont(headFont);
        return headStyle;
    }

    /**
     * 设置单元格内容样式
     * @param wb
     * @return
     */
    private static XSSFCellStyle setCellStyle(XSSFWorkbook wb) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置上下左右边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        //设置左对齐
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        // 设置标题字体
        XSSFFont cellFont = wb.createFont();
        // 设置字体大小
        cellFont.setFontHeightInPoints((short) 11);
        // 设置字体
        cellFont.setFontName("等线");
        // 把字体应用到当前的样式
        cellStyle.setFont(cellFont);
        return cellStyle;
    }

    /**
     * 获取表头的方法名
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return "get" + s;
        } else {
            return "get" + (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1))
                    .toString();
        }
    }

    /**
     * 自适应宽度(中文支持)
     * @param sheet
     * @param size
     */
    private static void setSizeColumn(XSSFSheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                XSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(columnNum) != null) {
                    XSSFCell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }

    /**
     * 导出响应
     * @param response
     * @param name
     * @param wb
     */
    public static void setReportNameAndLoad(HttpServletResponse response, String name, XSSFWorkbook wb) {
        BufferedOutputStream fos = null;
        try {
            // 设置响应输出的头类型
            //response.setContentType("application/vnd.ms-excel;charset=GBK");//导出xls格式
            response.setContentType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=GBK");//导出xlsx格式
            // 设置下载文件名称(注意中文乱码)
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String((name).getBytes("GB2312"), "ISO8859-1") + ".xlsx");
            response.setHeader("Pragma", "No-cache");
            fos = new BufferedOutputStream(response.getOutputStream());
            wb.write(fos);
        } catch (Exception e) {
            LOGGER.error("ExcelUtil->setReportNameAndLoad exception:", e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    LOGGER.error("ExcelUtil->setReportNameAndLoad close outputStream exception:", e);
                }
            }
        }
    }

}
