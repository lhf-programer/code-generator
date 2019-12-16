package com.lvhaifeng.generator.window;

import com.lvhaifeng.generator.database.DBReadTableUtil;
import com.lvhaifeng.generator.generate.impl.CodeGenerator;
import com.lvhaifeng.generator.generate.pojo.TableVo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 代码生成器
 * @author haifeng.lv
 * @updateTime 2019/12/13 15:41
 */
public class GeneratorWindow extends JFrame {
    public GeneratorWindow() {
        // 初始化
        this.init();
    }

    /**
     * @description 窗口初始化
     * @author haifeng.lv
     * @updateTime 2019/12/13 15:43
     */
    public void init() {
        JPanel jPanel = new JPanel();
        this.setContentPane(jPanel);
        jPanel.setLayout(new GridLayout(9, 2));

        JLabel remind = new JLabel("提示:");
        // 标题
        final JLabel title = new JLabel();

        JLabel basePackageLabel = new JLabel("包名（小写）：");
        final JTextField basePackageField = new JTextField();

        JLabel entityClassLabel = new JLabel("实体类名（首字母大写）：");
        final JTextField entityClassField = new JTextField();

        JLabel tableLabel = new JLabel("表名：");
        final JTextField tableField = new JTextField(20);

        JLabel descriptionLabel = new JLabel("功能描述：");
        final JTextField descriptionField = new JTextField();

        final JCheckBox controller = new JCheckBox("controller");
        controller.setSelected(true);
        final JCheckBox service = new JCheckBox("Service");
        service.setSelected(true);
        final JCheckBox mapper = new JCheckBox("Mapper");
        mapper.setSelected(true);
        final JCheckBox entity = new JCheckBox("entity");
        entity.setSelected(true);

        jPanel.add(remind);
        jPanel.add(title);
        jPanel.add(basePackageLabel);
        jPanel.add(basePackageField);
        jPanel.add(entityClassLabel);
        jPanel.add(entityClassField);
        jPanel.add(tableLabel);
        jPanel.add(tableField);
        jPanel.add(descriptionLabel);
        jPanel.add(descriptionField);
        jPanel.add(controller);
        jPanel.add(service);
        jPanel.add(mapper);
        jPanel.add(entity);

        JButton generate = new JButton("生成");

        generate.addActionListener((ActionEvent e) -> {
            String basePackage;
            String entityClass;
            String table;
            String description;
            boolean verify = !"".equals(basePackageField.getText())
                    && !"".equals(entityClassField.getText())
                    && !"".equals(descriptionField.getText())
                    && !"".equals(tableField.getText());

            if (verify) {
                basePackage = basePackageField.getText();
                entityClass = entityClassField.getText();
                table = descriptionField.getText();
                description = tableField.getText();

                try {
                    boolean hasTable = DBReadTableUtil.hasTable(table);
                    if (hasTable) {
                        TableVo tableVo = new TableVo();
                        tableVo.setTableName(table);
                        tableVo.setEntityPackage(basePackage);
                        tableVo.setEntityName(entityClass);
                        tableVo.setFieldRowNum(1);
                        tableVo.setFtlDescription(description);

                        List<Boolean> isSelects = new ArrayList();
                        isSelects.add(controller.isSelected());
                        isSelects.add(service.isSelected());
                        isSelects.add(mapper.isSelected());
                        isSelects.add(entity.isSelected());

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
            new GeneratorWindow().pack();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
