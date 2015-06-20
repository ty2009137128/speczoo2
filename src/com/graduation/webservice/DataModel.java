package com.graduation.webservice;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.graduation.webservice.databinding.MapAdapter;

@XmlRootElement
public class DataModel {
	private Set<String> names;
	

	private List<Map<String,Object>> datas;
	private List<String> error;
	
	@XmlJavaTypeAdapter(MapAdapter.class)
	public List<Map<String, Object>> getDatas() {
		return datas;
	}
	public void setDatas(List<Map<String, Object>> datas) {
		this.datas = datas;
	}
	public List<String> getError() {
		return error;
	}
	public void setError(List<String> error) {
		this.error = error;
	}
	public Set<String> getNames() {
		return names;
	}
	public void setNames(Set<String> names) {
		this.names = names;
	}
	

}
