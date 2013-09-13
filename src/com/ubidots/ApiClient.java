package com.ubidots;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;


/**
 * API Client.
 * 
 * @author Ubidots
 */
public class ApiClient {
	private ServerBridge bridge;
	
	public ApiClient(String apiKey) {
		bridge = new ServerBridge(apiKey);
	}

	ServerBridge getServerBridge() {
		return bridge;
	}
	
	public DataSource[] getDataSources() {
		String json = bridge.get("datasources");
		
		Gson gson = new Gson();
		List<Map<String, String>> rawDataSources = gson.fromJson(json, List.class);
		
		DataSource[] dataSources = new DataSource[rawDataSources.size()];
		
		for (int i = 0; i < rawDataSources.size(); i++) {
			dataSources[i] = new DataSource(rawDataSources.get(i), this);
		}

		return dataSources;
	}
	
	public DataSource getDataSource(String id) {
		String json = bridge.get("datasources/" + id);
		
		Gson gson = new Gson();
		Map<String, String> rawDataSource = (Map<String, String>) gson.fromJson(json, Map.class);
		
		DataSource ds = new DataSource(rawDataSource, this);
		
		return ds;
	}
	
	public DataSource createDataSource(String name) {
		return createDataSource(name, null, null);
	}
	
	public DataSource createDataSource(String name, Map<String, String> context, List<String> tags) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("name", name);
		
		if (context != null) 
			data.put("context", context);
		
		if (tags != null)
			data.put("tags", tags);
		
		Gson gson = new Gson();
		String json = bridge.post("datasources/", gson.toJson(data));
		Map<String, String> rawDataSource = (Map<String, String>) gson.fromJson(json, Map.class);
		
		DataSource ds = new DataSource(rawDataSource, this);

		return ds;
	}
	
	public Variable[] getVariables() {
		String json = bridge.get("variables");
		
		Gson gson = new Gson();
		List<Map<String, String>> rawVariables = gson.fromJson(json, List.class);
		
		Variable[] variables = new Variable[rawVariables.size()];
		
		for (int i = 0; i < rawVariables.size(); i++) {
			variables[i] = new Variable(rawVariables.get(i), this);
		}
		
		return variables;
	}
}
