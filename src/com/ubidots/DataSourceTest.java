package com.ubidots;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class DataSourceTest {

	@Test
	public void testNameAndID() {		
		Map<String, Object> raw  = new HashMap<String, Object>();
		raw.put("id", "abcdef");
		raw.put("name", "MyDS");
		
		DataSource dataSource = new DataSource(raw,  mock(ApiClient.class));
		
		assertEquals(dataSource.getId(), raw.get("id"));
		assertEquals(dataSource.getName(), raw.get("name"));
	}
	
	@Test
	public void testGetVariablesEmptyList() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.get("datasources/a/variables")).thenReturn("[]");
		
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		
		Map<String, Object> raw  = new HashMap<String, Object>();
		raw.put("id", "a");
		raw.put("name", "MyDS");

		DataSource dataSource = new DataSource(raw, api);
		Variable[] variables = dataSource.getVariables();
		
		verify(bridge).get("datasources/a/variables");
		assertEquals(0, variables.length);	
	}
	
	@Test
	public void testGetVariablesThreeVariables() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.get("datasources/a/variables")).thenReturn("["
				+ "{'id': 'a', 'name': 'A', 'unit': 'unitA', 'icon': 'iconA'},"
				+ "{'id': 'b', 'name': 'B', 'unit': 'unitB', 'icon': 'iconB'},"
				+ "{'id': 'c', 'name': 'C', 'unit': 'unitC', 'icon': 'iconC'}"
				+ "]");
		
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		
		Map<String, Object> raw  = new HashMap<String, Object>();
		raw.put("id", "a");
		raw.put("name", "MyDS");

		DataSource dataSource = new DataSource(raw, api);
		Variable[] variables = dataSource.getVariables();
		
		verify(bridge).get("datasources/a/variables");
		assertEquals(3, variables.length);
		
		// Check IDs
		assertEquals("a", variables[0].getId());
		assertEquals("b", variables[1].getId());
		assertEquals("c", variables[2].getId());

		// Check names
		assertEquals("A", variables[0].getName());
		assertEquals("B", variables[1].getName());
		assertEquals("C", variables[2].getName());
		
		// Check units
		assertEquals("unitA", variables[0].getUnit());
		assertEquals("unitB", variables[1].getUnit());
		assertEquals("unitC", variables[2].getUnit());

		// Check icons
		assertEquals("iconA", variables[0].getIcon());
		assertEquals("iconB", variables[1].getIcon());
		assertEquals("iconC", variables[2].getIcon());
	}

	@Test
	public void testRemoveCallsAPIEndpoint() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.delete("datasources/a")).thenReturn("");
		
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		
		Map<String, Object> raw  = new HashMap<String, Object>();
		raw.put("id", "a");
		raw.put("name", "MyDS");

		DataSource dataSource = new DataSource(raw, api);
		dataSource.remove();
		
		verify(bridge).delete("datasources/a");
	}
}
