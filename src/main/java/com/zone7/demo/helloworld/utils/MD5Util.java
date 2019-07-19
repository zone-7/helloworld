package com.zone7.demo.helloworld.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5Util
 * MD5加密工具类
 *
 * @author: Misaka
 * @time: 2018-10-11
 */
public class MD5Util {

    public static String encode(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(content.getBytes());
            return hash(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String hash(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }
}
