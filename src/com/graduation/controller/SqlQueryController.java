package com.graduation.controller;

import com.graduation.common.Pager;
import com.graduation.common.SystemContext;
import com.graduation.model.AjaxObj;
import com.graduation.model.AuthorityMethod;
import com.graduation.model.AuthorityType;
import com.graduation.model.User;
import com.graduation.service.IMydbService;
import com.graduation.service.ISqlQueryService;

import com.graduation.util.FitsUtil;
import com.graduation.util.ParameterHandleUtil;
import com.graduation.util.ResultSetHandleUtil;
import com.graduation.util.TableHandleUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by xiaoquan on 2014/5/17.
 * Changed by tyang on 2014-8-19.
 */
@Controller
@RequestMapping("/sql")
public class SqlQueryController {

    @Inject
    private ISqlQueryService sqlQueryService;

	private String sql;

	private List<Map<String, Object>> lmps;

	private String tableName;


    @AuthorityMethod(authorityTypes = AuthorityType.SQL_QUERY)
    @RequestMapping(value = "/sqlsearch",method = RequestMethod.GET)
    public String toSqlpage(){
        return "sql/sqlsearch";
    }

    @SuppressWarnings({ "unchecked", "null" })
	@AuthorityMethod(authorityTypes = AuthorityType.SQL_QUERY)
    @RequestMapping(value = "/sqlsearch",method = RequestMethod.POST)
    public String showData(@RequestParam("sql") String sql,Model model){
        ArrayList<String> error = new ArrayList<String>();
        if (sql == null || "".equals(sql.trim())){
            error.add("The SQL statement cannot be empty....");
            model.addAttribute("error", error);
            model.addAttribute("sql", sql);
            return "sql/sqlsearch";
        }
        Pattern p=Pattern.compile("(.*from\\s)(\\w*)(.*)");
		Matcher m=p.matcher(sql);
		
		String tableName=null;
		if(m.find()){
			//System.out.println("~~~~~~~~~~~TABLE NAME="+m.group(2));
			tableName=m.group(2);
			}
		else{
				//System.out.println("~~~~~~~~~~~TABLE NAME=false");
				tableName="false";
			} 
		this.tableName=tableName;
        String correctedSql=ParameterHandleUtil.correctSql(sql,tableName);
        this.sql=correctedSql;
        Pager<Map<String,Object>> pager = null;
        Pager<Map<String,Object>> pagerBySyelect = null;
        
        System.out.println(correctedSql);
        try {
            pagerBySyelect = this.sqlQueryService.executeSql(correctedSql,error);
            pager=this.sqlQueryService.executeSql(ParameterHandleUtil.getALLBySqlName(this.tableName),error);
            if (null == pager && pagerBySyelect==null)
                return "sql/sqlsearch";
        } catch (SQLException e) {
           // e.printStackTrace();
        	
        	
            error.add("The SQL statement is abnormal....,Or you deleted data contains the referenced data");
            model.addAttribute("error",error);
            return "sql/sqlsearch";
        }
       
        List<Map<String,Object>> lmps = pager.getDatas();
        List<Map<String,Object>> lmpsBySyelect = pagerBySyelect.getDatas();
        System.out.println("lmps的size："+lmps.size());
        this.lmps=lmps;
        
        // 截取lmps中的四个字段，拼接成fits文件的文件名。
        List<Map<String, Object>> nameFiled= new ArrayList<Map<String, Object>>();
        for(Map lmap:lmps){
        	Map mapTransit=new HashMap<String, Object>();
        	if(lmap.get(FitsUtil.fields[0])==null){
        		mapTransit.put(FitsUtil.fields[1],lmap.get(FitsUtil.fields[1]));
        	}else{
        		mapTransit.put(FitsUtil.fields[0],lmap.get(FitsUtil.fields[0]));
        	}
        	
        	mapTransit.put(FitsUtil.fields[2],lmap.get(FitsUtil.fields[2]));
        	mapTransit.put(FitsUtil.fields[3],lmap.get(FitsUtil.fields[3]));
        	mapTransit.put(FitsUtil.fields[4],lmap.get(FitsUtil.fields[4]));
        	
        	nameFiled.add(mapTransit);
        }
      //  String[] fitsNames= FitsUtil.getFitsNameByValue(nameFiled);
        
       
		
		boolean havefits=FitsUtil.JudeFitsCatalog(tableName,error);
		if (havefits==true){
			model.addAttribute("haveFits", true);
//			for(int i=0;i<lmpsBySyelect.size();i++){
//				lmpsBySyelect.get(i).put("Name", fitsNames[i]+".fits");
//				lmps.get(i).put("Name", fitsNames[i]+".fits");
//			}
	     }else{
	            model.addAttribute("haveFits", false); 
	     }
		if(FitsUtil.JudeHaveRaAndDec(lmps.get(0).keySet())==true){
        	model.addAttribute("haveRaAndDec", true);
        	//Names.add("RA_DEC");
        	for(int i=0;i<lmps.size();i++){
        		lmpsBySyelect.get(i).put("Ra", lmps.get(i).get("RA"));
        		lmpsBySyelect.get(i).put("Dec", lmps.get(i).get("DEC"));
        		lmps.get(i).put("Ra", lmps.get(i).get("RA"));
        		lmps.get(i).put("Dec", lmps.get(i).get("DEC"));
        		
			}
        	
        }
		model.addAttribute("tableName", tableName);
		
		String nameString=lmps.get(0).keySet().toString();
		 List<String> FitsPaths =new ArrayList<String>();
	        boolean havePATH=false;
	        if(nameString.contains(FitsUtil.fields[1])&& nameString.contains(FitsUtil.fields[2])&&nameString.contains(FitsUtil.fields[3])){
	        	FitsPaths=FitsUtil.getFitsUrlFromLAMOST(lmps);
	        	havePATH=true;
	        }else if(nameString.contains(FitsUtil.fields[0])&& nameString.contains(FitsUtil.fields[6])&&nameString.contains(FitsUtil.fields[7])){
	        	FitsPaths=FitsUtil.getFitsUrlFromSDSS(lmps);
	        	havePATH=true;
	        }else if(nameString.contains(FitsUtil.fields[8])){
	        	FitsPaths=FitsUtil.getFitsUrlFromLAMOST2(lmps);
	        	havePATH=true;
	        }else {
	        	havePATH=false;
	        }
	        
		String col = sql.substring(7, sql.indexOf(" from"));//WantList这里的问题！！！！！
		System.out.println(col);
		List<String> Names=new ArrayList<String>();
		List<Map<String, Object>> dataList= new ArrayList<Map<String, Object>>();
		if (col.equals("*")) {
				System.out.println("*条件****到这里了么？");
				Names.addAll(lmps.get(0).keySet());
				dataList.addAll(lmps);
				//System.out.println("***条件下的lmps"+lmps);
			} else {

				dataList.addAll(lmpsBySyelect);
				Names.addAll(lmpsBySyelect.get(0).keySet());
		    
		}
		
		if(havePATH==true){
        	Names.add("PATH");
        	for(int i=0;i<dataList.size();i++){
	        	
        		dataList.get(i).put("PATH", FitsPaths.get(i));
			}
        	
        }else{
        	
        }
			model.addAttribute("names",Names);
			model.addAttribute("lmps", dataList);
	        model.addAttribute("havePATH",havePATH);
			model.addAttribute("error", error);
	        model.addAttribute("sql", sql);
	        model.addAttribute("total",pager.getTotal());
			 return "sql/sqlsearch";
    }
    
    @RequestMapping(value = "/sqldownload",method = RequestMethod.POST)
    public String sqldown(@RequestParam("record_id") String record_id,@RequestParam(defaultValue="") String fileSource,HttpServletResponse response,HttpSession session){		
		
    	boolean havefits=FitsUtil.JudeFitsCatalog(tableName,null);
    	if (havefits==true){
				 return "redirect:/sql/fitsZipDownload/" + record_id;
	    
	        }else{
	        	 return "redirect:/sql/tableDownload";
	        }
	
    	}
    @RequestMapping(value = "/tableDownload")
  public void SqlTableDownload(@RequestParam(defaultValue="") String fileSource,HttpServletResponse response,HttpSession session){
		  try {//fileSource表示从哪里取数据，如果为空则从数据库里面取，如果为zip则到fits/username/tablename/下取zip文件
	      	//如果是file则到fits/username/tablename下取未打包的文件
			User loginUser = (User) session.getAttribute("loginUser");	
	  		System.out.println("~~~~~~~+ls"+lmps);
	  		String fileName = new String(tableName.getBytes("UTF-8"), "ISO-8859-1");	  	
	  		response.reset();
	  		response.setCharacterEncoding("UTF-8");
	  		response.setContentType("application/octet-stream;charset=UTF-8");
	  		if("file".equals(fileSource)){
	      	String realPath = SystemContext.getRealPath()+loginUser.getUsername()+System.getProperty("file.separator")+TableHandleUtil.getRealTableName(tableName);
	  		File fileFile = new File(realPath,tableName);
	  		String zipFileName = new String(fileFile.getName().getBytes("UTF-8"), "ISO-8859-1");
	  		response.setHeader("Content-Disposition", "attachment;filename=" + zipFileName);
	  		IOUtils.copy(new FileInputStream(fileFile),response.getOutputStream());	  		
	  		}else if("zip".equals(fileSource)){
		      	String realPath = SystemContext.getRealPath();
		      	File zipFile = new File(realPath+"/temp","data.zip");
		  		String zipFileName = new String(zipFile.getName().getBytes("UTF-8"), "ISO-8859-1");
		  		response.setHeader("Content-Disposition", "attachment;filename=" + zipFileName);
		  		IOUtils.copy(new FileInputStream(zipFile),response.getOutputStream());
		  		FileUtils.deleteQuietly(new File(realPath+"/temp"));
		  		
	  		}else{
		      	//System.out.println("到这里了吗？TXT !");
		      	fileName = fileName + ".txt";
		      	//System.out.println("!!!!fileName!!!"+fileName);
		      	response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		      	ResultSetHandleUtil.listMap2OutputStream(lmps, response.getOutputStream());
	      	
	  		}
		  } catch (UnsupportedEncodingException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		  } catch (IOException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		  }


	  
  }
    /*
     * 通过勾选的id 得到对应的记录，获取记录中的LMJD，PLANID，SPID，FIBERID找出对应的文件。
     * 
     * */

    @RequestMapping(value = "/fitsZipDownload/{record_id}")
    public void sqlFitsDownlod(@PathVariable("record_id") String record_id,HttpServletResponse response,HttpSession session)throws MalformedURLException{
        
    	List<String> error=new ArrayList<String>();
    	 AjaxObj ajax = new AjaxObj();
    	String[] record=record_id.split(",");
    	//System.out.println("record的值是：：：：：："+record.toString());
        List<String> recordList=new ArrayList<String>();
        for(String str:record){
	    	if(str!=null && str.length()!=0){ 
	        	recordList.add(str);
	    	}
		  	   
		}
        if(recordList.size()<1){
        	error.add("You did not select any!!");
        	ajax.setResult(0);
            ajax.setObj(error);
        }
        
        List<URL> fitsUrls=FitsUtil.getPathsFromTableById(lmps,recordList,error);
    	ajax.setResult(1);
        ajax.setObj(error);
    	
    	ZipOutputStream zos=null;
        ServletOutputStream sos=null;
        java.io.InputStream in=null;
        //comm_data cdata=new comm_data();
        try{

            response.reset();
            response.setContentType("application/x-msdownload"); //通知客户文件的MIME类型：
            String filename = "default.zip";
            response.setHeader("Content-disposition","attachment;filename=" + filename);
            sos = response.getOutputStream();
            zos = new ZipOutputStream(sos);
            //接受参数


            ZipEntry ze = null;
            byte[] buf = new byte[2048]; //输出文件用的字节数组,每次发送2048个字节到输出流：
            int readLength = 0;
            int z=0;
         
          //处理数组,打包  
            for(URL fitsURL:fitsUrls){
              
               // String FilePath = filepaths[i];      //list为存放路径的数组 循环可以得到路径和文件名
            	URLConnection con = fitsURL.openConnection();
         		  String urlPath = con.getURL().getFile();
          	  System.out.println("fitspaths=="+urlPath);
                String FileName = urlPath.substring(urlPath.indexOf("spec"));		//下载打包时的名称，以下是为把文件打包到第一目录而做
         		
                System.out.println("File"+urlPath);
                ze = new ZipEntry(FileName);
                ze.setSize(urlPath.length());
                zos.putNextEntry(ze);

               InputStream is = new BufferedInputStream( new FileInputStream(urlPath));
               System.out.println("InputStream~~~~~~~~~~=="+is);
               while ( (readLength = is.read(buf, 0, 2048))!=-1) 
               {
                 zos.write(buf, 0, readLength);
                // System.out.println("读取到了！！！");
               }
               is.close();
             }
   
        }catch(Exception ex)
               {
              System.out.println("Error download:"+ex.toString());
               }finally
               {
               if(zos!=null){
              try
              {
                zos.close();
                sos.close();
              }catch(Exception ex)
              {
                 System.out.println("Error download:"+ex.toString());
              }
             }
             }
    	
    }
    
    
    @RequestMapping(value = "/fitsdownload/{fitpath}", method = RequestMethod.GET)
    public String sqlDownloadFits(@PathVariable("fitpath") String fitPath, HttpServletResponse response) {
    	
    	
    	String[] ut = this.tableName.split("_");
		String FitsFilePath =ut[0]+","+ut[1]+","+"fits,"+fitPath;
    	
    	System.out.println("FitsFilePath:"+FitsFilePath);
    	return "redirect:/fits/"+FitsFilePath ;
    

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
