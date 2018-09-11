package com.tamlove.jokelib;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Joke {

    private List<String> mJokes = new ArrayList<>();

    public Joke(){
        addJokes();
    }

    private void addJokes(){
        // Type jokes selected from - https://www.prepressure.com/library/fun/fonts-typography-jokes
        mJokes.add("One font meets the other in Rome. He asks: Hey, are you a Roman too? No, says the other, but I am an Italic!");
        mJokes.add("Two fonts walk into a bar, and the barman says, Sorry, we don’t serve your type.");
        mJokes.add("What is a type designers favourite breakfast? ...Kern flakes!");
        mJokes.add("Why did the traveling typeface salesman cross the road? ...Because he forgot his Suitcase!");
        mJokes.add("How long does it take for a type designer to change a light bulb? ...It depends on if you need it to be normal, bold, or heavy.");
        mJokes.add("There is a new revival of Cooper Black rolling on to the market which only contains ordinals. It is called Mini-Cooper.");
    }

    private int getCount(){
        if(mJokes == null) return 0;
        return mJokes.size();
    }

    private int getRandomNumber(int max){
        Random random = new Random();
        return random.nextInt(max);
    }

    public String getJoke() {
        return mJokes.get(getRandomNumber(getCount()));
    }

}
