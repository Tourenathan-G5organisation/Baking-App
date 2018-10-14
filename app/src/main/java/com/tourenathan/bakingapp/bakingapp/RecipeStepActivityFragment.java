package com.tourenathan.bakingapp.bakingapp;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.tourenathan.bakingapp.bakingapp.model.Step;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeStepActivityFragment extends Fragment {

    public static final String TAG = RecipeStepActivityFragment.class.getSimpleName();

    Step mStep;
    TextView mDescription;
    PlayerView mPlayerView;
    SimpleExoPlayer mEXoplayer;
    long contentPosition;

    public RecipeStepActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        mDescription = rootView.findViewById(R.id.step_description_Textview);
        mPlayerView = rootView.findViewById(R.id.playerView);
        contentPosition = 0;
        return rootView;
    }

    /**
     * Set the recipe step to display in fragment
     *
     * @param recipeStep Step to display
     */

    public void setStep(Step recipeStep) {
        mStep = recipeStep;
    }

    public void initialiseData() {
        if (mStep != null) {
            mDescription.setText(mStep.getDescription());
        }
    }

    public void initPlayer() {
        if (mEXoplayer == null) {
            // Create an instance of exoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mEXoplayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            // Bind the player to the view.
            mPlayerView.setPlayer(mEXoplayer);
            // Prepare the media source
            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(getContext(), userAgent);
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mStep.getVideoURL()));
            mEXoplayer.seekTo(contentPosition);
            mEXoplayer.prepare(mediaSource);
            mEXoplayer.setPlayWhenReady(true);

        }

    }

    void releasePlayer() {
        if (mEXoplayer != null) {
            mEXoplayer.stop();
            mEXoplayer.release();
            mEXoplayer = null;
        }
    }


    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        contentPosition = mEXoplayer.getContentPosition();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initPlayer();
    }
}
