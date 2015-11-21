package com.legacy.sifarish.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.foursquare.android.nativeoauth.FoursquareOAuth;
import com.foursquare.android.nativeoauth.model.AccessTokenResponse;
import com.foursquare.android.nativeoauth.model.AuthCodeResponse;
import com.legacy.sifarish.API.IApiMethods;
import com.legacy.sifarish.POJO.FourSquareResponse;
import com.legacy.sifarish.R;
import com.legacy.sifarish.util.Constants;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

import retrofit.RestAdapter;
import retrofit.client.OkClient;


public class LoginActivity extends AppCompatActivity {
    ArrayList venuesList;
    String token ="";
    String API_URL = "https://api.foursquare.com/v2";
    CallbackManager callbackManager;
    private  final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_about_me,user_birthday,user_events");
        Log.d("registerCallback", "before");
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Set<String> permissionSet = loginResult.getAccessToken().getPermissions();
                String token = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    Log.d(TAG, response.getJSONObject().getString("name"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,events");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("onCancel", "in onCreate");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("onError", "in onCreate");
            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////
        Button fsloginButton = (Button) findViewById(R.id.fslogin);
        fsloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = FoursquareOAuth.getConnectIntent(getApplicationContext(), Constants.CLIENT_ID);
                startActivityForResult(intent, Constants.REQUEST_CODE_FSQ_CONNECT);
            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////
    }


@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Log.d("request Code : ",String.valueOf(requestCode));
        Log.d("result Code : ",String.valueOf(resultCode));
        Log.d("Intent data : ", data.getDataString()+" ///ToString : "+data.toString());
        switch (requestCode) {
            case Constants.REQUEST_CODE_FSQ_CONNECT :
                AuthCodeResponse codeResponse = FoursquareOAuth.getAuthCodeFromResult(resultCode, data);
                Intent intent = FoursquareOAuth.getTokenExchangeIntent(this, Constants.CLIENT_ID, Constants.CLIENT_SECRET, codeResponse.getCode());
                startActivityForResult(intent, Constants.REQUEST_CODE_FSQ_TOKEN_EXCHANGE);
                break;
            case Constants.REQUEST_CODE_FSQ_TOKEN_EXCHANGE :
                AccessTokenResponse tokenResponse = FoursquareOAuth.getTokenFromResult(resultCode, data);
                token = tokenResponse.getAccessToken();
                BackgroundTask task = new BackgroundTask();
                task.execute();
                break;
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private class BackgroundTask extends AsyncTask<Void, Void, String> {
        RestAdapter restAdapter;

        @Override
        protected void onPreExecute() {
            final OkHttpClient okHttpClient = new OkHttpClient();
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient(okHttpClient))
                    .build();
        }

        @Override
        protected String doInBackground(Void... params) {

            IApiMethods methods = restAdapter.create(IApiMethods.class);
            String date = "20151120"; // version greater than 20131017
            FourSquareResponse searchResults = methods.search(token,date);
            Log.d("doInBackground : ",searchResults.meta.code);
            Log.d("Request ID : ",searchResults.meta.requestId);
            try {
                System.out.println("SYSO"+searchResults.toString());
            } catch (Exception e) {
                System.out.print("in catch");
                e.printStackTrace();
            }
            return searchResults.toString();
        }

        @Override
        protected void onPostExecute(String curators) {
            //textView.setText(curators + "\n\n");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}