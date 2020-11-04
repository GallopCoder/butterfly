package com.ali.retail.common.file;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

public class ExportDataUtil {
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportDataUtil.class);


    /**
     *
     * @param wb xls文件
     * @return 附带head的sheet
     */
    public static Sheet makeSheetWithHead(Workbook wb) {
        Sheet sheet = wb.createSheet();
        Row head = sheet.createRow(0);
        head.createCell(0).setCellValue("序号");
        head.createCell(1).setCellValue("name");
        head.createCell(2).setCellValue("uid");
        head.createCell(3).setCellValue("发文内容");
        head.createCell(4).setCellValue("发文作者");
        head.createCell(5).setCellValue("发文链接");
        head.createCell(6).setCellValue("发文时间");
        head.createCell(7).setCellValue("总表情数");
        head.createCell(8).setCellValue("点赞数");
        head.createCell(9).setCellValue("回应数");
        head.createCell(10).setCellValue("分享数");
        return sheet;
    }

    /**
     *
     * @param sheet
     * @param jsonArray 从快照中拿到的数据
     * @param atomicInteger 线程安全的自增长参数
     */
    public static void fillWorkbook(Sheet sheet, JSONArray jsonArray, AtomicInteger atomicInteger) {
        for (int j = 0; j < jsonArray.size(); j++){
            JSONObject siteObject=jsonArray.getJSONObject(j);
            int rowNum = atomicInteger.getAndIncrement();
            Row row = sheet.createRow(rowNum);
            String uid = siteObject.getString("uid").split("_")[1];
            row.createCell(0).setCellValue(rowNum);
            row.createCell(1).setCellValue(siteObject.get("name") + "");
            row.createCell(2).setCellValue(uid + "");
            row.createCell(3).setCellValue(siteObject.get("text") + "");
            row.createCell(4).setCellValue(siteObject.get("name") + "");
            row.createCell(5).setCellValue(siteObject.get("article_url") + "");
            row.createCell(6).setCellValue(siteObject.get("created_at") + "");
            if(siteObject.containsKey("feelings")){
                JSONObject feelings = siteObject.getJSONObject("feelings");
                if(feelings.containsKey("all_feelings_count")){
                    row.createCell(7).setCellValue(feelings.getString("all_feelings_count") + "");
                }else{
                    row.createCell(7).setCellValue("");
                }
            }
            if (siteObject.containsKey("zans_count")) {
                row.createCell(8).setCellValue(siteObject.getString("zans_count") + "");
            }
            row.createCell(9).setCellValue(siteObject.get("comments_count") + "");
            row.createCell(10).setCellValue(siteObject.get("reports_count") + "");
        }
    }

    /**
     *
     * @param fileName 文件名
     * @return 创建文件
     */
    public static File createFile(String fileName) throws Exception {
        String separator = File.separator;
        String filePath = "E:" + separator + "export_data" + separator + "data" + separator + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     *
     * @param wb 包含数据的xls文件
     * @param file 目标文件
     */
    public static void exportData2File(Workbook wb, File file) {
        try (OutputStream fileOut = new FileOutputStream(file)) {
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 搜索的时间范围 @param month  @param day
     * @throws Exception
     */
    public static void exportData(String start, String end, String ... uidFileName) throws Exception{
        if (uidFileName == null || uidFileName.length <= 0) {
            LOGGER.error("The uidFileName is null! Plz check!");
            return;
        }
        for (String var0 : uidFileName) {
//            SentimentCondition condition = makeCondition(start, end, var0);
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = makeSheetWithHead(wb);
            AtomicInteger atomicInteger = new AtomicInteger(1);
            JSONObject var1 = null;//sentimentMonitor.scrollAll(condition, 5000, null);
            JSONArray array1 = var1.getJSONArray("source");
            int size = array1.size();
            if (array1.size() <= 0) {
                LOGGER.warn("Has no data! You can check your condition!");
                return;
            }
            LOGGER.info(var0 + " has export data, size: " + size);
            fillWorkbook(sheet, array1, atomicInteger);

            String date = start.substring(5, 10).replaceAll("-", "");
            File file = createFile(date + "FB" + var0 + ".xls");
            exportData2File(wb, file);
            String scrollId = var1.getString("scrollId");
            do {
                JSONObject var2  = null;//sentimentMonitor.scrollAll(scrollId);
                JSONArray array2  = var2.getJSONArray("source");
                size = array2.size();
                if (size == 0) {
                    LOGGER.info("Export data to file " + file.getName() + " has finished!");
                    break;
                }
                LOGGER.info("do while ->" + var0 + " has export data, size: " + size);
                if (atomicInteger.getAndIncrement() > 65000) {
                    sheet = makeSheetWithHead(wb);
                    atomicInteger = new AtomicInteger(1);
                }
                fillWorkbook(sheet, array2, atomicInteger);
                exportData2File(wb, file);
            } while (true);
        }
    }

    public void doExportData() throws Exception{
        String year = "2020";
        String month = "04";
        String day = "01";
        String from = "00:00:00";
        String to = "23:59:59";
        String start = year + "-" + month + "-" + day + " " + from, end = year + "-" + month + "-" + day + " " + to;
        LOGGER.info("Time scope of export data[" + start + " --- " + end + "]");
        exportData(start, end, "重点账号", "媒体阵营");
    }
}

