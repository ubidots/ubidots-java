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
		assertEquals(variables[0].getName(), "A");
		assertEquals(variables[0].getId(),   "a");
		assertEquals(variables[1].getName(), "B");
		assertEquals(variables[1].getId(),   "b");
		assertEquals(variables[2].getName(), "C");
		assertEquals(variables[2].getId(),   "c");
		assertEquals(variables[3].getName(), "D");
		assertEquals(variables[3].getId(),   "d");
		assertEquals(variables[4].getName(), "E");
		assertEquals(variables[4].getId(),   "e");
	}
	
	@Test
	public void testGetDatasourceMissing() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.get("datasources/a")).thenReturn("{'detail': 'Not found'}");
		
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		DataSource ds = api.getDataSource("a");
		
		assertNull(ds);
	}

	@Test
	public void testGetDatasourceFound() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.get("datasources/a")).thenReturn("{"
				+ "'context': {},"
				+ "'created_at': '2013-10-31T22:07:37.394', "
				+ "'description': null, "
				+ "'id': 'a', "
				+ "'last_activity': null, "
				+ "'name': 'XYZ', "
				+ "'number_of_variables': 0,"
				+ "'owner': 'http://things.ubidots.com/api/v1.5/users/180', "
				+ "'parent': null,"
				+ "'tags': [], "
				+ "'url': 'http://things.ubidots.com/api/v1.5/datasources/a', "
				+ "'variables_url': 'http://things.ubidots.com/api/v1.5/datasources/a/variables' "
				+ "}");

		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		DataSource ds = api.getDataSource("a");

		assertNotNull(ds);
		assertEquals("a", ds.getId());
		assertEquals("XYZ", ds.getName());
	}
}
