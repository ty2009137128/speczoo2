<%@ page import = "com.vogoal.util.JspFileDownload"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.io.*,java.util.*, java.sql.* ,org.apache.tools.zip.*"%>
<%@page import= "com.graduation.common.SystemContext"%>
<%@page import="java.net.* "%>
<% 
    String[] record_id = request.getParameterValues("record_id");
	String[] record_id1s=record_id[0].split(",");
    String[] paths=request.getParameterValues("PATH");
    String[] fitsfiles=new String[record_id1s.length];
    System.out.println("FDSFSD "+fitsfiles.length);
    String basePath = SystemContext.getRealPath();
	for(int i=0;i<fitsfiles.length; i++){
		
		String fitsfile=(String)paths[i];
		
		
		String filePath = basePath+fitsfile.substring(fitsfile.lastIndexOf("spec"));
		File f= new File(filePath);
		System.out.println("~~~~~~~~~~"+fitsfile);
		if(!f.exists()){
		 	 boolean flag = saveUrlAs(fitsfile, filePath);
		}
		fitsfiles[i]=filePath;

	}
	

      ZipOutputStream zos=null;
      ServletOutputStream sos=null;
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
          for(int i=0;i<fitsfiles.length;i++){
            System.out.print("@#@!#@!#@!"+fitsfiles[i].substring(fitsfiles[i].lastIndexOf("spec")));
   
        	  String FileName = fitsfiles[i].substring(fitsfiles[i].lastIndexOf("spec")-1);		//下载打包时的名称，以下是为把文件打包到第一目录而做
        		
              File f = new File(fitsfiles[i]);

              ze = new ZipEntry(FileName);
              ze.setSize(f.length());
              ze.setTime(f.lastModified());
              zos.putNextEntry(ze);

              InputStream is = new BufferedInputStream( new FileInputStream(f));
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
               sos.flush();
               sos.close();
               
             }catch(Exception ex)
             {
                System.out.println("Error download:"+ex.toString());
             }
            }
            }

%>
<%!
	public boolean saveUrlAs(String photoUrl, String fileName) {
	//此方法只能用户HTTP协议
	  try {
	    URL url = new URL(photoUrl);
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    DataInputStream in = new DataInputStream(connection.getInputStream());
	   // File fileFolder = new File(fileName);
		 // if(!fileFolder.exists()){
		//	fileFolder.mkdir();
		//  }
	    DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));
	    byte[] buffer = new byte[4096];
	    int count = 0;
	    while ((count = in.read(buffer)) > 0) {
	      out.write(buffer, 0, count);
	    }
	    out.close();
	    in.close();
	    return true;
	  }
	  catch (Exception e) {
	    return false;
	  }
	}
	
	public String getDocumentAt(String urlString) {
	//此方法兼容HTTP和FTP协议
	  StringBuffer document = new StringBuffer();
	  try {
	    URL url = new URL(urlString);
	    URLConnection conn = url.openConnection();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.
	        getInputStream()));
	    String line = null;
	    while ( (line = reader.readLine()) != null) {
	      document.append(line + "\n");
	    }
	    reader.close();
	  }
	  catch (MalformedURLException e) {
	    System.out.println("Unable to connect to URL: " + urlString);
	  }
	  catch (IOException e) {
	    System.out.println("IOException when connecting to URL: " + urlString);
	  }
	  return document.toString();
	}

%>