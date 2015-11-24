package com.legacy.sifarish.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.legacy.sifarish.API.IApiMethods;
import com.legacy.sifarish.POJO.RecommendationItem;
import com.legacy.sifarish.R;
import com.legacy.sifarish.util.Constants;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RecommendationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        AwsBackgroundTask task = new AwsBackgroundTask();
        task.execute();
    }


    private class AwsBackgroundTask extends AsyncTask<Void, Void, String> {
        RestAdapter restAdapter;

        @Override
        protected void onPreExecute() {
            //retrofit settings

            final OkHttpClient okHttpClient = new OkHttpClient();
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(Constants.AWS_URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient(okHttpClient))
                    .build();

        }

        @Override
        protected String doInBackground(Void... params) {
            final IApiMethods awsConnect = restAdapter.create(IApiMethods.class);
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONST, MODE_PRIVATE);
            String idName = sharedPreferences.getString("idName","null");

            ArrayList<RecommendationItem> obj = awsConnect.getRecommendation(idName);
            return obj.size()+"";
        }

        @Override
        protected void onPostExecute(String curators) {

        }
    }
}
