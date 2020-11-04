package com.ali.retail.common.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Locale;
import java.util.RandomAccess;

public class RandomAccessFileUtil {

    //定时任务读取本地断线记录，进行断线续传功能
    public void httpReadFile(String uri, String dstAbsFilePath, String agent, long position) throws IOException {
        URL url = new URL(uri);//"http://www.sjtu.edu.cn/down.zip"
        HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
        // 设置 User-Agent
        httpConnection.setRequestProperty("User-Agent", agent);//"NetFox"
        // 设置断点续传的开始位置
        httpConnection.setRequestProperty("RANGE","bytes=" + position);
        // 获得输入流
        InputStream input = httpConnection.getInputStream();
//        long skip = input.skip(position);
        RandomAccessFile oSavedFile = new RandomAccessFile(dstAbsFilePath,"rw");
        // 定位文件指针到 position 位置
        oSavedFile.seek(position);
        byte[] b = new byte[1024];
        int nRead;
        // 从输入流中读入字节流，然后写到文件中
        while((nRead=input.read(b,0,1024)) > 0) {
            oSavedFile.write(b,0,nRead);
        }
    }
}

class UploadFile{
    private long startPos;//断点
    private long endPos;//断点
    private long length;//文件总长度
    private String fileName;//文件名
    private String filePath;//文件路径or网络路径

    private Date[] update_time;
    private long version;

    public long getStartPos() {
        return startPos;
    }

    public void setStartPos(long startPos) {
        this.startPos = startPos;
    }

    public long getEndPos() {
        return endPos;
    }

    public void setEndPos(long endPos) {
        this.endPos = endPos;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date[] getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date[] update_time) {
        this.update_time = update_time;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
