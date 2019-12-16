package com.lvhaifeng.generator.generate.util;

import java.text.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class SimpleFormat {
    public SimpleFormat() {
    }

    public static String humpToShortbar(String para) {
        StringBuilder stringBuilder = new StringBuilder(para);
        int index = 0;
        if (!para.contains("-")) {
            for(int i = 0; i < para.length(); ++i) {
                if (Character.isUpperCase(para.charAt(i))) {
                    stringBuilder.insert(i + index, "-");
                    ++index;
                }
            }
        }

        if (stringBuilder.toString().toLowerCase().startsWith("-")) {
            return stringBuilder.toString().toLowerCase().substring(1);
        } else {
            System.out.println(stringBuilder.toString().toLowerCase());
            return stringBuilder.toString().toLowerCase();
        }
    }

    public static void main(String[] args) {
        System.out.println(humpToShortbar("Demo"));
    }

    public String number(Object obj) {
        obj = obj != null && obj.toString().length() != 0 ? obj : 0;
        return obj.toString().equalsIgnoreCase("NaN") ? "NaN" : (new DecimalFormat("0.00")).format(Double.parseDouble(obj.toString()));
    }

    public String number(Object obj, String pattern) {
        obj = obj != null && obj.toString().length() != 0 ? obj : 0;
        return obj.toString().equalsIgnoreCase("NaN") ? "NaN" : (new DecimalFormat(pattern)).format(Double.parseDouble(obj.toString()));
    }

    public String round(Object obj) {
        obj = obj != null && obj.toString().length() != 0 ? obj : 0;
        return obj.toString().equalsIgnoreCase("NaN") ? "NaN" : (new DecimalFormat("0")).format(Double.parseDouble(obj.toString()));
    }

    public String currency(Object obj) {
        obj = obj != null && obj.toString().length() != 0 ? obj : 0;
        return NumberFormat.getCurrencyInstance(Locale.CHINA).format(obj);
    }

    public String timestampToString(Object obj, String pattern) {
        if (obj == null) {
            return "";
        } else {
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MM月 -yy");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern);
            Date date = null;

            try {
                date = simpleDateFormat1.parse(obj.toString());
            } catch (ParseException ex) {
                ex.printStackTrace();
                return "error";
            }

            return simpleDateFormat2.format(date);
        }
    }

    public String percent(Object obj) {
        obj = obj != null && obj.toString().length() != 0 ? obj : 0;
        return obj.toString().equalsIgnoreCase("NaN") ? "" : NumberFormat.getPercentInstance(Locale.CHINA).format(obj);
    }

    public String date(Object obj, String pattern) {
        return obj == null ? "" : (new SimpleDateFormat(pattern)).format(obj);
    }

    public String date(Object obj) {
        return obj == null ? "" : DateFormat.getDateInstance(1, Locale.CHINA).format(obj);
    }

    public String time(Object obj) {
        return obj == null ? "" : DateFormat.getTimeInstance(3, Locale.CHINA).format(obj);
    }

    public String datetime(Object obj) {
        return obj == null ? "" : DateFormat.getDateTimeInstance(1, 3, Locale.CHINA).format(obj);
    }

}
