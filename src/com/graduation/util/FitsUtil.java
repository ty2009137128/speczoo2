package com.graduation.util;

import com.graduation.common.SystemContext;
import com.graduation.common.include;
import java.io.File;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

public class FitsUtil
{
  private static final String String = null;
  public static String[] fields = { "MJD", "LMJD", "PLANID", "SPID", "FIBERID", "SPECID", "PLATEID", "FIBER", "OBSID" };
  public static String[] RaDec = { "ra", "dec", "decl" };
  private static File CatalogFile;
  private File basir = new File(SystemContext.getRealPath());
  private String FilePath;
  private static String errorString = "was not exited!";

  public static boolean JudeFitsCatalog(String username_tableNames, List<String> error)
  {
    String[] ut = username_tableNames.toUpperCase().split("_");
    String FileFilePath = SystemContext.getRealPath() + ut[0] + System.getProperty("file.separator") + ut[1] + System.getProperty("file.separator") + "fits";

    File CatalogFile = new File(FileFilePath);
    System.out.println();
    boolean haveFitsCatalog = (CatalogFile.exists()) && (CatalogFile.isDirectory());
    return haveFitsCatalog;
  }

  public static String TestFitsCatalog(String username_tableNames, List<String> error)
  {
    String[] ut = username_tableNames.toUpperCase().split("_");
    String FileFilePath = SystemContext.getRealPath() + ut[0] + System.getProperty("file.separator") + ut[1] + System.getProperty("file.separator") + "fits";
    return FileFilePath;
  }

  public static List<URL> getPathsFromTableById(List<Map<String, Object>> lmps, List<String> recordList, List<String> error)
    throws MalformedURLException
  {
    List filepath = new ArrayList();
    for (String str : recordList)
    {
      String FitsFilePath = (String)((Map)lmps.get(Integer.parseInt(str))).get("PATH");
      System.out.println("````FitsFilePath0000" + FitsFilePath);
      URL fitsFile = new URL(FitsFilePath);
      filepath.add(fitsFile);
    }
    return filepath;
  }

  private static File JudeHaveFitsByField(String FitsFilePath, String lMJD, String pLANID, String sPID, String fIBERID)
  {
    String SPID = addZeroForNum(sPID, 2);
    String FIBERID = addZeroForNum(fIBERID, 3);
    String fitspath = FitsFilePath + System.getProperty("file.separator") + "spec-" + lMJD + "-" + pLANID + "_sp" + SPID + "-" + FIBERID + ".fits";
    File fitsFile = new File(fitspath);
    System.out.println("Fits File的路径为： " + fitsFile.toString());

    return fitsFile;
  }

  public static String[] getFitsNameByValue(List<Map<String, Object>> nameFiled) {
    List FitsName = new ArrayList();

    for (Map map : nameFiled) {
      System.out.println("map===" + map);
      String LMJD = null;
      if ((String)map.get(fields[0]) == null){
        LMJD = (String)map.get(fields[1]);
      }else {
        LMJD = (String)map.get(fields[0]);
      }
      
	      String PLANID = (String)map.get(fields[2]);
	      System.out.println("SDSS这里出问题SPID==" + ((String)map.get(fields[3])));
	
	      String SPID = addZeroForNum((String)map.get(fields[3]), 2);
	
	      System.out.println("FIBERID==" + ((String)map.get(fields[4])));
	
	      String FIBERID = addZeroForNum((String)map.get(fields[4]),3);
	      String Name = "spec-" + LMJD + "-" + PLANID + "_sp" + SPID + "-" + FIBERID + ".fits";
	      FitsName.add(Name);
	      
    }
    String[] FitsNames = (String[])FitsName.toArray(new String[FitsName.size()]);

    return FitsNames;
  }

  public static String addZeroForNum(String str, int strLength)
  {
    int strLen = str.length();

    StringBuffer sb = null;
    while (strLen < strLength) {
      sb = new StringBuffer();
      sb.append("0").append(str);

      str = sb.toString();
      strLen = str.length();
    }
    return str;
  }

  public static void getFitsUrlFromLocal(List<Map<String, Object>> lmps, String tableName)
  {
  }

  public static List<String> getFitsUrlFromLAMOST(List<Map<String, Object>> lmps)
  {
    List lamostPath = new ArrayList();
    for (Map maps : lmps) {
    	 String LMJD = null;
         if ((String)maps.get(fields[0]) == null){
           LMJD = (String)maps.get(fields[1]);
         }else {
           LMJD = (String)maps.get(fields[0]);
         }
      String fitsName = "/spec-" + LMJD + "-" + ((String)maps.get(fields[2])) + "_sp" + addZeroForNum((String)maps.get(fields[3]), 2) + "-" + addZeroForNum((String)maps.get(fields[4]), 3) + ".fits";
      String path = include.LamostPDRPath + ((String)maps.get(fields[2])) + fitsName;
      lamostPath.add(path);
    }
    return lamostPath;
  }

  public static List<String> getFitsUrlFromLAMOST2(List<Map<String, Object>> lmps) {
    List lamost2Path = new ArrayList();
    for (Map maps : lmps) {
      String path = include.LamostPath2 + ((String)maps.get(fields[8])) + "?token=8NV4Dy6Dut";
      lamost2Path.add(path);
    }
    return lamost2Path;
  }

  public static List<String> getFitsUrlFromLAMOST1(List<Map<String, Object>> lmps)
  {
    List lamost1Path = new ArrayList();
    for (Map maps : lmps) {
      String path = include.LamostPath1 + ((String)maps.get(fields[8])) + "?token=8NV4Dy6Dut";
      lamost1Path.add(path);
    }
    return lamost1Path;
  }

  public static List<String> getFitsUrlFromSDSS(List<Map<String, Object>> lmps)
  {
    List sdssPath = new ArrayList();
    for (Map maps : lmps) {
      String fitsName = "/spec-" + ((String)maps.get(fields[6])) + "-" + ((String)maps.get(fields[0])) + "-" + addZeroForNum((String)maps.get(fields[7]), 4) + ".fits";
      String path = include.SdssPath + ((String)maps.get(fields[6])) + fitsName;
      sdssPath.add(path);
    }
    return sdssPath;
  }

  @Test
  public void test()
  {
    String baseString = "C:\\Program Files\\apache-tomcat-6.0.36\\webapps\\graduation\\resources\\fits";
    String realPath = SystemContext.getRealPath();
    String username_tableNames = "ADMIN_BOOk1";
    String[] ut = username_tableNames.split("_");
    File basicdir = new File(baseString);
    String FileFilePath = basicdir + "\\" + ut[0] + "\\" + ut[1] + "\\fits";

    System.out.println(FileFilePath);
    File CatalogFile = new File(FileFilePath);
    if ((CatalogFile.exists()) && (CatalogFile.isDirectory())) {
      System.out.println("yes exited!!");
      if (CatalogFile.listFiles().length > 0) {
        System.out.println("yes fits file exited!!");
      }
      else
        System.out.println("Sorry he is lazy and have no fits file exited!!");
    }
    else
    {
      System.out.println("sorry have not!!");
    }
  }

  public static boolean JudeHaveRaAndDec(Set<String> names)
  {
    String fields = names.toString().toLowerCase();
    boolean haveRaAndDec = (fields.contains(RaDec[0])) && (((fields.contains(RaDec[1])) || (fields.contains(RaDec[2]))));
    return haveRaAndDec;
  }

  public static List changeRaAndDecNames(Set<String> names)
  {
    List NameList = new ArrayList();
    NameList.addAll(names);
    if (JudeHaveRaAndDec(names)) {
      NameList.set(NameList.toString().indexOf(RaDec[0]), "ra");
      NameList.set(NameList.toString().indexOf(RaDec[1]), "dec");
      NameList.set(NameList.toString().indexOf(RaDec[2]), "dec");
    }

    return NameList;
  }
}