package com.lvhaifeng.generator.database.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @description 字段工具类
 * @author haifeng.lv
 * @updateTime 2019/12/13 16:52
 */
public class FiledUtils {
    public FiledUtils() {
    }

    /**
     * @description 字段格式化
     * @author haifeng.lv
     * @param: strings
     * @updateTime 2019/12/13 16:53
     * @return: java.lang.String
     */
    public static String filedFormat(String[] strings) {
        StringBuffer stringBuffer = new StringBuffer();
        String[] strs = strings;
        int length = strs.length;

        for(int i = 0; i < length; ++i) {
            if (StringUtils.isNotBlank(strs[i])) {
                stringBuffer.append(",");
                stringBuffer.append("'");
                stringBuffer.append(strs[i].trim());
                stringBuffer.append("'");
            }
        }

        return stringBuffer.toString().substring(1);
    }

    /**
     * @description 格式化
     * @author haifeng.lv
     * @param: str
     * @updateTime 2019/12/13 16:51
     * @return: java.lang.String
     */
    public static String format(String str) {
        if (StringUtils.isNotBlank(str)) {
            str = str.substring(0, 1).toLowerCase() + str.substring(1);
        }

        return str;
    }

    public static Integer isBlank(Integer integer) {
        return integer == null ? 0 : integer;
    }

    /**
     * @description 比较是否有相同得
     * @author haifeng.lv
     * @param: str
     * @param: strings
     * @updateTime 2019/12/13 16:48
     * @return: boolean
     */
    public static boolean equal(String str, String[] strs) {
        if (strs != null && strs.length != 0) {
            for(int i = 0; i < strs.length; ++i) {
                String s = strs[i];
                if (s.equals(str)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

}
