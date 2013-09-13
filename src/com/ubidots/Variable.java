package com.ubidots;

import java.util.Map;

public class Variable extends ApiObject {

	Variable(Map<String, String> raw, ApiClient api) {
		super(raw, api);
	}
	
	public String getName() {
		return getAttribute("name");
	}
}
