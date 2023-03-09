package org.hatmani.Kafka_Streams;

import kafka.tools.ConsoleConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Arrays;
import java.util.Properties;

public class KafkaStreamApp {
    public static void main(String[] args) {
        //properties de kafka
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "mbp-de-mac:9092");
        //groupID
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "K-stream-app");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        //Build Our Topology
        //KStream<String,String> ktextLines=streamsBuilder.stream("testTopic");
        streamsBuilder.<String, String>stream("testTopic")
                .peek((key,value)-> System.out.println("Input sentence=>"+value))
                .flatMapValues((readOnlyKey, value) -> Arrays.asList(value.toLowerCase().split(" ")))
                .groupBy((k, word) -> word)
                //tell kafka streams how to serialize
                .count(Materialized.as("count-store"))
                .toStream()
                .peek((key,value)-> System.out.println("Output: "+ key+" =>"+value))
                .to("wcTopic", Produced.with(Serdes.String(), Serdes.Long()));

        Topology topology = streamsBuilder.build();
        KafkaStreams kafkaStreams = new KafkaStreams(topology,properties);

        kafkaStreams.start();
        //fermer le stream en cas de recu de signa
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }
}
