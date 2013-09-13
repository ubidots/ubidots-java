package com.ubidots;

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

		// Part III:
		System.out.println(api.getDataSource("522fa5e6f91b282c73573a75").getName());
		System.out.println();
	}
}
