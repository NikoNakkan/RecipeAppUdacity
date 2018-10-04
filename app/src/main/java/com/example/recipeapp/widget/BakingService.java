package com.example.recipeapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.recipeapp.models.Ingredients;
import com.example.recipeapp.R;
import com.example.recipeapp.models.Recipe;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class BakingService extends IntentService {
    List<Recipe> recipeList;
    List<Ingredients>ingredientsList;
    public static final String ACTION_UPDATE_WIDGET="update_widget";

    public BakingService(String name) {
        super(name);
    }

    public BakingService(){
        super("Baking Service");

    }
    public static void startActionUpdateWidgets(Context context) {
        Intent intent = new Intent(context, BakingService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);

    }
    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {

            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGET.equals(action)) {
                //THATS THE MAIN ACTIVITY JSONSTRINGEXTRA
                //IF THE INTENT HAS A STRING EXTRA,THEN DO THE NORMAL WIDGET IMPLEMENTATION,IF NOT..
                if (intent.getStringExtra("json") != null)
                {

                    String json = intent.getStringExtra("json");

                    //AND THATS THE POSITIONSTRINGEXTRA
                    int position = intent.getIntExtra("position", 0);
                    Gson gson = new Gson();
                    recipeList = Arrays.asList(gson.fromJson(json, Recipe[].class));
                    ingredientsList = recipeList.get(position).getIngrediList();
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingProvider.class));
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
                    BakingProvider.updateAppWidget(this, appWidgetManager, ingredientsList, appWidgetIds);
                }
                //POPULATE WIDGET WITH A CUSTOM XML LAYOUT
                else{
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingProvider.class));
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
                    BakingProvider.updateAppWidget(this, appWidgetManager, ingredientsList, appWidgetIds);                }
            }
        }
    }

}