# 代码生成器

## 导入resources.generator 下资源文件
### code-template 生成模板
### config.properties 生成路径配置
### database.properties 数据库配置

## 使用
```Java
// 是否生成 java
private static final boolean ISJAVA = false;
// 是否生成 vue
private static final boolean ISVUE = true;

public static void main(String[] args) {
    try {
        // 默认构造器则都为 true
        // new GeneratorWindow();
        new GeneratorWindow(ISJAVA, ISVUE).pack();
    } catch (Exception ex) {
        System.out.println(ex.getMessage());
    }
}