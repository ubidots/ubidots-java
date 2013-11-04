package com.ubidots;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import java.util.*;

public class ValueTest {

	@Test
	public void testAttributes() {
		ApiClient api = mock(ApiClient.class);
		
		Map<String, Object> raw  = new HashMap<String, Object>();
		raw.put("id", "abcdef");
		raw.put("timestamp", new Integer(1234));
		raw.put("value", new Integer(5678));
		
		Value value = new Value(raw, api);
		
		assertEquals(value.getId(), raw.get("id"));
		assertEquals(value.getValueInt(), ((Integer)raw.get("value")).intValue());
		assertEquals(value.getTimestamp(), ((Integer)raw.get("timestamp")).intValue());
	}

}
