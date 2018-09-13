package com.tamlove.jokeandroidlib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    private TextView mJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        mJoke = findViewById(R.id.joke_textview);

        String joke = "";
        Intent intent = getIntent();
        if(intent.hasExtra(Utils.JOKE_KEY)) {
            joke = intent.getStringExtra(Utils.JOKE_KEY);
        }
        mJoke.setText(joke);
    }
}
