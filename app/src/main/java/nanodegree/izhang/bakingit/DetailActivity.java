package nanodegree.izhang.bakingit;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.realm.Realm;
import nanodegree.izhang.bakingit.Model.Recipe;

public class DetailActivity extends AppCompatActivity implements RecipeFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        long recipeId = getIntent().getLongExtra(getString(R.string.passed_recipe_id), -1);

        // TODO: Put in the if statement to check for dual screen

        // Added this in so that fragments won't overlap when rotating from landscape to portrait mode
        if (savedInstanceState != null) {
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeFragment recipeFragment = RecipeFragment.newInstance(recipeId);
        fragmentManager.beginTransaction()
                .add(R.id.recipe_fragment, recipeFragment)
                .commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
