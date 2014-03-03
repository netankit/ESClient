package com.ecircle.es;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

public class App {

    private static RandomData rnd = new RandomDataImpl();
    
    
    public static void main(String[] args) throws Exception {
        Node node = nodeBuilder().node();
        Client client = node.client();

        BulkRequestBuilder bulkRequest = client.prepareBulk();

        ArrayList<String> names = new ArrayList<String>();
        names.add("Patrick");
        names.add("Matthias");
        names.add("Kamil");
        names.add("Achim");
        names.add("Kinga");
        names.add("Mladen");
        names.add("Francesco");
        names.add("Stefan");
        names.add("Jörg");
        names.add("Stefan");

        ArrayList<String> ides = new ArrayList<String>();
        ides.add("Eclipse");
        ides.add("IntelliJ");
        ides.add("NetBeans");

        for (int bulkIndex = 0; bulkIndex < 2000; bulkIndex++) {

            System.out.println("bulkIndex : " + bulkIndex);
            
            for (int i = 0; i < 1000; i++) {
                // either use client#prepare, or use Requests# to directly build
                // index/delete requests
                bulkRequest.add(client.prepareIndex("user", "profile", Integer.toString(bulkIndex * 2000 + i))
                        .setSource(
                                jsonBuilder().startObject().field("name", names.get(rnd.nextInt(0, 9)))
                                        .field("joined", new Date()).field("age", 26 + (rnd.nextInt(0, 9)))
                                        .field("fitness", (rnd.nextInt(0, 9)))
                                        .field("income", 1000 * (rnd.nextInt(0, 9)))
                                        .field("performance", (rnd.nextInt(0, 9)))
                                        .field("codelinesperday", (rnd.nextInt(0, 9)))
                                        .field("IDE", ides.get(i % 3)).field("fun", (rnd.nextInt(0, 9)))
                                        .field("looks", (rnd.nextInt(0, 9))).endObject()));
            }
            
            BulkResponse bulkResponse = bulkRequest.execute().actionGet();
            if (bulkResponse.hasFailures()) {
                System.out.println(bulkResponse.buildFailureMessage());
            }
        }

        node.close();
    }
}