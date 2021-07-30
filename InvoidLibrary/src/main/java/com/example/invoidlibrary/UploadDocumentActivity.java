package com.example.invoidlibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class UploadDocumentActivity extends AppCompatActivity {

    public static final int GALLERY_CODE = 1;
    public CardView saveButton;
    public ProgressBar post_progressbar;
    public ImageView imageView;
    static UploadDocumentActivity INSTANCE;
    public StorageReference storageReference;

    public String url;

    private Uri imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        INSTANCE=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document);
        this.getSupportActionBar().hide();

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        FirebaseApp.initializeApp(this);

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_CODE);

        storageReference= FirebaseStorage.getInstance().getReference();
        imageView=findViewById(R.id.UploadedImage);
        post_progressbar=findViewById(R.id.progress_bar);
        saveButton=findViewById(R.id.UploadTheDocumentCardView);
        post_progressbar.setVisibility(View.INVISIBLE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDocument();
            }
        });

    }

    public static UploadDocumentActivity getActivityInstance()
    {
        return INSTANCE;
    }

    private void saveDocument() {
        post_progressbar.setVisibility(View.VISIBLE);

        if (imageUrl != null) {

            final StorageReference filepath = storageReference.child("images")
                    .child("myimage" + Timestamp.now().getSeconds());
            filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url = uri.toString();

                            post_progressbar.setVisibility(View.INVISIBLE);
                            SharedPreferences mySharedPreferences = getSharedPreferences("URLLINK", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mySharedPreferences.edit();
                            editor.putString("URL",url);
                            editor.apply();
                            finish();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    post_progressbar.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            if (imageUrl==null) {
                Toast.makeText(UploadDocumentActivity.this, "Add an Image", Toast.LENGTH_LONG).show();
                post_progressbar.setVisibility(View.INVISIBLE);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                imageUrl = data.getData();
                imageView.setImageURI(imageUrl);

            }
        }
    }

}