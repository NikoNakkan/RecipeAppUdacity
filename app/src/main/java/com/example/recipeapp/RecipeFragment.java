package com.example.recipeapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipeapp.models.Recipe;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class RecipeFragment extends Fragment {
    Gson gson;
    private Parcelable adapterState;
    List<Recipe> list;
    RecipeAdapter recipeAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    OnRecipeClickListener mCallback;
    GridLayoutManager gridLayoutManager;
    private final String ADAPTER_STATE = "AdapterState";
    static String json;

    public RecipeFragment() {

    }

    public interface OnRecipeClickListener {
        void onRecipeClickListener(int position,String json);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        try {
            mCallback = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeClickListener");
        }
    }
    //ON MY RECIPE FRAGMENT,IF THE DEVICE IS A TABLET,I SET A GRIDMANAGER,AS OPPOSED TO THE OTHER CASE WHERE I USE LINERMANAGER

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        gridLayoutManager=new GridLayoutManager(getActivity(),3);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        if(savedInstanceState!=null){
            adapterState=savedInstanceState.getParcelable(ADAPTER_STATE);
            linearLayoutManager.onRestoreInstanceState(adapterState);
        }
        final View rootView = inflater.inflate(R.layout.main_activity_recycler, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recipies);
        gson = new Gson();
        json=getArguments().getString(MainActivity.JSONSTRINGEXTRA);
        list = Arrays.asList(gson.fromJson(json, Recipe[].class));
        if (MainActivity.isTablet) {
            recyclerView.setLayoutManager(gridLayoutManager);
        }
        else {
            recyclerView.setLayoutManager(linearLayoutManager);

        }
        recipeAdapter = new RecipeAdapter(getActivity(), list, new RecipeAdapter.RecipeListClickListener() {
            @Override
            public void OnListItemClick(int clickedItemPosition) {
                mCallback.onRecipeClickListener(clickedItemPosition,json);

            }
        });
        recyclerView.setAdapter(recipeAdapter);

        return rootView;

    }

    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        adapterState = linearLayoutManager.onSaveInstanceState();
        state.putParcelable(ADAPTER_STATE, adapterState);
    }



}
