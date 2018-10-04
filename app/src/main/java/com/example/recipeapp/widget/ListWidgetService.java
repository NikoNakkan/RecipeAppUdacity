
package com.example.recipeapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.recipeapp.models.Ingredients;
import com.example.recipeapp.R;

import java.util.List;

public class ListWidgetService extends RemoteViewsService{


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return (new ListRemoteViewsFactory(this.getApplicationContext(),intent) {
        });
    }

}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mcontext;
    public  List<Ingredients> list;
    public ListRemoteViewsFactory(Context context,Intent intent ){
        mcontext=context;

    }



    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        list= BakingProvider.tlist;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views=new RemoteViews(mcontext.getPackageName(), R.layout.widget_list_item);
        views.setTextViewText(R.id.widget_tv_ingredient_list_item,list.get(i).getIngredient()+" : "+list.get(i).getQuantity()+" "+list.get(i).getMeasure());



        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
