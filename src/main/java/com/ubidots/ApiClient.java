package com.ubidots;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ApiClient {

	private ServerBridge bridge;

	public ApiClient(String apiKey) {
		bridge = new ServerBridge(apiKey);
	}

	public ApiClient(String apiKey, String baseUrl) {
		bridge = new ServerBridge(apiKey, baseUrl);
	}

	public ApiClient() {}

	ServerBridge getServerBridge() {
		return bridge;
	}

	public ApiClient fromToken(String token) {
		bridge = new ServerBridge(token, true);
		return this;
	}

	public void setBaseUrl(String baseUrl) {
		bridge.setBaseUrl(baseUrl);
	}

	public DataSource[] getDataSources() {
		String json = bridge.get("datasources");

		Gson gson = new Gson();
		List<Map<String, Object>> rawDataSources = gson.fromJson(json, List.class);

		DataSource[] dataSources = new DataSource[rawDataSources.size()];

		for (int i = 0; i < rawDataSources.size(); i++) {
			dataSources[i] = new DataSource(rawDataSources.get(i), this);
		}

		return dataSources;
	}

	public DataSource getDataSource(String id) {
		String json = bridge.get("datasources/" + id);

		Gson gson = new Gson();
		Map<String, Object> rawDataSource = (Map<String, Object>) gson.fromJson(json, Map.class);

		if (rawDataSource.containsKey("detail"))
			return null;
		else
			return new DataSource(rawDataSource, this);
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
		Map<String, Object> rawDataSource = (Map<String, Object>) gson.fromJson(json, Map.class);

		DataSource ds = new DataSource(rawDataSource, this);

		return ds;
	}

	public Variable[] getVariables() {
		String json = bridge.get("variables");

		Gson gson = new Gson();
		List<Map<String, Object>> rawVariables = gson.fromJson(json, List.class);

		Variable[] variables = new Variable[rawVariables.size()];

		for (int i = 0; i < rawVariables.size(); i++) {
			variables[i] = new Variable(rawVariables.get(i), this);
		}

		return variables;
	}

	public Variable getVariable(String id) {
		String json = bridge.get("variables/" + id);

		Gson gson = new Gson();
		Map<String, Object> rawVariable = (Map<String, Object>) gson.fromJson(json, Map.class);

		if (rawVariable.containsKey("detail"))
			return null;
		else
			return new Variable(rawVariable, this);
	}

	// For testing
	void setServerBridge(ServerBridge bridge) {
		this.bridge = bridge;
	}
}
