package com.ali.retail.service;


import com.ali.retail.common.file.DownloadUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class JSoupService {

    private static final Logger logger = LoggerFactory.getLogger(JSoupService.class);
    
    public Document baidu(String url) throws IOException {
        Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0")
                    .get();
        return document;
    }


    public List<Document> tencent(String url) {
        Connection connect = Jsoup.connect("http://www.qq.com");
        try {
            // 得到Document对象
            Document document = connect.get();
            // 查找所有img标签
            Elements imgs = document.getElementsByTag("img");
            System.out.println("开始下载");
            System.out.println("共检测到下列图片URL：");

            // 遍历img标签并获得src的属性
            for (Element element : imgs) {
                //获取每个img标签URL "abs:"表示绝对路径
                String imgSrc = element.attr("abs:src");
                // 打印URL
                System.out.println(imgSrc);
                //下载图片到本地
                DownloadUtil.downImages("D:\\_note_self\\picture", imgSrc);
            }
            System.out.println("下载完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public List<String> weibo(String url) {
        Connection connect = Jsoup.connect(url);
        try {
            Document document = connect.get();
            Elements div = document.body().getElementsByTag("div");

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }
}
