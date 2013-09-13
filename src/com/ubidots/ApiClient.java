package com.ubidots;

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

	
	
}
