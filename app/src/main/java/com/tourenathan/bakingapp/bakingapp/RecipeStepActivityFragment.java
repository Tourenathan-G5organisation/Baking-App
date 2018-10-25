package com.tourenathan.bakingapp.bakingapp;

import android.net.Uri;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.tourenathan.bakingapp.bakingapp.model.Step;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeStepActivityFragment extends Fragment implements Player.EventListener {

    public static final String TAG = RecipeStepActivityFragment.class.getSimpleName();
    final String CONTENT_POSITION = "content_position";

    Step mStep;
    TextView mDescription;
    PlayerView mPlayerView;
    SimpleExoPlayer mEXoplayer;
    long contentPosition;
    boolean isLanscape;

    public RecipeStepActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        mDescription = rootView.findViewById(R.id.step_description_Textview);
        mPlayerView = rootView.findViewById(R.id.playerView);
        if (savedInstanceState != null) {
            contentPosition = savedInstanceState.getLong(CONTENT_POSITION);
        } else {
            contentPosition = 0;
        }
        initialiseData();
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
        if (mStep != null && mDescription != null) {
            mDescription.setText(mStep.getDescription());
        }
    }

    public void initPlayer() {
        if (mEXoplayer == null && !(mStep.getVideoURL().isEmpty())) {
            mPlayerView.setVisibility(View.VISIBLE);
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
            mEXoplayer.prepare(mediaSource);
            mEXoplayer.seekTo(contentPosition);
            mEXoplayer.setPlayWhenReady(true);

        } else {
            // hide the video view
            mPlayerView.setVisibility(View.GONE);
        }
        Log.d(TAG, "content:" + mStep.getVideoURL());

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
        if (mEXoplayer != null)
            contentPosition = mEXoplayer.getContentPosition();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initPlayer();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playWhenReady && playbackState == Player.STATE_READY) {
            Log.d(TAG, "Player is plaing");
        } else if (playbackState == Player.STATE_READY) {
            Log.d(TAG, "Player is paused");
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(CONTENT_POSITION, contentPosition);
        super.onSaveInstanceState(outState);
    }
}
