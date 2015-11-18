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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.foursquare.android.nativeoauth.FoursquareOAuth;
import com.foursquare.android.nativeoauth.model.AuthCodeResponse;
import com.legacy.sifarish.R;

public class LoginActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    String CLIENT_ID = "0VTUYS0HMSUK1GDLYIREAKMEYRQLFXF0DR1SKD04X2XK1NKM";
    String CLIENT_SECRET = "IHL4GEL5KOPGB42J5SC1GPOBYHI2AO1CXBUTP0CRVYK2LEQP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);

        setContentView(R.layout.activity_login);

        Intent intent = FoursquareOAuth.getConnectIntent(context, CLIENT_ID);
        startActivityForResult(intent, REQUEST_CODE_FSQ_CONNECT);

//        callbackManager = CallbackManager.Factory.create();
//        LoginButton loginButton = (LoginButton) view.findViewById(R.id.usersettings_fragment_login_button);
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {...});

//        String keyhash = Settings.getApplicationSignature(getApplicationContext());

//        Log.d("key in onCreate :",keyhash);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_FSQ_CONNECT:
                AuthCodeResponse codeResponse = FoursquareOAuth.getAuthCodeFromResult(resultCode, data);
                Log.d("inside OnActivity", "hello world");
                break;
        }
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
