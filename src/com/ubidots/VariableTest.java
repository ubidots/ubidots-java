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
		
		var.saveValue(999);
		
		// Verify
		verify(bridge).post(eq("variables/a/values"), anyString());
	}

	@Test
	public void testSaveValueIntSendsAPIEndpontJSONDict() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.post(eq("variables/a/values"), argThat(new isJSONDict()))).thenReturn("{}");
		
		// Create an ApiClient instance with mock server bridge
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		
		// Create a Variable instance with our patched ApiClient
		Map<String, Object> raw = new HashMap<String, Object>();
		raw.put("id", "a");
		Variable var = new Variable(raw, api);
		
		var.saveValue(999);
		
		// Verify
		verify(bridge).post(eq("variables/a/values"), argThat(new isJSONDict()));
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

		var.saveValue(0.0);

		// Verify
		verify(bridge).post(eq("variables/a/values"), anyString());
	}
	
	@Test
	public void testSaveValueDoubleSendsAPIEndpointJSONDict() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.post(eq("variables/a/values"), argThat(new isJSONDict()))).thenReturn("{}");

		// Create an ApiClient w/ mock
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);

		// Create a Variable
		Map<String, Object> raw = new HashMap<String, Object>();
		raw.put("id", "a");
		Variable var = new Variable(raw, api);

		var.saveValue(0.0);

		// Verify
		verify(bridge).post(eq("variables/a/values"), argThat(new isJSONDict()));
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
	
	@Test
	public void testGetValuesListSizeThree() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.get("variables/a/values")).thenReturn("["
				+ "{'id': 'xa', 'value': 5, 'timestamp': 1383602207000},"
				+ "{'id': 'yb', 'value': 2, 'timestamp': 1383602208000},"
				+ "{'id': 'zc', 'value': 7, 'timestamp': 1383602209000}"
				+ "]");

		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		
		Map<String, Object> raw  = new HashMap<String, Object>();
		raw.put("id", "a");
		raw.put("name", "My Var");
		raw.put("unit", "hPa");
		raw.put("icon", "foobar");
		
		Variable variable = new Variable(raw, api);
		Value[] values = variable.getValues();
		
		assertEquals(3, values.length);
		verify(bridge).get("variables/a/values");
		
		// Check ids
		assertEquals("xa", values[0].getId());
		assertEquals("yb", values[1].getId());
		assertEquals("zc", values[2].getId());
		
		// Check values
		assertTrue(Math.abs(values[0].getValue() - 5.0) < .1);
		assertTrue(Math.abs(values[1].getValue() - 2.0) < .1);
		assertTrue(Math.abs(values[2].getValue() - 7.0) < .1);

		// Check timestamps
		assertEquals(1383602207000l, values[0].getTimestamp());
		assertEquals(1383602208000l, values[1].getTimestamp());
		assertEquals(1383602209000l, values[2].getTimestamp());
	}
}
