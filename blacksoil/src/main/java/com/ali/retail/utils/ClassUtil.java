package com.ali.retail.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

//SeagullArticleUtil<T>
public class ClassUtil {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);
    public static List<String> fieldNames = new ArrayList<>();//kys业务字段名称集合
    public static Map<String, String> dbFieldMap = new HashMap<>();//<RQ, created_at>

    static {
//        fillFieldNameList(HOArticleConstant.class);
//        fillValue2FieldMap(HOArticleConstant.class);
    }

    /**
     * 封装该类的属性名集合
     * @param clazz
     */
    private static void fillFieldNameList(Class<?> clazz) {
        for (Field field : clazz.getFields()) {
            fieldNames.add(field.getName());
        }
    }

    /**
     * 根据该类属性名获取值；并将值做key，属性名为value放入map
     * @param clazz
     */
    private static void fillValue2FieldMap(Class<?> clazz) {
        if (fieldNames != null && fieldNames.size() > 0) {
            for (String field : fieldNames) {
                String methodName = "get"+ field.substring(0, 1).toUpperCase() + field.substring(1);
                try {
                    Method var2 = clazz.getMethod(methodName);
                    Object object = clazz.newInstance();
                    String key = (String) var2.invoke(object);
                    dbFieldMap.put(key, field);
                } catch (NoSuchMethodException e) {
                    logger.error("NoSuchMethodException: " + clazz.getSimpleName() + " has no such method - " + methodName + "().");
                } catch (Exception e) {
                    logger.error("methodName: " + methodName + ", " + e.getMessage());
                }
            }
        }
    }

    /**
     *  根据属性名，获取值
     */
    public static String getFieldValue(String key, Class<?> clazz){
        String value = "";
        if (!StringUtils.isBlank(key)) {
            String methodName = "get"+ key.substring(0, 1).toUpperCase() + key.substring(1);
            try {
                Method var2 = clazz.getMethod(methodName);
                Object object = clazz.newInstance();
                value = (String) var2.invoke(object);
            } catch (Exception e) {
                logger.error("NoSuchMethodException: " + clazz.getSimpleName() + " has no such method - " + methodName + "().");
            }
        }
        return value;
    }


    // 若按推荐、收藏排序，最终需要再加如下操作，按照id是的顺序排序
    public static JSONArray resortJSONArrayByIds(JSONArray source, String[] ids) {
        JSONArray array = new JSONArray();
        Iterator<Object> iterator = source.iterator();
        if (ids != null && ids.length > 0) {
            Arrays.stream(ids).forEach(id -> {
                while (iterator.hasNext()) {
                    Object next = iterator.next();
                    if (next instanceof JSONObject) {
                        JSONObject tmp = (JSONObject) next;
                        if (tmp.containsKey(id)) {
                            array.add(tmp);
                            iterator.remove();
                        }
                    }
                }
            });
        }
        return array;
    }

    public static void main(String[] args) throws Exception {

    }
}
