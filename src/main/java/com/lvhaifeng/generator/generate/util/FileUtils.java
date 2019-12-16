package com.lvhaifeng.generator.generate.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description 文件工具类
 * @author haifeng.lv
 * @updateTime 2019/12/13 17:43
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    public static List<String> fileSuffix = new ArrayList();

    public FileUtils() {
    }

    /**
     * @description 获取该文件下所有得文件
     * @author haifeng.lv
     * @param: file
     * @updateTime 2019/12/13 17:40
     * @return: java.util.List<java.io.File>
     */
    public static List<File> getAllFile(File file) throws IOException {
        ArrayList response = new ArrayList();
        getAllFile(file, response);
        Collections.sort(response, (File file1, File file2) -> file1.getAbsolutePath().compareTo(file2.getAbsolutePath()));
        return response;
    }

    public static void getAllFile(File file, List<File> files) {
        logger.debug("---------dir------------path: " + file.getPath() + " -- isHidden --: " + file.isHidden() + " -- isDirectory --: " + file.isDirectory());
        if (!file.isHidden() && file.isDirectory() && !equalFileSuffix(file)) {
            File[] listFiles = file.listFiles();

            for(int i = 0; i < listFiles.length; ++i) {
                getAllFile(listFiles[i], files);
            }
        } else if (!equalFileSuffix(file)) {
            files.add(file);
        }

    }

    public static String getFile(File file1, File file2) {
        if (file1.equals(file2)) {
            return "";
        } else {
            return file1.getParentFile() == null ? file2.getAbsolutePath().substring(file1.getAbsolutePath().length()) : file2.getAbsolutePath().substring(file1.getAbsolutePath().length() + 1);
        }
    }

    /**
     * @description 是否是文件夹
     * @author haifeng.lv
     * @param: file
     * @updateTime 2019/12/13 17:53
     * @return: boolean
     */
    public static boolean isDirectory(File file) {
        return file.isDirectory() ? false : !StringUtils.isBlank(file.getName());
    }

    /**
     * @description 解析字符串
     * @author haifeng.lv
     * @param: str
     * @updateTime 2019/12/13 17:54
     * @return: java.lang.String
     */
    public static String resolverStr(String str) {
        if (str == null) {
            return null;
        } else {
            int num = str.indexOf(".");
            return num == -1 ? "" : str.substring(num + 1);
        }
    }

    /**
     * @description 获取文件
     * @author haifeng.lv
     * @param: str
     * @updateTime 2019/12/13 17:55
     * @return: java.io.File
     */
    public static File getFile(String str) {
        if (str == null) {
            throw new IllegalArgumentException("file must be not null");
        } else {
            File file = new File(str);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            return file;
        }
    }

    private static boolean equalFileSuffix(File file) {
        for(int i = 0;i < fileSuffix.size(); ++i) {
            if (file.getName().equals(fileSuffix.get(i))) {
                return true;
            }
        }
        return false;
    }

    static {
        fileSuffix.add(".svn");
        fileSuffix.add("CVS");
        fileSuffix.add(".cvsignore");
        fileSuffix.add(".copyarea.db");
        fileSuffix.add("SCCS");
        fileSuffix.add("vssver.scc");
        fileSuffix.add(".DS_Store");
        fileSuffix.add(".git");
        fileSuffix.add(".gitignore");
    }
}
