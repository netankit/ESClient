package com.ecircle.es;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;

/**
 * Demonstrates the search API We use the user index and the profile type which
 * was populated automatically using the App.java
 * 
 * @author ankit
 * 
 */

public class SearchAPI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// on startup
		Node node = nodeBuilder().node();
		Client client = node.client();

		SearchResponse response = client.prepareSearch("user") // Index
																// or
																// Database
				.setTypes("profile") // Type or Table
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH) // Search Type
																// is defined
				.setQuery(QueryBuilders.termQuery("Matthias", "Patrick")) // Term
				// query
				// Query
				.setPostFilter(
						FilterBuilders.rangeFilter("age").from(12).to(18)) // Filter
				.setFrom(0).setSize(60).setExplain(true).execute().actionGet();
		// Prints Response
		System.out.println(response.toString());

		// On close
		node.close();

	}

}
