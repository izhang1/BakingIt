package nanodegree.izhang.bakingit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;


import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import nanodegree.izhang.bakingit.Model.Recipe;
import nanodegree.izhang.bakingit.Model.Step;

/**
 * Created by ivanzhang on 11/5/17.
 *
 *  StepFragment
 *  - Shows the steps UI with ExoPlayer, Next Button and Description
 *  - Shows only the ExoPlayer in Horizontal view
 */
public class StepFragment extends Fragment {
    private static final String ARG_PARAM1 = "stepId";
    private static final String ARG_PARAM2 = "recipeId";
    private static final int INVALID_ID = -1;

    private int stepId;
    private long recipeId;

    private Step mStep;
    private Recipe mRecipe;
    private boolean hasNextStep = false;
    private long savedVideoPosition = 0;

    // Used for Exoplayer
    private SimpleExoPlayerView mExoPlayer;
    private SimpleExoPlayer mVidPlayer;

    @BindView(R.id.tv_description) TextView tvDescription;
    @Nullable @BindView(R.id.button_nextstep) Button btnNext;
    @Nullable @BindView(R.id.button_next_land) Button btnNextLand;
    @Nullable @BindView(R.id.thumbnail_view) ImageView thumbnailView;

    private OnFragmentInteractionListener mListener;

    public StepFragment() {
        // Required empty public constructor
    }

    public static StepFragment newInstance(int stepId, long recipeId) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, stepId);
        args.putLong(ARG_PARAM2, recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            stepId = getArguments().getInt(ARG_PARAM1);
            recipeId = getArguments().getLong(ARG_PARAM2);
        }

        if (savedInstanceState != null) {
            savedVideoPosition = savedInstanceState.getLong(getString(R.string.param_video_state));
            Log.v("StepFragment", "On Create Position saved: " + savedVideoPosition);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);

        // Setup views here
        if(stepId != INVALID_ID && recipeId != INVALID_ID){
            Realm realm = Realm.getDefaultInstance();
            mRecipe = realm.where(Recipe.class).equalTo(getContext().getString(R.string.id), recipeId).findFirst();
            mStep = mRecipe != null ? mRecipe.getStepList().get(stepId) : null;
        }

        // Set title to short description name
        getActivity().setTitle(mStep.getShortDescription());

        // Null means that the layout is landscape. Not null means the layout is portrait.
        if(btnNext != null){
            // Check if another value is available. If size is less than the current step
            if(mRecipe.getStepList().size() > (stepId + 1)){
                hasNextStep = true;
                btnNext.setVisibility(View.VISIBLE);
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonPressed();
                    }
                });
            }

            // Check if ExoPlayer is available and hide if not
            if( TextUtils.isEmpty(mStep.getVideoUrl()) || mStep.getVideoUrl() == null){
                SimpleExoPlayerView exoPlayer = (SimpleExoPlayerView) view.findViewById(R.id.exoplayer_video);
                exoPlayer.setVisibility(View.GONE);
            }else{
                // Setup ExoPlayer if it has not already been setup
                if(mVidPlayer == null) initializeVideo(view);
            }

            // Set the description text
            tvDescription.setText(mStep.getDescription());

        }else{
            // Check if ExoPlayer is available and hide if not
            if(mStep.getVideoUrl().isEmpty() || mStep.getVideoUrl() == null){
                SimpleExoPlayerView exoPlayer = (SimpleExoPlayerView) view.findViewById(R.id.exoplayer_video);
                exoPlayer.setVisibility(View.GONE);
                assert btnNextLand != null;
                btnNextLand.setVisibility(View.VISIBLE);
                tvDescription.setText(mStep.getDescription());
                btnNextLand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonPressed();
                    }
                });
            }else{
                // Setup ExoPlayer
                if(mVidPlayer == null) initializeVideo(view);
            }
        }

        return view;
    }

    // Initializes the video components for media playback
    private void initializeVideo(View view){

        // show the thumbnail
        showThumbNail();

        // 1. Create a default TrackSelector
        TrackSelector selector = new DefaultTrackSelector();

        // 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();
        mVidPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), selector, loadControl);
        mExoPlayer = new SimpleExoPlayerView(getContext());
        mExoPlayer = (SimpleExoPlayerView) view.findViewById(R.id.exoplayer_video);

        // Set media controller and put it in focus
        mExoPlayer.setUseController(true);
        mExoPlayer.requestFocus();

        // Bind the player to the view.
        mExoPlayer.setPlayer(mVidPlayer);

        // Step video
        Uri mp4VideoUri =Uri.parse(mStep.getVideoUrl());

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "BakingIt"), null);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri, dataSourceFactory, extractorsFactory, null, null);

        mVidPlayer.prepare(videoSource);

        mVidPlayer.seekTo(savedVideoPosition);
        Log.v("StepFragment", "Video Player seek to: " + savedVideoPosition);


        mVidPlayer.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            }


            @Override
            public void onPlayerError(ExoPlaybackException error) {
                mVidPlayer.stop();
                mVidPlayer.setPlayWhenReady(true);
            }

            @Override
            public void onPositionDiscontinuity() {
            }


        });

        // Video is ready. Hide thumbnail
        hideThumbNail();

        mVidPlayer.setPlayWhenReady(true);
    }

    private void showThumbNail(){
        if(!TextUtils.isEmpty(mStep.getThumbnailUrl())){
            Picasso.with(getContext())
                    .load(mStep.getThumbnailUrl())
                    .into(thumbnailView);
        }
    }

    private void hideThumbNail(){
        if(!TextUtils.isEmpty(mStep.getThumbnailUrl())){
            thumbnailView.setVisibility(View.GONE);
        }
    }

    private void onButtonPressed() {
        if (mListener != null) {
            mListener.onNextStepClicked((stepId + 1));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if(mVidPlayer != null){
            mVidPlayer.release();
            mVidPlayer = null;
        }
    }

    public interface OnFragmentInteractionListener {
        void onNextStepClicked(int nextStepId);
    }

    /** Lifecycle **/
    /*************************************************************************************************/
    // onResume init the video if there is a video URL
    @Override
    public void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(mStep.getVideoUrl()) || mStep.getVideoUrl() != null){
            if(mVidPlayer == null) initializeVideo(getView());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mVidPlayer != null) {
            savedVideoPosition = mVidPlayer.getCurrentPosition();
            mVidPlayer.release();
            mVidPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putLong(getString(R.string.param_video_state), savedVideoPosition);
        Log.v("StepFragment", "Poisition saved: " + savedVideoPosition);
    }
}
