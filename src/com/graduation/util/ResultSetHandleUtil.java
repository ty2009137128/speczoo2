package com.graduation.util;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

public class ResultSetHandleUtil {

    public static List ResultSetHandle(ResultSet rs, List<Map<String, Object>> lmps) {

        return null;

    }

    public static List<Map<String, Object>> ResultSetHandle(ResultSet rs) throws SQLException {
        List<Map<String, Object>> lmps = new ArrayList<Map<String, Object>>();
        if (rs == null)
            return null;

        ResultSetMetaData rsd = rs.getMetaData();
        int columnCount = rsd.getColumnCount();

        while (rs.next()) {
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            for (int i = 1; i <= columnCount; i++) {
                String name = rsd.getColumnName(i);
                String value = rs.getString(i);
                map.put(name, value);
            }
            //	System.out.println(map);
            lmps.add(map);
        }
        //System.out.println(lmps.get(0).keySet());
        return lmps;

    }

    public static long getCountByResultSet(ResultSet rs) throws SQLException {
        if(null == rs) return 0L;
        long total = 0L;
        while (rs.next()){
            total = Long.parseLong(rs.getString(1));
        }
        return total;
    }

    /**
     * 将形如
     * {1={id=1, username=admin, password=admin}, 2={id=2, username=zhangsan, password=123}}
     * 转化为集合
     *
     * @param
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List<Map<String, Object>> map2List(Map<String, Object> map) {
        Collection values = map.values();
        Iterator it = values.iterator();
        ArrayList<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
        while (it.hasNext()) {
            Map<String, Object> m = (Map<String, Object>) it.next();
            ls.add(m);
        }
        /*for(Map mm : ls){
			System.out.println(mm);
		}*/
        Collections.sort(ls,new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
            	Object obj1 = o1.get("RECORD_ID");
            	Object obj2 = o2.get("RECORD_ID");
            	
            	if((obj1 instanceof java.lang.Long) && (obj2 instanceof java.lang.Long)){
            		Long l1 = (Long)obj1;
            		Long l2 = (Long)obj2;
            		return (int)(l1-l2);
            	}
            	
            	
                String s1 = (String)o1.get("RECORD_ID");
                String s2 = (String)o2.get("RECORD_ID");
                Long n1 = Long.parseLong(s1);
                Long n2 = Long.parseLong(s2);
                return (int)(n1-n2);
               // return int((Long)o1.get("record_Id") - (Long)o2.get("record_Id"));
            }
        });
        return ls;
    }

    /**
     * 将list中的数据输入到流中，list中放的Map
     *
     * @param
     * @return
     * @throws java.io.IOException
     */
    public static void listMap2OutputStream(List<Map<String, Object>> maps, OutputStream out) throws IOException {
        StringBuilder names = new StringBuilder();
        Set<String> nameSet = maps.get(0).keySet();
        Iterator<String> it = nameSet.iterator();
        while (it.hasNext()) {
            String name = it.next();
            names.append(name + ",");
        }
        String nameStr = names.deleteCharAt(names.length() - 1).toString() + "\r\n";
        //可在此设置编码集
        out.write(nameStr.getBytes());


        for (Map<String, Object> map : maps) {
            Set<Entry<String, Object>> sets = map.entrySet();
            Iterator<Entry<String, Object>> setIt = sets.iterator();
            StringBuilder values = new StringBuilder();
            while (setIt.hasNext()) {
                Entry entry = setIt.next();
                String value = entry.getValue().toString();
                values.append(value + ",");
            }
            String valueStr = values.deleteCharAt(values.length() - 1).toString() + "\r\n";
            //可在此设置编码集
            out.write(valueStr.getBytes());
            values = null;
        }

    }

    /**
     * 从ResultSet中获取表字段名字
     * @return
     */
    public static List<String> getFieldNamesByResultSet(ResultSet rs) throws SQLException {
        List<String> filedNames = new ArrayList<String>();
        ResultSetMetaData metaData =  rs.getMetaData();
        int columNumber = metaData.getColumnCount();
        for(int i=1;i<=columNumber;i++){
            String columName = metaData.getColumnName(i);
            filedNames.add(columName);
        }
        return filedNames;
    }

}
