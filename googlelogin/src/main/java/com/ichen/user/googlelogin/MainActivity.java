package com.ichen.user.googlelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new InnerFragment()).commit();
        }
    }

    public static class InnerFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {
        private final String TAG = this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());

        private final int RC_SIGN_IN = 0;

        private AppCompatTextView name;
        private Button signOutButton;
        private SignInButton signInButton;

        private GoogleApiClient mGoogleApiClient;


        public InnerFragment() {
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            Log.d(TAG, "onCreate:");
            super.onCreate(savedInstanceState);
            initGoogle();
        }

        private void initGoogle() {
            Log.d(TAG, "initGoogle:");

            // Configure sign-in to request the user's ID, email address, and basic
            // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            // Build a GoogleApiClient with access to the Google Sign-In API and the
            // options specified by gso.
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }



        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Log.d(TAG, "onCreateView:");
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            // Set the dimensions of the sign-in button.
            initIDs(rootView);
            initView();
            return rootView;
        }

        private void initIDs(View rootView) {
            name = (AppCompatTextView) rootView.findViewById(R.id.name);
            signInButton = (SignInButton) rootView.findViewById(R.id.sign_in_button);
            signOutButton = (Button) rootView.findViewById(R.id.signOutButton);
        }

        private void initView() {

            signInButton.setSize(SignInButton.SIZE_STANDARD);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.sign_in_button:
                            signIn();
                            break;
                        // ...
                    }
                }
            });
            signOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.signOutButton:
                            signOut();
                            break;
                        // ...
                    }
                }
            });

        }

        private void signIn() {
            Log.d(TAG, "signIn:");
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }


        private void signOut() {
            Log.d(TAG, "signOut:");
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            signInButton.setVisibility(View.VISIBLE);
                            signOutButton.setVisibility(View.GONE);
                            name.setText("");
                            revokeAccess();
                        }
                    });
        }

        private void revokeAccess() {
            Log.d(TAG, "revokeAccess:");
            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // ...
                        }
                    });
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            Log.d(TAG, "onActivityResult:");
            super.onActivityResult(requestCode, resultCode, data);

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }
        }

        private void handleSignInResult(GoogleSignInResult result) {
            Log.d(TAG, "handleSignInResult:" + result.isSuccess());
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                name.setText(acct.getDisplayName());
                signInButton.setVisibility(View.GONE);
                signOutButton.setVisibility(View.VISIBLE);
            } else {

            }
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Log.d(TAG, "onConnectionFailed:");
        }
    }


}
