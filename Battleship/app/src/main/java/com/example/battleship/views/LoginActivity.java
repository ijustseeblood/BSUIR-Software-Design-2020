package com.example.battleship.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.battleship.R;
import com.example.battleship.viewModels.LoginViewModel;
import com.example.battleship.viewModels.AccountViewModel;

import static com.example.battleship.models.Constants.keyToGetMyUid;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        LoginViewModel loginVM = new ViewModelProvider(this).get(LoginViewModel.class);
        AccountViewModel userProfileVM = new ViewModelProvider(this).get(AccountViewModel.class);

        Button loginBtn = findViewById(R.id.loginButton);
        Button registerBtn = findViewById(R.id.registerButton);
        EditText emailET = findViewById(R.id.emailEditText);
        EditText passwordET = findViewById(R.id.passwordEditText);

        registerBtn.setOnClickListener(view ->
                loginVM.createUser(emailET.getText().toString(), passwordET.getText().toString()));

        loginBtn.setOnClickListener(view ->
                loginVM.authUser(emailET.getText().toString(), passwordET.getText().toString()));

        loginVM.getUserUid().observe(this, userUID -> {
            String email = emailET.getText().toString();
            if (!email.isEmpty()) {
                userProfileVM.createUserProfile(userUID, emailET.getText().toString());
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(keyToGetMyUid, userUID);
            startActivity(intent);
            finish();
        });

        loginVM.getAuthFailMessage().observe(this, authException ->
                Toast.makeText(this, authException, Toast.LENGTH_LONG).show());
    }
}