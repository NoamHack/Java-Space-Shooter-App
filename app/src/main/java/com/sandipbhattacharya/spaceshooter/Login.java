package com.sandipbhattacharya.spaceshooter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    public void register(View view) {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
        finish();
    }

    public boolean isEmailValid(String email) {
        if (email.indexOf("@") == -1) {
            Toast.makeText(Login.this, "you don't have: @ in your email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.indexOf(".") == -1) {
            Toast.makeText(Login.this, "you don't have: . in your email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.indexOf(".") < email.indexOf("@")) {
            Toast.makeText(Login.this, "the . is before the @", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.length() < 11) {
            Toast.makeText(Login.this, "the email is not long enough", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isPassValid(String password) {
        if (password.length() < 4) {
            Toast.makeText(Login.this, "the password is not long enough", Toast.LENGTH_SHORT).show();
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isDigit(password.charAt(i))) {
                Toast.makeText(Login.this, "non digit character deducted in password", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
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

    public boolean isInputValid(String email, String password) {
        return isEmailValid(email) && isPassValid(password);
    }

        EditText et_email, et_password;
        Button btn_login;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            et_email = findViewById(R.id.et_mail);
            et_password = findViewById(R.id.et_password);
            btn_login = findViewById(R.id.btn_login);

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String email = et_email.getText().toString();
                    final String password = et_password.getText().toString();

                    if (!isInputValid(email, password)) {
                        return;
                    }

                    db.collection("users")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (email.equals(document.get("Email")) && password.equals(document.get("Password"))) {
                                                Toast.makeText(Login.this, "Logged in", Toast.LENGTH_LONG).show();
                                                mAuth.signInWithEmailAndPassword(email,password);
                                                Intent intent = new Intent(Login.this, StartUp.class);
                                                startActivity(intent);
                                                return;
                                            }
                                        }
                                        Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                                    } else {
                                        Log.d("Login", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
            });
        }
    }
