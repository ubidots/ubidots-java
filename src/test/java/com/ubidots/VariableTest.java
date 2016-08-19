package com.ubidots;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.Test;


public class VariableTest {
	private String valueGenerator(int page_size) {
		StringBuilder valuesGenerated = new StringBuilder("[");
		Random random = new Random();
		long initialTimestamp = 1234567890987L;
		int randomNumber;
		for (int i = 0; i < page_size; i++) {
			randomNumber = random.nextInt();
			valuesGenerated.append(
					String.format("{'id': '%d', 'value': %d, 'timestamp': %d}", i, randomNumber, initialTimestamp)
			);
			initialTimestamp++;
		}

		valuesGenerated.append("]");

		return valuesGenerated.toString();
	}

	@Test
	public void testSaveValueIntCallsAPIEndpoint() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.post(eq("variables/a/values"), anyString())).thenReturn("{}");
		
		// Create an ApiClient w/ mock
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		
		// Create a Variable
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
		
		// Create an ApiClient w/ mock
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		
		// Create a Variable
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

		// Create an ApiClient w/ mock
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);

		// Create a Variable
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
	public void testSaveValuesIntCallsAPIEndpoint() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.post(eq("variables/a/values"), anyString())).thenReturn("{}");

		// Create an ApiClient w/ mock
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);

		// Create a Variable
		Map<String, Object> raw = new HashMap<String, Object>();
		raw.put("id", "a");
		Variable var = new Variable(raw, api);

		int[] values = {1, 2, 3};
		long[] timestamps = {1383602207000l, 1383602208000l, 1383602209000l};
		var.saveValues(values, timestamps);
		
		// Verify
		verify(bridge).post(eq("variables/a/values"), anyString());
	}

	@Test
	public void testSaveValuesIntSendsAPIEndpointJSONList() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.post(eq("variables/a/values"), argThat(new isJSONList()))).thenReturn("{}");

		// Create an ApiClient w/ mock
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);

		// Create a Variable
		Map<String, Object> raw = new HashMap<String, Object>();
		raw.put("id", "a");
		Variable var = new Variable(raw, api);

		int[] values = {1, 2, 3};
		long[] timestamps = {1383602207000l, 1383602208000l, 1383602209000l};
		var.saveValues(values, timestamps);
		
		// Verify
		verify(bridge).post(eq("variables/a/values"), argThat(new isJSONList()));
	}

	@Test
	public void testSaveValuesDoubleCallsAPIEndpoint() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.post(eq("variables/a/values"), anyString())).thenReturn("{}");

		// Create an ApiClient w/ mock
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);

		// Create a Variable
		Map<String, Object> raw = new HashMap<String, Object>();
		raw.put("id", "a");
		Variable var = new Variable(raw, api);

		double[] values = {1.0, 2.0, 3.0};
		long[] timestamps = {1383602207000l, 1383602208000l, 1383602209000l};
		var.saveValues(values, timestamps);
		
		// Verify
		verify(bridge).post(eq("variables/a/values"), anyString());
	}

	@Test
	public void testSaveValuesDoubleSendsAPIEndpointJSONList() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.post(eq("variables/a/values"), argThat(new isJSONList()))).thenReturn("{}");

		// Create an ApiClient w/ mock
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);

		// Create a Variable
		Map<String, Object> raw = new HashMap<String, Object>();
		raw.put("id", "a");
		Variable var = new Variable(raw, api);

		double[] values = {1.0, 2.0, 3.0};
		long[] timestamps = {1383602207000l, 1383602208000l, 1383602209000l};
		var.saveValues(values, timestamps);
		
		// Verify
		verify(bridge).post(eq("variables/a/values"), argThat(new isJSONList()));
	}

	@Test
	public void testSaveValueIntContextCallsAPIEndpoint() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.post(eq("variables/a/values"), anyString())).thenReturn("{}");
		
		// Create an ApiClient w/ mock
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		
		// Create a Variable
		Map<String, Object> context = new HashMap<String, Object>();
		Map<String, Object> raw = new HashMap<String, Object>();
		raw.put("id", "a");
		Variable var = new Variable(raw, api);
		
		context.put("lat", "0.0");
		context.put("lng", "0.0");
		var.saveValue(999, context);
		
		// Verify
		verify(bridge).post(eq("variables/a/values"), anyString());
	}

	@Test
	public void testSaveValueIntContextCallsAPIEndpointJSONDict() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.post(eq("variables/a/values"), argThat(new isJSONDict()))).thenReturn("{}");
		
		// Create an ApiClient w/ mock
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		
		// Create a Variable
		Map<String, Object> context = new HashMap<String, Object>();
		Map<String, Object> raw = new HashMap<String, Object>();
		raw.put("id", "a");
		Variable var = new Variable(raw, api);
		
		context.put("lat", "0.0");
		context.put("lng", "0.0");
		var.saveValue(999, context);
		
		// Verify
		verify(bridge).post(eq("variables/a/values"), argThat(new isJSONDict()));
	}

	@Test
	public void testSaveValueDoubleContextCallsAPIEndpointJSONList() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.post(eq("variables/a/values"), argThat(new isJSONDict()))).thenReturn("{}");
		
		// Create an ApiClient w/ mock
		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		
		// Create a Variable
		Map<String, Object> context = new HashMap<String, Object>();
		Map<String, Object> raw = new HashMap<String, Object>();
		raw.put("id", "a");
		Variable var = new Variable(raw, api);
		
		context.put("lat", "0.0");
		context.put("lng", "0.0");
		var.saveValue(0.0, context);
		
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
		
		Variable variable = new Variable(raw, mock(ApiClient.class));
		
		assertEquals(variable.getId(), raw.get("id"));
		assertEquals(variable.getName(), raw.get("name"));
		assertEquals(variable.getUnit(), raw.get("unit"));
		assertEquals(variable.getIcon(), raw.get("icon"));
	}
	
	@Test
	public void testGetValuesEmptyList() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.get("variables/a/values", null)).thenReturn("[]");

		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);
		
		Map<String, Object> raw  = new HashMap<String, Object>();
		raw.put("id", "a");
		raw.put("name", "My Var");
		raw.put("unit", "hPa");

		Variable variable = new Variable(raw, api);
		Value[] values = variable.getValues();
		
		assertEquals(0, values.length);
	}
	
	@Test
	public void testGetValuesListSizeThree() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.get("variables/a/values", null)).thenReturn("["
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
		
		Variable variable = new Variable(raw, api);
		Value[] values = variable.getValues();
		
		assertEquals(3, values.length);
		verify(bridge).get("variables/a/values", null);
		
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

	@Test
	public void testGetStatistics() {
		ServerBridge bridge = mock(ServerBridge.class);
		when(bridge.get("variables/a/statistics/mean/0/1383602209000")).thenReturn("{'mean': 0.0}");

		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);

		Map<String, Object> raw = new HashMap<>();
		raw.put("id", "a");
		raw.put("name", "My var");
		raw.put("unit", "hPa");

		Variable variable = new Variable(raw, api);
		variable.getMean(0, 1383602209000L);

		verify(bridge).get("variables/a/statistics/mean/0/1383602209000");
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testSaveValuesIntExceptionDifferentArraySizes() {
		ApiClient api = new ApiClient("abc");
		
		Map<String, Object> raw  = new HashMap<String, Object>();
		raw.put("id", "a");
		Variable variable = new Variable(raw, api);
		
		int[] values = {1, 2, 3};
		long[] timestamps = {1383602207000l};
		variable.saveValues(values, timestamps);   // should throw
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testSaveValuesDoubleExceptionDifferentArraySizes() {
		ApiClient api = new ApiClient("abc");

		Map<String, Object> raw = new HashMap<String, Object>();
		raw.put("id", "a");
		Variable variable = new Variable(raw, api);

		double[] values = {1.0, 2.0};
		long[] timestamps = {1383602207000l};
		variable.saveValues(values, timestamps);   // should throw
	}

	public void testGetValuesWithStaticPageSize()  {
		ServerBridge bridge = mock(ServerBridge.class);
		Map<String, String> params = new HashMap<>();
		int page_size;

		params.put("page_size", "90");
		page_size = Integer.parseInt(params.get("page_size"));
		when(bridge.get("variables/a/values", params)).thenReturn(valueGenerator(page_size));

		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);

		Map<String, Object> raw  = new HashMap<String, Object>();
		raw.put("id", "a");
		raw.put("name", "My Var");
		raw.put("unit", "hPa");

		Variable variable = new Variable(raw, api);
		Value[] values = variable.getValues(params);

		assertEquals(values.length, page_size);
		assertEquals(values[values.length - 1], page_size - 1);
	}

	public void testGetValuesWithRandomPageSize()  {
		ServerBridge bridge = mock(ServerBridge.class);
		Map<String, String> params = new HashMap<>();
		Random random = new Random();
		int page_size;

		params.put("page_size", String.valueOf(random.nextInt(100)));
		page_size = Integer.parseInt(params.get("page_size"));
		when(bridge.get("variables/a/values", params)).thenReturn(valueGenerator(page_size));

		ApiClient api = new ApiClient("abc");
		api.setServerBridge(bridge);

		Map<String, Object> raw  = new HashMap<String, Object>();
		raw.put("id", "a");
		raw.put("name", "My Var");
		raw.put("unit", "hPa");

		Variable variable = new Variable(raw, api);
		Value[] values = variable.getValues(params);

		assertEquals(values.length, page_size);
		assertEquals(values[values.length - 1], page_size - 1);
	}
}
