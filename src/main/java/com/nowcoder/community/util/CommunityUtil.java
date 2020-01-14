package com.nowcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 *随机工具类
 */
public class CommunityUtil {
    //    生成随机字符串
    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //MD5加密
    public static String md5(String kay) {
        //判空
        if (StringUtils.isBlank(kay)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(kay.getBytes());

    }
}
