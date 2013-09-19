package com.ubidots;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class Variable extends ApiObject {

	Variable(Map<String, Object> raw, ApiClient api) {
		super(raw, api);
	}
	
	public String getName() {
		return getAttributeString("name");
	}
	
	public void remove() {
		bridge.delete("variables/" + getAttributeString("id"));
	}
	
	public Value[] getValues() {
		String json = bridge.get("variables/" + getAttributeString("id") + "/values");
		
		Gson gson = new Gson();
		List<Map<String, Object>> rawValues = gson.fromJson(json, List.class);
		
		Value[] values = new Value[rawValues.size()];
		
		for (int i = 0; i < rawValues.size(); i++) {
			values[i] = new Value(rawValues.get(i), api);
		}
		
		return values;
	}
	
	public void saveValue(int value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", new Integer(value));
		map.put("timestamp", new Long(getTimestamp()));
		
		Gson gson = new Gson();
		String json = gson.toJson(map);
		
		bridge.post("variables/" + getAttributeString("id") + "/values", json);
	}
	
	public void saveValue(double value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", new Double(value));
		map.put("timestamp", new Long(getTimestamp()));
		
		Gson gson = new Gson();
		String json = gson.toJson(map);
		
		bridge.post("variables/" + getAttributeString("id") + "/values", json);

	}
	
	private long getTimestamp() {
		return System.currentTimeMillis();
	}
}
