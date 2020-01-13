package com.lvhaifeng.generator.database;

import com.lvhaifeng.generator.generate.pojo.ColumnVo;
import com.lvhaifeng.generator.generate.util.FiledUtils;
import com.lvhaifeng.generator.generate.util.PrecisionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 读取表工具类
 * @author haifeng.lv
 * @updateTime 2019/12/13 17:00
 */
public class DBReadTable {
    private static final Logger logger = LoggerFactory.getLogger(DBReadTable.class);
    private static Connection connection;
    private static Statement statement;

    public DBReadTable() {
    }

    /**
     * @Description 查询所有表
     * @Author haifeng.lv
     * @Date 2020/1/10 9:55
     * @return: java.util.List<java.lang.String>
     */
    public static Map<String, String> readTableNameList() {
        String resultSet = null;
        Map<String, String> tables = new HashMap<>(0);

        try {
            Class.forName(DBConstant.diverName);
            connection = DriverManager.getConnection(DBConstant.url, DBConstant.username, DBConstant.password);
            statement = connection.createStatement(1005, 1007);
            if (DBConstant.dbType.equals("mysql")) {
                resultSet = MessageFormat.format("select table_name tableName, table_comment tableComment, engine, create_time createTime from information_schema.tables where table_schema = {0} order by create_time desc", PrecisionUtils.addColon(DBConstant.databaseName));
            }

            if (DBConstant.dbType.equals("oracle")) {
                resultSet = MessageFormat.format("select table_name tableName, table_comment tableComment, engine, create_time createTime from information_schema.tables where table_schema = {0} order by create_time desc", PrecisionUtils.addColon(DBConstant.databaseName));
            }

            if (DBConstant.dbType.equals("postgresql")) {
                resultSet = MessageFormat.format("select table_name tableName, table_comment tableComment, engine, create_time createTime from information_schema.tables where table_schema = {0} order by create_time desc", PrecisionUtils.addColon(DBConstant.databaseName));
            }

            if (DBConstant.dbType.equals("sqlserver")) {
                resultSet = MessageFormat.format("select table_name tableName, table_comment tableComment, engine, create_time createTime from information_schema.tables where table_schema = {0} order by create_time desc", PrecisionUtils.addColon(DBConstant.databaseName));
            }

            ResultSet result = statement.executeQuery(resultSet);

            while (result.next()) {
                String key = result.getString(1);
                String value = result.getString(2) == null?"":result.getString(2);
                tables.put(key, value);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    statement = null;
                    System.gc();
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                    System.gc();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return tables;
    }

    /**
     * @description 连接
     * @author haifeng.lv
     * @updateTime 2019/12/13 17:00
     * @return: java.util.List<java.lang.String>
     */
    public static List<String> connect() throws SQLException {
        String resultSet = null;
        ArrayList columns = new ArrayList(0);

        try {
            Class.forName(DBConstant.diverName);
            connection = DriverManager.getConnection(DBConstant.url, DBConstant.username, DBConstant.password);
            statement = connection.createStatement(1005, 1007);
            if (DBConstant.dbType.equals("mysql")) {
                resultSet = MessageFormat.format("select distinct table_name from information_schema.columns where table_schema = {0}", PrecisionUtils.addColon(DBConstant.databaseName));
            }

            if (DBConstant.dbType.equals("oracle")) {
                resultSet = " select distinct colstable.table_name as  table_name from user_tab_cols colstable order by colstable.table_name";
            }

            if (DBConstant.dbType.equals("postgresql")) {
                resultSet = "select tablename from pg_tables where schemaname='public'";
            }

            if (DBConstant.dbType.equals("sqlserver")) {
                resultSet = "select distinct c.name as  table_name from sys.objects c where c.type = 'U' ";
            }

            ResultSet result = statement.executeQuery(resultSet);

            while (result.next()) {
                String str = result.getString(1);
                columns.add(str);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    statement = null;
                    System.gc();
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                    System.gc();
                }
            } catch (SQLException ex) {
                throw ex;
            }
        }

        return columns;
    }

    public static List<ColumnVo> readColumns(String str) throws Exception {
        String sql = null;
        ArrayList columns = new ArrayList();

        ColumnVo columnVo;
        try {
            Class.forName(DBConstant.diverName);
            connection = DriverManager.getConnection(DBConstant.url, DBConstant.username, DBConstant.password);
            statement = connection.createStatement(1005, 1007);
            if (DBConstant.dbType.equals("mysql")) {
                sql = MessageFormat.format("select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1} order by ORDINAL_POSITION", PrecisionUtils.addColon(str), PrecisionUtils.addColon(DBConstant.databaseName));
            }

            if (DBConstant.dbType.equals("oracle")) {
                sql = MessageFormat.format(" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}", PrecisionUtils.addColon(str));
            }

            if (DBConstant.dbType.equals("postgresql")) {
                sql = MessageFormat.format("select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname FROM\tpg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name", PrecisionUtils.addColon(str.toLowerCase()), PrecisionUtils.addColon(str.toLowerCase()));
            }

            if (DBConstant.dbType.equals("sqlserver")) {
                sql = MessageFormat.format("select distinct cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as NVARCHAR(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) c on a.object_id=c.object_id left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0} order by a.column_id", PrecisionUtils.addColon(str.toLowerCase()));
            }

            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.last();
            int row = resultSet.getRow();
            if (row <= 0) {
                throw new Exception("该表不存在或者表中没有字段");
            }

            columnVo = new ColumnVo();
            if (DBConstant.dbFiledConvert) {
                columnVo.setFieldName(resolverStr(resultSet.getString(1).toLowerCase()));
            } else {
                columnVo.setFieldName(resultSet.getString(1).toLowerCase());
            }

            columnVo.setFieldDbName(resultSet.getString(1));
            columnVo.setFieldType(resolverStr(resultSet.getString(2).toLowerCase()));
            columnVo.setFieldDbType(resolverStr(resultSet.getString(2).toLowerCase()));
            columnVo.setPrecision(resultSet.getString(4));
            columnVo.setScale(resultSet.getString(5));
            columnVo.setCharmaxLength(resultSet.getString(6));
            columnVo.setNullable(PrecisionUtils.equal(resultSet.getString(7)));
            resolverType(columnVo);
            columnVo.setFiledComment(StringUtils.isBlank(resultSet.getString(3)) ? columnVo.getFieldName() : resultSet.getString(3));
            logger.debug("columnt.getFieldName() -------------" + columnVo.getFieldName());
            String[] baseFiled = new String[0];
            if (DBConstant.pageFilterFields != null) {
                baseFiled = DBConstant.pageFilterFields.toLowerCase().split(",");
            }

            if (!DBConstant.dbTableId.equals(columnVo.getFieldName()) && !FiledUtils.equal(columnVo.getFieldDbName().toLowerCase(), baseFiled)) {
                columns.add(columnVo);
            }

            while (resultSet.previous()) {
                ColumnVo column = new ColumnVo();
                if (DBConstant.dbFiledConvert) {
                    column.setFieldName(resolverStr(resultSet.getString(1).toLowerCase()));
                } else {
                    column.setFieldName(resultSet.getString(1).toLowerCase());
                }

                column.setFieldDbName(resultSet.getString(1));
                logger.debug("columnt.getFieldName() -------------" + column.getFieldName());
                if (!DBConstant.dbTableId.equals(column.getFieldName()) && !FiledUtils.equal(column.getFieldDbName().toLowerCase(), baseFiled)) {
                    column.setFieldType(resolverStr(resultSet.getString(2).toLowerCase()));
                    column.setFieldDbType(resolverStr(resultSet.getString(2).toLowerCase()));
                    logger.debug("-----po.setFieldType------------" + column.getFieldType());
                    column.setPrecision(resultSet.getString(4));
                    column.setScale(resultSet.getString(5));
                    column.setCharmaxLength(resultSet.getString(6));
                    column.setNullable(PrecisionUtils.equal(resultSet.getString(7)));
                    resolverType(column);
                    column.setFiledComment(StringUtils.isBlank(resultSet.getString(3)) ? column.getFieldName() : resultSet.getString(3));
                    columns.add(column);
                }
            }

            logger.debug("读取表成功");
        } catch (ClassNotFoundException ex) {
            throw ex;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    statement = null;
                    System.gc();
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                    System.gc();
                }
            } catch (SQLException ex) {
                throw ex;
            }

        }

        ArrayList response = new ArrayList();

        for (int i = columns.size() - 1; i >= 0; --i) {
            columnVo = (ColumnVo) columns.get(i);
            response.add(columnVo);
        }

        return response;
    }

    public static List<ColumnVo> readOriginalColumns(String str) throws Exception {
        ResultSet resultSet;
        String sql = "";
        ArrayList columns = new ArrayList();

        ColumnVo columnVo;
        try {
            Class.forName(DBConstant.diverName);
            connection = DriverManager.getConnection(DBConstant.url, DBConstant.username, DBConstant.password);
            statement = connection.createStatement(1005, 1007);
            if (DBConstant.dbType.equals("mysql")) {
                sql = MessageFormat.format("select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1}", PrecisionUtils.addColon(str), PrecisionUtils.addColon(DBConstant.databaseName));
            }

            if (DBConstant.dbType.equals("oracle")) {
                sql = MessageFormat.format(" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}", PrecisionUtils.addColon(str));
            }

            if (DBConstant.dbType.equals("postgresql")) {
                sql = MessageFormat.format("select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname FROM\tpg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name", PrecisionUtils.addColon(str.toLowerCase()), PrecisionUtils.addColon(str.toLowerCase()));
            }

            if (DBConstant.dbType.equals("sqlserver")) {
                sql = MessageFormat.format("select distinct cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as NVARCHAR(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) c on a.object_id=c.object_id left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0} order by a.column_id", PrecisionUtils.addColon(str.toLowerCase()));
            }

            resultSet = statement.executeQuery(sql);
            resultSet.last();
            int row = resultSet.getRow();
            if (row <= 0) {
                throw new Exception("该表不存在或者表中没有字段");
            }

            columnVo = new ColumnVo();
            if (DBConstant.dbFiledConvert) {
                columnVo.setFieldName(resolverStr(resultSet.getString(1).toLowerCase()));
            } else {
                columnVo.setFieldName(resultSet.getString(1).toLowerCase());
            }

            columnVo.setFieldDbName(resultSet.getString(1));
            columnVo.setPrecision(PrecisionUtils.isBlank(resultSet.getString(4)));
            columnVo.setScale(PrecisionUtils.isBlank(resultSet.getString(5)));
            columnVo.setCharmaxLength(PrecisionUtils.isBlank(resultSet.getString(6)));
            columnVo.setNullable(PrecisionUtils.equal(resultSet.getString(7)));
            columnVo.setFieldType(resolverType(resultSet.getString(2).toLowerCase(), columnVo.getPrecision(), columnVo.getScale()));
            columnVo.setFieldDbType(resolverStr(resultSet.getString(2).toLowerCase()));
            resolverType(columnVo);
            columnVo.setFiledComment(StringUtils.isBlank(resultSet.getString(3)) ? columnVo.getFieldName() : resultSet.getString(3));
            logger.debug("columnt.getFieldName() -------------" + columnVo.getFieldName());
            columns.add(columnVo);

            while (true) {
                if (!resultSet.previous()) {
                    logger.debug("读取表成功");
                    break;
                }

                ColumnVo column = new ColumnVo();
                if (DBConstant.dbFiledConvert) {
                    column.setFieldName(resolverStr(resultSet.getString(1).toLowerCase()));
                } else {
                    column.setFieldName(resultSet.getString(1).toLowerCase());
                }

                column.setFieldDbName(resultSet.getString(1));
                column.setPrecision(PrecisionUtils.isBlank(resultSet.getString(4)));
                column.setScale(PrecisionUtils.isBlank(resultSet.getString(5)));
                column.setCharmaxLength(PrecisionUtils.isBlank(resultSet.getString(6)));
                column.setNullable(PrecisionUtils.equal(resultSet.getString(7)));
                column.setFieldType(resolverType(resultSet.getString(2).toLowerCase(), column.getPrecision(), column.getScale()));
                column.setFieldDbType(resolverStr(resultSet.getString(2).toLowerCase()));
                resolverType(column);
                column.setFiledComment(StringUtils.isBlank(resultSet.getString(3)) ? column.getFieldName() : resultSet.getString(3));
                columns.add(column);
            }
        } catch (ClassNotFoundException ex) {
            throw ex;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    statement = null;
                    System.gc();
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                    System.gc();
                }
            } catch (SQLException ex) {
                throw ex;
            }

        }

        ArrayList response = new ArrayList();

        for (int i = columns.size() - 1; i >= 0; --i) {
            columnVo = (ColumnVo) columns.get(i);
            response.add(columnVo);
        }

        return response;
    }

    /**
     * @description 检查是否有该表
     * @author haifeng.lv
     * @param: tableName
     * @updateTime 2019/12/13 18:44
     * @return: boolean
     */
    public static boolean hasTable(String tableName) {
        String sql = null;

        try {
            logger.debug("数据库驱动: " + DBConstant.diverName);
            Class.forName(DBConstant.diverName);
            connection = DriverManager.getConnection(DBConstant.url, DBConstant.username, DBConstant.password);
            statement = connection.createStatement(1005, 1007);
            if (DBConstant.dbType.equals("mysql")) {
                sql = "select column_name,data_type,column_comment,0,0 from information_schema.columns where table_name = '" + tableName + "' and table_schema = '" + DBConstant.databaseName + "'";
            }
            if (DBConstant.dbType.equals("oracle")) {
                sql = "select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = '" + tableName + "'";
            }
            if (DBConstant.dbType.equals("postgresql")) {
                sql = MessageFormat.format("select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname FROM\tpg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY\tA.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name", PrecisionUtils.addColon(tableName.toLowerCase()), PrecisionUtils.addColon(tableName.toLowerCase()));
            }
            if (DBConstant.dbType.equals("sqlserver")) {
                sql = MessageFormat.format("select distinct cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as NVARCHAR(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) c on a.object_id=c.object_id left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0} order by a.column_id", PrecisionUtils.addColon(tableName.toLowerCase()));
            }

            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.last();
            int row = resultSet.getRow();
            return row > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private static String resolverStr(String field) {
        String[] split = field.split("_");
        field = "";
        int index = 0;

        for(int i = split.length; index < i; ++index) {
            if (index > 0) {
                String toLowerCase = split[index].toLowerCase();
                toLowerCase = toLowerCase.substring(0, 1).toUpperCase() + toLowerCase.substring(1, toLowerCase.length());
                field = field + toLowerCase;
            } else {
                field = field + split[index].toLowerCase();
            }
        }

        return field;
    }

    private static void resolverType(ColumnVo column) {
        String resultSet = column.getFieldType();
        String scale = column.getScale();
        column.setClassType("inputxt");
        if ("N".equals(column.getNullable())) {
            column.setOptionType("*");
        }

        if (!"datetime".equals(resultSet) && !resultSet.contains("time")) {
            if ("date".equals(resultSet)) {
                column.setClassType("easyui-datebox");
            } else if (resultSet.contains("int")) {
                column.setOptionType("n");
            } else if ("number".equals(resultSet)) {
                if (StringUtils.isNotBlank(scale) && Integer.parseInt(scale) > 0) {
                    column.setOptionType("d");
                }
            } else if (!"float".equals(resultSet) && !"double".equals(resultSet) && !"decimal".equals(resultSet)) {
                if ("numeric".equals(resultSet)) {
                    column.setOptionType("d");
                }
            } else {
                column.setOptionType("d");
            }
        } else {
            column.setClassType("easyui-datetimebox");
        }
    }

    private static String resolverType(String str, String resultSet, String value) {
        if (str.contains("char")) {
            str = "String";
        } else if (str.contains("int")) {
            str = "Integer";
        } else if (str.contains("float")) {
            str = "java.lang.Float";
        } else if (str.contains("double")) {
            str = "java.lang.Double";
        } else if (str.contains("number")) {
            if (StringUtils.isNotBlank(value) && Integer.parseInt(value) > 0) {
                str = "java.math.BigDecimal";
            } else if (StringUtils.isNotBlank(resultSet) && Integer.parseInt(resultSet) > 10) {
                str = "java.lang.Long";
            } else {
                str = "java.lang.Integer";
            }
        } else if (str.contains("decimal")) {
            str = "java.math.BigDecimal";
        } else if (str.contains("date")) {
            if (str.contains("datetime")) {
                str = "java.time.LocalDateTime";
            } else {
                str = "java.time.LocalDate";
            }
        } else if (str.contains("time")) {
            str = "java.time.LocalTime";
        } else if (str.contains("blob")) {
            str = "byte[]";
        } else if (str.contains("clob")) {
            str = "java.sql.Clob";
        } else if (str.contains("numeric")) {
            str = "java.math.BigDecimal";
        } else {
            str = "Object";
        }

        return str;
    }

}