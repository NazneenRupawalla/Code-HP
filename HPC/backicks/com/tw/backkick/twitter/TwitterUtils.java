package com.tw.backkick.twitter;

import android.content.SharedPreferences;
import oauth.signpost.OAuth;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.io.File;

public class TwitterUtils {

    public static boolean isAuthenticated(SharedPreferences prefs) {

        String token = prefs.getString(OAuth.OAUTH_TOKEN, "883899103-K68WH64E4UgHciFhBLsk7Cx1UlhOpCueVxoDcllg");
        String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "vX6TvjzHqZqC7175rziKmg1U0kO3SLpRCGhqzBDells");

        AccessToken a = new AccessToken(token, secret);
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
        twitter.setOAuthAccessToken(a);

        try {
            twitter.getAccountSettings();
            return true;
        } catch (TwitterException e) {
            return false;
        }
    }

    public static void sendTweet(SharedPreferences prefs, String msg, String image) throws Exception {
        String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
        String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

        AccessToken a = new AccessToken(token, secret);
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
        twitter.setOAuthAccessToken(a);
        StatusUpdate statusUpdate = new StatusUpdate(msg);
        statusUpdate.media(new File(image));
        twitter.updateStatus(statusUpdate);
    }
}
