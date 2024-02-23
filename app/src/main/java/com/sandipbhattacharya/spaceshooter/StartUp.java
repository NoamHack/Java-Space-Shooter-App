package com.sandipbhattacharya.spaceshooter;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StartUp extends AppCompatActivity {
    private TextView userName;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private BatteryReceiver batteryReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        batteryReceiver = new BatteryReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        registerReceiver(batteryReceiver, intentFilter);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.UserName);

        if (mAuth.getUid() != null)  {
            db.collection("users").document(String.valueOf(mAuth.getUid())).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    String user_txt = "Hello " + task.getResult().get("Name");
                    userName.setText(user_txt);
                }
            });
        }
        else {
            userName.setText("Hello Guest");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the battery receiver
        unregisterReceiver(batteryReceiver);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
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

    public void PlayBackgroundSound(View view) {
        Intent intent = new Intent(StartUp.this, BackgroundSoundService.class);
        startService(intent);
    }

    public void Login (View view){
        startActivity(new Intent(this, Login.class));
        finish();
    }

    public void Logout (View view){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        startActivity(new Intent(this,StartUp.class));
        finish();
        mAuth.signOut();
        //String user = mAuth.getUid().toString();
        if(mAuth.getUid() == null)
            Toast.makeText(StartUp.this, "logged out", Toast.LENGTH_SHORT).show();

    }
    public void MyUser(View view) {
        if (mAuth.getUid() != null) {
            startActivity(new Intent(this, MyUser.class));
            finish();
        }
        else {
            Toast.makeText(StartUp.this, "You must be logged in to see your profile", Toast.LENGTH_SHORT).show();
        }
    }
    public void startGame(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
