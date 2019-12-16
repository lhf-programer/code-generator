package com.lvhaifeng.generator.generate.util;

import org.apache.commons.lang3.StringUtils;

public class PrecisionUtils {
    public PrecisionUtils() {
    }

    public static String equal(String str) {
        if (!"YES".equals(str) && !"yes".equals(str) && !"y".equals(str) && !"Y".equals(str) && !"f".equals(str)) {
            return !"NO".equals(str) && !"N".equals(str) && !"no".equals(str) && !"n".equals(str) && !"t".equals(str) ? null : "N";
        } else {
            return "Y";
        }
    }

    public static String isBlank(String str) {
        return StringUtils.isBlank(str) ? "" : str;
    }

    public static String addColon(String str) {
        return "'" + str + "'";
    }
}
