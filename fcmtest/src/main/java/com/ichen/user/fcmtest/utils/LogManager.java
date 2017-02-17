package com.ichen.user.fcmtest.utils;

import android.util.Log;

/**
 * Created by Ichen on 2017/2/17.
 */
public class LogManager {
    /**
     * Global switch of Log
     */
    private final boolean ENABLE_GLOBAL_LOG = true;
    /**
     * the switch of Log
     */
    private final boolean enableLocalLog;

    public LogManager(boolean enableLocalLog) {
        this.enableLocalLog = enableLocalLog;
    }

    /**
     * Verbose
     *
     * @param tag
     * @param msg
     */
    public void v(String tag, String msg) {
        if (!ENABLE_GLOBAL_LOG) {
            return;
        }
        if (enableLocalLog) {
            Log.v(tag, msg);
        }
    }

    /**
     * Info
     *
     * @param tag
     * @param msg
     */
    public void i(String tag, String msg) {
        if (!ENABLE_GLOBAL_LOG) {
            return;
        }
        if (enableLocalLog) {
            Log.i(tag, msg);
        }
    }

    /**
     * Debug
     *
     * @param tag
     * @param msg
     */
    public void d(String tag, String msg) {
        if (!ENABLE_GLOBAL_LOG) {
            return;
        }
        if (enableLocalLog) {
            Log.d(tag, msg);
        }
    }

    /**
     * Warn
     *
     * @param tag
     * @param msg
     */
    public void w(String tag, String msg) {
        if (!ENABLE_GLOBAL_LOG) {
            return;
        }
        if (enableLocalLog) {
            Log.w(tag, msg);
        }
    }

    /**
     * Error
     *
     * @param tag
     * @param msg
     */
    public void e(String tag, String msg) {
        if (!ENABLE_GLOBAL_LOG) {
            return;
        }
        if (enableLocalLog) {
            Log.e(tag, msg);
        }
    }


}
