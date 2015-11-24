package com.legacy.sifarish.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.legacy.sifarish.API.IApiMethods;
import com.legacy.sifarish.POJO.RecommendationItem;
import com.legacy.sifarish.R;
import com.legacy.sifarish.adapter.RecommendationAdapter;
import com.legacy.sifarish.util.Constants;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RecommendationActivity extends AppCompatActivity {

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        AwsBackgroundTask task = new AwsBackgroundTask();
        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_questionaire, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent k = new Intent(RecommendationActivity.this,PurchaseActivity.class);
            startActivity(k);
            return true;
        }

        if(id == R.id.action_mock_location){
            Intent k = new Intent(RecommendationActivity.this,MockLocationActivity.class);
            startActivity(k);
            return true;
        }

        if(id == R.id.action_logout){
            getApplicationContext().getSharedPreferences(Constants.PREF_CONST, 0).edit().clear().commit();
            Intent k = new Intent(RecommendationActivity.this,LoginActivity.class);
            k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(k);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class AwsBackgroundTask extends AsyncTask<Void, Void, String> {
        RestAdapter restAdapter;
        ArrayList<RecommendationItem> ri = new ArrayList<>();
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
                ArrayList<RecommendationItem> obj = awsConnect.getRecommendation("1015380193041890",Constants.MOCK_LOCATION);
                ri = obj;
                return obj.size()+"";
            }catch (Exception e){
                Log.d("Recommendation",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String curators) {
            final RecommendationAdapter ra = new RecommendationAdapter(RecommendationActivity.this, ri);
            lv=(ListView) findViewById(R.id.listView);
            lv.setAdapter(ra);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    RecommendationItem clicked = ra.getItem(position);
                    Constants.ri = clicked;
                    Intent k = new Intent(RecommendationActivity.this,ItemDetailActivity.class);
                    startActivity(k);
                }
            });
        }
    }
}