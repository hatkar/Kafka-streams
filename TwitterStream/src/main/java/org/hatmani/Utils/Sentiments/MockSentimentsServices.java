package org.hatmani.Utils.Sentiments;

import org.hatmani.Models.Tweet;
import org.hatmani.Models.Tweetenriched;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.google.common.base.Splitter;

public class MockSentimentsServices implements SentimentsServices{

    @Override
    public List<Tweetenriched> getEntitySentiment(Tweet OriginalTweet) {
        List<Tweetenriched> results = new ArrayList<>();
        Iterable<String> words =Splitter.on(' ').split(OriginalTweet.getText().toLowerCase().replace("#",""));
       for(String entity:words){
           Tweetenriched tweetenriched=
                   new Tweetenriched(OriginalTweet.getCreatedAt(),
                           OriginalTweet.getId(),
                           entity,
                           OriginalTweet.getText(),
                           randomDouble());
           results.add(tweetenriched);
       }
        return results;
    }
    Double randomDouble() {
        return ThreadLocalRandom.current().nextDouble(0, 1);
    }
}
