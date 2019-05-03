package example.com.rxbtc;

import twitter4j.StatusListener;
import twitter4j.TwitterStream;

public class Twitter4jFixer {
    public static void addListener(TwitterStream stream, StatusListener listener) {
        stream.addListener(listener);
    }
}
