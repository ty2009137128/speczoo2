package com.graduation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class ZipUtil {
	
	/**
	 * 將某個目錄压缩
	 * @param dirFile 要压缩的文件或是目录
	 * @param zipOutFile 将压缩包的输出文件xx.zip
	 */
    public static void ZipCompress(File dirFile,File zipOutFile){
        ZipArchiveOutputStream zip = null;
        try
        {
//            String dir = "F:\\C++产生文件\\最小生成树";//要压缩文件的目录
//            String zipOut = "F:/OK.zip";//文件输出的zip文件
            zip = new ZipArchiveOutputStream(new FileOutputStream(zipOutFile));
            int len = dirFile.getParent().length()+1;//这个位置 与tar文件的压缩不同，这里要+1，
            zipFile(dirFile,zip,len);
        } catch (Exception e)
        {
            e.printStackTrace();
        }finally{
            IOUtils.closeQuietly(zip);
        }
    }
    public static void ZipCompress(Set<File> files,File zipOutFile){
    	ZipArchiveOutputStream zip = null;
    	FileInputStream fileIn = null;
    	try
		{
			zip = new ZipArchiveOutputStream(new FileOutputStream(zipOutFile));
			for(File file : files){
				ZipArchiveEntry entry = new ZipArchiveEntry(file.getName());
				entry.setSize(file.length());
				fileIn = new FileInputStream(file);
				zip.putArchiveEntry(entry);
				IOUtils.copy(fileIn,zip);
			}
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(fileIn);
			try
			{
				zip.closeArchiveEntry();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			IOUtils.closeQuietly(zip);
		}
    }
    /**
     * 遍历文件目录，将里面的文件逐一压缩
     * @param file
     * @param zips
     * @param len
     */
    private static void zipFile(File file, ZipArchiveOutputStream zips,int len) {
        if(file.isDirectory()) {
            File[] fs = file.listFiles();
            for(File f:fs) {
                zipFile(f,zips,len);
            }
        } else {
            FileInputStream fis = null;
            try {
              //  System.out.println(file.getParent().substring(len)+File.separator+file.getName());
              //  ZipArchiveEntry entry = new ZipArchiveEntry(file.getParent().substring(len)+File.separator+file.getName());
            	ZipArchiveEntry entry = new ZipArchiveEntry(file.getName());
                entry.setSize(file.length());
                fis = new FileInputStream(file);
                zips.putArchiveEntry(entry);
                IOUtils.copy(fis, zips);
            //    zips.closeArchiveEntry();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(fis!=null) fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    zips.closeArchiveEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 将文件解压
     * @param zipFile 要解压的文件
     * @param unZipFile 将文件解压到的目录
     */
    
    public static void unZipCompress(File zipFile,File unZipFile){
        ZipArchiveInputStream zipIn    = null;
        try
        {
            zipIn = new ZipArchiveInputStream(new FileInputStream(zipFile));
            ZipArchiveEntry entry = null;
            while(null != (entry = zipIn.getNextZipEntry())){
                if (!entry.isDirectory())
                {
                    String name = entry.getName();
                    File path = new File(unZipFile + File.separator + name);
                    if (!path.exists())
                        path.getParentFile().mkdirs();
                    FileOutputStream fos = null;
                    
                    try
                    {
                        fos = new FileOutputStream(path);
                        IOUtils.copy(zipIn,fos);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }finally{
                        if(fos != null) fos.close();
                    }
                }
                
                
                
            }
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }finally{
            IOUtils.closeQuietly(zipIn);
        }
        
    }

    
    public static Set<File> getZipFiles(File baseDir,String[] username_tableNames){
    	//List<File> files = new ArrayList<File>();
    	Set<File> files = new HashSet<File>();
    	//String baseDir = SystemContext.getRealPath();
    	for(final String username_tableName : username_tableNames){
    		String[] ut = username_tableName.split("_");
    		String zipFilePath = baseDir+System.getProperty("file.separator")+ut[0]+System.getProperty("file.separator")+ut[1];
    		System.out.println("ZipFilePath:"+zipFilePath);
    		File zipFile = new File(zipFilePath);
    		if(zipFile.exists() && zipFile.isDirectory()){
	    		File[] zf = zipFile.listFiles(new FilenameFilter(){
					@Override
					public boolean accept(File dir, String name) {
						if(name.startsWith(username_tableName)){
							return true;
						}
						return false;
					}
	    			
	    		});
	    		//files.addAll(Arrays.asList(zf));
	    		for(File f : zf){
	    			files.add(f);
	    		}
    		}
    	}
    	return files;
    }
    
  /* @Test
   public void testZipFiles(){
	   String baseString = "E:\\javaSDK\\apache-tomcat-6.0.36\\webapps\\graduation\\resources\\fits";
	   File baseDir = new File(baseString);
	   Set<File> files = ZipUtil.getZipFiles(baseDir,new String[]{"admin_test08","admin_test05","admin_test05","admin_test02"});
	   System.out.println(files);
	   for(File file : files){
		   System.out.println(file.getAbsolutePath());
	   }
	   
	   ZipUtil.ZipCompress(files,new File("D:\\ok.zip"));
			   
   }*/

}
