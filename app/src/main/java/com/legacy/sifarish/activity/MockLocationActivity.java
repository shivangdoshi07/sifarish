package com.legacy.sifarish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.legacy.sifarish.R;
import com.legacy.sifarish.util.Constants;

public class MockLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_location);
        ListView lv = (ListView) findViewById(R.id.locationList);
        ListAdapter la = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Constants.stores);
        lv.setAdapter(la);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Constants.MOCK_LOCATION = position + 1 +"";
                Snackbar.make(view, "Your location is set to:" + Constants.stores[position], Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(MockLocationActivity.this, RecommendationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
