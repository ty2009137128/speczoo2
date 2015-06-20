package com.graduation.webservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;

import com.graduation.common.SystemContext;
import com.graduation.model.SearchParam;
import com.graduation.model.SpectroscopicElement;
import com.graduation.model.User;
import com.graduation.service.IOmlElementService;
import com.graduation.service.ISpectroscopicService;
import com.graduation.service.IUserService;
import com.graduation.util.ParameterHandleUtil;

public class WebServiceImpl implements IWebService{

	private IUserService userService;
	private IOmlElementService omlElementService;
	private ISpectroscopicService spectroscopicService;
	
	
	@Override
	@POST
	@Path("/login")
	@Produces("application/json")
	public boolean login(@QueryParam("username") String username,
			@QueryParam("password") String password) {
		if(username == null || password == null){
			return false;
		}
		
		User user = this.userService.login(username,password);
		if(user != null){
			return true;
		}
		return false;
	}
	
	
	@Override
	@POST
	@Path("/search")
	@Produces({ "application/xml", "application/json" })
	public List<SpectroscopicElement> searchData( SearchParam sp) {
		Map<String,Object> parameters = new HashMap<String,Object>();
		List<String> errorMsg = ParameterHandleUtil.handleParameters(sp,parameters);
		if("0".equals(sp.getOr_and())){
			parameters.put("or_and","0");
		}else{
			parameters.put("or_and","1");
		}
//		return this.spectroscopicService.list(parameters);
		return null;
	}
	
	




	@Override
	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public void upload(Attachment attachment) {
		String path = SystemContext.getRealPath();
		String filename = attachment.getContentDisposition().getParameter("filename");
		
		File dest = new File(path,filename);
		try
		{
			InputStream in = attachment.getDataHandler().getInputStream();
			FileUtils.copyInputStreamToFile(in,dest);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	@GET
	@Path("/exesql")
	public DataModel executreSQL(@QueryParam("sql") String sql) {
		DataModel data = new DataModel();
		List<Map<String,Object>> lmaps = this.spectroscopicService.executeSql(sql,data.getError());
		data.setDatas(lmaps);
		Set<String> names = lmaps.get(0).keySet();
		data.setNames(names);
		return data;
	}


	@Override
	@POST
	@Path("/download")
	@Produces("multipart/form-data")
	public Attachment download(@QueryParam("filename") String filename) {
		Attachment at = null;
		String path = SystemContext.getRealPath();
		File src = new File(path,filename);
		try
		{
			FileInputStream srcfile = new FileInputStream(src);
			ContentDisposition cd = new ContentDisposition("attachment;filename="+filename);
			at = new Attachment("file",srcfile,cd);
			
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return at;
	}


	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public void setOmlElementService(IOmlElementService omlElementService) {
		this.omlElementService = omlElementService;
	}


	public void setSpectroscopicService(ISpectroscopicService spectroscopicService) {
		this.spectroscopicService = spectroscopicService;
	}

	
	//----------------------------------------------	
	/*@Override
	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	@Produces(MediaType.APPLICATION_JSON)
	//public AjaxObj upload(@FormParam("file") File file,@FormParam("tableName") String tableName,@Context HttpServletRequest request) {
	//public AjaxObj upload(@FormParam("file") InputStream file,@FormParam("tableName") String tableName,@Context HttpServletRequest request) {
	//public AjaxObj upload(InputStream file,@FormParam("tableName") String tableName,@Context HttpServletRequest request) {
	//public AjaxObj upload(@QueryParam("file") File file,@QueryParam("tableName") String tableName,@Context HttpServletRequest request){
	//public AjaxObj upload(@Multipart(value="file",type = "application/octet-stream") File file,@Multipart("tableName") String tableName,@Context HttpServletRequest request){
	public AjaxObj upload(@FormParam("filename") File file,@FormParam("tableName") String tableName,@Context HttpServletRequest request){
		AjaxObj obj = new AjaxObj();
		System.out.println(tableName);
		if(request == null){
			return new AjaxObj("error");
		}
		try
		{
			FileInputStream fin = new FileInputStream(file);
			FileUtils.copyInputStreamToFile(fin,new File("D:\\2.txt"));
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		obj.setResult(1);
		obj.setMessage("成功上传....");
		return obj;
	}
*/
	




	//------------------------test-------------------------
/*	@Override
	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public void testupload(Attachment attachment) {
		try
		{
		//	System.out.println(file);
		//	FileUtils.copyFile(file,new File("D:\\3.jpg"));
			System.out.println(attachment.getContentType());
			System.out.println(attachment.getContentDisposition());
			System.out.println(attachment.getContentDisposition().getParameters());
			System.out.println(attachment.getContentDisposition().getType());
			System.out.println(attachment.getContentDisposition().getParameter("filename"));
			System.out.println(attachment.getDataHandler().getName());
			InputStream in = attachment.getDataHandler().getInputStream();
			FileUtils.copyInputStreamToFile(in,new File("D:\\3.jpg"));
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	
	@Override
	@POST
	@Path("/download")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Attachment testdownload() {
		File in =  new File("D:\\sql.txt");
		ContentDisposition cd = new ContentDisposition("attachment;filename=sql.txt");
		Attachment attachment = null;
		try
		{
			attachment = new Attachment("sql",new FileInputStream(in),cd);
			
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return attachment;
	}
*/


}
