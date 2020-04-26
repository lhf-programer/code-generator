package com.lvhaifeng.generator.window;

import com.lvhaifeng.generator.database.DBReadTable;
import com.lvhaifeng.generator.generate.impl.CodeGenerator;
import com.lvhaifeng.generator.generate.pojo.TableVo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author haifeng.lv
 * @description 代码生成器
 * @updateTime 2019/12/13 15:41
 */
public class GeneratorWindow extends JFrame {
    public GeneratorWindow(boolean isJava, boolean isVue) {
        // 初始化
        this.init(isJava, isVue);
    }

    public GeneratorWindow() {
        // 初始化
        this.init(true, true);
    }

    /**
     * @description 窗口初始化
     * @author haifeng.lv
     * @updateTime 2019/12/13 15:43
     */
    public void init(boolean isJava, boolean isVue) {
        JPanel jPanel = new JPanel();
        this.setContentPane(jPanel);
        jPanel.setLayout(new GridLayout(12, 2));

        JLabel remind = new JLabel("提示:");
        // 标题
        final JLabel title = new JLabel();

        JLabel basePackageLabel = new JLabel("包名（小写）：");
        final JTextField basePackageField = new JTextField();

        // 查询所有数据库表
        Map<String, String> tables = DBReadTable.readTableNameList();
        if (tables.isEmpty()) {
            throw new RuntimeException("没有查询到数据库表请检查配置");
        }

        JLabel tableLabel = new JLabel("表名：");
        JComboBox tableBox = new JComboBox();
        tables.entrySet().forEach(entity -> {
            tableBox.addItem(entity.getKey());
        });
        String firstItem = (String) tableBox.getItemAt(0);

        JLabel entityClassLabel = new JLabel("实体类名（首字母大写）：");
        final JTextField entityClassField = new JTextField(20);
        entityClassField.setText(firstItem.substring(0, 1).toUpperCase() + firstItem.substring(1));

        JLabel descriptionLabel = new JLabel("功能描述：");
        final JTextField descriptionField = new JTextField();
        descriptionField.setText(tables.get(firstItem));

        final JCheckBox javaMode = new JCheckBox("java");
        javaMode.setSelected(isJava);
        final JCheckBox isEmpty = new JCheckBox("is_empty");
        isEmpty.setSelected(false);

        final JCheckBox controller = new JCheckBox("controller");
        controller.setSelected(isJava);
        final JCheckBox service = new JCheckBox("Service");
        service.setSelected(isJava);
        final JCheckBox mapper = new JCheckBox("Mapper");
        mapper.setSelected(isJava);
        final JCheckBox entity = new JCheckBox("entity");
        entity.setSelected(isJava);
        final JCheckBox isCheckClient = new JCheckBox("检查客户端");
        isCheckClient.setSelected(isJava);
        final JCheckBox isCheckUser = new JCheckBox("检查用户端");
        isCheckUser.setSelected(isJava);

        final JCheckBox vueMode = new JCheckBox("vue");
        vueMode.setSelected(isVue);
        JLabel vueLabel = new JLabel();
        final JCheckBox requestConfig = new JCheckBox("请求配置");
        requestConfig.setSelected(isVue);
        final JCheckBox tableInfo = new JCheckBox("表格");
        tableInfo.setSelected(isVue);

        jPanel.add(remind);
        jPanel.add(title);
        jPanel.add(basePackageLabel);
        jPanel.add(basePackageField);
        jPanel.add(tableLabel);
        jPanel.add(tableBox);
        jPanel.add(entityClassLabel);
        jPanel.add(entityClassField);
        jPanel.add(descriptionLabel);
        jPanel.add(descriptionField);
        jPanel.add(javaMode);
        jPanel.add(isEmpty);
        jPanel.add(controller);
        jPanel.add(service);
        jPanel.add(mapper);
        jPanel.add(entity);
        jPanel.add(isCheckClient);
        jPanel.add(isCheckUser);
        jPanel.add(vueMode);
        jPanel.add(vueLabel);
        jPanel.add(requestConfig);
        jPanel.add(tableInfo);

        JButton generate = new JButton("生成");

        tableBox.addActionListener((ActionEvent e) -> {
            String tableName = (String) tableBox.getSelectedItem();
            String[] tableNames = tableName.split("_");
            StringBuilder entityClassName = new StringBuilder();

            for (int i = 0; i < tableNames.length; i++) {
                entityClassName.append(tableNames[i].substring(0, 1).toUpperCase() + tableNames[i].substring(1));
            }

            entityClassField.setText(entityClassName.toString());
            descriptionField.setText(tables.get(tableName));
        });
        // java 模块
        javaMode.addActionListener((ActionEvent e) -> {
            boolean selected = javaMode.isSelected();
            controller.setSelected(selected);
            service.setSelected(selected);
            mapper.setSelected(selected);
            entity.setSelected(selected);
            isCheckClient.setSelected(selected);
            isCheckUser.setSelected(selected);
        });
        // vue 模块
        vueMode.addActionListener((ActionEvent e) -> {
            boolean selected = vueMode.isSelected();
            requestConfig.setSelected(selected);
            tableInfo.setSelected(selected);
        });
        generate.addActionListener((ActionEvent e) -> {
            String basePackage;
            String entityClass;
            String table;
            String description;
            boolean verify = !"".equals(basePackageField.getText())
                    && !"".equals(entityClassField.getText())
                    && !"".equals(descriptionField.getText())
                    && !"".equals(tableBox.getSelectedItem());

            if (verify) {
                basePackage = basePackageField.getText();
                entityClass = entityClassField.getText();
                table = (String) tableBox.getSelectedItem();
                description = descriptionField.getText();

                try {
                    boolean hasTable = DBReadTable.hasTable(table);
                    if (hasTable) {
                        TableVo tableVo = new TableVo();
                        tableVo.setTableName(table);
                        tableVo.setEntityPackage(basePackage);
                        tableVo.setEntityName(entityClass);
                        tableVo.setFieldRowNum(1);
                        tableVo.setFtlDescription(description);
                        tableVo.setIsCheckClient(isCheckClient.isSelected() ? 1 : 0);
                        tableVo.setIsCheckUser(isCheckUser.isSelected() ? 1 : 0);
                        tableVo.setIsEmpty(isEmpty.isSelected() ? 1 : 0);

                        List<Boolean> isSelects = new ArrayList();
                        isSelects.add(controller.isSelected());
                        isSelects.add(service.isSelected());
                        isSelects.add(mapper.isSelected());
                        isSelects.add(entity.isSelected());
                        isSelects.add(tableInfo.isSelected());
                        isSelects.add(requestConfig.isSelected());

                        new CodeGenerator(tableVo).generateCodeFile(isSelects);
                        title.setForeground(Color.red);
                        title.setText("成功生成增删改查->功能：" + description);
                    } else {
                        title.setForeground(Color.red);
                        title.setText("表[" + table + "] 在数据库中，不存在");
                        System.err.println(" ERROR ：   表 [ " + table + " ] 在数据库中，不存在 ！请确认数据源配置是否配置正确、表名是否填写正确~ ");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                error(title, "都得填！");
            }
        });

        JButton exit = new JButton("退出");
        exit.addActionListener((ActionEvent actionEvent) -> {
            GeneratorWindow.this.dispose();
            System.exit(0);
        });

        jPanel.add(generate);
        jPanel.add(exit);
        this.setTitle("代码生成器[单表模型]");
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(this.getOwner());
    }

    /**
     * 是否生成 java
     */
    private static final boolean ISJAVA = true;
    /**
     * 是否生成 vue
     */
    private static final boolean ISVUE = true;

    /**
     * @description 设置错误信息
     * @author haifeng.lv
     * @param: title
     * @param: text 错误信息
     * @updateTime 2019/12/13 15:59
     */
    public void error(JLabel title, String text) {
        title.setText(text);
        title.setForeground(Color.red);
    }

    public static void main(String[] args) {
        try {
            // 默认构造器则都为 true
            // new GeneratorWindow();
            new GeneratorWindow(ISJAVA, ISVUE).pack();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
