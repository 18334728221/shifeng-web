package com.xwd.base.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * md5加密
 * Created by liukeqiang on 2016/6/20 0020.
 */
public class MD5 {
    /**
     * MD5编码
     *
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String MD5Encode(String origin) {
        return DigestUtils.md5Hex(origin);
    }
}
