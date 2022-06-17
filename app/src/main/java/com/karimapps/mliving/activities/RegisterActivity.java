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
import android.widget.CheckBox;
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
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karimapps.mliving.R;
import com.karimapps.mliving.fcmodels.UserActions;


/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {
    private static RegisterActivity mInstance;
    public static FirebaseAuth mAuth;
    public static FirebaseDatabase mDatabase;
    public static DatabaseReference mRef;
    public static FirebaseOptions opts;
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    TextView tv_forgot, tv_new_account;
    Button mEmailSignInButton;
    //    ProgressDialog progressDialog;
    Context context;
    public static Activity login;
    ImageView ic_fb_login, ic_g_login;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 10;
    private Button btn_reg;
    private String name, email, pass, cpass;
    private EditText etname, etphone, etemail, etpass, etcpass;
    String mVerificationId;
    private CheckBox cbterms;
    private TextView tv_login;
    private ImageView iv_fb, iv_gmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        context = this;
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("users");
        mRef.keepSynced(true);
        mAuth = FirebaseAuth.getInstance();
        opts = FirebaseApp.getInstance().getOptions();

        mInstance = this;


        Intent bundle = this.getIntent();

        if (bundle != null) {
            name = bundle.getStringExtra("name");
            email = bundle.getStringExtra("email");
        }


//        String keyhash=printKeyHash(this);
//        FirebaseAuth.getInstance().signOut();

//        progressDialog = new ProgressDialog(context);


        IntializeView();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    private void IntializeView() {

        cbterms = findViewById(R.id.cbterms);
        etname = findViewById(R.id.etname);
        etemail = findViewById(R.id.etemail);
        etpass = findViewById(R.id.etpass);
        etcpass = findViewById(R.id.etcpass);
        tv_login = findViewById(R.id.tv_login);

        iv_fb = findViewById(R.id.iv_fb);
        iv_gmail = findViewById(R.id.iv_gmail);


        if (!TextUtils.isEmpty(name)) {
            etname.setText(name);
            etname.setEnabled(false);
        }

        if (!TextUtils.isEmpty(email)) {
            etemail.setText(email);
            etemail.setEnabled(false);
        }


        btn_reg = findViewById(R.id.btn_reg);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbterms.isChecked()) {
                    registerUser();
                } else {
                    Toast.makeText(context, "Accept our terms and conditions first", Toast.LENGTH_SHORT).show();
                }


            }
        });


        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, LoginActivity.class));
            }
        });


        iv_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "login with fb", Toast.LENGTH_SHORT).show();
            }
        });

        iv_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "login with gmail", Toast.LENGTH_SHORT).show();
            }
        });


        etcpass.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (cbterms.isChecked()) {
                        registerUser();
                    } else {
                        Toast.makeText(context, "Accept our terms and conditions first", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
//

    }

    private void registerUser() {


        name = etname.getText().toString();

        email = etemail.getText().toString();
        pass = etpass.getText().toString();
        cpass = etcpass.getText().toString();

        if (TextUtils.isEmpty(name)) {
            etname.setError("Enter name");
        } else if (TextUtils.isEmpty(email)) {
            etemail.setError("Enter email");
        } else if (TextUtils.isEmpty(pass)) {
            etpass.setError("Enter password");
        } else if (TextUtils.isEmpty(cpass)) {
            etcpass.setError("Enter confirm password");
        } else if (!pass.equals(cpass)) {
            etcpass.setError("password dose not match");
        } else {

            firebaseRegister();
//
        }

    }

    private void firebaseRegister() {

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            final String uid = user.getUid();

                            UserActions.userExists(uid, new UserActions.UserExistsInterface() {
                                @Override
                                public void userExist(boolean exists) {


//                                        Utills.savePreferences(Utills.NAME,displayName,getApplicationContext());
//                                        Utills.savePreferences(Utills.EMAIL,email,getApplicationContext());

                                    UserActions.saveUser2(uid, name, "0", email, pass
                                            , new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                    if (databaseError == null) {
//                                                        startActivity(new Intent(context, MainActivity.class));
//                                                        finish();
                                                        sendVerificationEmail();

                                                    } else {

                                                    }
                                                }
                                            });


//                                        finish();
//                                    }

                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();


                        }

                        // ...
                    }
                });
    }


    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent


                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(RegisterActivity.this, ActivitySuccess.class));
                            finish();
                        } else {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }


}

