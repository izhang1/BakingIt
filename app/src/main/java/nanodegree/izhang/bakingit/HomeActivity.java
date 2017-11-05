package nanodegree.izhang.bakingit;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import nanodegree.izhang.bakingit.Model.Recipe;
import nanodegree.izhang.bakingit.Util.JsonUtil;
import nanodegree.izhang.bakingit.Util.NetworkUtils;

public class HomeActivity extends AppCompatActivity {
    ArrayList<Recipe> mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Testing network and http request
        GetRecipeTask task = new GetRecipeTask();
        task.execute();

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
        }
    }
}
