package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.tamlove.jokeandroidlib.JokeActivity;
import com.tamlove.jokeandroidlib.Utils;

public class MainActivityFragment extends Fragment {

    private static final String AD_UNIT_TEST_ID = "ca-app-pub-3940256099942544/1033173712";
    private ProgressBar mProgressBar;
    private Button mJokeButton;
    private InterstitialAd mInterstitialAd;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mProgressBar = root.findViewById(R.id.progress_bar);
        mProgressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(getContext(), R.color.colorPink))),
                android.graphics.PorterDuff.Mode.MULTIPLY);
        mJokeButton = root.findViewById(R.id.tellJoke_button);
        mJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tellJoke();
            }
        });

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(AD_UNIT_TEST_ID);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

    public void tellJoke() {
        mProgressBar.setVisibility(View.VISIBLE);
        if (mInterstitialAd.isLoaded()) {
            Log.v("MainActivity", "Add loaded");
            mInterstitialAd.show();
        }
        new JokeAsyncTask(new JokeAsyncTask.RequestFinishedListener() {
            @Override
            public void onRequestFinished(String jokeData) {
                if(jokeData != null) {
                    final Intent jokeIntent = new Intent(getContext(), JokeActivity.class);
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
                    Toast.makeText(getContext(), "Error fetching a joke", Toast.LENGTH_SHORT).show();
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
