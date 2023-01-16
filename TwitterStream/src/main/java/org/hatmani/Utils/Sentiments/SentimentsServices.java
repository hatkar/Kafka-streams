package org.hatmani.Utils.Sentiments;

import org.hatmani.Models.Tweet;
import org.hatmani.Models.Tweetenriched;

import java.util.List;

public interface SentimentsServices {
    List<Tweetenriched> getEntitySentiment(Tweet OriginalTweet);

}
