package com.lvhaifeng.generator.generate.impl.a;

import com.lvhaifeng.generator.generate.util.b;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class a {
    private static final Logger a = LoggerFactory.getLogger(com.lvhaifeng.generator.generate.impl.a.a.class);
    protected static String c = "UTF-8";

    public a() {
    }

    protected void a(com.lvhaifeng.generator.generate.a.a var1, String var2, Map<String, Object> var3) throws Exception {
        a.info("--------generate----projectPath--------" + var2);

        for(int var4 = 0; var4 < var1.b().size(); ++var4) {
            File var5 = (File)var1.b().get(var4);
            this.a(var2, var5, var3, var1);
        }

    }

    protected void a(String var1, File var2, Map<String, Object> var3, com.lvhaifeng.generator.generate.a.a var4) throws Exception {
        if (var2 == null) {
            throw new IllegalStateException("'templateRootDir' must be not null");
        } else {
            a.debug("-------------------load template from templateRootDir = '" + var2.getAbsolutePath() + "' outJavaRootDir:" + (new File(com.lvhaifeng.generator.a.a.i.replace(".", File.separator))).getAbsolutePath() + "' outWebappRootDir:" + (new File(com.lvhaifeng.generator.a.a.j.replace(".", File.separator))).getAbsolutePath());
            List var5 = com.lvhaifeng.generator.generate.util.a.a(var2);
            a.debug("----srcFiles----size-----------" + var5.size());
            a.debug("----srcFiles----list------------" + var5.toString());

            for(int var6 = 0; var6 < var5.size(); ++var6) {
                File var7 = (File)var5.get(var6);
                this.a(var1, var2, var3, var7, var4);
            }

        }
    }

    protected void a(String var1, File var2, Map<String, Object> var3, File var4, com.lvhaifeng.generator.generate.a.a var5) throws Exception {
        a.debug("-------templateRootDir--" + var2.getPath());
        a.debug("-------srcFile--" + var4.getPath());
        String var6 = com.lvhaifeng.generator.generate.util.a.a(var2, var4);

        try {
            a.debug("-------templateFile--" + var6);
            String var7 = a(var3, var6, var5);
            a.debug("-------outputFilepath--" + var7);
            String var8;
            if (var7.startsWith("java")) {
                var8 = var1 + File.separator + com.lvhaifeng.generator.a.a.i.replace(".", File.separator);
                var7 = var7.substring("java".length());
                var7 = var8 + var7;
                a.debug("-------java----outputFilepath--" + var7);
                this.a(var6, var7, var3, var5);
            } else if (var7.startsWith("webapp")) {
                var8 = var1 + File.separator + com.lvhaifeng.generator.a.a.j.replace(".", File.separator);
                var7 = var7.substring("webapp".length());
                var7 = var8 + var7;
                a.debug("-------webapp---outputFilepath---" + var7);
                this.a(var6, var7, var3, var5);
            }
        } catch (Exception var10) {
            var10.printStackTrace();
            a.error(var10.toString());
        }

    }

    protected void a(String var1, String var2, Map<String, Object> var3, com.lvhaifeng.generator.generate.a.a var4) throws Exception {
        if (var2.endsWith("i")) {
            var2 = var2.substring(0, var2.length() - 1);
        }

        Template var5 = this.a(var1, var4);
        var5.setOutputEncoding(c);
        File var6 = com.lvhaifeng.generator.generate.util.a.c(var2);
        a.debug("[generate]\t template:" + var1 + " ==> " + var2);
        b.a(var5, var3, var6, c);
        if (this.a(var6)) {
            a(var6, "#segment#");
        }

    }

    protected Template a(String var1, com.lvhaifeng.generator.generate.a.a var2) throws IOException {
        return b.a(var2.b(), c, var1).getTemplate(var1);
    }

    protected boolean a(File var1) {
        return var1.getName().startsWith("[1-n]");
    }

    protected static void a(File var0, String var1) {
        InputStreamReader var2 = null;
        BufferedReader var3 = null;
        ArrayList var4 = new ArrayList();
        boolean var19 = false;

        int var27;
        label341: {
            label342: {
                try {
                    var19 = true;
                    var2 = new InputStreamReader(new FileInputStream(var0), "UTF-8");
                    var3 = new BufferedReader(var2);
                    boolean var6 = false;
                    OutputStreamWriter var7 = null;

                    while(true) {
                        String var5;
                        while((var5 = var3.readLine()) != null) {
                            if (var5.trim().length() > 0 && var5.startsWith(var1)) {
                                String var8 = var5.substring(var1.length());
                                String var9 = var0.getParentFile().getAbsolutePath();
                                var8 = var9 + File.separator + var8;
                                a.debug("[generate]\t split file:" + var0.getAbsolutePath() + " ==> " + var8);
                                var7 = new OutputStreamWriter(new FileOutputStream(var8), "UTF-8");
                                var4.add(var7);
                                var6 = true;
                            } else if (var6) {
                                a.debug("row : " + var5);
                                var7.append(var5 + "\r\n");
                            }
                        }

                        for(int var28 = 0; var28 < var4.size(); ++var28) {
                            ((Writer)var4.get(var28)).close();
                        }

                        var3.close();
                        var2.close();
                        a.debug("[generate]\t delete file:" + var0.getAbsolutePath());
                        b(var0);
                        var19 = false;
                        break label341;
                    }
                } catch (FileNotFoundException var24) {
                    var24.printStackTrace();
                    var19 = false;
                    break label342;
                } catch (IOException var25) {
                    var25.printStackTrace();
                    var19 = false;
                } finally {
                    if (var19) {
                        try {
                            if (var3 != null) {
                                var3.close();
                            }

                            if (var2 != null) {
                                var2.close();
                            }

                            if (var4.size() > 0) {
                                for(int var11 = 0; var11 < var4.size(); ++var11) {
                                    if (var4.get(var11) != null) {
                                        ((Writer)var4.get(var11)).close();
                                    }
                                }
                            }
                        } catch (IOException var20) {
                            var20.printStackTrace();
                        }

                    }
                }

                try {
                    if (var3 != null) {
                        var3.close();
                    }

                    if (var2 != null) {
                        var2.close();
                    }

                    if (var4.size() > 0) {
                        for(var27 = 0; var27 < var4.size(); ++var27) {
                            if (var4.get(var27) != null) {
                                ((Writer)var4.get(var27)).close();
                            }
                        }
                    }
                } catch (IOException var21) {
                    var21.printStackTrace();
                }

                return;
            }

            try {
                if (var3 != null) {
                    var3.close();
                }

                if (var2 != null) {
                    var2.close();
                }

                if (var4.size() > 0) {
                    for(var27 = 0; var27 < var4.size(); ++var27) {
                        if (var4.get(var27) != null) {
                            ((Writer)var4.get(var27)).close();
                        }
                    }
                }
            } catch (IOException var22) {
                var22.printStackTrace();
            }

            return;
        }

        try {
            if (var3 != null) {
                var3.close();
            }

            if (var2 != null) {
                var2.close();
            }

            if (var4.size() > 0) {
                for(var27 = 0; var27 < var4.size(); ++var27) {
                    if (var4.get(var27) != null) {
                        ((Writer)var4.get(var27)).close();
                    }
                }
            }
        } catch (IOException var23) {
            var23.printStackTrace();
        }

    }

    protected static String a(Map<String, Object> var0, String var1, com.lvhaifeng.generator.generate.a.a var2) throws Exception {
        String var3 = var1;
        boolean var4 = true;
        int var8;
        if ((var8 = var1.indexOf(64)) != -1) {
            var3 = var1.substring(0, var8);
            String var5 = var1.substring(var8 + 1);
            Object var6 = var0.get(var5);
            if (var6 == null) {
                System.err.println("[not-generate] WARN: test expression is null by key:[" + var5 + "] on template:[" + var1 + "]");
                return null;
            }

            if (!"true".equals(String.valueOf(var6))) {
                a.error("[not-generate]\t test expression '@" + var5 + "' is false,template:" + var1);
                return null;
            }
        }

        Configuration var9 = b.a(var2.b(), c, "/");
        var3 = b.a(var3, var0, var9);
        String var10 = var3.substring(var3.lastIndexOf("."));
        String var7 = var3.substring(0, var3.lastIndexOf(".")).replace(".", File.separator);
        var3 = var7 + var10;
        return var3;
    }

    protected static boolean b(File var0) {
        boolean var1 = false;

        for(int var2 = 0; !var1 && var2++ < 10; var1 = var0.delete()) {
            System.gc();
        }

        return var1;
    }

    protected static String a(String var0, String var1) {
        boolean var2 = true;
        boolean var3 = true;

        do {
            int var4 = var0.indexOf(var1) == 0 ? 1 : 0;
            int var5 = var0.lastIndexOf(var1) + 1 == var0.length() ? var0.lastIndexOf(var1) : var0.length();
            var0 = var0.substring(var4, var5);
            var2 = var0.indexOf(var1) == 0;
            var3 = var0.lastIndexOf(var1) + 1 == var0.length();
        } while(var2 || var3);

        return var0;
    }
}
