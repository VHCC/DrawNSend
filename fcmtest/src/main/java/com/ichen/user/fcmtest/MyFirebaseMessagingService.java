package com.ichen.user.fcmtest;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Ichen on 2017/1/19.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: " + remoteMessage.getFrom());
    }
}
