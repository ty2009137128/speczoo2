package com.graduation.controller;

import com.graduation.common.Pager;
import com.graduation.common.SystemContext;
import com.graduation.model.AjaxObj;
import com.graduation.model.MydbElement;
import com.graduation.model.User;
import com.graduation.service.IMydbService;
import com.graduation.util.JsonUtil;
import com.graduation.util.ParameterHandleUtil;
import com.graduation.util.ResultSetHandleUtil;
import com.graduation.util.TableHandleUtil;
import com.graduation.util.ZipUtil;
import com.graduation.web.SessionContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/mydb")
public class MyDbController {

    @Inject
    private IMydbService mydbService;

    @RequestMapping(value = "/mydbUpload", method = RequestMethod.GET)
    public String mydb() {
        return "mydb/mydb";
    }

    @RequestMapping(value = "/mydbUpload", method = RequestMethod.POST)
    @ResponseBody
    public String mydbUpload(MultipartFile file, String tableName, HttpServletRequest request, Model model) {
        //error中记录了出错数据的行数和信息
        ArrayList<String> error = new ArrayList<String>();
        AjaxObj ajax = new AjaxObj();
        if (tableName == null || "".equals(tableName.trim())) {
            ajax.setResult(0);
            ajax.setMessage("The Table'name is null.....");
            return JsonUtil.getInstance().obj2json(ajax);
        }
        if (ParameterHandleUtil.isNumber(tableName)) {
            ajax.setResult(0);
            ajax.setMessage("TableName must contain char....");
            return JsonUtil.getInstance().obj2json(ajax);
        }
        HttpSession session = SessionContext.getSession(request.getParameter("sessionId"));
        User loginUser = (User) session.getAttribute("loginUser");
        try {
            tableName = loginUser.getUsername()+"_"+tableName.trim();
            tableName = tableName.toUpperCase();
            long rowNum = this.mydbService.insertBach(file.getInputStream(), tableName, error);
            if(rowNum==0){
                ajax.setResult(0);
                ajax.setMessage("update number is empty......");
                ajax.setObj(error);
                return JsonUtil.getInstance().obj2json(ajax);
            }
            MydbElement mydb = new MydbElement();
            mydb.setRowNum(rowNum);
            mydb.setTableName(tableName);
            mydb.setUid(loginUser.getId());
            mydb.setUsername(loginUser.getUsername());
            this.mydbService.insertMydb(mydb);

        } catch (IOException e) {
            e.printStackTrace();
            return JsonUtil.getInstance().obj2json(ajax);
        }
        ParameterHandleUtil.saveUploadFile(file,loginUser.getUsername(),tableName,error);
        ajax.setResult(1);
        ajax.setObj(error);
 
        return JsonUtil.getInstance().obj2json(ajax);

    }

    @RequestMapping(value = "/listMydbInfo")
    @ResponseBody
    public List<MydbElement> getMydbElementList(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        //把自己上传的表和共享的表查询出来
        return this.mydbService.list(loginUser.getId());
    }
    
    //将表设为私有的还是共有的
    @RequestMapping(value="/updateTableStatus/{tableName}")
    @ResponseBody
    public AjaxObj updateTableStatus(@PathVariable("tableName") String tableName,HttpSession session){
    	AjaxObj ajax = new AjaxObj();
    	if(tableName == null || "".equals(tableName)){
    		ajax.setResult(0);
    		ajax.setMessage("没有该表..");
    		return ajax;
    	}
    	User loginUser = (User) session.getAttribute("loginUser");
    	MydbElement mydbElement = this.mydbService.getMydbElementByUidAndTableName(loginUser.getId(),tableName);
    	if(mydbElement == null){
    		ajax.setResult(0);
    		ajax.setMessage("该表不是您上传...无法修改");
    		return ajax;
    	}
    	this.mydbService.updateTableStatus(mydbElement);
    	ajax.setResult(1);
    	ajax.setMessage("修改成功..");
    	return ajax;
    }
    
    @RequestMapping("/deleteTable")
    public String deleteByTableName(@RequestParam("id") Integer id,HttpSession session,Model model) {
    	User loginUser = (User) session.getAttribute("loginUser");
        //通过指定的表id来删除，第一参数为uid，表示不是删除用户所建的所有表
        int result = this.mydbService.deleteTable(loginUser.getId(), id);
        List<String> error = new ArrayList<String>();
        if (result == 0) {
            //未做任何改变
        	error.add("您无法删除不属于您上传的表.....");
        	model.addAttribute("error",error);
        }
        return "mydb/mydb";
    }

    @RequestMapping("/clearAllTable")
    public String clearAll(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        this.mydbService.deleteTable(loginUser.getId(), null);
        return "mydb/mydb";
        
    }
    
    @RequestMapping("/clearSelectedTables/{tableNames}")
    public String clearSelectedTables(@PathVariable("tableNames") String tableNames,HttpServletResponse response,HttpSession session,Model model) {
    	//AjaxObj ajax = new AjaxObj();
    	User loginUser = (User) session.getAttribute("loginUser");
    	
    //	String realPath = SystemContext.getRealPath();
    	String[] _tableName = tableNames.split("&");
    	int result=this.mydbService.deleteSelectedTable(loginUser.getId(), _tableName);
        List<String> error = new ArrayList<String>();
        if (result == 0) {
            //未做任何改变
        	error.add("您无法删除不属于您上传的表.....");
        	model.addAttribute("error",error);
        }
        return  "mydb/mydb";
        
    }
    @RequestMapping("/showTable/{tableName}")
    public String showTable(@PathVariable("tableName") String tableName, Model model) {

        if (null == tableName || "".equals(tableName.trim()))
            return "mydb/mydb";
        //验证表格在mydb_user中是否存在，用户只能查看自己建的表
        MydbElement mydb = this.mydbService.getMydbElementByTableName(tableName);
        if (mydb == null){
            model.addAttribute("error", "您没有创建该表.....");
            return "mydb/mydb";
        }
        Pager<Map<String, Object>> pager = this.mydbService.findTableData(tableName);
        Set<String> names = pager.getDatas().get(0).keySet();
        model.addAttribute("names", names);
        model.addAttribute("pager", pager);
        model.addAttribute("tableName", tableName);
        return "mydb/mydb";
    }

    @RequestMapping("/download/{tableName}")
    public String downLoad(@PathVariable("tableName") String tableName, HttpServletResponse response, Model model) {
        if (null == tableName || "".equals(tableName.trim())) {
            model.addAttribute("error", "表格名不能为空空....");
            return "mydb/mydb";
        }
        //验证表格在mydb_user中是否存在，用户只能查看自己建的表
        MydbElement mydb = this.mydbService.getMydbElementByTableName(tableName);
        if (mydb == null) {
            model.addAttribute("error", "您没有创建该表.....");
            return "mydb/mydb";
        }

        return "redirect:/mydb/data/download/" + tableName;
    }

    @RequestMapping("/data/download/{tableName}")
    public void down(@PathVariable("tableName") String tableName ,@RequestParam(defaultValue="") String fileSource,HttpServletResponse response,HttpSession session) {
        try {//fileSource表示从哪里取数据，如果为空则从数据库里面取，如果为zip则到fits/username/tablename/下取zip文件
        	//如果是file则到fits/username/tablename下取未打包的文件
        	User loginUser = (User) session.getAttribute("loginUser");
        	List<Map<String, Object>> ls = new ArrayList<Map<String,Object>>();
        	if(tableName !=  null && !"".equals(tableName) && !"data".equals(tableName)){
        		ls = this.mydbService.listTableData(tableName);

        	}
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            String fileName = new String(tableName.getBytes("UTF-8"), "ISO-8859-1");
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
            	fileName = fileName + ".txt";
            	response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            	ResultSetHandleUtil.listMap2OutputStream(ls, response.getOutputStream());
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    
    @RequestMapping(value="/zipDownLoad/{tableNames}")
    @ResponseBody
    public AjaxObj zipDownLoad(@PathVariable("tableNames") String tableNames,HttpServletResponse response,HttpSession session,Model model){
    	AjaxObj ajax = new AjaxObj();
    	User loginUser = (User) session.getAttribute("loginUser");
    	
    	String realPath = SystemContext.getRealPath();
    	System.out.println("realPath:"+realPath);
    	String[] _tableName = tableNames.split("&");

    	Set<File> toZipFiles = ZipUtil.getZipFiles(new File(realPath),_tableName);
    	
    	for(File str:toZipFiles){
    		System.out.println("每个mydb下载路径是:"+str.getPath());
    	}
    	model.addAttribute("downloadPath", toZipFiles.toString());
    	//下一步，把路径显示在网页上，这样就可以看出，是哪里的问题
    	System.out.println("dasdsafwaerfwew3q"+toZipFiles.toString());
		if(toZipFiles.size()==0){
			ajax.setResult(0);
			ajax.setMessage("File is missing.....");
			//ajax.setResult(1);
			//ajax.setMessage(toZipFiles.toString());
			return ajax;
		}
	
		ajax.setResult(2);
		ajax.setMessage(toZipFiles.toString());
		//ajax.setResult(2);
		//ajax.setMessage("有该文件");
		File zipTemp = new File(realPath+"/temp");
		if(!zipTemp.exists()){
			zipTemp.mkdirs();
		}
		File zipTempFile = new File(zipTemp,"data.zip");
		
		ZipUtil.ZipCompress(toZipFiles,zipTempFile);
    	return ajax;
    }

    @RequestMapping("/test")
    @ResponseBody
    public List<Map<String, Object>> test(@RequestParam("tableName") String tableName, Model model) {
        //return this.mydbService.findTableData(tableName);
        return this.mydbService.listTableData(tableName);
    }
}
