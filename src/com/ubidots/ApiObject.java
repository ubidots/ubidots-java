package com.ubidots;

import java.util.HashMap;
import java.util.Map;

class ApiObject {

	private Map<String, String> raw;
	private ApiClient api;
	private ServerBridge bridge;
	
	ApiObject(Map<String, String> raw, ApiClient api) {
		this.raw = new HashMap<String, String>(raw);
		this.api = api;
		bridge = api.getServerBridge();
	}
	
	String getAttribute(String name) {
		return raw.get(name);
	}
}
