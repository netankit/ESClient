package com.ecircle.es;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

public class apiExamples {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// on startup
		Node node = nodeBuilder().node();
		Client client = node.client();

		// GET API
		GetResponse response = client.prepareGet("twitter", "tweet", "1")
				.execute().actionGet();
		// Retrieving Index name
		String _index = response.getIndex();
		System.out.println("My Index: " + _index);

		String source = response.getSourceAsString();
		System.out.println("My Source: " + source);

		// The delete API allows to delete a typed JSON document from a specific
		// index based on its id. The following example deletes the JSON
		// document from an index called twitter, under a type called tweet,
		// with id valued 1:

		DeleteResponse response_delete = client
				.prepareDelete("twitter", "tweet", "1").execute().actionGet();

		// On close
		node.close();

	}
}
