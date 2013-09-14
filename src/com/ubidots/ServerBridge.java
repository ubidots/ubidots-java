package com.ubidots;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
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
	
	private Map<String, String> prepareHeaders(Map<String, String> arg) {
		Map<String, String> headers = new HashMap<String, String>();
		
		headers.putAll(getCustomHeaders());
		headers.putAll(arg);
		
		return headers;
	}

	/**
	 * Perform a GET request on the API with a given path
	 * @param path Path to append to the base URL.
	 * @return Response from the API. The API sends back data encoded in JSON.
	 * In the event of an error, will return null.
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

	/**
	 * Perform a POST request on the API with a given path
	 * @param path Path to append to the base URL.
	 * @param json String of data encoded in JSON to send to the server.
	 * @return Response from the API. The API sends back data encoded in JSON.
	 * In the event of an error, will return null.
	 */
	String post(String path, String json) {
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
	
	/**
	 * Perform a DELETE request on the API with a given path
	 * @param path Path to append to the base URL.
	 * @return Response from the API. The API sends back data encoded in JSON.
	 * In the event of an error, will return null.
	 */
	String delete(String path) {
		String response = null; // return variable
		Map<String, String> headers = prepareHeaders(tokenHeader);		
		
		try {
			String url = baseUrl + path;
			
			HttpClient client = new DefaultHttpClient();
			HttpDelete delete = new HttpDelete(url);
			
			for (String name : headers.keySet()) {
				delete.setHeader(name, headers.get(name)); 
			}
			
			HttpResponse resp = client.execute(delete);
			
			if (resp.getEntity() == null)  {
				response = "";
			} else {
				BufferedReader rd = new BufferedReader(
	                    new InputStreamReader(resp.getEntity().getContent()));
	
				StringBuffer result = new StringBuffer();
				String line = "";
				
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
	
				response = result.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return response;
	}
}
