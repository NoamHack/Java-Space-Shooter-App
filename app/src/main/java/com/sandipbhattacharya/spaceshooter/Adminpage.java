package com.sandipbhattacharya.spaceshooter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Adminpage extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PlayerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Player> mPlayerList;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);

        db = FirebaseFirestore.getInstance();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mPlayerList = new ArrayList<>();
        mAdapter = new PlayerAdapter(mPlayerList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getString("Name");
                                String email = document.getString("Email");
                                Object age = document.get("Age");
                                if (age instanceof Number) {
                                    int ageValue = ((Number) age).intValue();
                                    String password = document.getString("Password");
                                    int highScore = document.getLong("High Score").intValue();
                                    Player player = new Player(name, email, ageValue, password, highScore);
                                    player.setPlayerId(document.getId());
                                    mPlayerList.add(player);
                                } else if (age instanceof String) {
                                    int ageValue = Integer.parseInt((String) age);
                                    String password = document.getString("Password");
                                    int highScore = document.getLong("High Score").intValue();
                                    Player player = new Player(name, email, ageValue, password, highScore);
                                    player.setPlayerId(document.getId());
                                    mPlayerList.add(player);
                                } else {
                                    Log.e("Adminpage", "Age is not a number or string: " + age);
                                }
                            }
                            mAdapter.notifyDataSetChanged();
                        } else {
                            Log.e("Adminpage", "Error getting documents.", task.getException());
                        }
                    }
                });
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
}