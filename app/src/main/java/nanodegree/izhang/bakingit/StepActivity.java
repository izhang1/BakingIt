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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        int stepId = getIntent().getIntExtra("StepId", INVALID_ID);
        long recipeId = getIntent().getLongExtra("RecipeId", INVALID_ID);

        FragmentManager fragmentManager = getSupportFragmentManager();
        StepFragment recipeFragment = StepFragment.newInstance(stepId, recipeId);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_step, recipeFragment)
                .commit();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
