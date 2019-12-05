package com.lvhaifeng.generator.generate.util;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class b {
    public b() {
    }

    public static Configuration a(List<File> var0, String var1, String var2) throws IOException {
        Configuration var3 = new Configuration();
        FileTemplateLoader[] var4 = new FileTemplateLoader[var0.size()];

        for(int var5 = 0; var5 < var0.size(); ++var5) {
            var4[var5] = new FileTemplateLoader((File)var0.get(var5));
        }

        MultiTemplateLoader var6 = new MultiTemplateLoader(var4);
        var3.setTemplateLoader(var6);
        var3.setNumberFormat("###############");
        var3.setBooleanFormat("true,false");
        var3.setDefaultEncoding(var1);
        return var3;
    }

    public static List<String> a(String var0, String var1) {
        String[] var2 = b(var0, "\\/");
        ArrayList var3 = new ArrayList();
        var3.add(var1);
        var3.add(File.separator + var1);
        String var4 = "";

        for(int var5 = 0; var5 < var2.length; ++var5) {
            var4 = var4 + File.separator + var2[var5];
            var3.add(var4 + File.separator + var1);
        }

        return var3;
    }

    public static String[] b(String var0, String var1) {
        if (var0 == null) {
            return new String[0];
        } else {
            StringTokenizer var2 = new StringTokenizer(var0, var1);
            ArrayList var3 = new ArrayList();

            while(var2.hasMoreElements()) {
                Object var4 = var2.nextElement();
                var3.add(var4.toString());
            }

            return (String[])var3.toArray(new String[var3.size()]);
        }
    }

    public static String a(String var0, Map<String, Object> var1, Configuration var2) {
        StringWriter var3 = new StringWriter();

        try {
            Template var4 = new Template("templateString...", new StringReader(var0), var2);
            var4.process(var1, var3);
            return var3.toString();
        } catch (Exception var5) {
            throw new IllegalStateException("cannot process templateString:" + var0 + " cause:" + var5, var5);
        }
    }

    public static void a(Template var0, Map<String, Object> var1, File var2, String var3) throws IOException, TemplateException {
        BufferedWriter var4 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(var2), var3));
        var1.put("Format", new SimpleFormat());
        var0.process(var1, var4);
        var4.close();
    }
}
