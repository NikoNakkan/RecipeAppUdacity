package com.example.recipeapp;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.models.Steps;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class StepDetailFragment extends Fragment {
    private SimpleExoPlayer simpleExoPlayer;
    SimpleExoPlayerView simpleExoPlayerView;
    TextView textView;
    Gson gson;
    List<Steps> stepsList;
    Uri videoUri ;
    List<Recipe> recipeList;
    Button rightButton;
    Button leftButton;
    ////ADDED THE THREE STATIC VARIABLES.ON FOR SAVING TIME,READINESS AND THE LAST ONE IS TO SHOW IF THE FRAGMENT CREATION IS FROM A RIGHT-L NAVIGATION ARROW
    static boolean playerReady;
     static long exoPosition;
    static boolean isFromButton;
    OnArrowClickListener mCallback;
    static MediaSource  svideoSource;
    public StepDetailFragment() {

    }
    public interface OnArrowClickListener {
        void onArrowClickListener(int position);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!MainActivity.isTablet) {
            try {
                mCallback = (StepDetailFragment.OnArrowClickListener) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString()
                        + " must implement OnStepClickLnstr");
            }
        }
    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView;
        String json = getArguments().getString(MainActivity.JSONSTRINGEXTRA);
        int recipePosition = getArguments().getInt(MainActivity.RECIPEPOSITIONEXTRA);
        final int stepPosition = getArguments().getInt(MainActivity.STEPPOSITIONEXTRA);
        gson = new Gson();
        recipeList = Arrays.asList(gson.fromJson(json, Recipe[].class));
        stepsList = recipeList.get(recipePosition).getStepList();

        //HERE I COVER THE LAYOUTS FOR EACH AND EVERY CASE
        //HERE IS THE TABLET LAYOUT
        if(MainActivity.isTablet){
            rootView=portraitNormalSettings(inflater,container,stepPosition);
        }
        //HERE IS THE CASE WHERE A SMALLER DEVISE IS ON PORTRAIT
        else if(!isInLandscapeMode(getActivity())) {
            rootView = portraitNormalSettings(inflater, container, stepPosition);
        }
        //HERE THE NAME OF THE METHOD SPEAKS FOR ITSELF
        else if ((!stepsList.get(stepPosition).getVideoUrl().equals("")||!stepsList.get(stepPosition).getThumbnailUrl().equals(""))&&isInLandscapeMode(getActivity())){
            rootView = setLandScapeWhenTheresVideo(inflater, container, stepPosition);
        }
        //SAME HERE
        else {
            rootView = setForLandscapeWhenNoVideo(inflater, container, stepPosition);

        }





        return rootView;
    }

    @NonNull
    private View setLandScapeWhenTheresVideo(LayoutInflater inflater, @Nullable ViewGroup container, int stepPosition) {
        View rootView;
        rootView = inflater.inflate(R.layout.rotation_exo_lay, container, false);
        simpleExoPlayerView=rootView.findViewById(R.id.player_iew_andscape);

        if(!stepsList.get(stepPosition).getVideoUrl().equals("")){
            videoUri= Uri.parse(stepsList.get(stepPosition).getVideoUrl());
        }
        else if(!stepsList.get(stepPosition).getThumbnailUrl().equals("")){
            videoUri=Uri.parse(stepsList.get(stepPosition).getThumbnailUrl());
        }
        return rootView;
    }

    @NonNull
    private View setForLandscapeWhenNoVideo(LayoutInflater inflater, @Nullable ViewGroup container, final int stepPosition) {
        View rootView;
        rootView = inflater.inflate(R.layout.detail_step_fragment, container, false);
        textView = rootView.findViewById(R.id.Detailed_Descr);
        leftButton=rootView.findViewById(R.id.leftarrow);
        simpleExoPlayerView = rootView.findViewById(R.id.playerView);
        simpleExoPlayerView.setVisibility(View.INVISIBLE);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SHOW THAT CREATION IS FROM A BUTTON CALL ETC ALL OTHER SAME CALLS
                isFromButton=true;
                mCallback.onArrowClickListener(stepPosition-1);
            }
        });
        rightButton=rootView.findViewById(R.id.rightarrow);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFromButton=true;
                mCallback.onArrowClickListener(stepPosition+1);
            }
        });
        if(stepPosition+1==stepsList.size())
            rightButton.setVisibility(View.INVISIBLE);
        if(stepPosition==0)
            leftButton.setVisibility(View.INVISIBLE);
        textView.setText(stepsList.get(stepPosition).getDescription());
        return rootView;
    }

    @NonNull
    private View portraitNormalSettings(LayoutInflater inflater, @Nullable ViewGroup container, final int stepPosition) {
        View rootView;

        rootView = inflater.inflate(R.layout.detail_step_fragment, container, false);
        textView = rootView.findViewById(R.id.Detailed_Descr);
        leftButton=rootView.findViewById(R.id.leftarrow);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFromButton=true;
                mCallback.onArrowClickListener(stepPosition-1);
            }
        });
        rightButton=rootView.findViewById(R.id.rightarrow);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFromButton=true;
                mCallback.onArrowClickListener(stepPosition+1);
            }
        });
        if(stepPosition+1==stepsList.size())
            rightButton.setVisibility(View.INVISIBLE);
        if(stepPosition==0)
            leftButton.setVisibility(View.INVISIBLE);

        simpleExoPlayerView = rootView.findViewById(R.id.playerView);


        if(!stepsList.get(stepPosition).getVideoUrl().equals("")){
            videoUri= Uri.parse(stepsList.get(stepPosition).getVideoUrl());
        }
        else if(!stepsList.get(stepPosition).getThumbnailUrl().equals("")){
            videoUri=Uri.parse(stepsList.get(stepPosition).getThumbnailUrl());
        }
        else {
            simpleExoPlayerView.setVisibility(View.INVISIBLE);
        }


        textView.setText(stepsList.get(stepPosition).getDescription());
        if(MainActivity.isTablet){

            rightButton.setVisibility(View.INVISIBLE);
            leftButton.setVisibility(View.INVISIBLE);
        }
        return rootView;
    }


    public boolean isInLandscapeMode( Context context ) {
        return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    //I ACTUALLY USED SOME CODE FROM THIS TUTORIAL HERE https://cloudinary.com/blog/exoplayer_android_tutorial_easy_video_delivery_and_editing
    private void initializePlayer(){
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

        simpleExoPlayerView.setPlayer(simpleExoPlayer);

        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "exoplayer"));

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource videoSource = new ExtractorMediaSource(videoUri,
                dataSourceFactory, extractorsFactory, null, null);

        svideoSource=videoSource;

        simpleExoPlayer.prepare(videoSource);

    }






    @Override
    public void onDetach() {
        super.onDetach();
        if (simpleExoPlayer!=null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }
//ADDED
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
            //IF  ONSTARTS FOLLOWS A NAVIGATION BUTTON FRAG CREATION,JUST SET IT TO FALSE,AND PUT IN EXO NORMAL STARTING
           if(isFromButton) {
               isFromButton=false;
           }
           else {
               simpleExoPlayer.seekTo(exoPosition);
               simpleExoPlayer.setPlayWhenReady(playerReady);
           }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || simpleExoPlayer == null)) {
            initializePlayer();
            if(isFromButton) {
                isFromButton=false;
            }
            else {
                simpleExoPlayer.seekTo(exoPosition);
                simpleExoPlayer.setPlayWhenReady(playerReady);
            }
            }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (simpleExoPlayer!=null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer=null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (simpleExoPlayer!=null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
           simpleExoPlayer=null; //ADDED
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer!=null) {
            exoPosition=simpleExoPlayer.getCurrentPosition(); //added get the current exoPosit and put it in a static long
            playerReady=simpleExoPlayer.getPlayWhenReady(); //added as well
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer=null;  //ADDED
        }
    }
}