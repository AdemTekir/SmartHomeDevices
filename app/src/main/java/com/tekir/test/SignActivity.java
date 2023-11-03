package com.tekir.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignActivity extends AppCompatActivity {
    private EditText editEmail, editPassword,editConfirmPassword;
    private String emailTxt, passwordTxt,confirmPasswordTxt;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Button signSignBtn,backToLoginBtn;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        editEmail = (EditText) findViewById(R.id.editTextSignEmail);
        editPassword = (EditText) findViewById(R.id.editTextSignPassword);
        editConfirmPassword = (EditText) findViewById(R.id.editTextSignConfirmPassword);
        signSignBtn = (Button) findViewById(R.id.signSignBtn);
        backToLoginBtn = (Button) findViewById(R.id.backToLoginBtn);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        signSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addUser() {
        emailTxt = editEmail.getText().toString();
        passwordTxt = editPassword.getText().toString();
        confirmPasswordTxt = editConfirmPassword.getText().toString();

        if (!emailTxt.matches(emailPattern)){
            editEmail.setError("Hatalı Giriş!");
            editEmail.requestFocus();
        } else if (passwordTxt.isEmpty()|passwordTxt.length()<6) {
            editPassword.setError("Şifre boş yada 6 karakterden kısa olamaz!");
            editPassword.requestFocus();
        } else if (!passwordTxt.equals(confirmPasswordTxt)) {
            editConfirmPassword.setError("Şifreler uyuşmuyor!");
            editConfirmPassword.requestFocus();
        }else {
            progressDialog.setMessage("Kaydınız Oluşturuluyor...");
            progressDialog.setTitle("Kayıt İşlemi");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(emailTxt,passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        Intent intent = new Intent(SignActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(SignActivity.this,"Kaydınız Oluşturuldu",Toast.LENGTH_SHORT).show();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(SignActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}