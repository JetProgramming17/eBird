package com.example.ebird;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ebird.Models.NearbyVariables;

import java.util.List;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NearbyAdapter adapter;
    private String regionCode = "ZA";
    Button liked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        liked = findViewById(R.id.liked_btn);

        liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.ebird.org/v2/data/obs/" + regionCode + "/recent/notable/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        EBirdJson jsonPlaceholder = retrofit.create(EBirdJson.class);

        Call<List<NearbyVariables>> call = jsonPlaceholder.getNotableObservations();
        call.enqueue(new Callback<List<NearbyVariables>>() {
            @Override
            public void onResponse(Call<List<NearbyVariables>> call, Response<List<NearbyVariables>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                List<NearbyVariables> ebirdsList = response.body();
                adapter = new NearbyAdapter(getBaseContext(), ebirdsList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<NearbyVariables>> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

}