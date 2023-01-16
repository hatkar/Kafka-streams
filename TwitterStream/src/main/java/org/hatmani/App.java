package org.hatmani;

import org.apache.kafka.streams.KafkaStreams;
import org.hatmani.Utils.Sentiments.MockSentimentsServices;
import org.hatmani.Utils.Transalation.MockTranslationService;
import org.hatmani.topology.TwitterTopology;

import static org.hatmani.Configurations.StreamConfiguration.createTopologyConfig;

public class App {
    //private static Object KAFKA_BROKER_URL;
   // private String KAFKA_BROKER_URL = " mbp-de-mac:9092";
    public static void main(String[] args) {

       // StreamsBuilder streamsBuilder = new StreamsBuilder();
       //  Topology topology = TwitterTopology.build();

        TwitterTopology twitterTopology = new TwitterTopology(new MockTranslationService(),new MockSentimentsServices() );


        KafkaStreams streams = new KafkaStreams(twitterTopology.createTopology(), createTopologyConfig());
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        System.out.println("Starting Twitter streams");
        streams.start();
    }
}
