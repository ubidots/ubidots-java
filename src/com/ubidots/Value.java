package com.ubidots;

import java.util.Map;

public class Value extends ApiObject {

	Value(Map<String, Object> raw, ApiClient api) {
		super(raw, api);
	}
	
	public long getTimestamp() {
		return getAttributeDouble("timestamp").longValue();
	}

	public double getValue() {
		return getAttributeDouble("value").doubleValue();
	}
}
