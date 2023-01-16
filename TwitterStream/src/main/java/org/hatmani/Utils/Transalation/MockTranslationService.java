package org.hatmani.Utils.Transalation;

import org.hatmani.Models.Tweet;

public class MockTranslationService implements TranslationService{
    @Override
    public Tweet Translate(Tweet OriginalTweet,String targetLanguage) {
        Tweet newTweet= new Tweet();
        newTweet=OriginalTweet;
        newTweet.setLang(targetLanguage);

        return OriginalTweet;
    }
}
