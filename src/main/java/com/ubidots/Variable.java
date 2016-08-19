package com.ubidots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.ubidots.Value.StatisticsFigures;

public class Variable extends ApiObject {
	Variable(Map<String, Object> raw, ApiClient api) {
		super(raw, api);
	}
	
	public String getName() {
		return getAttributeString("name");
	}
	
	public String getUnit() {
		return getAttributeString("unit");
	}
	
	public String getIcon() {
		return getAttributeString("icon");
	}
	
	public void remove() {
		bridge.delete("variables/" + getAttributeString("id"));
	}

	public Value[] getValues() {
		return getValues(null);
	}

	public Value[] getValues(Map<String, String> customParameters) {
		String json = bridge.get("variables/" + getAttributeString("id") + "/values", customParameters);
		
		Gson gson = new Gson();
		List<Map<String, Object>> rawValues = gson.fromJson(json, List.class);
		
		Value[] values = new Value[rawValues.size()];

		for (int i = 0; i < rawValues.size(); i++) {
			values[i] = new Value(rawValues.get(i), api);
		}

		return values;
	}
	
	public void saveValue(int value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", new Integer(value));
		map.put("timestamp", new Long(getTimestamp()));
		
		Gson gson = new Gson();
		String json = gson.toJson(map);
		
		bridge.post("variables/" + getAttributeString("id") + "/values", json);
	}
	
	public void saveValue(double value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", new Double(value));
		map.put("timestamp", new Long(getTimestamp()));
		
		Gson gson = new Gson();
		String json = gson.toJson(map);
		
		bridge.post("variables/" + getAttributeString("id") + "/values", json);
	}

	public void saveValue(int value, Map<String, Object> context) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("value", new Integer(value));
		map.put("context", context);
		map.put("timestamp", new Long(getTimestamp()));

		Gson gson = new Gson();
		String json = gson.toJson(map);

		bridge.post("variables/" + getAttributeString("id") + "/values", json);
	}

	public void saveValue(double value, Map<String, Object> context) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("value", new Double(value));
		map.put("context", context);
		map.put("timestamp", new Long(getTimestamp()));

		Gson gson = new Gson();
		String json = gson.toJson(map);

		bridge.post("variables/" + getAttributeString("id") + "/values", json);
	}
	
	public void saveValues(int values[], long timestamps[]) {
		double valuesDouble[] = new double[values.length];

		for (int i = 0; i < values.length; i++) {
			valuesDouble[i] = (double) values[i];
		}

		saveValues(valuesDouble, timestamps);
	}

	public void saveValues(double values[], long timestamps[]) {
		if (values == null || timestamps == null) {
			throw new NullPointerException();
		} else if (values.length != timestamps.length) {
			throw new IndexOutOfBoundsException("values[] and timestamps[] "
				+ "must have same length");
		}

		// Create a list of maps to be sent as JSON: [{...}, ...]
		List<Map> list = new ArrayList<Map>();
		for (int i = 0; i < values.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", values[i]);
			map.put("timestamp", timestamps[i]);
			list.add(map);
		}

		// Convert to JSON and POST to server
		Gson gson = new Gson();
		String json = gson.toJson(list);
		bridge.post("variables/" + getId() + "/values", json);
	}

	public double getMean() {
		return getMean(0L, getTimestamp());
	}

	public double getMean(long startTime, long endTime) {
		return getStatistics(StatisticsFigures.MEAN , startTime, endTime);
	}

	public double getVariance() {
		return getVariance(0L, getTimestamp());
	}

	public double getVariance(long startTime, long endTime) {
		return getStatistics(StatisticsFigures.VARIANCE, startTime, endTime);
	}

	public double getMin() {
		return getMin(0L, getTimestamp());
	}

	public double getMin(long startTime, long endTime) {
		return getStatistics(StatisticsFigures.MIN, startTime, endTime);
	}

	public double getMax() {
		return getMax(0L, getTimestamp());
	}

	public double getMax(long startTime, long endTime) {
		return getStatistics(StatisticsFigures.MAX, startTime, endTime);
	}

	public double getCount() {
		return getCount(0L, getTimestamp());
	}

	public double getCount(long startTime, long endTime) {
		return getStatistics(StatisticsFigures.COUNT, startTime, endTime);
	}

	public double getSum() {
		return getSum(0L, getTimestamp());
	}

	public double getSum(long startTime, long endTime) {
		return getStatistics(StatisticsFigures.SUM, startTime, endTime);
	}

	private double getStatistics(String figure, long startTime, long endTime) {
		String json = bridge.get("variables/" + getId() + "/statistics/" +
				 figure + "/" + startTime + "/" + endTime);

		Gson gson = new Gson();
		Map<String, Object> rawValue = gson.fromJson(json, Map.class);

		return (double) rawValue.get(figure);
	}

	private long getTimestamp() {
		return System.currentTimeMillis();
	}
}
