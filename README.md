# 代码生成器

## 导入resources.generator 下资源文件
### code-template 生成模板
### config.properties 生成路径配置
### database.properties 数据库配置

## 使用
```Java
public static void main(String[] args) {
    try {
        (new CodeWindow()).pack();
    } catch (Exception var2) {
        System.out.println(var2.getMessage());
    }
}
