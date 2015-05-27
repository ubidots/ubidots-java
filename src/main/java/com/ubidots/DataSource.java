package com.ubidots;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

/**
 * Data Source.
 * 
 * @author Ubidots
 */
public class DataSource extends ApiObject {

	DataSource(Map<String, Object> raw, ApiClient api) {
		super(raw, api);
	}
	
	public String getName() {
		return getAttributeString("name");
	}
	
	public void remove() {
		bridge.delete("datasources/" + getAttributeString("id"));
	}
	
	public Variable[] getVariables() {
		String json = bridge.get("datasources/" + getAttributeString("id") + "/variables");

		Gson gson = new Gson();
		List<Map<String, Object>> rawVariables = gson.fromJson(json, List.class);
		
		Variable[] variables = new Variable[rawVariables.size()];
		
		for (int i = 0; i < rawVariables.size(); i++) {
			variables[i] = new Variable(rawVariables.get(i), api);
		}
		
		return variables;
	}

	public Variable createVariable(String name) {
		return createVariable(name, null, null, null, null);
	}

	public Variable createVariable(String name, String unit) {
		return createVariable(name, unit, null, null, null);
	}
	
	public Variable createVariable(String name, String unit,
			String description, Map<String, String> properties,
			String[] tags) {
		if (name == null) {
			throw new NullPointerException();
		}

		// Build data map
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("name", name);

		if (unit != null)
			data.put("unit", unit);

		if (description != null)
			data.put("description", description);
		
		if (properties != null)
			data.put("properties", properties);
		
		if (tags != null)
			data.put("tags", tags);
		
		Gson gson = new Gson();
		String json = bridge.post("datasources/" + getAttributeString("id") + "/variables",
				gson.toJson(data));

		Variable var = new Variable(gson.fromJson(json, Map.class), api);
		
		return var;
	}
	
}
