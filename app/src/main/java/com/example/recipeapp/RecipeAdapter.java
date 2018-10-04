package com.example.recipeapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recipeapp.models.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipeList;
    final private RecipeListClickListener recipeListClickListener;



    public RecipeAdapter(Context context, List<Recipe> recipeList, RecipeListClickListener recipeListClickListener) {
        this.recipeList = recipeList;
        this.recipeListClickListener=recipeListClickListener;


    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_list_item,viewGroup,false );
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);
        return viewHolder;
    }


    public interface RecipeListClickListener{
        void OnListItemClick(int clickedItemPosition);

    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

        Recipe recipe=recipeList.get(position);
        holder.textView.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {

        return recipeList.size();

    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView textView;
        public RecipeViewHolder(View view){
            super(view);
            textView=(TextView) view.findViewById(R.id.text_view_list_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition=getAdapterPosition();
            recipeListClickListener.OnListItemClick(clickedPosition);
        }

}
}
