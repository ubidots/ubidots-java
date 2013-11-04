package com.ubidots;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import java.util.*;

public class ValueTest {

	@Test
	public void testAttributes() {
		Map<String, Object> raw  = new HashMap<String, Object>();
		raw.put("id", "abcdef");
		raw.put("timestamp", new Integer(1234));
		raw.put("value", new Integer(5678));
		
		Value value = new Value(raw, mock(ApiClient.class));
		
		assertEquals(value.getId(), raw.get("id"));
		assertEquals(value.getValueInt(), ((Integer)raw.get("value")).intValue());
	}

}
