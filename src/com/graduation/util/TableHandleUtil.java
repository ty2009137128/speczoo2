package com.graduation.util;

import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableHandleUtil {
    /**
     * 判断字符串中是否有Record_id
     * @param firstLine
     * @return
     */
    public static boolean hasRecordId(String firstLine){
        if(null == firstLine) return false;
        return firstLine.contains("RECORD_ID");
    }
    /**
     * 生成创建表格的sql语句
     *
     * @param tableName
     * @param firstLine
     * @return
     */
    public static String getTableByString(String tableName, String firstLine) {
        StringBuilder createTableSql = new StringBuilder();
        //以非字符来分割字符串，可以连续出现多个空格逗号等多个非字符
        String[] fields = firstLine.split("[,;' '\t]+");
        /*System.out.println(fields.length);
		for(int i=0;i<fields.length;i++){
			System.out.println(i+":"+fields[i]);
		}*/
        //createTableSql.append("drop table if exists "+tableName+";\n");
        createTableSql.append("create table ").append("`"+tableName + "`(");
        for (int i = 0; i < fields.length; i++) {
            createTableSql.append("`"+fields[i]).append("` text");
            if (i != (fields.length - 1)) {
                createTableSql.append(",");
            } else {
                createTableSql.append("");
            }
        }
        createTableSql.append(")");
        return createTableSql.toString().toUpperCase();
    }

    /**
     * 将一个字符串分割成数组，返回数组的长度
     *
     * @param line
     * @return
     */
    public static int getColumnNumberByString(String line) {
        return line.split("[,;' '\t]+").length;
    }

    /**
     * 将字符串处理成可插入的语句
     *
     * @param line           要处理的字符串
     * @param fieldColumnNum 表格字段的数目
     * @param row            正在处理的字符串在所在文件的行数
     * @param error          存储错误消息
     * @return
     */
    public static String getInsertValueByString(String line, int fieldColumnNum, Long row, List<String> error) {
    	String line1=line.replaceAll(",(?=[^\\w])", ",null");
    	String line2=line1.replaceAll("null[\\-]", "-");
    	System.out.println("所得到的Line为："+line2);
        String[] values = line2.split("[,;' '\t]+");
        if (values.length != fieldColumnNum) {
            error.add(row + "行字段数目错误.....");
            return null;
        }
        //TODO
        //validate未实现
        if (!TableHandleUtil.validate(values)) {
            error.add(row + "数据类型不正确.....");
            return null;
        }

        StringBuilder str = new StringBuilder();
        str.append("(");
        for (int i = 0; i < values.length; i++) {
            str.append("'" + values[i] + "'");
            if (i != values.length - 1) {
                str.append(",");
            }
        }
        str.append(")");
        return str.toString();
    }

    /**
     * 验证给定数组是否类型错误
     *
     * @param values
     * @return
     */
    public static boolean validate(String[] values) {
        return true;
    }

    /**
     * 判断某个Sql语句是否为对数据库的修改语句
     * @return
     */
    public static boolean isSqlUpdate(String sql){
        String regex = "(\\bupdate\\b)|(\\bdelete\\b)|(\\binsert\\b)|(\\bdrop\\b)|(\\balter\\b)";
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE );
        Matcher m =  p.matcher(sql);
        System.out.println("isSqlUpdate"+m.find());
        return m.find();
    }

    /**
     * 将一条sql语句处理成查询该语句结果的sql语句
     * @param sql
     * @return
     */
    public static String getSqlQueryCount(String sql){
        String newSql = "select count(*) from";
        int start = sql.indexOf(" from")+" from".length();
        newSql = newSql+" "+sql.substring(start);
        return newSql;
    }
    
    public static String getRealTableName(String username_tablename){
    	return username_tablename.substring(username_tablename.lastIndexOf("_")+1);
    }
/*-------------------------------------Test Aera-----------------------------------------------------------*/
    @Test
    public void test01() {
        //String str = "id,name pa,nick";
       // System.out.println(TableHandleUtil.getTableByString("test", str));
      //  String str = "record_Id,planId,    mjd,ra,     ; dec1 ";
        String str = "record_Id planId mjd \tra \t     dec   ";
        String[] ss= str.split("[,;' '\t]+");
        System.out.println(ss.length);
        for(String s : ss){
            System.out.println(s);
        }

    }

    @Test
    public void test02(){
        boolean b = TableHandleUtil.isSqlUpdate("select  * from t_user (delete from)");
        System.out.println(b);
    }
    @Test
    public void test03(){
        String b = TableHandleUtil.getSqlQueryCount("select  * from t_user where 1=1");
        System.out.println(b);
    }


    @Test
    public void test04(){
       String firstLine = "record_Id planId mjd ra     dec ";
       String s  = TableHandleUtil.getTableByString("xxxx",firstLine);
       System.out.println(s);
    }

}
