package com.lvhaifeng.generator.generate.util;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @description 模板工具
 * @author haifeng.lv
 * @updateTime 2019/12/16 11:14
 */
public class TemplateUtils {
    public TemplateUtils() {
    }

    public static Configuration loadTemplate(List<File> files, String encoding) throws IOException {
        Configuration configuration = new Configuration(new Version(2, 3, 0));
        FileTemplateLoader[] fileTemplateLoaders = new FileTemplateLoader[files.size()];

        for(int i = 0; i < files.size(); ++i) {
            fileTemplateLoaders[i] = new FileTemplateLoader(files.get(i));
        }

        MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(fileTemplateLoaders);
        configuration.setTemplateLoader(multiTemplateLoader);
        configuration.setNumberFormat("###############");
        configuration.setBooleanFormat("true,false");
        configuration.setDefaultEncoding(encoding);
        return configuration;
    }

    public static List<String> loadTemplate(String s1, String s2) {
        String[] strs = stringTokenizer(s1, "\\/");
        ArrayList list = new ArrayList();
        list.add(s2);
        list.add(File.separator + s2);
        String path = "";

        for(int i = 0; i < strs.length; ++i) {
            path = path + File.separator + strs[i];
            list.add(path + File.separator + s2);
        }

        return list;
    }

    public static String[] stringTokenizer(String s1, String s2) {
        if (s1 == null) {
            return new String[0];
        } else {
            StringTokenizer stringTokenizer = new StringTokenizer(s1, s2);
            ArrayList list = new ArrayList();

            while(stringTokenizer.hasMoreElements()) {
                Object element = stringTokenizer.nextElement();
                list.add(element.toString());
            }

            return (String[])list.toArray(new String[list.size()]);
        }
    }

    public static String loadTemplate(String s1, Map<String, Object> path, Configuration configuration) {
        StringWriter stringWriter = new StringWriter();

        try {
            Template template = new Template("templateString...", new StringReader(s1), configuration);
            template.process(path, stringWriter);
            return stringWriter.toString();
        } catch (Exception ex) {
            throw new IllegalStateException("cannot process templateString:" + s1 + " cause:" + ex, ex);
        }
    }

    public static void loadTemplate(Template template, Map<String, Object> map, File file, String value) throws IOException, TemplateException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), value));
        map.put("Format", new SimpleFormat());
        template.process(map, bufferedWriter);
        bufferedWriter.close();
    }
}
