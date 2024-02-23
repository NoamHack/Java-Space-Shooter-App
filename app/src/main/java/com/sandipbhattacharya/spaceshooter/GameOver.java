package com.sandipbhattacharya.spaceshooter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameOver extends AppCompatActivity {

    TextView tvPoints;
    TextView highScore;
    int high;
    ImageButton exitBtn;
    RecyclerView LeaderBoard;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        Utilities save = new Utilities();
        exitBtn = findViewById(R.id.exitbtn);
        MyDialog myDialog = new MyDialog(this);
        int points = getIntent().getExtras().getInt("points");
        highScore = findViewById(R.id.highScore);
        tvPoints = findViewById(R.id.tvPoints);
        LeaderBoard = findViewById(R.id.Leaderboard);

        tvPoints.setText("" + points);

        high = save.getIntValue(this, "int");
        if (points > high) {
            save.saveInt(this, "int", points);

            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                String userId = user.getUid();
                Map<String, Object> highScoreData = new HashMap<>();
                highScoreData.put("High Score", points);

                db.collection("users").document(userId).set(highScoreData, SetOptions.merge());
            }
        }

        highScore.setText("" + save.getIntValue(this, "int"));

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.createDialog();
            }
        });

        LeaderBoard.setLayoutManager(new LinearLayoutManager(this));
        LeaderBoard.setAdapter(new LeaderBoardAdapter(new ArrayList<Integer>()));

        List<Integer> highScores = new ArrayList<>();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Long highScoreValue = document.getLong("High Score");
                                if (highScoreValue != null) {
                                    highScores.add(highScoreValue.intValue());
                                }
                            }
                            Log.d("highScores size", "" + highScores.size());
                            LeaderBoard.setAdapter(new LeaderBoardAdapter(highScores));
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                            Toast.makeText(GameOver.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void restart(View view) {
        Intent intent = new Intent(GameOver.this, StartUp.class);
        startActivity(intent);
        finish();
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


    public void exit(View view) {
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

