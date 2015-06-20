package com.graduation.webservice;

import java.io.File;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import com.graduation.model.SearchParam;
import com.graduation.model.SpectroscopicElement;

@Path("/remote")
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON,MediaType.APPLICATION_OCTET_STREAM,MediaType.MULTIPART_FORM_DATA})

public interface IWebService {
	
	@POST
	@Path("/login")
	public boolean login(@QueryParam("username") String username,@QueryParam("password") String password);
	
	
	
	@POST
	@Path("/search")
	public List<SpectroscopicElement> searchData( SearchParam sp);
	
	@GET
	@Path("/exesql")
	public DataModel executreSQL(@QueryParam("sql") String sql);
	
	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public void upload(Attachment attachment);
	
	@POST
	@Path("/download")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Attachment download(@QueryParam("filename") String filename);
	
//	@POST
//	@Path("/upload")
//	@Consumes("multipart/form-data")
//	@Produces(MediaType.APPLICATION_JSON)
	//public AjaxObj upload(@FormParam("file") File file,@FormParam("tableName") String tableName,@Context HttpServletRequest request);
//	public AjaxObj upload(@FormParam("file") InputStream file,@FormParam("tableName") String tableName,@Context HttpServletRequest request);
//	public AjaxObj upload(InputStream file,@FormParam("tableName") String tableName,@Context HttpServletRequest request);
//	public AjaxObj upload(@QueryParam("file") File file,@QueryParam("tableName") String tableName,@Context HttpServletRequest request);
//	public AjaxObj upload(@Multipart(value="file",type = "application/octet-stream") File file,@Multipart("tableName") String tableName,@Context HttpServletRequest request);
//	public AjaxObj upload(@FormParam("file") File file,@FormParam("tableName") String tableName,@Context HttpServletRequest request);
	
/*	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public void testupload(Attachment attachment);
	
	@POST
	@Path("/download")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Attachment testdownload();
	*/
	
}
