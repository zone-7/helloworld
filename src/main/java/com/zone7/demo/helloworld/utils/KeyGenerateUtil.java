package com.zone7.demo.helloworld.utils;

import java.util.UUID;

/**
 * KeyGenerateUtil
 *
 */
public class KeyGenerateUtil {

    /**
     * 生成主键UUID
     *
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
