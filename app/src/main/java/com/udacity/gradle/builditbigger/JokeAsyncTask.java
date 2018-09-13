package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class JokeAsyncTask extends AsyncTask<Void, Void, String> {

    private static final String LOCALHOST = "http://10.0.2.2:8080/_ah/api/";
    private static MyApi apiService = null;
    private RequestFinishedListener mRequestFinishedListener;

    public JokeAsyncTask(RequestFinishedListener requestFinishedListener) {
        mRequestFinishedListener = requestFinishedListener;
    }

    public interface RequestFinishedListener {
        void onRequestFinished(String jokeData);
    }

    @Override
    protected String doInBackground(Void... voids) {
        if(apiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(LOCALHOST)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            apiService = builder.build();
        }

        try {
            return apiService.getAJoke().execute().getJokeData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        mRequestFinishedListener.onRequestFinished(result);
    }
}
