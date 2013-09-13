package com.ubidots;

import java.util.Map;

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
}
