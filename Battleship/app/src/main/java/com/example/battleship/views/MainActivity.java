package com.example.battleship.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.battleship.R;
import com.example.battleship.viewModels.LoginViewModel;

import static com.example.battleship.models.Constants.keyToGetMyUid;

public class MainActivity extends AppCompatActivity {
    private String myUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myUid = getIntent().getStringExtra(keyToGetMyUid);

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        Button createNewRoomBtn = findViewById(R.id.createNewRoomButton);
        Button connectToRoomBtn = findViewById(R.id.connectToRoomButton);
        Button userSettingsBtn = findViewById(R.id.userSettingsButton);
        Button gameResultsBtn = findViewById(R.id.gameResultsButton);
        Button signOutBtn = findViewById(R.id.signOutButton);

        createNewRoomBtn.setOnClickListener(view -> navigateToActivityWithUid(CreateRoomActivity.class));

        connectToRoomBtn.setOnClickListener(view -> navigateToActivityWithUid(ConnectToRoomActivity.class));

        gameResultsBtn.setOnClickListener(view -> navigateToActivityWithUid(GameResultsActivity.class));

        userSettingsBtn.setOnClickListener(view -> navigateToActivityWithUid(UserSettingsActivity.class));

        signOutBtn.setOnClickListener(view -> {
            loginViewModel.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void navigateToActivityWithUid(Class<?> activityClassToNavigate) {
        Intent intent = new Intent(this, activityClassToNavigate);
        intent.putExtra(keyToGetMyUid, myUid);
        startActivity(intent);
    }
}