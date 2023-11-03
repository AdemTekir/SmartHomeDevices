package com.tekir.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText editEmail, editPassword;
    private String emailTxt, passwordTxt,sharedPreferencesGetUserEmail,sharedPreferencesGetUserPassword;
    private Boolean sharedPreferencesGetRememberMe;
    private Button loginBtn, signBtn;
    private Switch rememberMeSwitch;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = (EditText) findViewById(R.id.editTextLoginEmail);
        editPassword = (EditText) findViewById(R.id.editTextLoginPassword);
        loginBtn = (Button) findViewById(R.id.loginButton);
        signBtn = (Button) findViewById(R.id.signButton);
        rememberMeSwitch = (Switch) findViewById(R.id.loginRememberMe);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("com.tekir.test", Context.MODE_PRIVATE);
        sharedPreferencesGetUserEmail = sharedPreferences.getString("Email",null);
        sharedPreferencesGetUserPassword = sharedPreferences.getString("Password",null);
        sharedPreferencesGetRememberMe = sharedPreferences.getBoolean("RememberMe",false);

        sharedPreferencesCheck();

        rememberMeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    emailTxt = editEmail.getText().toString();
                    passwordTxt = editPassword.getText().toString();

                    editor = sharedPreferences.edit();
                    editor.putString("Email",emailTxt);
                    editor.putString("Password",passwordTxt);
                    editor.putBoolean("RememberMe",true);
                    editor.apply();
                }
                else{
                    editor = sharedPreferences.edit();
                    editor.putString("Email", null);
                    editor.putString("Password", null);
                    editor.putBoolean("RememberMe",false);
                    editor.apply();
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign();
            }
        });
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignActivity.class);
                startActivity(intent);
            }
        });
    }
    public void sharedPreferencesCheck(){
        if (!TextUtils.isEmpty(sharedPreferencesGetUserEmail) && !TextUtils.isEmpty(sharedPreferencesGetUserPassword)) {
            editEmail.setText(sharedPreferencesGetUserEmail);
            editPassword.setText(sharedPreferencesGetUserPassword);
            sign();
        }
        if (sharedPreferencesGetRememberMe){
            rememberMeSwitch.setChecked(true);
        }
    }
    public void sign(){
        emailTxt = editEmail.getText().toString();
        passwordTxt = editPassword.getText().toString();

        if (!TextUtils.isEmpty(emailTxt) && !TextUtils.isEmpty(passwordTxt)){
            mAuth.signInWithEmailAndPassword(emailTxt,passwordTxt)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mUser = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }else
            Toast.makeText(this,"E-mail Ve Şifre Alanlarını Doldurunuz.",Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed(){
        finishAffinity();
    }
}
