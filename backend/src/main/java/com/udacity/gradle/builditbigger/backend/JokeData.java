package com.udacity.gradle.builditbigger.backend;

import com.tamlove.jokelib.Joke;

/** The object model for the data we are sending through endpoints */
public class JokeData {

    private String mJokeData;

    public String getJokeData() {
        return mJokeData;
    }

    public void setJokeData() {
        Joke joke = new Joke();
        mJokeData = joke.getJoke();
    }
}