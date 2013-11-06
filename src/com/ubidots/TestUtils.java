package com.ubidots;
import org.mockito.ArgumentMatcher;


class isJSONDict extends ArgumentMatcher<String> {
	public boolean matches(Object string) {
		return ((String) string).trim().startsWith("{")
				&& ((String) string).trim().endsWith("}");
	}
}
