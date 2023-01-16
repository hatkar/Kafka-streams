package org.hatmani.Utils.Serialisation;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serializer;
import org.hatmani.Models.Tweet;
import org.hatmani.Models.Tweetenriched;

import java.nio.charset.StandardCharsets;

public class TweetenrichedSerializer implements Serializer<Tweetenriched> {
private Gson gson = new Gson();

    @Override
    public byte[] serialize(String topic, Tweetenriched data) {
        if(data==null) return null;
        return gson.toJson(data).getBytes(StandardCharsets.UTF_8);
    }
}
