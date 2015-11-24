package com.legacy.sifarish.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.legacy.sifarish.API.IApiMethods;
import com.legacy.sifarish.POJO.PurchaseItem;
import com.legacy.sifarish.R;
import com.legacy.sifarish.adapter.PurchaseAdapter;
import com.legacy.sifarish.util.Constants;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class PurchaseActivity extends AppCompatActivity {

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        AwsBackgroundTask task = new AwsBackgroundTask();
        task.execute();
    }

    private class AwsBackgroundTask extends AsyncTask<Void, Void, String> {
        RestAdapter restAdapter;
        ArrayList<PurchaseItem> ri = new ArrayList<>();
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
            String idName = sharedPreferences.getString("idName", "null");
            try{
                ArrayList<PurchaseItem> obj = awsConnect.getHistory("1015380193041890");
                ri = obj;
                return obj.size()+"";
            }catch (Exception e){
                Log.d("Recommendation", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String curators) {
            final PurchaseAdapter ra = new PurchaseAdapter(PurchaseActivity.this, ri);
            lv=(ListView) findViewById(R.id.listView2);
            lv.setAdapter(ra);
        }
    }
}
