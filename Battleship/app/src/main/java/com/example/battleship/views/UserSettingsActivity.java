package com.example.battleship.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.battleship.R;
import com.example.battleship.viewModels.AccountViewModel;
import com.facebook.drawee.view.SimpleDraweeView;

import static com.example.battleship.models.Constants.keyToGetMyUid;

public class UserSettingsActivity extends AppCompatActivity {
    private static final int pickImageCode = 1;
    private Uri imageUriToUpload;
    private SimpleDraweeView userImageDW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        String myUid = getIntent().getStringExtra(keyToGetMyUid);

        AccountViewModel accountVM = new ViewModelProvider(this).get(AccountViewModel.class);

        userImageDW = findViewById(R.id.userImageImageView);
        EditText nicknameET = findViewById(R.id.nicknameEditText);
        Button updateNicknameBtn = findViewById(R.id.updateNickNameButton);
        Button selectImageBtn = findViewById(R.id.selectImageButton);
        Button uploadImageBtn = findViewById(R.id.uploadImageButton);


        updateNicknameBtn.setOnClickListener(view ->
                accountVM.updateNickname(myUid, nicknameET.getText().toString()));
        selectImageBtn.setOnClickListener(view -> openGallery());
        uploadImageBtn.setOnClickListener(view -> {
            if (imageUriToUpload != null) {
                accountVM.updateUserImage(myUid, getImageExtension(imageUriToUpload), imageUriToUpload);
            } else {
                Toast.makeText(this, getString(R.string.select_image_to_upload), Toast.LENGTH_SHORT).show();
            }
        });

        accountVM.getUploadingImageProgress().observe(this, uploadingImageProgress ->
                Toast.makeText(this, uploadingImageProgress, Toast.LENGTH_SHORT).show());

        accountVM.listenForProfileOnce(myUid);
        accountVM.getUserProfile().observe(this, userProfile -> {
            nicknameET.setText(userProfile.getNickname());
            userImageDW.setImageURI(userProfile.getImageUrl());
        });

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, pickImageCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == pickImageCode) {
            imageUriToUpload = data.getData();
            userImageDW.setImageURI(imageUriToUpload);
        }
    }

    private String getImageExtension(Uri imageUri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(imageUri));
    }
}