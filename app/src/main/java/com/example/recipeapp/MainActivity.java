package com.example.recipeapp;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipeapp.models.Recipe;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeFragment.OnRecipeClickListener {
    Gson gson;
    List<Recipe> list;
    String json;
    static final String JSONSTRINGEXTRA="json";
    static final String POSITIONEXTRA="position";
    static final String RECIPEPOSITIONEXTRA="recipePosition";
    static final String STEPPOSITIONEXTRA="stepPosition";
    public static boolean isTablet;
    private static final String ENDPOINT = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private RequestQueue requestQueue;
    @Nullable
    private SimpleIdlingResource mIdlingResource;
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
    @VisibleForTesting
    @NonNull
    public void unregisterIdlingResource()
    {
        if (mIdlingResource != null)
        {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //IF THERE'S CONNECTION GET THE RECIPE LIST,ELSE JUST POP A TOAST
        if(connectioCheck()) {
            requestQueue = Volley.newRequestQueue(this);
            getIdlingResource();
            if (mIdlingResource != null) {
                mIdlingResource.setIdleState(false);
            }
            fetchPosts();

            setContentView(R.layout.n_activity_main);
            if(findViewById(R.id.tab_linear_identifier)==null){
                isTablet=false;
            }
            else isTablet=true;
        }
        else {
            Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT).show();
        }

    }
    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);
        requestQueue.add(request);

    }


    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            json=response;


            Bundle bundle = new Bundle();
            bundle.putString(JSONSTRINGEXTRA, json);
                RecipeFragment recipeFragment = new RecipeFragment();
            recipeFragment.setArguments(bundle);
            FragmentManager manager = getFragmentManager();
                manager.beginTransaction().add(R.id.main_fraime, recipeFragment).commit();
            unregisterIdlingResource();
            if (mIdlingResource != null) {
                mIdlingResource.setIdleState(true);
            }

        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
        }
    };



    @Override
    public void onRecipeClickListener(int position,String json) {
        gson=new Gson();
        Intent intent=new Intent(this,  RecipeDetailActivity.class);

        intent.putExtra(MainActivity.JSONSTRINGEXTRA,json);
        intent.putExtra(POSITIONEXTRA,position);
        startActivity(intent);

    }
    public boolean connectioCheck(){
    ConnectivityManager cm =
            (ConnectivityManager)MainActivity.this
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null &&
            activeNetwork.isConnectedOrConnecting();
    return isConnected;
}
}
