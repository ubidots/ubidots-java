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
}
