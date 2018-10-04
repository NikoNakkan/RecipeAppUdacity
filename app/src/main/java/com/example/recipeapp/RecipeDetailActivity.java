package com.example.recipeapp;

import android.app.Fragment;
import android.content.Intent;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
public class RecipeDetailActivity extends AppCompatActivity  implements StepFragment.OnStepClickListener {
    static int recipePosition;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //IN THIS ACTIVITY,YOU I WILL GET THE STUFF FROM THE BASIC RECIPE LIST,AND HAVE A TO INGREDIENTACTIVITY BUTTON FIRST,AND THEN THE STEP LIST

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Intent intent =getIntent();
        final String json=intent.getStringExtra(MainActivity.JSONSTRINGEXTRA);
        final int  recipeposition= intent.getIntExtra(MainActivity.POSITIONEXTRA,0);
        recipePosition=recipeposition;
        tv=(TextView)findViewById(R.id.tv_step_to_ingredient);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent ingredIntent=new Intent(RecipeDetailActivity.this,IngredientsActivity.class);
                ingredIntent.putExtra(MainActivity.JSONSTRINGEXTRA,json);
                ingredIntent.putExtra(MainActivity.POSITIONEXTRA,recipeposition);
                startActivity(ingredIntent);
            }
        });
        if(savedInstanceState==null) {
            StepFragment stepFragment = new StepFragment();
            FragmentManager manager = getFragmentManager();

            Bundle bundle = new Bundle();
            bundle.putString(MainActivity.JSONSTRINGEXTRA, json);
            bundle.putInt(MainActivity.POSITIONEXTRA, recipeposition);
            stepFragment.setArguments(bundle);
            manager.beginTransaction().add(R.id.step_fragment, stepFragment).commit();
        }

    }
//FOLLOWING I SPECIFY WHETHER I WILL START A NEW AACTIVITY(SMALLER DEVICES)
    //OR REPLACE THE FRAGMENT,IN TABLETS
    @Override
    public void onStepClickListener(int position, String json) {
        if (!MainActivity.isTablet) {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(MainActivity.JSONSTRINGEXTRA, json);
            intent.putExtra(MainActivity.RECIPEPOSITIONEXTRA, recipePosition);
            intent.putExtra(MainActivity.STEPPOSITIONEXTRA, position);
            startActivity(intent);
        }
        else{
            StepDetailFragment stepFragment = new StepDetailFragment();
            FragmentManager manager = getFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString(MainActivity.JSONSTRINGEXTRA, json);
            bundle.putInt(MainActivity.RECIPEPOSITIONEXTRA, recipePosition);
            bundle.putInt(MainActivity.STEPPOSITIONEXTRA,position);
            stepFragment.setArguments(bundle);
            manager.beginTransaction().replace(R.id.step_detail_fragment_home, stepFragment).commit();
            StepDetailFragment.isFromButton=true; //ADDED TO HANFLE THE SAME ACTION AS THE ARROW BUTTONS,DETAILS ON SDFRAGMENT
        }

    }
}
