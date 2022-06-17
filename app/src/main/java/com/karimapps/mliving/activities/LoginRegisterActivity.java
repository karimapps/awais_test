package com.karimapps.mliving.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karimapps.mliving.Common.Utills;
import com.karimapps.mliving.MainActivity;
import com.karimapps.mliving.R;

import java.util.Arrays;
import java.util.List;


public class LoginRegisterActivity extends AppCompatActivity {

    private static final String TAG = "LoginRegisterActivity";
    int AUTHUI_REQUEST_CODE = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button btn_login = findViewById(R.id.btn_login);

        Intent intentf = this.getIntent();
        if (intentf != null) {
            String forgot = intentf.getStringExtra("forgot");

            if (!TextUtils.isEmpty(forgot)) {
                if (forgot.equalsIgnoreCase("true")) {


                    List<AuthUI.IdpConfig> providers = Arrays.asList(

                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                            new AuthUI.IdpConfig.FacebookBuilder().build()

                    );

                    Intent intent = AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setTosAndPrivacyPolicyUrls("https://karimapps.com", "https://karimapps.com")
//                .setLogo(R.drawable.notes)
                            .setAlwaysShowSignInMethodScreen(true)
                            .setIsSmartLockEnabled(false)
                            .build();

                    startActivityForResult(intent, AUTHUI_REQUEST_CODE);

                }
            }


        }


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginRegisterActivity.this, LoginActivity.class));
                finish();
            }
        });


    }

    public void handleLoginRegister(View view) {


        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        this.finish();


//        new AuthUI.IdpConfig.GoogleBuilder().build()
//        new AuthUI.IdpConfig.EmailBuilder().build(),
//        new AuthUI.IdpConfig.PhoneBuilder().build()
//        List<AuthUI.IdpConfig> providers = Arrays.asList(
//
//                new AuthUI.IdpConfig.EmailBuilder().build()
//
//        );
//
//        Intent intent = AuthUI.getInstance()
//                .createSignInIntentBuilder()
//                .setAvailableProviders(providers)
//                .setTosAndPrivacyPolicyUrls("https://karimapps.com", "https://karimapps.com")
////                .setLogo(R.drawable.notes)
//                .setAlwaysShowSignInMethodScreen(true)
//                .setIsSmartLockEnabled(false)
//                .build();
//
//        startActivityForResult(intent, AUTHUI_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTHUI_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // We have signed in the user or we have a new user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d(TAG, "onActivityResult: " + user.toString());
                //Checking for User (New/Old)
                if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()) {
                    //This is a New User

//                    Intent intent = new Intent(this, RegisterActivity.class);
//                    intent.putExtra("name", user.getDisplayName());
//                    intent.putExtra("email", user.getEmail());
//                    startActivity(intent);
//                    this.finish();
                } else {
                    //This is a returning user

                    Intent intent = new Intent(this, MainActivity.class);
                    Utills.savePreferences("name", user.getDisplayName(), this);
                    Utills.savePreferences("phone", user.getPhoneNumber(), this);
                    intent.putExtra("name", user.getDisplayName());
                    intent.putExtra("phone", user.getPhoneNumber());
                    startActivity(intent);
                    this.finish();
                }


            } else {
                // Signing in failed
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (response == null) {

                    Log.d(TAG, "onActivityResult: the user has cancelled the sign in request");
                } else {
                    Log.e(TAG, "onActivityResult: ", response.getError());
                }
            }
        }
    }
}
