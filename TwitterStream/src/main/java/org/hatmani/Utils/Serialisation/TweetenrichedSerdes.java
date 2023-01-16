package org.hatmani.Utils.Serialisation;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.hatmani.Models.Tweet;
import org.hatmani.Models.Tweetenriched;

public class TweetenrichedSerdes implements Serde<Tweetenriched> {
    @Override
    public Serializer<Tweetenriched> serializer() {
        return  new TweetenrichedSerializer();
    }

    @Override
    public Deserializer<Tweetenriched> deserializer() {
        return new TweetenrichedDeserializer();
    }
}
