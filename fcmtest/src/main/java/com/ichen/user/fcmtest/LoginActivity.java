package com.ichen.user.fcmtest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.ichen.user.fcmtest.utils.FireBaseAgent;
import com.ichen.user.fcmtest.utils.LogManager;

/**
 * Created by Ichen on 2017/1/19.
 */
public class LoginActivity extends AppCompatActivity {
    private static final LogManager Log = new LogManager(true);
    private final String TAG = getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());

    /*View Block*/
    private EditText edEmail;
    private EditText edPw;
    private Button btnLogin;

    /*Listener Block*/
    private LoginAgentListener mLoginAgentListener = new LoginAgentListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initID();
        initView();
    }

    private void initID() {
        edEmail = (EditText) findViewById(R.id.input_email);
        edPw = (EditText) findViewById(R.id.input_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }

    private void initView() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");
                if (edEmail.getText().toString().isEmpty() || edPw.getText().toString().isEmpty()) {
                    Log.d(TAG, "edEmail.getText().toString().isEmpty() || edPw.getText().toString().isEmpty()");
                    return;
                }
                FireBaseAgent.getInstance().signIn(edEmail.getText().toString(), edPw.getText().toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FireBaseAgent.getInstance().setOnBeforSignInActionListener(mLoginAgentListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FireBaseAgent.getInstance().setOnBeforSignInActionListener(null);
    }

    /*Life Cycle Line*/

    private class LoginAgentListener implements FireBaseAgent.OnBeforSignInActionListener {

        @Override
        public void onSignInFail() {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("登入問題")
                    .setMessage("無此帳號，是否要以此帳號與密碼註冊?")
                    .setPositiveButton("註冊",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FireBaseAgent.getInstance().createUser(edEmail.getText().toString(), edPw.getText().toString());
                                }
                            })
                    .setNeutralButton("取消", null)
                    .show();
        }

        @Override
        public void onUnCheckVerifiedMail() {
            new AlertDialog.Builder(LoginActivity.this)
                    .setMessage("請確認註冊信件")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }

        @Override
        public void onSignInSuccess() {
            setResult(RESULT_OK);
            finish();
        }

        @Override
        public void onSignUpFail() {
            new AlertDialog.Builder(LoginActivity.this)
                    .setMessage("註冊失敗")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
    }
}
