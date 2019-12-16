package com.lvhaifeng.generator.generate.util;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NonceUtils {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static Date date;

    public NonceUtils() {
    }

    public static long getRandom() {
        return (new SecureRandom()).nextLong();
    }

    public static String format() {
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

}
