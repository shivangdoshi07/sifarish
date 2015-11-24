package com.legacy.sifarish.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.legacy.sifarish.API.IApiMethods;
import com.legacy.sifarish.POJO.BuyItem;
import com.legacy.sifarish.POJO.RecommendationItem;
import com.legacy.sifarish.R;
import com.legacy.sifarish.util.Constants;
import com.legacy.sifarish.util.ImageDownloaderTask;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        if(Constants.ri == null){
            Intent k = new Intent(ItemDetailActivity.this,RecommendationActivity.class);
            startActivity(k);
        }

        TextView itemName = (TextView) findViewById(R.id.itemName);
        TextView itemDescription = (TextView) findViewById(R.id.itemDescription);
        TextView itemStoreName = (TextView) findViewById(R.id.itemStoreName);
        TextView itemPrice = (TextView) findViewById(R.id.itemPrice);
        TextView itemDepartment = (TextView) findViewById(R.id.itemDepartment);
        ImageView itemImage = (ImageView) findViewById(R.id.itemImage);
        Button buy = (Button) findViewById(R.id.button);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AwsBackgroundTask task = new AwsBackgroundTask();
                task.execute();

                Snackbar.make(v, "Thank you for shopping with us! You payment has been done by NFC Pay", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        itemName.setText(Constants.ri.itemName);
        itemDescription.setText(Constants.ri.itemShortDescription);
        itemStoreName.setText(Constants.ri.storeId);
        //itemDepartment.setText(Constants.ri.itemDepartment);
        itemPrice.setText("$ "+Constants.ri.itemRestrictedSalePrice);

        if(Constants.ri.itemMediumImage != null) {
            new ImageDownloaderTask(itemImage).execute(Constants.ri.itemMediumImage);
        }
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
                BuyItem bi = awsConnect.buyItem(new BuyItem(Long.parseLong(idName),Long.parseLong(Constants.ri.itemId)));
            }catch (Exception e){
                Log.d("Recommendation", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String curators) {

        }
    }
}
