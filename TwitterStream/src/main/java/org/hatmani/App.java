package org.hatmani.topology;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.util.Properties;

public class TwitTopology {
    //private static Object KAFKA_BROKER_URL;
   // private String KAFKA_BROKER_URL = " mbp-de-mac:9092";
    public static void main(String[] args) {
       // StreamsBuilder streamsBuilder = new StreamsBuilder();
        Topology topology = CryptoTopology.build();
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "dev");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "mbp-de-mac:9092");
        KafkaStreams streams = new KafkaStreams(topology, config);
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        System.out.println("Starting Twitter streams");
        streams.start();
    }
}
