package com.example.recipeapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.models.Steps;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class StepFragment extends Fragment {
    StepAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    static int position;
    static String json;
    Gson gson;
    List<Steps> stepsList;
    List<Recipe> recList;
    OnStepClickListener mCallback;
    public StepFragment(){

    }
    public interface OnStepClickListener {
        void onStepClickListener(int position,String json);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        try {
            mCallback = (StepFragment.OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickLnstr");
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.step_recycler, container, false);
        //HERE IS THE STEP LIST FRAGMENT
        recyclerView=rootView.findViewById(R.id.rv_steps);
        json=getArguments().getString(MainActivity.JSONSTRINGEXTRA);
        position=getArguments().getInt(MainActivity.POSITIONEXTRA);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        gson=new Gson();
        recList = Arrays.asList(gson.fromJson(json,Recipe[].class));
        stepsList=recList.get(position).getStepList();
        getActivity().setTitle(recList.get(position).getName());


        adapter = new StepAdapter(getActivity(), stepsList, new StepAdapter.StepClickListener() {
            @Override
            public void OnListItemClick(int clickedItemPosition) {
                mCallback.onStepClickListener(clickedItemPosition,json);

            }
        });
        recyclerView.setAdapter(adapter);
        return rootView;
        }

}
