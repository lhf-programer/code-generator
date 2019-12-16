package com.lvhaifeng.generator.generate.impl;

import com.lvhaifeng.generator.common.DBConstant;
import com.lvhaifeng.generator.generate.util.FileUtils;
import com.lvhaifeng.generator.generate.util.TemplateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @description 生成文件
 * @author haifeng.lv
 * @updateTime 2019/12/13 17:28
 */
public class GenerateFile {
    private static final Logger logger = LoggerFactory.getLogger(GenerateFile.class);
    private String path;
    private List<File> files;
    protected static String encoding = "UTF-8";

    private static final String CONTROLLER = "${entityName}Controller.javai";
    private static final String ENTITY = "${entityName}.javai";
    private static final String MAPPERXML = "${entityName}Mapper.xml";
    private static final String MAPPER = "${entityName}Mapper.javai";
    private static final String SERVICEIMPL = "${entityName}ServiceImpl.javai";
    private static final String SERVICE = "I${entityName}Service.javai";

    public GenerateFile(String path) {
        logger.info("----templatePath-----------------" + path);
        this.path = path;

        // 解析路径
        String filePath = this.getClass().getResource(this.path).getFile();
        filePath = filePath.replaceAll("%20", " ");
        this.files = Arrays.asList(new File(filePath));
        logger.debug("-------classpath-------" + path);
    }

    public List<File> getFiles() {
        return this.files;
    }

    /**
     * @description 生成文件
     * @author haifeng.lv
     * @param: constant 基本信息
     * @param: path 路径
     * @param: map 基本信息
     * @param: isSelects 生成哪些
     * @updateTime 2019/12/13 17:37
     */
    protected void generateFiles(String path, Map<String, Object> map, List<Boolean> isSelects) throws Exception {
        logger.info("--------generate----projectPath--------" + path);
        // 获取文件列表
        List<File> files = getFiles();

        for(int i = 0; i < files.size(); ++i) {
            File file = files.get(i);
            generateFiles(path, file, map, isSelects);
        }
    }

    protected void generateFiles(String path, File file, Map<String, Object> map, List<Boolean> isSelects) throws Exception {
        if (file == null) {
            throw new IllegalStateException("文件路径不能未空");
        } else {
            logger.debug("-------------------加载模板路径 = '" + file.getAbsolutePath() + "' java路径:" + (new File(DBConstant.sourceRootPackage.replace(".", File.separator))).getAbsolutePath() + "' 资源路径:" + (new File(DBConstant.resourcePackage.replace(".", File.separator))).getAbsolutePath());
            List files = FileUtils.getAllFile(file);
            logger.debug("----srcFiles----size-----------" + files.size());
            logger.debug("----srcFiles----list------------" + files.toString());

            for(int i = 0; i < files.size(); ++i) {
                String filePath = files.get(i).toString();
                if (filePath.endsWith(CONTROLLER) && !isSelects.get(0)) {
                    continue;
                } else if (filePath.endsWith(ENTITY) && !isSelects.get(3)) {
                    continue;
                } else if (filePath.endsWith(MAPPERXML) && !isSelects.get(2)) {
                    continue;
                } else if (filePath.endsWith(MAPPER) && !isSelects.get(2)) {
                    continue;
                } else if (filePath.endsWith(SERVICEIMPL) && !isSelects.get(1)) {
                    continue;
                } else if (filePath.endsWith(SERVICE) && !isSelects.get(1)) {
                    continue;
                }

                generateFiles(path, file, map, (File)files.get(i));
            }

        }
    }

    protected void generateFiles(String path, File file, Map<String, Object> map, File subFile) throws Exception {
        logger.debug("-------templateRootDir--" + file.getPath());
        logger.debug("-------srcFile--" + subFile.getPath());
        String templateFile = FileUtils.getFile(file, subFile);

        try {
            logger.debug("-------templateFile--" + templateFile);
            String generateFiles = generateFiles(map, templateFile);
            logger.debug("-------outputFilepath--" + generateFiles);
            String filePath;
            if (generateFiles.startsWith("java")) {
                filePath = path + File.separator + DBConstant.sourceRootPackage.replace(".", File.separator);
                generateFiles = generateFiles.substring("java".length());
                generateFiles = filePath + generateFiles;
                logger.debug("-------java----outputFilepath--" + generateFiles);
                this.generateFiles(templateFile, generateFiles, map);
            } else if (generateFiles.startsWith("resource")) {
                filePath = path + File.separator + DBConstant.resourcePackage.replace(".", File.separator);
                generateFiles = generateFiles.substring("resource".length());
                generateFiles = filePath + generateFiles;
                logger.debug("-------resource---outputFilepath---" + generateFiles);
                this.generateFiles(templateFile, generateFiles, map);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex.toString());
        }

    }

    protected void generateFiles(String templateFile, String generateFiles, Map<String, Object> map) throws Exception {
        if (generateFiles.endsWith("i")) {
            generateFiles = generateFiles.substring(0, generateFiles.length() - 1);
        }

        Template template = this.generateFiles(templateFile);
        template.setOutputEncoding(encoding);
        File file = FileUtils.getFile(generateFiles);
        logger.debug("[generate]\t template:" + templateFile + " ==> " + generateFiles);
        TemplateUtils.loadTemplate(template, map, file, encoding);
        if (this.generateFiles(file)) {
            generateFiles(file, "#segment#");
        }

    }

    protected Template generateFiles(String str) throws IOException {
        return TemplateUtils.loadTemplate(getFiles(), encoding).getTemplate(str);
    }

    protected boolean generateFiles(File file) {
        return file.getName().startsWith("[1-n]");
    }

    protected static void generateFiles(File file, String segment) {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        ArrayList list = new ArrayList();
        boolean flag = false;

        int index;
        label341: {
            label342: {
                try {
                    flag = true;
                    inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
                    bufferedReader = new BufferedReader(inputStreamReader);
                    boolean bool = false;
                    OutputStreamWriter outputStreamWriter = null;

                    while(true) {
                        String readLine;
                        while((readLine = bufferedReader.readLine()) != null) {
                            if (readLine.trim().length() > 0 && readLine.startsWith(segment)) {
                                String substring = readLine.substring(segment.length());
                                String absolutePath = file.getParentFile().getAbsolutePath();
                                substring = absolutePath + File.separator + substring;
                                logger.debug("[generate]\t split file:" + file.getAbsolutePath() + " ==> " + substring);
                                outputStreamWriter = new OutputStreamWriter(new FileOutputStream(substring), "UTF-8");
                                list.add(outputStreamWriter);
                                bool = true;
                            } else if (bool) {
                                logger.debug("row : " + readLine);
                                outputStreamWriter.append(readLine + "\r\n");
                            }
                        }

                        for(int i = 0; i < list.size(); ++i) {
                            ((Writer)list.get(i)).close();
                        }

                        bufferedReader.close();
                        inputStreamReader.close();
                        logger.debug("[generate]\t delete file:" + file.getAbsolutePath());
                        closeFile(file);
                        flag = false;
                        break label341;
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                    flag = false;
                    break label342;
                } catch (IOException ex) {
                    ex.printStackTrace();
                    flag = false;
                } finally {
                    if (flag) {
                        try {
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }

                            if (inputStreamReader != null) {
                                inputStreamReader.close();
                            }

                            if (list.size() > 0) {
                                for(int i = 0; i < list.size(); ++i) {
                                    if (list.get(i) != null) {
                                        ((Writer)list.get(i)).close();
                                    }
                                }
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }
                }

                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }

                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }

                    if (list.size() > 0) {
                        for(index = 0; index < list.size(); ++index) {
                            if (list.get(index) != null) {
                                ((Writer)list.get(index)).close();
                            }
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                return;
            }

            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }

                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }

                if (list.size() > 0) {
                    for(index = 0; index < list.size(); ++index) {
                        if (list.get(index) != null) {
                            ((Writer)list.get(index)).close();
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return;
        }

        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (list.size() > 0) {
                for(index = 0; index < list.size(); ++index) {
                    if (list.get(index) != null) {
                        ((Writer)list.get(index)).close();
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public String generateFiles(Map<String, Object> map, String path) throws Exception {
        String filePath = path;
        int index;
        if ((index = path.indexOf(64)) != -1) {
            filePath = path.substring(0, index);
            String key = path.substring(index + 1);
            Object value = map.get(key);
            if (value == null) {
                System.err.println("[not-generate] WARN: test expression is null by key:[" + key + "] on template:[" + path + "]");
                return null;
            }

            if (!"true".equals(String.valueOf(value))) {
                logger.error("[not-generate]\t test expression '@" + key + "' is false,template:" + path);
                return null;
            }
        }

        Configuration configuration = TemplateUtils.loadTemplate(this.files, encoding);
        filePath = TemplateUtils.loadTemplate(filePath, map, configuration);
        filePath = filePath.substring(0, filePath.lastIndexOf(".")).replace(".", File.separator) + filePath.substring(filePath.lastIndexOf("."));
        return filePath;
    }

    protected static boolean closeFile(File file) {
        boolean flag= false;
        for(int i = 0; !flag && i++ < 10; flag = file.delete()) {
            System.gc();
        }

        return flag;
    }

}
