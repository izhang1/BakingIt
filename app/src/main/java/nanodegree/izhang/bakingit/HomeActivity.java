package nanodegree.izhang.bakingit;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import nanodegree.izhang.bakingit.Model.Recipe;
import nanodegree.izhang.bakingit.Util.JsonUtil;
import nanodegree.izhang.bakingit.Util.NetworkUtils;

public class HomeActivity extends AppCompatActivity {

    ArrayList<Recipe> mData;
    RecyclerView mRecipeRV;
    RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Init Realm
        Realm.init(this);

        // Get recipe from Realm if available
        // Save data to realm database
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Recipe> recipes = realm.where(Recipe.class).findAll();

        if(recipes.size() < 0) // Has no value from database
        {
            // Testing network and http request
            GetRecipeTask task = new GetRecipeTask();
            task.execute();
        }


        mRecipeRV = (RecyclerView) this.findViewById(R.id.realm_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecipeRV.setLayoutManager(layoutManager);

        mRecipeAdapter = new RecipeAdapter(recipes, false);

        mRecipeRV.setAdapter(mRecipeAdapter);
        mRecipeAdapter.setData(recipes);
        mRecipeAdapter.notifyDataSetChanged();
    }

    public void loadRecyclerViewRecipeData(){
        // Realm Test code
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Recipe> recipes = realm.where(Recipe.class).findAll();

        mRecipeAdapter.setData(recipes);
        mRecipeAdapter.notifyDataSetChanged();
    }

    public class GetRecipeTask extends AsyncTask<Integer, Integer, ArrayList<Recipe>>{

        @Override
        protected ArrayList<Recipe> doInBackground(Integer... integers) {
            ArrayList<Recipe> data = new ArrayList<>();

            URL url = NetworkUtils.buildUrl();
            try {
                String response = NetworkUtils.getResponseFromHttpUrl(url);
                Log.v("JSON Response", response);

                data = JsonUtil.getRecipesFromJson(response);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> Recipes) {
            mData = Recipes;
            loadRecyclerViewRecipeData();
        }
    }
}
