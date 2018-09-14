package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.tamlove.jokeandroidlib.JokeActivity;
import com.tamlove.jokeandroidlib.Utils;


public class MainActivity extends AppCompatActivity {

    private static final String AD_UNIT_TEST_ID = "ca-app-pub-3940256099942544/1033173712";
    private InterstitialAd mInterstitialAd;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progress_bar);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(AD_UNIT_TEST_ID);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void tellJoke(View view) {
        mProgressBar.setVisibility(View.VISIBLE);
        if (mInterstitialAd.isLoaded()) {
            Log.v("MainActivity", "Add loaded");
            mInterstitialAd.show();
        }
        new JokeAsyncTask(new JokeAsyncTask.RequestFinishedListener() {
            @Override
            public void onRequestFinished(String jokeData) {
                if(jokeData != null) {
                    final Intent jokeIntent = new Intent(MainActivity.this, JokeActivity.class);
                    jokeIntent.putExtra(Utils.JOKE_KEY, jokeData);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            startActivity(jokeIntent);
                        }
                    }, 3000);
                } else {
                    Toast.makeText(MainActivity.this, "Error fetching a joke", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }


}
