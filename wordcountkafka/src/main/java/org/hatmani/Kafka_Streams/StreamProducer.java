package org.hatmani.Kafka_Streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class StreamProducer {
    private String KAFKA_BROKER_URL = " mbp-de-mac:9092";
    private String TOPIC_NAME = "testTopic";

    private String clientID = "client_prod_1";

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        new StreamProducer();
    }

    public StreamProducer() throws InterruptedException, ExecutionException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER_URL);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, clientID);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        do {
            System.out.println("======================================");
            System.out.println("Enter your sentences: ");
            Scanner scanner = new Scanner(System.in);
            String word = scanner.nextLine();
            System.out.println("Your word is " + word);

            Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>(TOPIC_NAME, "K" + word, word),
                    (metadata, ex) -> {
                        System.out.println("Sending Message " + word);
                        System.out.println("Partition => " + metadata.partition() + " Offset=> " + metadata.offset());
                    });

          future.get();
            while (!future.isDone()) {

            }



        } while (true);


    }
}
