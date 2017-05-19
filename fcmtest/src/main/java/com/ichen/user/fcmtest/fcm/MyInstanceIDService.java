package com.ichen.user.fcmtest.fcm;

import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.ichen.mygcm.backend.registration.Registration;
import com.ichen.user.fcmtest.R;

import java.io.IOException;

/**
 * Created by Ichen on 2017/1/19.
 */
public class MyInstanceIDService extends FirebaseInstanceIdService {
    private final String TAG = this.getClass().getSimpleName();


    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed Token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        try {
            sendRegistrationToServer(refreshedToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) throws IOException {
        // Add custom implementation, as needed.
        Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // Need setRootUrl and setGoogleClientRequestInitializer only for local testing,
                // otherwise they can be skipped
                .setRootUrl("https://jaredtest-425bb.appspot.com/_ah/api/");

        builder.setApplicationName(getResources().getString(R.string.app_name));
        Registration regService = builder.build();
        regService.register(token).execute();
    }
}
