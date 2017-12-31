package nanodegree.izhang.bakingit;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements RecipeFragment.OnFragmentInteractionListener, StepFragment.OnFragmentInteractionListener{

    @Nullable @BindView(R.id.tablet_layout) View tabletView;
    private static int INITIAL_STEP_ID = 0;
    private long mRecipeId;

    private FragmentManager mFragMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // Gets the data passed in by the intent
        mRecipeId = getIntent().getLongExtra(getString(R.string.passed_recipe_id), -1);

        // Added this in so that fragments won't overlap when rotating from landscape to portrait mode
        if (savedInstanceState != null) {
            return;
        }

        // Checks if the table layout is there. Populates the view accordingly.
        if(tabletView == null){
            mFragMgr = getSupportFragmentManager();
            RecipeFragment recipeFragment = RecipeFragment.newInstance(mRecipeId);
            mFragMgr.beginTransaction()
                    .replace(R.id.recipe_fragment, recipeFragment)
                    .commit();
        }else{
            mFragMgr = getSupportFragmentManager();
            RecipeFragment recipeFragment = RecipeFragment.newInstance(mRecipeId);
            mFragMgr.beginTransaction()
                    .replace(R.id.recipe_fragment, recipeFragment)
                    .commit();

            StepFragment stepFragment = StepFragment.newInstance(INITIAL_STEP_ID, mRecipeId);
            mFragMgr.beginTransaction()
                    .replace(R.id.step_fragment, stepFragment)
                    .commit();
        }


    }

    // Overrides the recipe adapter when a user clicks on a step
    @Override
    public void onStepItemClick(int stepId) {
        if(tabletView != null){
            StepFragment stepFragment = StepFragment.newInstance(stepId, mRecipeId);
            if(mFragMgr == null) mFragMgr = getSupportFragmentManager();
            mFragMgr.beginTransaction()
                    .replace(R.id.step_fragment, stepFragment)
                    .commit();
        }else{
            Intent intent = new Intent(this, StepActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(this.getString(R.string.param_step_id), stepId);
            bundle.putLong(this.getString(R.string.param_recipe_id), mRecipeId);
            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

    // Overrides the next step button on the step fragment
    @Override
    public void onNextStepClicked(int nextStepId) {
        if(mFragMgr == null) mFragMgr = getSupportFragmentManager();
        StepFragment stepFragment = StepFragment.newInstance(nextStepId, mRecipeId);
        mFragMgr.beginTransaction()
                .replace(R.id.step_fragment, stepFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
