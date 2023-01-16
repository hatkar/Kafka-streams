package org.hatmani.producer;


import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.*;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Stream;

public class KafkaTwitProducer {
    private String KAFKA_BROKER_URL = " mbp-de-mac:9092";
    private String TOPIC_NAME = "tweets";

    private String clientID = "twit_prod_1";
    public static void main(String[] args) {

        new KafkaTwitProducer();

    }
    public KafkaTwitProducer()
    {

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER_URL);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, clientID);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);




        InputStream is = KafkaTwitProducer.class.getClassLoader().getResourceAsStream("test.json");

        try {
            InputStreamReader isr = new InputStreamReader(is);

            BufferedReader br = new BufferedReader(isr);
            Stream<String> lines = br.lines();
           // lines.forEach(System.out::println);
            lines.forEach(line->{
                System.out.println("trying "+line);
                producer.send(new ProducerRecord<String, String>(TOPIC_NAME, line),
                        (metadata, ex) -> {
                            System.out.println("Sending Message " + line);
                            System.out.println("Partition => " + metadata.partition() + " Offset=> " + metadata.offset());
                        });
            });
            producer.flush();
            producer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
