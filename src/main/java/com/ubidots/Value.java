package com.ubidots;

import java.util.Map;

public class Value extends ApiObject {
	public static class StatisticsFigures {
		public static final String MEAN = "mean";
		public static final String VARIANCE = "variance";
		public static final String MIN = "min";
		public static final String MAX = "max";
		public static final String COUNT = "count";
		public static final String SUM = "sum";
	}

	Value(Map<String, Object> raw, ApiClient api) {
		super(raw, api);
	}

	public long getTimestamp() {
		return getAttributeDouble("timestamp").longValue();
	}

	public double getValue() {
		return getAttributeDouble("value").doubleValue();
	}

	public Map<String, Object> getContext() {
		return (Map<String, Object>) getAttribute("context");
	}
}
