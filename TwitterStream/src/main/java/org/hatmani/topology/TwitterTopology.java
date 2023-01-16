package org.hatmani.topology;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.hatmani.Models.Tweet;
import org.hatmani.Models.Tweetenriched;
import org.hatmani.Utils.Sentiments.SentimentsServices;
import org.hatmani.Utils.Serialisation.TweetSerdes;
import org.hatmani.Utils.Serialisation.TweetenrichedSerdes;
import org.hatmani.Utils.Transalation.TranslationService;

import java.util.Arrays;
import java.util.List;

public class TwitterTopology {
    private final TranslationService translationService;
private final SentimentsServices sentimentsServices;
    private static final List<String> currencies = Arrays.asList("bitcoin", "ethereum");

    public TwitterTopology(TranslationService translationService, SentimentsServices sentimentsServices) {
        this.translationService = translationService;
        this.sentimentsServices = sentimentsServices;
    }
public Topology createTopology()
{
    // the builder is used to construct the topology
    StreamsBuilder builder = new StreamsBuilder();
    // start streaming tweets using our custom value serdes. Note: regarding
    // the key serdes (Serdes.ByteArray()), if could also use Serdes.Void()
    // if we always expect our keys to be null
    KStream<byte[], Tweet> stream = builder.stream("tweets",
            //on est pas oblige de serialize le key mais le value oui
            Consumed.with(Serdes.ByteArray(), new TweetSerdes()));
    //filter out retweets
    KStream<byte[], Tweet> filtred = stream.filterNot((key, tweet) -> {
        return tweet.getRetweet();
    });
    //Predicates pour la separation de tweets
    Predicate<byte[], Tweet> englishTweetsPredicate = (key, tweet) -> tweet.getLang().equals("en");
    Predicate<byte[], Tweet> nonEnglishTweetsPredicate = (key, tweet) -> !tweet.getLang().equals("en");
    //branch basé sur le language de Tweets
    KStream<byte[], Tweet>[] branches = filtred.branch(englishTweetsPredicate, nonEnglishTweetsPredicate);
    //English twwets
    KStream<byte[],Tweet> englishStream=branches[0];
    englishStream.foreach((key,tweet)->System.out.println("tweets-english"+key+":"+tweet.toString()));//.print(Printed.<byte[],Tweet>);
    //Non English Tweets
    KStream<byte[],Tweet> nonEnglishStream=branches[1];
    //nonEnglishStream.foreach((key,tweet)->System.out.println("tweets-english"+key+":"+tweet.toString()));//.print(Printed.<byte[],Tweet>);
    //pour les non-english tweets ,translate tweet
    KStream<byte[],Tweet> translatedStream=nonEnglishStream.mapValues(
            (tweet)->{return translationService.Translate(tweet,"en");}
            );
    //merge the two streams
    KStream<byte[],Tweet> merged =englishStream.merge(translatedStream);
    //enrich with sentiment and salience scores
    //note: the EntitySentiment class is auto-generated from schema
    //defintition in src/main/avro/entity_sentiment.avsc
    KStream<byte[], Tweetenriched> enriched=
            merged.flatMapValues(
                    (tweet)->{
                        List<Tweetenriched> results=sentimentsServices.getEntitySentiment(tweet);
                  //supprimer tous qui ne contient aucun currency
                    results.removeIf(entity->!currencies.contains(entity.getEntity()));
                        return results;
                    }
            );
    enriched.to("crypto-sentiment", Produced.with(Serdes.ByteArray(),new TweetenrichedSerdes()));


    enriched.print(Printed.<byte[], Tweetenriched>toSysOut().withLabel("final-tweets-stream"));

    return builder.build();

}
  /*  public static Topology build() {
        // the builder is used to construct the topology
        StreamsBuilder builder = new StreamsBuilder();
        // start streaming tweets using our custom value serdes. Note: regarding
        // the key serdes (Serdes.ByteArray()), if could also use Serdes.Void()
        // if we always expect our keys to be null
        KStream<byte[], Tweet> stream = builder.stream("tweets",
                //on est pas oblige de serialize le key mais le value oui
                Consumed.with(Serdes.ByteArray(), new TweetSerdes()));
        //filter out retweets
        KStream<byte[], Tweet> filtred = stream.filterNot((key, tweet) -> {
            return tweet.getRetweet();
        });
        //Predicates pour la separation de tweets
        Predicate<byte[], Tweet> englishTweetsPredicate = (key, tweet) -> tweet.getLang().equals("en");
        Predicate<byte[], Tweet> nonEnglishTweetsPredicate = (key, tweet) -> !tweet.getLang().equals("en");
        //branch basé sur le language de Tweets
        KStream<byte[], Tweet>[] branches = filtred.branch(englishTweetsPredicate, nonEnglishTweetsPredicate);
        //English twwets
        KStream<byte[],Tweet> englishStream=branches[0];
        englishStream.foreach((key,tweet)->System.out.println("tweets-english"+key+":"+tweet.toString()));//.print(Printed.<byte[],Tweet>);
        //Non English Tweets
        KStream<byte[],Tweet> nonEnglishStream=branches[1];
        nonEnglishStream.foreach((key,tweet)->System.out.println("tweets-english"+key+":"+tweet.toString()));//.print(Printed.<byte[],Tweet>);
        //pour les non-english tweets ,translate tweet
        KStream<byte[],Tweet> translatedStream=nonEnglishStream.mapValues((tweet)->return translationService.)
        stream.print(Printed.<byte[], Tweet>toSysOut().withLabel("tweets-stream"));
        return builder.build();
    }
*/

}
