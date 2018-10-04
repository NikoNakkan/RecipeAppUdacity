package com.example.recipeapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recipeapp.models.Steps;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder>{
    private List<Steps> stepList;
    final private StepClickListener stepClickListener;



    public StepAdapter(Context context, List<Steps> stepList, StepClickListener stepClickListener) {
        this.stepList = stepList;
        this.stepClickListener=stepClickListener;


    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.step_list_item,viewGroup,false );
        StepViewHolder viewHolder = new StepViewHolder(view);
        return viewHolder;
    }


    public interface StepClickListener{
        void OnListItemClick(int clickedItemPosition);

    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {

        Steps steps=stepList.get(position);
        holder.adTextView.setText(steps.getShortDescription());

    }

    @Override
    public int getItemCount() {

        return stepList.size();

    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         TextView adTextView;
        public StepViewHolder(View view){
            super(view);
            adTextView=view.findViewById(R.id.tv_stepList);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition=getAdapterPosition();
            stepClickListener.OnListItemClick(clickedPosition);
        }
    }
}

