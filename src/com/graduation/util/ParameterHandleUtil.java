package com.graduation.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import com.graduation.common.SystemContext;
import com.graduation.model.SearchParam;

public class ParameterHandleUtil {

    /**
     * sp中封装所有参数字段，将合法的参数字段存放在 parameters中
     *
     * @param sp         封装参数的对象
     * @param parameters 合法的参数存放的位置
     * @return 参数验证失败的信息
     */
    public static List<String> handleParameters(SearchParam sp, Map<String, Object> parameters) {
        List<String> errorMsg = new ArrayList<String>();
        if (sp.getPlanId() != null && !"".equals(sp.getPlanId().trim()))
            parameters.put("planId", sp.getPlanId());
        if (sp.getObjtype() != null && !"".equals(sp.getObjtype().trim()))
            parameters.put("objtype", sp.getObjtype());
        if (sp.getClz() != null && !"".equals(sp.getClz().trim()))
            parameters.put("clz", sp.getClz());
        addParam("mjd", sp.getMjd(), sp.getMjdMin(), sp.getMjdMax(), errorMsg, parameters);
        addParam("ra", sp.getRa(), sp.getRaMin(), sp.getRaMax(), errorMsg, parameters);
        addParam("dec", sp.getDec(), sp.getDecMin(), sp.getDecMax(), errorMsg, parameters);
        addParam("snu", sp.getSnu(), sp.getSnuMin(), sp.getSnuMax(), errorMsg, parameters);
        addParam("sng", sp.getSng(), sp.getSngMin(), sp.getSngMax(), errorMsg, parameters);
        addParam("snr", sp.getSnr(), sp.getSnrMin(), sp.getSnrMax(), errorMsg, parameters);
        addParam("sni", sp.getSni(), sp.getSniMin(), sp.getSniMax(), errorMsg, parameters);
        addParam("snz", sp.getSnz(), sp.getSnzMin(), sp.getSnzMax(), errorMsg, parameters);
        addParam("z", sp.getZ(), sp.getzMin(), sp.getzMax(), errorMsg, parameters);
        return errorMsg;
    }

    public static void addParam(String name, String value, String valueMin, String valueMax,
                                List<String> errorMsg, Map<String, Object> parameters) {
        if (!isNullOrEmpty(value)) {
            if (!isNumber(value)) {
                errorMsg.add(name + "字段不是数字类型...");
            } else {
                parameters.put(name, value);
                parameters.put(name + "Min", Double.parseDouble(value));
                parameters.put(name + "Max", Double.parseDouble(value));
            }
        } else {

            if (!isNullOrEmpty(valueMin) && !isNullOrEmpty(valueMax)) {
                if (!isNumber(valueMin)) {
                    errorMsg.add(name + "字段不是数字...");
                    return;
                }
                if (!isNumber(valueMax)) {
                    errorMsg.add(name + "字段不是数字...");
                    return;
                }
                if (Double.parseDouble(valueMin) > Double.parseDouble(valueMax)) {
                    errorMsg.add(name + "的最小值不能比最大值大...");
                    return;
                }
                parameters.put(name, valueMin);
                parameters.put(name + "Min", Double.parseDouble(valueMin));
                parameters.put(name + "Max", Double.parseDouble(valueMax));
            } else if (!isNullOrEmpty(valueMin)) {
                if (!isNumber(valueMin)) {
                    errorMsg.add(name + "字段不是数字...");
                    return;
                }
                parameters.put(name, valueMin);
                parameters.put(name + "Min", Double.parseDouble(valueMin));
                parameters.put(name + "Max", Double.MAX_VALUE);
            } else if (!isNullOrEmpty(valueMax)) {
                if (!isNumber(valueMax)) {
                    errorMsg.add(name + "字段不是数字...");
                    return;
                }
                parameters.put(name, valueMax);
                parameters.put(name + "Min", Double.MIN_VALUE);
                parameters.put(name + "Max", Double.parseDouble(valueMax));

            }

        }

    }


    public static boolean isNullOrEmpty(String value) {
        if (value != null && !"".equals(value.trim()))
            return false;
        return true;

    }

    public static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            return false;
        }
    }

    public static boolean isInteger(String str){
        try {
            Integer.parseInt(str);
            return  true;
        }catch (Exception e){
            return  false;
        }
    }

    /**
     * 根据页面差数动态生成Sql语句  （此函数是针对动态查询页面）
     * @param tableName
     * @param request
     * @return
     */
    public static String gernerateSql(String tableName,HttpServletRequest request){
        StringBuilder sql = new StringBuilder();
        sql.append("select * from");
        sql.append(" "+tableName+" t");

        String or_and = request.getParameter("or_and");

        List<String> values = new ArrayList<String>();
        Enumeration es = request.getParameterNames();
        while (es.hasMoreElements()){
            String pname = es.nextElement().toString();
            if("tableName".equals(pname)) continue;
            if("or_and".equals(pname)) continue;
            if(ParameterHandleUtil.isInteger(pname)){
                String[] vs = request.getParameterValues(pname);
                for(int i=0;i<vs.length;i++){
                    System.out.println("TTTTTTT:"+vs[i]);
                    values.add(vs[i]);
                }
            }
        }

        if("0".equals(or_and) && values.size()>0){
            sql.append(" where 1=2 ");
            for(String value : values){
                sql.append(" or t."+value);
            }
        }else{
            sql.append(" where 1=1 ");
            for(String value : values){
                sql.append(" and t."+value);
            }
        }

        //分页添加
       /* Integer pageSize = 15;javascript完成分页
        Integer pageOffset = (pagenumber-1)*pageSize;
        sql.append(" "+"limit "+" "+pageOffset+", "+pageSize);*/
        return sql.toString();
    }
    /**
     * 
     * @param tableName
     * @param condition is a json String.
     * @param filedSelectNames fields.
     * @return sql 语句。
     */
   
    public static String getSqlFromJsonByFields(String tableName, String condition,String fieldMultiSelect){
    	
    	JSONObject jsonObj = JSONObject.fromObject(condition); 
		  StringBuilder sql = new StringBuilder();
		//  String filedSelectNames=fieldMultiSelect.replaceAll(",",",t.");
		  
		 String filedSelectNames=fieldMultiSelect.toLowerCase().replaceAll("dec", "t.dec");
	      sql.append("select "+filedSelectNames+" from");
	     // String tableName=(String) jsonObj.get("tableName");
	      String or_and=(String) jsonObj.get("or_and");
	      sql.append(" "+tableName+" t");
	      Set<String> jsonKeys= jsonObj.keySet();
	      List<String> values = new ArrayList<String>();
	     // System.out.println(jsonKeys);
	      for(String str:jsonKeys){
	    	  System.out.println(jsonObj.get(str));
	      }
	     // Enumeration es = (Enumeration) jsonObj.keySet();
	      for(String str:jsonKeys){
	           // String pname = es.nextElement().toString();
	            if("tableName".equals(str)) continue;
	            if("or_and".equals(str)) continue;
	            if(isInteger(str)){
	                    System.out.println("TTTTTTT:"+jsonObj.get(str));
	                    values.add((String) jsonObj.get(str));
	                }
	            }
	        
	      if("0".equals(or_and) && values.size()>0){
	            sql.append(" where 1=2 ");
	            for(String value : values){
	                sql.append(" or t."+value);
	            }
	        }else{
	            sql.append(" where 1=1 ");
	            for(String value : values){
	                sql.append(" and t."+value);
	            }
	        }
	      System.out.println(sql.toString());
		return sql.toString();
    }
    
    
	public static String getALLBySqlName(String tableName) {
    	StringBuilder sql = new StringBuilder();
	      sql.append("select * from");
	      sql.append(" "+tableName.toUpperCase());
	      return sql.toString();
	}

	/**
     * 
     * @param tableName
     * @param condition is a json String.
     * @return sql 语句。
     */
    public static String getSqlFromJson(String tableName, String condition) {
    	  JSONObject jsonObj = JSONObject.fromObject(condition); 
		  StringBuilder sql = new StringBuilder();
	      sql.append("select * from");
	     // String tableName=(String) jsonObj.get("tableName");
	      String or_and=(String) jsonObj.get("or_and");
	      sql.append(" "+tableName+" t");
	      Set<String> jsonKeys= jsonObj.keySet();
	      List<String> values = new ArrayList<String>();
	     // System.out.println(jsonKeys);
	      for(String str:jsonKeys){
	    	  System.out.println(jsonObj.get(str));
	      }
	     // Enumeration es = (Enumeration) jsonObj.keySet();
	      for(String str:jsonKeys){
	           // String pname = es.nextElement().toString();
	            if("tableName".equals(str)) continue;
	            if("or_and".equals(str)) continue;
	            if(isInteger(str)){
	                    System.out.println("TTTTTTT:"+jsonObj.get(str));
	                    values.add((String) jsonObj.get(str));
	                }
	            }
	        
	      if("0".equals(or_and) && values.size()>0){
	            sql.append(" where 1=2 ");
	            for(String value : values){
	                sql.append(" or t."+value);
	            }
	        }else{
	            sql.append(" where 1=1 ");
	            for(String value : values){
	                sql.append(" and t."+value);
	            }
	        }
	      System.out.println(sql.toString());
		return sql.toString();
	}
    
    /**
     * 将文件保存在fits/username/tablenane下
     * @param file
     * @param username
     * @param tableName
     * @param error
     */
    public static void saveUploadFile(MultipartFile file,String username,String tableName,List<String> error){
    	try
		{
			String realPath = SystemContext.getRealPath()+username.toUpperCase()+System.getProperty("file.separator")+TableHandleUtil.getRealTableName(tableName);

			File zipPath = new File(realPath+System.getProperty("file.separator")+"zip");
			System.out.println(zipPath);
			String clientFileName = file.getOriginalFilename();
			
			String fileSuffix = clientFileName.substring(clientFileName.lastIndexOf(".")+1);
			File dest = new File(realPath);
			if(!dest.exists()){
				dest.mkdirs();
			}
			if(! zipPath.exists()){
				zipPath.mkdirs();
			}
			File destFile = new File(dest,tableName+"."+fileSuffix);
			System.out.println(file.getInputStream().read());
			System.out.println(destFile.getName());
			FileUtils.copyInputStreamToFile(file.getInputStream(),destFile);
			ZipUtil.ZipCompress(destFile,new File(zipPath,tableName+".zip"));
		} catch (IOException e)
		{
			System.out.println("文件保存失败......");
			error.add("文件保存失败......");
			e.printStackTrace();
		}
    	
    }

    @Test
    public void test01() {
        System.out.println(Double.parseDouble("1.80200000000E-05"));
        System.out.println(ParameterHandleUtil.isNumber("1.80200000000E-05"));
    }

	public static String getTableNameFromJson(String condition) {
		JSONObject jsonObj = JSONObject.fromObject(condition); 
		  
	      String tableName=(String) jsonObj.get("tableName");
	      
		return tableName;
	}
	
	/**
	 * correct sql sentence like "select dec"and so on.
	 * @param sql
	 * @return corrected sql
	 * 
	 * */
	public static String correctSql(String sql,String tableName) {
		sql=sql.toLowerCase();
		sql=sql.replaceAll("dec", "t.dec");
		String newSql=sql.substring(0, sql.indexOf(" from")+5)+" "+tableName.toUpperCase()+" t";
		return newSql;
	}

	/**
     * 暂时在这里写，可能以后会移到services，dao类中去；
     * @param List<Map<String, Object>> list , String key
     * @param list, String key
     * @return String[] values
     * @author tyang
     */
    @SuppressWarnings("null")
	public String[] getValuesByKey(List<Map<String, Object>> list , String key){
    	String[] values=null;
    	for (int i=0;i<list.size();i++){
	    	System.out.println( "SIZE:"+list.get(i).get(key));
	    	values[i]=(String)list.get(i).get(key);
	    }
    	return values;
    	
    }

}
