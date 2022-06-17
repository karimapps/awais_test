package com.karimapps.mliving.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karimapps.mliving.Common.LoadingDialog;
import com.karimapps.mliving.MainActivity;
import com.karimapps.mliving.R;
import com.karimapps.mliving.fcmodels.UserActions;
import com.wang.avi.AVLoadingIndicatorView;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    TextView tv_forgot, tv_new_account;
    Button mEmailSignInButton;
    //    ProgressDialog progressDialog;
    Context context;
    public static Activity login;
    ImageView ic_fb_login, ic_g_login;
    AVLoadingIndicatorView avi;


    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 10;
    private Button btn_login, btnforgot, btn_back;
    private String name, pass;
    private EditText etemail, etpass;

    private static LoginActivity mInstance;
    public static FirebaseAuth mAuth;
    public static FirebaseDatabase mDatabase;
    public static DatabaseReference mRef;
    public static FirebaseOptions opts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        context = this;

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("users");
        mRef.keepSynced(true);
        mAuth = FirebaseAuth.getInstance();
        opts = FirebaseApp.getInstance().getOptions();

        mInstance = this;

        Intent bundle = this.getIntent();

        if (bundle != null) {
            name = bundle.getStringExtra("phone");
        }


        IntializeView();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    private void IntializeView() {

        avi = findViewById(R.id.avi);
        etemail = findViewById(R.id.etemail);
        etpass = findViewById(R.id.etpass);
        btn_login = findViewById(R.id.btn_login);

        btnforgot = findViewById(R.id.btnforgot);
        btn_back = findViewById(R.id.btn_back);

        if (!TextUtils.isEmpty(name)) {
            etemail.setText(name);
        }


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, LoginRegisterActivity.class));
                finish();
            }
        });


        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginRegisterActivity.class);
                intent.putExtra("forgot", "true");
                startActivity(intent);
                finish();
            }
        });

        etpass.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginMethod();
                    return true;
                }
                return false;
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(context, LoginRegisterActivity.class));
//                finish();

                loginMethod();
            }


        });
//        https://firebase.google.com/docs/auth/android/phone-auth


    }


    public void loginMethod() {
        name = etemail.getText().toString();
        pass = etpass.getText().toString();


        if (TextUtils.isEmpty(name)) {
            etemail.setError("Enter Email");
        } else if (TextUtils.isEmpty(pass)) {
            etpass.setError("Enter Password");
        } else {

            firebaseLogin();
//
        }
    }

    public void firebaseLogin() {
        LoadingDialog.startAnim(avi);
        mAuth.signInWithEmailAndPassword(name, pass)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
//                                Toast.makeText(LoginActivity.this, "successful",Toast.LENGTH_SHORT).show();

                            final FirebaseUser user = mAuth.getCurrentUser();

                            final String uid = user.getUid();

                            UserActions.userExists(uid, new UserActions.UserExistsInterface() {
                                @Override
                                public void userExist(boolean exists) {


                                    if (exists) {
//                                        progressDialog.dismiss();

                                        String profilePicUrl = "", userName = "", displayName = "", email = "";

                                        email = user.getEmail();
                                        Intent intent = new Intent(context, MainActivity.class);
                                        intent.putExtra("phone", user.getEmail());
                                        startActivity(intent);


                                    } else {
                                        String profilePicUrl = "", userName = "", displayName = "", email = "";

//                                        finish();
                                    }

                                }
                            });
//
                            finish();
                            LoadingDialog.stopAnim(avi);

                        } else {
                            // If sign in fails, display a message to the user.
//                                    Log.w("TAG", "signInWithEmail:failure", task.getException());

//                                progressDialog.dismiss();
                            LoadingDialog.stopAnim(avi);
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
////                            Log.d(TAG, "signInWithCredential:success");
//
//                            FirebaseUser user = task.getResult().getUser();
//                            // ...
//                        } else {
//                            // Sign in failed, display a message and update the UI
////                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//                            }
//                        }
//                    }
//                });
//    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */


}

