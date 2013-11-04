package com.ubidots;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.Test;

public class VariableTest {

	@Test
	public void testSaveValueIntCallsAPIEndpoint() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.post(eq("variables/a/values"), anyString())).thenReturn("{}");
		
		// Create an ApiClient instance with mock server bridge
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		
		// Create a Variable instance with our patched ApiClient
		Map<String, Object> raw = new HashMap<String, Object>();
		raw.put("id", "a");
		Variable var = new Variable(raw, api);
		
		// Call method to save a value
		var.saveValue(999);
		
		// Verify API endpoint was requested in ServerBridge
		verify(bridge).post(eq("variables/a/values"), anyString());
	}

	@Test
	public void testSaveValueDoubleCallsAPIEndpoint() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.post(eq("variables/a/values"), anyString())).thenReturn("{}");

		// Create an ApiClient instance with mock server bridge
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);

		// Create a Variable instance with our patched ApiClient
		Map<String, Object> raw = new HashMap<String, Object>();
		raw.put("id", "a");
		Variable var = new Variable(raw, api);

		// Call method to save a value
		var.saveValue(0.0);

		// Verify API endpoint was requested in ServerBridge
		verify(bridge).post(eq("variables/a/values"), anyString());
	}
	

	@Test
	public void testRemoveCallsAPIEndpoint() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.delete("variables/a")).thenReturn("");
		
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		
		Map<String, Object> raw  = new HashMap<String, Object>();
		raw.put("id", "a");
		raw.put("name", "My Var");
		raw.put("unit", "hPa");
		raw.put("icon", "foobar");

		Variable variable = new Variable(raw, api);
		variable.remove();
		
		verify(bridge).delete("variables/a");
	}
	
	@Test
	public void testAttributes() {
		Map<String, Object> raw  = new HashMap<String, Object>();
		raw.put("id", "a");
		raw.put("name", "My Var");
		raw.put("unit", "hPa");
		raw.put("icon", "foobar");
		
		Variable variable = new Variable(raw, mock(ApiClient.class));
		
		assertEquals(variable.getId(), raw.get("id"));
		assertEquals(variable.getName(), raw.get("name"));
		assertEquals(variable.getUnit(), raw.get("unit"));
		assertEquals(variable.getIcon(), raw.get("icon"));
	}
	
	@Test
	public void testGetValuesEmptyList() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.get("variables/a/values")).thenReturn("[]");

		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		
		Map<String, Object> raw  = new HashMap<String, Object>();
		raw.put("id", "a");
		raw.put("name", "My Var");
		raw.put("unit", "hPa");
		raw.put("icon", "foobar");

		Variable variable = new Variable(raw, api);
		Value[] values = variable.getValues();
		
		assertEquals(0, values.length);
	}
}
