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

}
