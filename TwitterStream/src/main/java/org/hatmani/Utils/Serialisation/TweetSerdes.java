package org.hatmani.Utils.Serialisation;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.hatmani.Models.Tweet;

public class TweetSerdes implements Serde<Tweet> {
    @Override
    public Serializer<Tweet> serializer() {
        return  new TweetSerializer();
    }

    @Override
    public Deserializer<Tweet> deserializer() {
        return new TweetDeserializer();
    }
}
