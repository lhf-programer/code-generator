package com.lvhaifeng.generator.database;

import java.util.ResourceBundle;

/**
 * @description 常量类
 * @author haifeng.lv
 * @updateTime 2019/12/13 16:18
 */
public class DBConstant {
    private static final ResourceBundle database = ResourceBundle.getBundle("generator/database");
    private static final ResourceBundle config = ResourceBundle.getBundle("generator/config");
    public static String dbType = database.getString("db_type");
    public static String diverName = database.getString("diver_name");
    public static String url = database.getString("url");
    public static String username = database.getString("username");
    public static String password = database.getString("password");
    public static String databaseName = database.getString("database_name");
    public static String projectPath = config.getString("project_path");
    public static String basePackage = config.getString("base_package");
    public static String sourceRootPackage = config.getString("source_root_package");
    public static String resourcePackage = config.getString("resource_package");
    public static String templatePath = config.getString("template_path");
    public static boolean dbFiledConvert = true;
    public static String dbTableId = config.getString("db_table_id");
    public static String pageSearchFiledNum = config.getString("page_search_filed_num");
    public static String pageFilterFields = config.getString("page_filter_fields");

    static {
        sourceRootPackage = sourceRootPackage.replace(".", "/");
        resourcePackage = resourcePackage.replace(".", "/");
    }
}