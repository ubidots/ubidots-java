package com.ubidots;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class ApiClientTest {

	@Test
	public void testGetVariablesEmptyList() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.get("variables")).thenReturn("[]");
				
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		Variable[] variables = api.getVariables();

		assertEquals(variables.length, 0);
	}

	@Test
	public void testGetVariablesSingleVar() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.get("variables")).thenReturn(
				"[{'name': 'A', 'id': 'a', 'icon': 'temperature'}]"
		);
			
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		Variable[] variables = api.getVariables();
		
		assertEquals(variables.length, 1);
	}
	
	@Test
	public void testGetVariablesFiveVars() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.get("variables")).thenReturn("[" + 
				"{'name': 'A', 'id': 'a', 'icon': 'temperature'}," +
				"{'name': 'B', 'id': 'b', 'icon': 'temperature'}," +
				"{'name': 'C', 'id': 'c', 'icon': 'temperature'}," +
				"{'name': 'D', 'id': 'd', 'icon': 'temperature'}," +
				"{'name': 'E', 'id': 'e', 'icon': 'heart'}" +
				"]"
		);
		
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		Variable[] variables = api.getVariables();
		
		assertEquals(variables.length, 5);
	}
}
