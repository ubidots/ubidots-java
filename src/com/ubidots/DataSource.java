package com.ubidots;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

/**
 * Data Source.
 * 
 * @author Ubidots
 */
public class DataSource extends ApiObject {

	DataSource(Map<String, String> raw, ApiClient api) {
		super(raw, api);
	}
	
	public String getName() {
		return getAttribute("name");
	}
	
	public void remove() {
		bridge.delete("datasources/" + getAttribute("id"));
	}
	
	public Variable[] getVariables() {
		String json = bridge.get("datasources/" + getAttribute("id") + "/variables");

		Gson gson = new Gson();
		List<Map<String, String>> rawVariables = gson.fromJson(json, List.class);
		
		Variable[] variables = new Variable[rawVariables.size()];
		
		for (int i = 0; i < rawVariables.size(); i++) {
			variables[i] = new Variable(rawVariables.get(i), api);
		}
		
		return variables;

	}
}
