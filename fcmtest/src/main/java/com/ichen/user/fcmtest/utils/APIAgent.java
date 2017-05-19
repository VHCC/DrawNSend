package com.ichen.user.fcmtest.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.ichen.mygcm.backend.myApi.MyApi;
import com.ichen.user.fcmtest.R;

import java.io.IOException;


/**
 * Created by user on 2017/5/18.
 */

public class APIAgent {
    private static final LogManager Log = new LogManager(true);
    private final String TAG = getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());

    private static APIAgent mAPIAgent = null;

    private APIAgent() {
    }

    public static APIAgent newInstance() {
        if (mAPIAgent == null) {
            mAPIAgent = new APIAgent();
        }
        return mAPIAgent;
    }

    public static class PushTask extends AsyncTask<Pair<Context, String>, Void, String> {
        private MyApi myApiService = null;
        private Context mContext;


        public PushTask() {
        }

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if (myApiService == null) {  // Only do this once
                mContext = params[0].first;
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("https://jaredtest-425bb.appspot.com/_ah/api/")
                        .setApplicationName(mContext.getString(R.string.app_name));
                // end options for devappserver

                myApiService = builder.build();
            }

            String name = params[0].second;
            try {
                return myApiService.sayHi(name).execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
        }
    }
}
