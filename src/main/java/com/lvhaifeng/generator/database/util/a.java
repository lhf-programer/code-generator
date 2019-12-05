package com.lvhaifeng.generator.database.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class a {
    public a() {
    }

    public static String a(String[] var0) {
        StringBuffer var1 = new StringBuffer();
        String[] var2 = var0;
        int var3 = var0.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            if (StringUtils.isNotBlank(var5)) {
                var1.append(",");
                var1.append("'");
                var1.append(var5.trim());
                var1.append("'");
            }
        }

        return var1.toString().substring(1);
    }

    public static String a(String var0) {
        if (StringUtils.isNotBlank(var0)) {
            var0 = var0.substring(0, 1).toLowerCase() + var0.substring(1);
        }

        return var0;
    }

    public static Integer a(Integer var0) {
        return var0 == null ? 0 : var0;
    }

    public static boolean a(String var0, String[] var1) {
        if (var1 != null && var1.length != 0) {
            for(int var2 = 0; var2 < var1.length; ++var2) {
                String var3 = var1[var2];
                if (var3.equals(var0)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean a(String var0, List<String> var1) {
        String[] var2 = new String[0];
        if (var1 != null) {
            var2 = (String[])((String[])var1.toArray());
        }

        if (var2 != null && var2.length != 0) {
            for(int var3 = 0; var3 < var2.length; ++var3) {
                String var4 = var2[var3];
                if (var4.equals(var0)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }
}
