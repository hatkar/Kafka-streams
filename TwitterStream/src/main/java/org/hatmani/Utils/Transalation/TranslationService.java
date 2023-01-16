package org.hatmani.Utils.Transalation;

import org.hatmani.Models.Tweet;

public interface TranslationService {
    Tweet Translate(Tweet OriginalTweet,String targetLanguage);
}
