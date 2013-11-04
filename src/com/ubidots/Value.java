package com.ubidots;

import java.util.Map;

public class Value extends ApiObject {

	Value(Map<String, Object> raw, ApiClient api) {
		super(raw, api);
	}
	
	public long getTimestamp() {
		return getAttributeLong("timestamp").longValue();
	}

	public double getValue() {
		Object o = getAttribute("value");
		if (o instanceof Double) {
			return ((Double)o).doubleValue();
		} else if (o instanceof Integer) {
			return ((Integer)o).doubleValue();
		} else {
			throw new RuntimeException("Unexpected value type: " + o.toString());
		}
	}
}
