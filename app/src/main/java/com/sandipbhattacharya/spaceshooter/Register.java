package com.sandipbhattacharya.spaceshooter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Register extends AppCompatActivity {

    EditText et_mail, et_password, et_age,et_name;
    Button btn_save, btn_login;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_mail = findViewById(R.id.et_mail);
        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);
        et_age = findViewById(R.id.et_age);
        btn_save = findViewById(R.id.btn_save);
        btn_login = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(et_mail.getText().toString(), et_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Map<String, Object> users = new HashMap<>();
                        users.put("Name", et_name.getText().toString());
                        users.put("Email", et_mail.getText().toString());
                        users.put("Password", et_password.getText().toString());
                        users.put("Age", et_age.getText().toString());
                        users.put("High Score", 0);

                        db.collection("users").document(Objects.requireNonNull(mAuth.getUid())).set(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(Register.this, "Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register.this, StartUp.class);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
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
}
