package com.ali.retail.contorller;

import com.ali.retail.service.JSoupService;
import com.ali.retail.utils.FileUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/jSoup")
public class JSoupController {

    @Resource
    private JSoupService jSoupService;

    @PostMapping("/baidu")
    public Document baiduSearch(@RequestParam String url) throws IOException {
        return jSoupService.baidu(url);
    }

    @PostMapping("/tencent")
    public List<Document> tencentSearch() {
        String strUrl = "http://www.qq.com";
        return jSoupService.tencent(strUrl);
    }

    @PostMapping("/weibo")
    public void weiboSearch () {
        String hotWordUrl = "https://s.weibo.com/top/summary";
        jSoupService.weibo(hotWordUrl);
    }


    @GetMapping("translate/uid/{method}")
    public List<String> uidSearch(@PathVariable String method) throws IOException {
        List<String> var0 = new ArrayList<>();
        Set<String> var1 = new HashSet<>(FileUtil.readSource("E:\\_offline_data\\uid" + File.separator + "uid_source.txt"));
        for (String uid : var1) {
            String var = null;
            String url = "http://192.168.2.112:5080/es/encodeUid?uid="+ uid +"&type=facebook";
            Connection connect = Jsoup.connect(url);
            if (method.equalsIgnoreCase("post")) {
                connect.data("uid", "解密的uid");
                connect.data("标签key", "标签值");
                Document document = connect.post();
                System.out.println(document.toString());
            } else if (method.equalsIgnoreCase("get")) {
                try {
                    Document document = connect.get();
                    var = document.select("body").text();
                } catch (Exception e) {
                    continue;
                }
            }
            var0.add(var);
        }
        return var0;
    }

}
