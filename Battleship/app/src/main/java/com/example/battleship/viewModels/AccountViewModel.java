package com.example.battleship.viewModels;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.battleship.models.UserProfile;
import com.example.battleship.utils.Gravatar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class AccountViewModel extends ViewModel {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private final MutableLiveData<UserProfile> userProfile = new MutableLiveData<>();
    private final MutableLiveData<String> uploadingImageProgress = new MutableLiveData<>();
    private final String userProfilesNode = "UserProfiles";
    private final String userNicknameNode = "nickname";
    private final String userImageNode = "imageUrl";
    private final String imagesNode = "Images";


    public void createUserProfile(String userUid, String userEmail) {
        UserProfile userProfile = new UserProfile(Gravatar.getGravatarProfileImageUrl(userEmail), "");
        firebaseDatabase.getReference(userProfilesNode).child(userUid).setValue(userProfile);
    }

    public void updateNickname(String userUid, String nickname) {
        firebaseDatabase.getReference(userProfilesNode).child(userUid).child(userNicknameNode).setValue(nickname);
    }

    public void updateProfileImageUrl(String userUid, String profileImageUrl) {
        firebaseDatabase.getReference(userProfilesNode).child(userUid).child(userImageNode).setValue(profileImageUrl);
    }

    public void listenForProfileOnce(String userUid) {
        firebaseDatabase.getReference(userProfilesNode).child(userUid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                            if (userProfile != null) {
                                AccountViewModel.this.userProfile.setValue(userProfile);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    public void updateUserImage(String userUid, String imageExt, Uri imageUriToUpload) {
        StorageReference imagesRef = firebaseStorage.getReference().child(imagesNode);
        imagesRef.child(userUid + "." + imageExt).putFile(imageUriToUpload).
                addOnSuccessListener(taskSnapshot -> taskSnapshot.getStorage().getDownloadUrl().
                        addOnSuccessListener(newImageUrl -> updateProfileImageUrl(userUid, newImageUrl.toString()))
                ).addOnFailureListener(exception -> {
        }).addOnProgressListener(progressTaskSnapshot ->
                uploadingImageProgress.setValue(
                progressTaskSnapshot.getBytesTransferred() / progressTaskSnapshot.getTotalByteCount() * 100.0 + "% uploaded"));
    }


    public LiveData<UserProfile> getUserProfile() {
        return userProfile;
    }

    public MutableLiveData<String> getUploadingImageProgress() {
        return uploadingImageProgress;
    }
}
