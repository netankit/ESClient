
package com.ecircle.es;

import static org.elasticsearch.node.NodeBuilder.*;
import static org.elasticsearch.common.xcontent.XContentFactory.*;

import java.io.IOException;
import java.util.Date;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

public class ESClientIndex {

	public static void main(String[] args) throws ElasticsearchException, IOException{
		// on startup
		Node node = nodeBuilder().node();
		Client client = node.client();
		
		//Index Document
		// Indexes a JSON document into an index called twitter, under a type called tweet, with id valued 1
		IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
		        .setSource(jsonBuilder()
		                    .startObject()
		                        .field("user", "kimchy")
		                        .field("postDate", new Date())
		                        .field("message", "trying out Elasticsearch")
		                    .endObject()
		                  )
		        .execute()
		        .actionGet();
		
//		Indexing without providing the ID
//		String json = "{" +
//		        "\"user\":\"kimchy\"," +
//		        "\"postDate\":\"2013-01-30\"," +
//		        "\"message\":\"trying out Elasticsearch\"," +
//		    "}";
//
//		IndexResponse response_noid = client.prepareIndex("twitter", "tweet")
//		        .setSource(json)
//		        .execute()
//		        .actionGet();
		
		// Index name
		String _index = response.getIndex();
		System.out.println(_index);
		// Type name
		String _type = response.getType();
		System.out.println(_type);

		// Document ID (generated or not)
		String _id = response.getId();
		System.out.println(_id);

		// Version (if it's the first time you index this document, you will get: 1)
		long _version = response.getVersion();
		System.out.println(_version);
		
		
		

		// on shutdown
		node.close();
		
	}
	
}
