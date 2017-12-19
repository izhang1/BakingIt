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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import nanodegree.izhang.bakingit.Model.Recipe;
import nanodegree.izhang.bakingit.Model.Step;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "stepId";
    private static final String ARG_PARAM2 = "recipeId";
    private static final int INVALID_ID = -1;

    // TODO: Rename and change types of parameters
    private int stepId;
    private long recipeId;

    private Step mStep;
    private Recipe mRecipe;
    private boolean hasNextStep = false;

    @BindView(R.id.tv_description) TextView tvDescription;
    @BindView(R.id.button_nextstep) Button btnNext;

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

        // Check if another value is available. If size is less than the current step
        if(mRecipe.getStepList().size() > (stepId + 1)){
            hasNextStep = true;
            btnNext.setVisibility(View.VISIBLE);
            btnNext.setOnClickListener(this);
        }

        // Set title to short description name
        getActivity().setTitle(mStep.getShortDescription());

        // Set the description text
        tvDescription.setText(mStep.getDescription());

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    }

    @Override
    public void onClick(View v) {
        // Tell Activity to show next fragment
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
