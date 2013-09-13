package com.ubidots;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;


/**
 * A Bridge between the Server and API objects.
 * 
 * Responsibilities: Make petitions to he browser with the right headers
 * and arguments.
 * @author Ubidots
 */
@SuppressWarnings("deprecation")
public class ServerBridge {
	
	/* Final static variables */
	public static final String DEFAULT_BASE_URL = "http://app.ubidots.com/api/";
	
	/* Instance variables */
	private String baseUrl;
	private String apiKey;
	private String token;
	private Map<String, String> tokenHeader;
	private Map<String, String> apiKeyHeader;
	
	
	ServerBridge(String apiKey) {
		this(apiKey, DEFAULT_BASE_URL);
	}

	ServerBridge(String apiKey, String baseUrl) {
		this.baseUrl = baseUrl;
		this.apiKey = apiKey;
		token = null;

		apiKeyHeader = new HashMap<String, String>();
		apiKeyHeader.put("X-UBIDOTS-APIKEY", this.apiKey);
		
		initialize();
	}
	
	public void initialize() {
		recieveToken();
	}
	
	private void recieveToken() {
		Gson gson = new Gson();
		token = (String) gson.fromJson(postWithApiKey("auth/token"), Map.class).get("token");
	
		tokenHeader = new HashMap<String, String>();
		tokenHeader.put("X-AUTH-TOKEN", token);
	}
	
	private String postWithApiKey(String path) {
		String response = null; // return variable
		Map<String, String> headers = prepareHeaders(apiKeyHeader);		
		
		try {
			String url = baseUrl + path;
			
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			
			for (String name : headers.keySet()) {
				post.setHeader(name, headers.get(name)); 
			}
			
			HttpResponse resp = client.execute(post);
			resp.getEntity().getContent();
			
			BufferedReader rd = new BufferedReader(
                    new InputStreamReader(resp.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			response = result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return response;
	}
	
	private Map<String, String> getCustomHeaders() {
		Map<String, String> customHeaders = new HashMap<String, String>();
		
		customHeaders.put("content-type", "application/json");
		
		return customHeaders;
	}
	
	private Map<String, String> prepareHeaders(Map<String, String>... args) {
		Map<String, String> headers = new HashMap<String, String>();
		
		headers.putAll(getCustomHeaders());
		
		for (Map<String, String> arg : args) {
			headers.putAll(arg);
		}
		
		return headers;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	String get(String path) {
		String response = null; // return variable
		Map<String, String> headers = prepareHeaders(tokenHeader);		
		
		try {
			String url = baseUrl + path;
			
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			
			for (String name : headers.keySet()) {
				get.setHeader(name, headers.get(name)); 
			}
			
			HttpResponse resp = client.execute(get);
			
			BufferedReader rd = new BufferedReader(
                    new InputStreamReader(resp.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			response = result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return response;
	}

	public String post(String path, String json) {
		String response = null; // return variable
		Map<String, String> headers = prepareHeaders(tokenHeader);		
		
		try {
			String url = baseUrl + path;
			
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			
			for (String name : headers.keySet()) {
				post.setHeader(name, headers.get(name)); 
			}
			
			post.setEntity(new StringEntity(json));
			HttpResponse resp = client.execute(post);
			
			BufferedReader rd = new BufferedReader(
                    new InputStreamReader(resp.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			response = result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return response;
	}
}
