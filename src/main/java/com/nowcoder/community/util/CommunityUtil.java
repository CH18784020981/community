package com.nowcoder.community.util;

import com.alibaba.fastjson.JSONObject;


import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 随机工具类
 */
public class CommunityUtil {
    //    生成随机字符串
    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * md5加密
     *
     * @param kay 加密前的字符串
     * @return 加密后的字符串
     */
    public static String md5(String kay) {
        //判空
        if (StringUtils.isBlank(kay)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(kay.getBytes());

    }

    /**
     * 转json格式
     *
     * @param code 编号
     * @param msg  提示信息
     * @param map
     * @return 返回json字符串
     */
    public static String getJSONString(int code, String msg, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if (map != null) {
            for (String key : map.keySet()) {
                json.put(key, map.get(key));
            }
        }
        return json.toJSONString();
    }

    /**
     *
     * @param code
     * @param msg
     * @return
     */
    public static String getJSONString(int code, String msg) {
        return getJSONString(code, msg, null);
    }

    /**
     *
     * @param code
     * @return
     */
    public static String getJSONString(int code) {
        return getJSONString(code, null, null);
    }

//    public static void main(String[] args) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("name","zhangsahn");
//        map.put("age",25);
//        System.out.println(getJSONString(0,"ok",map));
//    }
}
