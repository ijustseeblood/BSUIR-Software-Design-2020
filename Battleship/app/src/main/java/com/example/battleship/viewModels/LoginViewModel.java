package com.example.battleship.viewModels;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel {
    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<String> userUid;
    private final MutableLiveData<String> authFailMessage;

    public LoginViewModel() {
        userUid = new MutableLiveData<>();
        authFailMessage = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();
        notifyIfLoggedIn();
    }

    public void createUser(String email, String password) {
        String emailAndPasswordException = checkEmailAndPassword(email, password);
        if (emailAndPasswordException != null) {
            authFailMessage.setValue(emailAndPasswordException);
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    userUid.setValue(firebaseAuth.getUid());
                } else {
                    authFailMessage.setValue(task.getException().getMessage());
                }
            });
        }
    }

    public void authUser(String email, String password) {
        String emailAndPasswordException = checkEmailAndPassword(email, password);
        if (emailAndPasswordException != null) {
            authFailMessage.setValue(emailAndPasswordException);
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    userUid.setValue(firebaseAuth.getUid());
                } else {
                    authFailMessage.setValue(task.getException().getMessage());
                }
            });
        }
    }

    private String checkEmailAndPassword(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            return "Email is required";
        } else if (TextUtils.isEmpty(password)) {
            return "Password is required";
        }
        return null;
    }

    private void notifyIfLoggedIn() {
        if (firebaseAuth.getCurrentUser() != null) {
            userUid.setValue(firebaseAuth.getUid());
        }
    }

    public void signOut() {
        firebaseAuth.signOut();
    }

    public LiveData<String> getUserUid() {
        return userUid;
    }

    public MutableLiveData<String> getAuthFailMessage() {
        return authFailMessage;
    }
}
