package com.example.recipeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.recipeapp.models.Ingredients;
import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.widget.BakingService;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class IngredientsActivity extends AppCompatActivity {
    IngredientsAdapter adapter;
    RecyclerView recyclerView;
    Gson gson;
    List<Recipe> recipeList;
    LinearLayoutManager linearLayoutManager;
    List<Ingredients> ingredientsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incredient_list);
        Intent intent=getIntent();
        String json=intent.getStringExtra(MainActivity.JSONSTRINGEXTRA);
        final int position=intent.getIntExtra(MainActivity.POSITIONEXTRA,0);
        gson=new Gson();
        recipeList = Arrays.asList(gson.fromJson(json,Recipe[].class));
       ingredientsList= recipeList.get(position).getIngrediList();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView=findViewById(R.id.rv_incredients);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new IngredientsAdapter(this, ingredientsList, new IngredientsAdapter.IngredientsClickListener() {
            @Override
            public void OnListItemClick(int clickedItemPosition) {
            }
        });
        recyclerView.setAdapter(adapter);
        //When you pick this list,update the widget
        Intent wIntent=new Intent(this,BakingService.class);
        wIntent.putExtra(MainActivity.JSONSTRINGEXTRA,json);
        wIntent.putExtra(MainActivity.POSITIONEXTRA,position);
        wIntent.setAction(BakingService.ACTION_UPDATE_WIDGET);
        startService(wIntent);
    }
}
