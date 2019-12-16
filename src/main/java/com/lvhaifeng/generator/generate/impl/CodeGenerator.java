package com.lvhaifeng.generator.generate.impl;

import com.lvhaifeng.generator.common.DBConstant;
import com.lvhaifeng.generator.database.DBReadTableUtil;
import com.lvhaifeng.generator.generate.pojo.ColumnVo;
import com.lvhaifeng.generator.generate.pojo.TableVo;
import com.lvhaifeng.generator.generate.util.NonceUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @description 生成器
 * @author haifeng.lv
 * @updateTime 2019/12/13 16:10
 */
public class CodeGenerator {
    private static final Logger logger = LoggerFactory.getLogger(CodeGenerator.class);
    private TableVo tableVo;
    private List<ColumnVo> columns;
    private List<ColumnVo> originalColumns;

    public CodeGenerator(TableVo tableVo) {
        this.tableVo = tableVo;
    }

    /**
     * @description 加载字段
     * @author haifeng.lv
     * @updateTime 2019/12/13 17:22
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String, Object> loadFiled() throws Exception {
        HashMap map = new HashMap();
        map.put("basePackage", DBConstant.basePackage);
        map.put("entityPackage", this.tableVo.getEntityPackage());
        map.put("entityName", this.tableVo.getEntityName());
        map.put("tableName", this.tableVo.getTableName());
        map.put("primaryKeyField", DBConstant.dbTableId);
        if (this.tableVo.getFieldRequiredNum() == null) {
            this.tableVo.setFieldRequiredNum(4);
        }

        if (this.tableVo.getSearchFieldNum() == null) {
            this.tableVo.setSearchFieldNum(StringUtils.isNotEmpty(DBConstant.pageSearchFiledNum) ? Integer.parseInt(DBConstant.pageSearchFiledNum) : -1);
        }

        if (this.tableVo.getFieldRowNum() == null) {
            this.tableVo.setFieldRowNum(1);
        }

        map.put("tableVo", this.tableVo);

        try {
            if (this.columns == null || this.columns.size() == 0) {
                this.columns = DBReadTableUtil.connect(this.tableVo.getTableName());
            }

            map.put("columns", this.columns);
            if (this.originalColumns == null || this.originalColumns.size() == 0) {
                this.originalColumns = DBReadTableUtil.readColunms(this.tableVo.getTableName());
            }

            map.put("originalColumns", this.originalColumns);
            Iterator iterator = this.originalColumns.iterator();

            while(iterator.hasNext()) {
                ColumnVo columnVo = (ColumnVo)iterator.next();
                if (columnVo.getFieldName().toLowerCase().equals(DBConstant.dbTableId.toLowerCase())) {
                    map.put("primaryKeyPolicy", columnVo.getFieldType());
                }
            }
        } catch (Exception ex) {
            throw ex;
        }

        long time = NonceUtils.getRandom() + NonceUtils.getCurrentTimeMillis();
        map.put("serialVersionUID", String.valueOf(time));
        logger.info("code template data: " + map.toString());
        return map;
    }

    public void generateCodeFile(List<Boolean> isSelects) throws Exception {
        logger.info("---Code----Generation----[单表模型:" + this.tableVo.getTableName() + "]------- 生成中。。。");
        // 项目生成路径
        String path = DBConstant.projectPath;
        Map map = this.loadFiled();
        // 模板路径
        String templatePath = DBConstant.templatePath;

        GenerateFile generateFile = new GenerateFile(templatePath);
        generateFile.generateFiles(path, map, isSelects);
        logger.info("----Code----Generation-----[单表模型：" + this.tableVo.getTableName() + "]------ 生成完成。。。");
    }

}
