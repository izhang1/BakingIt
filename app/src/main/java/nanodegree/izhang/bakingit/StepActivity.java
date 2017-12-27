package nanodegree.izhang.bakingit;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;
import nanodegree.izhang.bakingit.Model.Recipe;
import nanodegree.izhang.bakingit.Model.Step;

public class StepActivity extends AppCompatActivity implements StepFragment.OnFragmentInteractionListener {

    int INVALID_ID = -1;
    private FragmentManager mFragMgr;

    private long recipeId;
    private int stepId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        // Gets the passed in data
        stepId = getIntent().getIntExtra(getString(R.string.param_step_id), INVALID_ID);
        recipeId = getIntent().getLongExtra(getString(R.string.param_recipe_id), INVALID_ID);

        // Stop the activity if there's no data being passed in
        if(stepId == INVALID_ID || recipeId == INVALID_ID) finish();

        mFragMgr = getSupportFragmentManager();
        StepFragment recipeFragment = StepFragment.newInstance(stepId, recipeId);
        mFragMgr.beginTransaction()
                .replace(R.id.fragment_step, recipeFragment)
                .commit();
    }

    @Override
    public void onNextStepClicked(int nextStepId) {
        if(mFragMgr == null) mFragMgr = getSupportFragmentManager();
        StepFragment recipeFragment = StepFragment.newInstance(nextStepId, recipeId);
        mFragMgr.beginTransaction()
                .replace(R.id.fragment_step, recipeFragment)
                .commit();

    }
}
