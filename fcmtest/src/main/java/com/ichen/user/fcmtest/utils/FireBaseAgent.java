package com.ichen.user.fcmtest.utils;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Ichen on 2017/2/17.
 */
public class FireBaseAgent {
    private static final LogManager Log = new LogManager(true);
    private final String TAG = getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());

    /*Listener Block*/
    private OnBeforSignInActionListener mBeforeListener;
    private OnAfterSignInActionListener mAfterListener;

    /*FireBase Block*/
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;


    private static FireBaseAgent mLoginAgent;

    private FireBaseAgent() {
        auth = FirebaseAuth.getInstance();
        initialListener();

    }

    public static FireBaseAgent getInstance() {
        if (null == mLoginAgent) {
            mLoginAgent = new FireBaseAgent();
        }
        return mLoginAgent;
    }

    private void initialListener() {
        authStateListener = new FirebaseAuthAuthStateListener();
    }

    /*Life Cycle Line*/

    public interface OnBeforSignInActionListener {
        void onSignInFail();

        void onUnCheckVerifiedMail();

        void onSignInSuccess();

        void onSignUpFail();
    }

    public void setOnBeforSignInActionListener(OnBeforSignInActionListener listener) {
        mBeforeListener = listener;
    }

    public interface OnAfterSignInActionListener {
        void onLogoutSuccess();

        void onUnLogin();

        void onLoginValidate();
    }

    public void setOnAfterSignInListener(OnAfterSignInActionListener listener) {
        if (null == listener) {
            auth.removeAuthStateListener(authStateListener);
        }
        mAfterListener = listener;
    }

    public void signIn(String email, String pwd) {
        auth.signInWithEmailAndPassword(email, pwd)
                        /*.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                setResult(RESULT_OK);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                btnLogin.setError("登入失敗，請檢查email/password");
                            }
                        })*/
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "onSignInFail");
                            mBeforeListener.onSignInFail();
                        } else {
                            if (!auth.getCurrentUser().isEmailVerified()) {
                                Log.d(TAG, "onUnCheckVerifiedMail");
                                mBeforeListener.onUnCheckVerifiedMail();
                            } else {
                                Log.d(TAG, "onSignInSuccess");
                                mBeforeListener.onSignInSuccess();
                            }
                        }
                    }
                });
    }

    public void createUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mBeforeListener.onSignInSuccess();
                                    Log.d(TAG, "sendEmailVerification");
                                    auth.getCurrentUser().sendEmailVerification();
                                } else {
                                    Log.d(TAG, "onSignUpFail");
                                    mBeforeListener.onSignUpFail();
                                }
                            }
                        });
    }

    public void logOut() {
        auth.signOut();
        Log.d(TAG, "onLogoutSuccess");
        mAfterListener.onLogoutSuccess();
    }

    /*Class Block*/

    private class FirebaseAuthAuthStateListener implements FirebaseAuth.AuthStateListener {

        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null || !user.isEmailVerified()) {
                Log.d(TAG, "onUnLogin");
                mAfterListener.onUnLogin();
            } else {
                Log.d(TAG, "onLoginValidate");
                mAfterListener.onLoginValidate();
            }
        }
    }

}
