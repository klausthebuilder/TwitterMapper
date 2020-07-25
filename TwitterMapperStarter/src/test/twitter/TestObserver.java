package test.twitter;

import twitter4j.Status;

import java.util.Observable;
import java.util.Observer;

public class TestObserver implements Observer {
    private int nTweets = 0;

    @Override
    public void update(Observable observable, Object arg) {
        Status status = (Status) arg;
        nTweets++;
    }

    public int getNTweets() {
        return nTweets;
    }
}