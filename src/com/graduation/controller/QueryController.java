package com.graduation.controller;

import com.graduation.common.Pager;
import com.graduation.common.SystemContext;
import com.graduation.model.AjaxObj;
import com.graduation.model.AuthorityMethod;
import com.graduation.model.AuthorityType;
import com.graduation.model.MydbElement;
import com.graduation.model.User;
import com.graduation.model.dto.Datadto;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
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
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/query")
public class QueryController{

    @Inject
    private IMydbService mydbService;

    @Inject
    private ISqlQueryService sqlQueryService;
    private String tableName;
    
    private List<Map<String, Object>> lmps;
   
    @AuthorityMethod(authorityTypes = AuthorityType.SQL_QUERY)
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public String toQuery(){
        return "query/query";
    }

    @RequestMapping("/getTableNames")
    @ResponseBody
    public List<String> tableNamesByClicked(HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        List<MydbElement> mydbs =  this.mydbService.list(loginUser.getId());
        ArrayList<String> tableNames = new ArrayList<String>();
        for(MydbElement mydb : mydbs){
            tableNames.add(mydb.getTableName());
        }
        return tableNames;
    }


    @RequestMapping("/getFieldNames")
    @ResponseBody
    public List<String> fieldNamesByTableName(@RequestParam("tableName") String tableName){
        List<String> filedNames = new ArrayList<String>();
        try {
            filedNames =  this.mydbService.getFieldNamesOfTable(tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filedNames;
    }

    
    @RequestMapping(value = "/querySearch",method = RequestMethod.POST)
    public String ShowData(@RequestParam("condition") String condition,@RequestParam("fieldMultiSelect") String fieldMultiSelect,HttpServletRequest request,Model model){
        List<String> error = new ArrayList<String>();
        String sql = ParameterHandleUtil.gernerateSql(tableName,request);
        System.out.println("得到condition了吗？？"+condition);
        this.tableName=ParameterHandleUtil.getTableNameFromJson(condition);
        System.out.println(this.tableName);
        
        String sqlFromJsonByFileds=ParameterHandleUtil.getSqlFromJsonByFields(this.tableName,condition,fieldMultiSelect);
        String sqlFromJson=ParameterHandleUtil.getSqlFromJson(this.tableName,condition);
        System.out.println(sqlFromJsonByFileds);
       
        System.out.println("得到filedSelectNames了吗？？"+fieldMultiSelect);
        Pager<Map<String,Object>> pager = null;
        Pager<Map<String,Object>> pagerByfields=null;
        try {
            pager = this.sqlQueryService.executeSql(sqlFromJson,error);
            System.out.println("pager"+pager.getDatas());
            pagerByfields=this.sqlQueryService.executeSql(sqlFromJsonByFileds,error);
            System.out.println("pagerByfields"+pagerByfields.getDatas());
            if (null == pager && null == pagerByfields)
                return "query/query";
        } catch (SQLException e) {
            // e.printStackTrace();
            error.add("SQL语句异常....,或者您删除的数据中含有被参照的数据");
            model.addAttribute("error",error);
            return "query/query";
        }
        List<Map<String,Object>> lmps = pager.getDatas();
        List<Map<String,Object>> lmpsByfileds = pagerByfields.getDatas();
     //   System.out.println("QueryController的lmps的查询结果："+lmps);
        
       // model.addAttribute("names", lmps.get(0).keySet());
        model.addAttribute("names", lmpsByfileds.get(0).keySet());
        
        List<Map<String, Object>> nameFiled= new ArrayList<Map<String, Object>>();
        for(Map<String, Object> lmap:lmps){
        	Map<String, Object> mapTransit=new HashMap<String, Object>();
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
        List<String> Names=new ArrayList<String>();
        Names.addAll(lmpsByfileds.get(0).keySet());
        //判断是否为fits表
        if(FitsUtil.JudeFitsCatalog(tableName,null)==true){
        	model.addAttribute("haveFits", true);
        	//for(int i=0;i<lmps.size();i++){
        	//	lmpsByfileds.get(i).put("Name", fitsNames[i]+".fits");
        		 
			//}
        	Names.add("Name");
    		
        }
        else{
        	model.addAttribute("haveFits", false);
        	System.out.println("test到这里了吗！？？？");
        	
        }
        
        //判断是否有RA，和DEC
        
        if(FitsUtil.JudeHaveRaAndDec(lmps.get(0).keySet())==true){
        	model.addAttribute("haveRaAndDec", true);
        	Names.add("RA_DEC");
        	for(int i=0;i<lmps.size();i++){
        		lmpsByfileds.get(i).put("Ra", lmps.get(i).get("RA"));
        		lmpsByfileds.get(i).put("Dec", lmps.get(i).get("DEC"));
        		
			}
        	
        }
        
        String nameString = ((Map)lmps.get(0)).keySet().toString();
        List FitsPaths = new ArrayList();
        boolean havePATH = false;
      //  String[] fitsNames = FitsUtil.getFitsNameByValue(nameFiled);
       // System.out.println("demo到这里了吗？" + fitsNames[0]);
       
       // if (fitsNames != null) {
       //   for (i = 0; i < fitsNames.length; ++i) {
       //     FitsPaths.add(fitsNames[i]);
       //   }
       //   havePATH = true;
       // }
       if (((nameString.contains(FitsUtil.fields[1]))||(nameString.contains(FitsUtil.fields[0])))&& (nameString.contains(FitsUtil.fields[2])) && (nameString.contains(FitsUtil.fields[3]))) {
	          
    	   	  FitsPaths = FitsUtil.getFitsUrlFromLAMOST(lmps);
	          havePATH = true;
        } else if ((nameString.contains(FitsUtil.fields[0])) && (nameString.contains(FitsUtil.fields[6])) && (nameString.contains(FitsUtil.fields[7]))) {
	          FitsPaths = FitsUtil.getFitsUrlFromSDSS(lmps);
	          havePATH = true;
        } else if (nameString.contains(FitsUtil.fields[8])) {
	          FitsPaths = FitsUtil.getFitsUrlFromLAMOST2(lmps);
	          havePATH = true;
        } else {
        	havePATH = false;
        }
       System.out.println("havePATH的值"+havePATH);
        if (havePATH) {
        	
	          Names.add("PATH");
	          for (int i = 0; i < lmps.size(); ++i)
	          {
	        	  System.out.println("这里的fitsURL为："+FitsPaths.get(i));
	        	  ((Map)lmpsByfileds.get(i)).put("PATH", FitsPaths.get(i));
	        	  ((Map)lmps.get(i)).put("PATH", FitsPaths.get(i));
	          }

        }

        model.addAttribute("havePATH", Boolean.valueOf(havePATH));
        this.lmps = lmps;
        model.addAttribute("names", Names);

        model.addAttribute("queryLmps", lmpsByfileds);

        model.addAttribute("error", error);
        model.addAttribute("total", Long.valueOf(pager.getTotal()));
        return "query/query";
    }
    
    
    @RequestMapping(value = "/querydownload",method = RequestMethod.POST)
    public String sqldown(@RequestParam("record_id") String record_id,@RequestParam(defaultValue="") String fileSource,HttpServletResponse response,HttpSession session){		
    	boolean havefits=FitsUtil.JudeFitsCatalog(tableName,null);
    	if (havefits==true){
				 return "redirect:/query/fitsZipDownload/" + record_id;
	    
	        }else{
	        	 return "redirect:/query/tableDownload";
	        }
	
    	}
    @RequestMapping(value = "/tableDownload",method = RequestMethod.GET)
  public void QueryTableDownload(@RequestParam(defaultValue="") String fileSource,HttpServletResponse response,HttpSession session){
		  try {//fileSource表示从哪里取数据，如果为空则从数据库里面取，如果为zip则到fits/username/tablename/下取zip文件
	      	//如果是file则到fits/username/tablename下取未打包的文件
			User loginUser = (User) session.getAttribute("loginUser");	
	  		//System.out.println("~~~~~~~+ls"+lmps);
	  		String fileName = new String(tableName.getBytes("UTF-8"), "ISO-8859-1");
	  		//System.out.println("tableName有值吗？:"+tableName);
	  		response.reset();
	  		response.setCharacterEncoding("UTF-8");
	  		response.setContentType("application/octet-stream;charset=UTF-8");
	  		if("file".equals(fileSource)){
	      	String realPath = SystemContext.getRealPath()+System.getProperty("file.separator")+loginUser.getUsername()+System.getProperty("file.separator")+TableHandleUtil.getRealTableName(tableName);
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
		      	System.out.println("!!!!fileName!!!"+fileName);
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

    @RequestMapping(value = "/fitsZipDownload")
    public void QueryFitsDownlod(@RequestParam("record_id") String record_id,HttpServletResponse response,HttpSession session) throws MalformedURLException{
    	List<String> error=new ArrayList<String>();
   	 	AjaxObj ajax = new AjaxObj();
   	 	String[] record=record_id.split(",");
   	System.out.println("record的值是：：：：：："+record_id);
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
           for(URL fitsURL:fitsUrls ){
             
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
    
    @RequestMapping(value = "/images", method = RequestMethod.POST)
    public String turnToSDSSByRa_Dec(@PathVariable("Ra") String ra, HttpServletResponse response) {
		
    	
    	
    	return "redirect:http://skyserver.sdss.org/dr10/en/tools/chart/listinfo.aspx";
    	
    	
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

   /* @RequestMapping(value = "/getQueryResult",method = RequestMethod.POST)
    @ResponseBody
    public Datadto getQueryResult1(@RequestParam("tableName") String tableName,HttpServletRequest request){
        List<String> error = new ArrayList<String>();
        String sql = ParameterHandleUtil.gernerateSql(tableName,request);
        System.out.println(sql);
        Datadto datadto = new Datadto();
        datadto.setTableName(tableName);
        Pager<Map<String,Object>> pager = null;
        try {
        	
            pager = this.sqlQueryService.executeSql(sql,error);
            if(null == pager.getDatas() || pager.getDatas().size() <=0 || null==pager)
            {
               // datadto.setMessage("没有数据.....");
                error.add("没有数据.....");
                datadto.setMessage(error);
                datadto.setResult(0);
                return datadto;
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            error.add("SQL语句异常....,或者您删除的数据中含有被参照的数据");
            datadto.setMessage(error);
            datadto.setResult(0);
            return datadto;
        }

        Set<String> names = pager.getDatas().get(0).keySet();
        datadto.setNames(names);
        datadto.setPager(pager);
        return datadto;
    }
    */

}