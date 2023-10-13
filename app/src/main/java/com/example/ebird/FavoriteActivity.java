package com.example.ebird;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ebird.Models.NearbyVariables;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class FavoriteActivity extends AppCompatActivity {
RecyclerView recyclerView2;
BirdAdapter birdAdapter;
Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView2 = findViewById(R.id.recyclerView2);
        back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoriteActivity.this, MainActivity.class));
            }
        });

        setUpRecyclerView();
    }
    void setUpRecyclerView() {
        Query query = Utility.getCollectionReferenceForData().orderBy("sciName",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<NearbyVariables> options = new FirestoreRecyclerOptions.Builder<NearbyVariables>()
                .setQuery(query, NearbyVariables.class).build();
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        birdAdapter = new BirdAdapter(options, this);
        recyclerView2.setAdapter(birdAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        birdAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        birdAdapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        birdAdapter.notifyDataSetChanged();
    }
}