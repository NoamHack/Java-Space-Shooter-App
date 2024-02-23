package com.sandipbhattacharya.spaceshooter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.Inflater;

public class MyUser extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 1;
    private TextView userName, age, email, highScore;
    private ImageButton pic;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    private BatteryReceiver batteryReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_user);
        Log.d("StartUp", "Registering BatteryReceiver");
        batteryReceiver = new BatteryReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        registerReceiver(batteryReceiver, intentFilter);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        userName = findViewById(R.id.textView5);
        age = findViewById(R.id.textView6);
        email = findViewById(R.id.textView7);
        highScore = findViewById(R.id.textView8);
        pic = findViewById(R.id.imageButton5);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storage.getReference().getStorage().setMaxUploadRetryTimeMillis(120000);

        db.collection("users").document(String.valueOf(mAuth.getUid())).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String name = "Hello " + document.getString("Name");
                            userName.setText(name);
                            String userAge = "Your age: " + document.getString("Age");
                            age.setText(userAge);
                            String userEmail = "Your Email: " + document.getString("Email");
                            email.setText(userEmail);
                            String userHighScore = "Your high score: " + document.get("High Score");
                            highScore.setText(userHighScore);

                            // Load the profile picture using Glide
                            String pictureUrl = document.getString("Picture");
                            if (pictureUrl != null) {
                                Glide.with(this)
                                        .load(pictureUrl)
                                        .into(pic);
                            }
                        }
                    }
                });

        pic.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the battery receiver
        unregisterReceiver(batteryReceiver);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home: {
                startActivity(new Intent(this, StartUp.class));
                finish();
                return true;
            }

            case R.id.login: {
                startActivity(new Intent(this, Login.class));
                finish();
                return true;
            }

            case R.id.register: {
                startActivity(new Intent(this, Register.class));
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data1 = baos.toByteArray();
            String filename = mAuth.getUid() + "/profile_pic.jpg";
            StorageReference imageRef = storageRef.child(filename);
            UploadTask uploadTask = imageRef.putBytes(data1);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return imageRef.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();

                    // Add the photo URL to the user's document
                    String imageUrl = downloadUri.toString();
                    Map<String, Object> user = new HashMap<>();
                    user.put("Pic", imageUrl);

                    db.collection("users").document(mAuth.getUid())
                            .set(user, SetOptions.merge())
                            .addOnSuccessListener(documentReference -> {
                                // Do something on success
                            })
                            .addOnFailureListener(e -> {
                                // Handle the error
                            });
                } else {
                    // Handle unsuccessful uploads
                }
            });


            pic.setScaleType(ImageView.ScaleType.CENTER_CROP);
            pic.setImageBitmap(photo);
        }

    }
    public void checkAdminAccess(View view) {
        db.collection("users").document(mAuth.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    String name = documentSnapshot.getString("Name");
                    if (name != null && name.toLowerCase().contains("admin")) {
                        Intent intent = new Intent(this, Adminpage.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "You do not have admin access", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error checking admin access", Toast.LENGTH_SHORT).show();
                });
    }
}