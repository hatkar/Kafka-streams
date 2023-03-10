package org.hatmani.Utils.Serialisation;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;
import org.hatmani.Models.Tweet;

import java.nio.charset.StandardCharsets;

public class TweetDeserializer implements Deserializer<Tweet> {
    private Gson gson= new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
    @Override
    public Tweet deserialize(String topic, byte[] data) {
        if (data == null) return null;
        return gson.fromJson(new String(data, StandardCharsets.UTF_8), Tweet.class);
    }
}
