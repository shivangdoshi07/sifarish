package com.legacy.sifarish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.legacy.sifarish.R;
import com.legacy.sifarish.util.Constants;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {
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
                                Log.d(TAG,response.toString());
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
        Log.d("registerCallback", "after");

////////////////////////////////////////////////////////////////////////////////////////////////
        Button fsloginButton = (Button) findViewById(R.id.fslogin);
        fsloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = FoursquareOAuth.getConnectIntent(getApplicationContext(), Constants.CLIENT_ID);
                startActivityForResult(intent, Constants.REQUEST_CODE_FSQ_CONNECT);
                Log.d("onCreate", String.valueOf(Constants.REQUEST_CODE_FSQ_CONNECT));
            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_FSQ_CONNECT :
                AuthCodeResponse codeResponse = FoursquareOAuth.getAuthCodeFromResult(resultCode, data);
                Log.d("inside OnActivity", "hello world");

                Intent intent = FoursquareOAuth.getTokenExchangeIntent(this, Constants.CLIENT_ID, Constants.CLIENT_SECRET, codeResponse.getCode());
                startActivityForResult(intent, Constants.REQUEST_CODE_FSQ_TOKEN_EXCHANGE);
                break;
            case Constants.REQUEST_CODE_FSQ_TOKEN_EXCHANGE :
                AccessTokenResponse tokenResponse = FoursquareOAuth.getTokenFromResult(resultCode, data);
                Log.d(TAG,tokenResponse.getAccessToken());
                break;
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    public View onCreateView(
//            LayoutInflater inflater,
//            ViewGroup container,
//            Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.splash, container, false);
//
//        loginButton = (LoginButton) view.findViewById(R.id.login_button);
//        loginButton.setReadPermissions("user_friends");
//        // If using in a fragment
//        loginButton.setFragment(this);
//        // Other app specific specialization
//
//        // Callback registration
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//            }
//        });
//        return view;
//    }

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
