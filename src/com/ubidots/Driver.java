package com.ubidots;

import java.util.LinkedList;
import java.util.List;

public class Driver {

	public static void main(String args[]) {
		// Part I: instantiate ApiClient
		String apiKey = "74ccf3b7957fe38e3382c9fd107d70870edbb462";
		ApiClient api = new ApiClient(apiKey);
		System.out.println();

		// Part II: api.getDataSources();
		for (DataSource ds : api.getDataSources()) {
			System.out.println(ds.getName());
		}
		System.out.println();

		// Part III: api.getDatasource(id)
		System.out.println(api.getDataSource("522fa5e6f91b282c73573a75").getName());
		System.out.println();
		
		// Part IV: api.createDataSource(data)
		List<String> tags = new LinkedList<String>();;
		tags.add("aabb");
		tags.add("ccdd");
		
		System.out.println(api.createDataSource("abc").getName());
		System.out.println(api.createDataSource("abc", null, tags).getName());
		
		System.out.println();
	}
}
