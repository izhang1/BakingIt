package nanodegree.izhang.bakingit;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import nanodegree.izhang.bakingit.Model.Recipe;

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

        mRecipeId = getIntent().getLongExtra(getString(R.string.passed_recipe_id), -1);

        // TODO: Put in the if statement to check for dual screen

        // Added this in so that fragments won't overlap when rotating from landscape to portrait mode
        if (savedInstanceState != null) {
            return;
        }

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

    @Override
    public void onStepItemClick(int stepId) {
        if(tabletView != null){
            StepFragment stepFragment = StepFragment.newInstance(stepId, mRecipeId);
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

    @Override
    public void onNextStepClicked(int nextStepId) {
        StepFragment stepFragment = StepFragment.newInstance(nextStepId, mRecipeId);
        mFragMgr.beginTransaction()
                .replace(R.id.step_fragment, stepFragment)
                .commit();
    }
}
