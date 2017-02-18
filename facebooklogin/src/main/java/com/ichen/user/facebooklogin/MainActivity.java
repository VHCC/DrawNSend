package com.ichen.user.facebooklogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.*;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new InnerFragment())
                    .commit();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class InnerFragment extends Fragment {

        private CallbackManager callbackManager;

        public InnerFragment() {
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            Log.d("AAA", "onCreate");
            super.onCreate(savedInstanceState);
            callbackManager = CallbackManager.Factory.create();

            init();
        }

        private void init() {
            Log.i("AAA", "AccessToken= " + AccessToken.getCurrentAccessToken());
            Log.i("AAA", "Profile= " + Profile.getCurrentProfile());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            LoginButton loginButton = (LoginButton) rootView.findViewById(R.id.login_button);
            loginButton.setReadPermissions(Arrays.asList(
                    "public_profile", "email", "user_birthday", "user_friends"));
            // If using in a fragment
            loginButton.setFragment(this);
            // Other app specific specialization

            // Callback registration
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    RequestData();
                    Log.d("AAA", "onSuccess= " + loginResult.toString());
                    // App code
                }

                @Override
                public void onCancel() {
                    Log.d("AAA", "onCancel");
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    Log.d("AAA", "onError= " + exception.toString());
                    // App code
                }

            });
            return rootView;
        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            Log.i("AAA", "requestCode= " + requestCode + ", resultCode= " + resultCode);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        private void RequestData() {

            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    final JSONObject json = response.getJSONObject();

                    try {
                        if(json != null){

                            Log.d("AAA", json.getString("name"));
                            Log.d("AAA", json.getString("email"));
                            Log.d("AAA", json.getString("id"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,email,picture");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

}
