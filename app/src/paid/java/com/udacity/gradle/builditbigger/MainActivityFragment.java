package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tamlove.jokeandroidlib.JokeActivity;
import com.tamlove.jokeandroidlib.Utils;

public class MainActivityFragment extends Fragment {

    private ProgressBar mProgressBar;
    private Button mJokeButton;

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

        return root;
    }

    public void tellJoke() {
        mProgressBar.setVisibility(View.VISIBLE);
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
    }
}
