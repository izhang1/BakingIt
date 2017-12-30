package nanodegree.izhang.bakingit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import nanodegree.izhang.bakingit.Model.Recipe;
import nanodegree.izhang.bakingit.Model.Step;

public class StepFragment extends Fragment {
    private static final String ARG_PARAM1 = "stepId";
    private static final String ARG_PARAM2 = "recipeId";
    private static final int INVALID_ID = -1;

    private int stepId;
    private long recipeId;

    private Step mStep;
    private Recipe mRecipe;
    private boolean hasNextStep = false;

    // Used for Exoplayer
    private SimpleExoPlayerView mExoPlayer;
    private SimpleExoPlayer mVidPlayer;

    @BindView(R.id.tv_description) TextView tvDescription;
    @Nullable @BindView(R.id.button_nextstep) Button btnNext;
    @Nullable @BindView(R.id.button_next_land) Button btnNextLand;

    private OnFragmentInteractionListener mListener;

    public StepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StepFragment.
     */
    // TODO: Rename and change types and number of parameters
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
            mStep = mRecipe.getStepList().get(stepId);
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
            if(mStep.getVideoUrl().isEmpty() || mStep.getVideoUrl() == null){
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

    public void initializeVideo(View view){

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

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "exoplayer2example"), null);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri, dataSourceFactory, extractorsFactory, null, null);

        mVidPlayer.prepare(videoSource);

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

        mVidPlayer.setPlayWhenReady(true);
    }

    public void onButtonPressed() {
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
        if(!mStep.getVideoUrl().isEmpty() || mStep.getVideoUrl() != null){
            if(mVidPlayer == null) initializeVideo(getView());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mVidPlayer != null) {
            mVidPlayer.release();
            mVidPlayer = null;
        }
    }
}
