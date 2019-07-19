package com.zone7.demo.helloworld.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * LevelUtil
 *
 * @author: zone7
 * @time: 2019.02.12
 */
public class LevelUtil {

    public final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    public static String calculateLevel(String parentLevel, String parentId) {
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }

}
