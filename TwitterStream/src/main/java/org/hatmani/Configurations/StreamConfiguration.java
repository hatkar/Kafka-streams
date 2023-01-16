package org.hatmani.Configurations;

import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

public  class StreamConfiguration {
    public static Properties createTopologyConfig()
    {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "dev");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "mbp-de-mac:9092");
   return config;
    }
}
