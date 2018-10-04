package com.example.recipeapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.recipeapp.models.Ingredients;
import com.example.recipeapp.R;

import java.util.List;

public class BakingProvider extends AppWidgetProvider {
    public static List<Ingredients> tlist;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,List<Ingredients> list,
                                int appWidgetId[]) {

        tlist=list;
        if(list!=null) {
            //SORRY FOR THE BIZZARE LAYOUT NAME,WON'T CHANGE ,AS I DONT WANT TO BREAK ANYTHING
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.can_you_please_work_now_widget);
            Intent intent = new Intent(context, ListWidgetService.class);
            views.setRemoteAdapter(R.id.widget_list, intent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        else {
            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.empty_list_widget_view);
            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        BakingService.startActionUpdateWidgets(context);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

