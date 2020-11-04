package com.artcode.service.common.utils;

import javax.servlet.http.HttpServletResponse;

public class CommUtil {

    // JSON格式化
    public static String printDataJason(HttpServletResponse response,
                                        Object item) {
        try {

            JsonUtil.renderString(response, item);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 随机生成6位随机验证码
     *
     */
    public static String createRandomVcode(int len) {
        // 验证码
        String vcode = "";
        for (int i = 0; i < len; i++) {
            vcode = vcode + (int) (Math.random() * 9);
        }
        return vcode;
    }
}
