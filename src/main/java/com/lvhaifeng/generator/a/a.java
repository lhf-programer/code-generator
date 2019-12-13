package com.lvhaifeng.generator.a;

import java.util.ResourceBundle;

public class a {
    private static final ResourceBundle r = ResourceBundle.getBundle("generator/database");
    private static final ResourceBundle s = ResourceBundle.getBundle("generator/config");
    public static String a = "mysql";
    public static String b = "com.mysql.jdbc.Driver";
    public static String c = "jdbc:mysql://localhost:3306/spring_cloud?useUnicode=true&characterEncoding=UTF-8";
    public static String d = "root";
    public static String e = "root";
    public static String f = "spring_cloud";
    public static String g = "c:/workspace/spring-cloud";
    public static String h = "com.lvhaifeng.cloud";
    public static String i = "src";
    public static String j = "WebRoot";
    public static String k = "/generator/code-template/";
    public static boolean l = true;
    public static String m;
    public static String n = "4";
    public static String o = "3";
    public static String p;
    public static String q = "1";

    public a() {
    }

    private void n() {
    }

    public static final String a() {
        return r.getString("diver_name");
    }

    public static final String b() {
        return r.getString("url");
    }

    public static final String c() {
        return r.getString("username");
    }

    public static final String d() {
        return r.getString("password");
    }

    public static final String e() {
        return r.getString("database_name");
    }

    public static final boolean f() {
        String var0 = s.getString("db_filed_convert");
        return !var0.toString().equals("false");
    }

    private static String o() {
        return s.getString("bussi_package");
    }

    private static String p() {
        return s.getString("templatepath");
    }

    public static final String g() {
        return s.getString("source_root_package");
    }

    public static final String h() {
        return s.getString("resource_package");
    }

    public static final String i() {
        return s.getString("db_table_id");
    }

    public static final String j() {
        return s.getString("page_filter_fields");
    }

    public static final String k() {
        return s.getString("page_search_filed_num");
    }

    public static final String l() {
        return s.getString("page_field_required_num");
    }

    public static String m() {
        String var0 = s.getString("project_path");
        if (var0 != null && !"".equals(var0)) {
            g = var0;
        }

        return g;
    }

    public static void a(String var0) {
        g = var0;
    }

    public static void b(String var0) {
        k = var0;
    }

    static {
        b = a();
        c = b();
        d = c();
        e = d();
        f = e();
        i = g();
        j = h();
        h = o();
        k = p();
        g = m();
        m = i();
        l = f();
        p = j();
        o = k();
        if (c.indexOf("mysql") < 0 && c.indexOf("MYSQL") < 0) {
            if (c.indexOf("oracle") < 0 && c.indexOf("ORACLE") < 0) {
                if (c.indexOf("postgresql") < 0 && c.indexOf("POSTGRESQL") < 0) {
                    if (c.indexOf("sqlserver") >= 0 || c.indexOf("sqlserver") >= 0) {
                        a = "sqlserver";
                    }
                } else {
                    a = "postgresql";
                }
            } else {
                a = "oracle";
            }
        } else {
            a = "mysql";
        }

        i = i.replace(".", "/");
        j = j.replace(".", "/");
    }
}