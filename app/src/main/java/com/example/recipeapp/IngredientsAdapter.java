package com.example.recipeapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recipeapp.models.Ingredients;

import java.util.List;


public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>{
    private List<Ingredients> ingrediList;
    final private IngredientsClickListener ingredientListClickListener;



    public IngredientsAdapter(Context context, List<Ingredients> ingrediList, IngredientsClickListener incredientListClickListener) {
        this.ingrediList = ingrediList;
        this.ingredientListClickListener=incredientListClickListener;


    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.incredient_list_item,viewGroup,false );
        IngredientsViewHolder viewHolder = new IngredientsViewHolder(view);
        return viewHolder;
    }


    public interface IngredientsClickListener{
        void OnListItemClick(int clickedItemPosition);

    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {

        Ingredients ingredients=ingrediList.get(position);
        holder.tv.setText(ingredients.getIngredient()+" : "+ingredients.getQuantity()+" "+ingredients.getMeasure());

    }

    @Override
    public int getItemCount() {

        return ingrediList.size();

    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView tv;
        public IngredientsViewHolder(View view){
            super(view);
             tv=view.findViewById(R.id.tv_incredient_list_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition=getAdapterPosition();
            ingredientListClickListener.OnListItemClick(clickedPosition);
        }
    }
}