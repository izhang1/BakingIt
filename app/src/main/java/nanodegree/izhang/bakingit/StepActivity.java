package nanodegree.izhang.bakingit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ivanzhang on 12/5/17.
 *
 *  StepActivity
 *  - Shows the fragment and implements the onclick for the steps
 *  - Is not used when in master detail view
 */
public class StepActivity extends AppCompatActivity implements StepFragment.OnFragmentInteractionListener {

    private final int INVALID_ID = -1;
    private FragmentManager mFragMgr;
    private Fragment mFragment;

    private long recipeId;
    private int stepId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if(savedInstanceState != null){
            recipeId = savedInstanceState.getLong(getString(R.string.param_recipe_id));
            stepId = savedInstanceState.getInt(getString(R.string.param_step_id));

            // Stop the activity if there's no data being passed in
            if(stepId == INVALID_ID || recipeId == INVALID_ID) finish();

            mFragment = getSupportFragmentManager().getFragment(savedInstanceState, "stepFragment");

        }else {
            stepId = getIntent().getIntExtra(getString(R.string.param_step_id), INVALID_ID);
            recipeId = getIntent().getLongExtra(getString(R.string.param_recipe_id), INVALID_ID);

            // Stop the activity if there's no data being passed in
            if(stepId == INVALID_ID || recipeId == INVALID_ID) finish();

            mFragMgr = getSupportFragmentManager();
            mFragment = StepFragment.newInstance(stepId, recipeId);
            mFragMgr.beginTransaction()
                    .replace(R.id.fragment_step, mFragment)
                    .commit();
        }

    }

    @Override
    public void onNextStepClicked(int nextStepId) {
        if(mFragMgr == null) mFragMgr = getSupportFragmentManager();
        stepId++;
        mFragment = StepFragment.newInstance(nextStepId, recipeId);
        mFragMgr.beginTransaction()
                .replace(R.id.fragment_step, mFragment)
                .commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, "stepFragment", mFragment);
        outState.putLong(getString(R.string.param_recipe_id), recipeId);
        outState.putInt(getString(R.string.param_step_id), stepId);
        super.onSaveInstanceState(outState);
    }

}
