package com.ubidots;

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
		
		return new DataSource(rawDataSource, this);
	}
}
