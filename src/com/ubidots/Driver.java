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
		
		// Part V: api.getVariables()
		for (Variable v : api.getVariables()) {
			System.out.println(v.getName());
		}
		
		System.out.println();
		
		// Part VI: ds.remove()
		api.createDataSource("todelete");
		DataSource ds = null;
		
		for (DataSource tmpDs : api.getDataSources()) {
			if (tmpDs.getName().equals("todelete")) {
				ds = tmpDs;
				break;
			}
		}
		
		ds.remove();
		
		boolean found = false;
		
		for (DataSource tmpDs : api.getDataSources()) {
			if (tmpDs.getName().equals("todelete")) {
				found = true;
				break;
			}
		}
		
		assert ! found;
		
		System.out.println(found ? "found" : "not found");
		System.out.println();
		
		// Part VII: ds.getVariables()
		for (DataSource tmpDs : api.getDataSources()) {
			System.out.println(tmpDs.getName());
			
			for (Variable tmpVar : tmpDs.getVariables()) {
				System.out.println("    " + tmpVar.getName());
			}
		}
		
		System.out.println();
		
		// Part VIII: ds.createVariable()
		String varName = "test#" + (int) (System.currentTimeMillis() / 1000);
		for (DataSource tmpDs : api.getDataSources()) {
			tmpDs.createVariable(varName, "mg");
		}
		
		for (DataSource tmpDs : api.getDataSources()) {
			System.out.println(tmpDs.getName());
			
			for (Variable tmpVar : tmpDs.getVariables()) {
				System.out.println("    " + tmpVar.getName());
			}
		}
	}
}
