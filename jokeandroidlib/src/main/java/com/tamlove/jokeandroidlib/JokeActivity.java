package com.tamlove.jokeandroidlib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    private TextView mJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mJoke = findViewById(R.id.joke_textview);

        Intent intent = getIntent();
        String currentJoke;
        if(intent.hasExtra(Utils.JOKE_KEY)) {
            currentJoke = intent.getStringExtra(Utils.JOKE_KEY);
        } else {
            currentJoke = getResources().getString(R.string.joke_error);
        }
        mJoke.setText(currentJoke);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
