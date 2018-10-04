package com.example.recipeapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class StepDetailActivity extends AppCompatActivity implements StepDetailFragment.OnArrowClickListener {

    static String sjson;
    static int srecipePosition;
    static int sstepPosition;

    //THIS ACTIVITY IS CREATED ONLY ON SMALLER DEVICES AND HOSTS THE STEP DETAIL FRAGMENT
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detail);
        Intent intent =getIntent();
        final String json=intent.getStringExtra(MainActivity.JSONSTRINGEXTRA);
        sjson=json;
        final int  recipePosition= intent.getIntExtra(MainActivity.RECIPEPOSITIONEXTRA,0);
        srecipePosition=recipePosition;
        final int stepPosition=intent.getIntExtra(MainActivity.STEPPOSITIONEXTRA,0);
        sstepPosition=stepPosition;
        if(savedInstanceState==null) {
            StepDetailFragment stepdtFragment = new StepDetailFragment();
            FragmentManager manager = getFragmentManager();

            Bundle bundle = new Bundle();
            bundle.putString(MainActivity.JSONSTRINGEXTRA, json);
            bundle.putInt(MainActivity.RECIPEPOSITIONEXTRA, recipePosition);
            bundle.putInt(MainActivity.STEPPOSITIONEXTRA,stepPosition);
            stepdtFragment.setArguments(bundle);
            manager.beginTransaction().add(R.id.step_detail_fragment_home, stepdtFragment).commit();
        }


    }
    //ARROW NAVIGATION BUTTONS
    @Override
    public void onArrowClickListener(int position) {
        StepDetailFragment stepdtFragment = new StepDetailFragment();
        FragmentManager manager = getFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.JSONSTRINGEXTRA, sjson);
        bundle.putInt(MainActivity.RECIPEPOSITIONEXTRA, srecipePosition);
        bundle.putInt(MainActivity.STEPPOSITIONEXTRA,position);
        stepdtFragment.setArguments(bundle);
        manager.beginTransaction().replace(R.id.step_detail_fragment_home, stepdtFragment).commit();

    }



}
