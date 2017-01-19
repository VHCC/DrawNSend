package com.ichen.user.fcmtest;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Ichen on 2017/1/19.
 */
public class MyInstanceIDService extends FirebaseInstanceIdService {
    private final String TAG = this.getClass().getSimpleName();


    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token: " + token);
    }
}
