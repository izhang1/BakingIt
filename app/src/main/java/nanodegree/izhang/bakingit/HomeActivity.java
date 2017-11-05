package nanodegree.izhang.bakingit;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

import nanodegree.izhang.bakingit.Util.NetworkUtils;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Testing network and http request
        testAsyncTask task = new testAsyncTask();
        task.execute();

    }

    public class testAsyncTask extends AsyncTask<Integer, Integer, String>{


        @Override
        protected String doInBackground(Integer... integers) {
            URL url = NetworkUtils.buildUrl();
            try {
                String response = NetworkUtils.getResponseFromHttpUrl(url);
                Log.v("JSON Response", response);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }
    }
}
