package com.lvhaifeng.generator.generate.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class a {
    private static final Logger c = LoggerFactory.getLogger(a.class);
    public static List<String> a = new ArrayList();
    public static List<String> b = new ArrayList();

    public a() {
    }

    public static List<File> a(File var0) throws IOException {
        ArrayList var1 = new ArrayList();
        a(var0, (List)var1);
        Collections.sort(var1, new Comparator<File>() {
            public int compare(File var1, File var2) {
                return var1.getAbsolutePath().compareTo(var2.getAbsolutePath());
            }
        });
        return var1;
    }

    public static void a(File var0, List<File> var1) throws IOException {
        c.debug("---------dir------------path: " + var0.getPath() + " -- isHidden --: " + var0.isHidden() + " -- isDirectory --: " + var0.isDirectory());
        if (!var0.isHidden() && var0.isDirectory() && !d(var0)) {
            File[] var2 = var0.listFiles();

            for(int var3 = 0; var3 < var2.length; ++var3) {
                a(var2[var3], var1);
            }
        } else if (!e(var0) && !d(var0)) {
            var1.add(var0);
        }

    }

    public static String a(File var0, File var1) {
        if (var0.equals(var1)) {
            return "";
        } else {
            return var0.getParentFile() == null ? var1.getAbsolutePath().substring(var0.getAbsolutePath().length()) : var1.getAbsolutePath().substring(var0.getAbsolutePath().length() + 1);
        }
    }

    public static boolean b(File var0) {
        return var0.isDirectory() ? false : a(var0.getName());
    }

    public static boolean a(String var0) {
        return !StringUtils.isBlank(b(var0));
    }

    public static String b(String var0) {
        if (var0 == null) {
            return null;
        } else {
            int var1 = var0.indexOf(".");
            return var1 == -1 ? "" : var0.substring(var1 + 1);
        }
    }

    public static File c(String var0) {
        if (var0 == null) {
            throw new IllegalArgumentException("file must be not null");
        } else {
            File var1 = new File(var0);
            c(var1);
            return var1;
        }
    }

    public static void c(File var0) {
        if (var0.getParentFile() != null) {
            var0.getParentFile().mkdirs();
        }

    }

    private static boolean d(File var0) {
        for(int var1 = 0; var1 < a.size(); ++var1) {
            if (var0.getName().equals(a.get(var1))) {
                return true;
            }
        }

        return false;
    }

    private static boolean e(File var0) {
        for(int var1 = 0; var1 < b.size(); ++var1) {
            if (var0.getName().endsWith((String)b.get(var1))) {
                return true;
            }
        }

        return false;
    }

    static {
        a.add(".svn");
        a.add("CVS");
        a.add(".cvsignore");
        a.add(".copyarea.db");
        a.add("SCCS");
        a.add("vssver.scc");
        a.add(".DS_Store");
        a.add(".git");
        a.add(".gitignore");
        b.add(".ftl");
    }
}
