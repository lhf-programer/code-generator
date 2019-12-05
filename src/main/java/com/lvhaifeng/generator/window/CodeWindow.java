package com.lvhaifeng.generator.window;

import com.lvhaifeng.generator.database.DbReadTableUtil;
import com.lvhaifeng.generator.generate.impl.CodeGenerateOne;
import com.lvhaifeng.generator.generate.pojo.TableVo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CodeWindow extends JFrame {
    private static final long b = -5324160085184088010L;
    private static String c = "test";
    private static String d = "TestEntity";
    private static String e = "t00_company";
    private static String f = "分公司";
    private static Integer g = 1;
    private static String h = "uuid";
    private static String i = "";
    String[] a = new String[]{"uuid", "identity", "sequence"};

    public CodeWindow() {
        JPanel var1 = new JPanel();
        this.setContentPane(var1);
        var1.setLayout(new GridLayout(14, 2));
        JLabel var2 = new JLabel("提示:");
        final JLabel var3 = new JLabel();
        JLabel var4 = new JLabel("包名（小写）：");
        final JTextField var5 = new JTextField();
        JLabel var6 = new JLabel("实体类名（首字母大写）：");
        final JTextField var7 = new JTextField();
        JLabel var8 = new JLabel("表名：");
        final JTextField var9 = new JTextField(20);
        JLabel var10 = new JLabel("主键生成策略：");
        final JComboBox var11 = new JComboBox(this.a);
        var11.setEnabled(false);
        JLabel var12 = new JLabel("主键SEQUENCE：(oracle序列名)");
        final JTextField var13 = new JTextField(20);
        JLabel var14 = new JLabel("功能描述：");
        final JTextField var15 = new JTextField();
        JLabel var16 = new JLabel("行字段数目：");
        JTextField var17 = new JTextField();
        var17.setText(g + "");
        JCheckBox var21 = new JCheckBox("Control");
        var21.setSelected(true);
        JCheckBox var23 = new JCheckBox("Service");
        var23.setSelected(true);
        JCheckBox var24 = new JCheckBox("Mapper.xml");
        var24.setSelected(true);
        JCheckBox var25 = new JCheckBox("Dao");
        var25.setSelected(true);
        var1.add(var2);
        var1.add(var3);
        var1.add(var4);
        var1.add(var5);
        var1.add(var6);
        var1.add(var7);
        var1.add(var8);
        var1.add(var9);
        var1.add(var10);
        var1.add(var11);
        var1.add(var12);
        var1.add(var13);
        var1.add(var14);
        var1.add(var15);
        var1.add(var16);
        var1.add(var17);
        var1.add(var21);
        var1.add(var23);
        var1.add(var24);
        var1.add(var25);
        JButton var27 = new JButton("生成");
        var27.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!"".equals(var5.getText())) {
                    CodeWindow.c = var5.getText();
                    if (!"".equals(var7.getText())) {
                        CodeWindow.d = var7.getText();
                        if (!"".equals(var15.getText())) {
                            CodeWindow.f = var15.getText();
                            if (!"".equals(var9.getText())) {
                                CodeWindow.e = var9.getText();
                                CodeWindow.h = (String)var11.getSelectedItem();
                                if (CodeWindow.h.equals("sequence")) {
                                    if ("".equals(var13.getText())) {
                                        var3.setForeground(Color.red);
                                        var3.setText("主键生成策略为sequence时，序列号不能为空！");
                                        return;
                                    }

                                    CodeWindow.i = var13.getText();
                                }

                                try {
                                    boolean var2 = DbReadTableUtil.c(CodeWindow.e);
                                    if (var2) {
                                        TableVo var3x = new TableVo();
                                        var3x.setTableName(CodeWindow.e);
                                        var3x.setPrimaryKeyPolicy(CodeWindow.h);
                                        var3x.setEntityPackage(CodeWindow.c);
                                        var3x.setEntityName(CodeWindow.d);
                                        var3x.setFieldRowNum(CodeWindow.g);
                                        var3x.setSequenceCode(CodeWindow.i);
                                        var3x.setFtlDescription(CodeWindow.f);
                                        (new CodeGenerateOne(var3x)).generateCodeFile();
                                        var3.setForeground(Color.red);
                                        var3.setText("成功生成增删改查->功能：" + CodeWindow.f);
                                    } else {
                                        var3.setForeground(Color.red);
                                        var3.setText("表[" + CodeWindow.e + "] 在数据库中，不存在");
                                        System.err.println(" ERROR ：   表 [ " + CodeWindow.e + " ] 在数据库中，不存在 ！请确认数据源配置是否配置正确、表名是否填写正确~ ");
                                    }
                                } catch (Exception var4) {
                                    var3.setForeground(Color.red);
                                    var3.setText(var4.getMessage());
                                }

                            } else {
                                var3.setForeground(Color.red);
                                var3.setText("表名不能为空！");
                            }
                        } else {
                            var3.setForeground(Color.red);
                            var3.setText("描述不能为空！");
                        }
                    } else {
                        var3.setForeground(Color.red);
                        var3.setText("实体类名不能为空！");
                    }
                } else {
                    var3.setForeground(Color.red);
                    var3.setText("包名不能为空！");
                }
            }
        });
        JButton var28 = new JButton("退出");
        var28.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CodeWindow.this.dispose();
                System.exit(0);
            }
        });
        var1.add(var27);
        var1.add(var28);
        this.setTitle("代码生成器[单表模型]");
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(this.getOwner());
    }

    public static void main(String[] args) {
        try {
            (new CodeWindow()).pack();
        } catch (Exception var2) {
            System.out.println(var2.getMessage());
        }

    }
}
