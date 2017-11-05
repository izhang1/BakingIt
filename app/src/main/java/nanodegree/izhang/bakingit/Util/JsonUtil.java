package nanodegree.izhang.bakingit.Util;

/**
 * Created by ivanzhang on 11/2/17.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nanodegree.izhang.bakingit.Model.recipe;

/**
 * Created by ivanzhang on 9/9/17.
 * utilities
 * - MovieJsonUtils class
 * - This class contains methods to parse JSON objects and pull out needed information for movies.
 * - This class contains methods to parse JSON objects and pull out needed information for trailers.

 */

class JsonUtil {

    /**
     *  Parses the JSON for Movie data, saves the data and returns this as a list
     * @param movieJsonStr
     * @return
     * @throws JSONException
     */
    public static List<recipe> getMovieListFromJson(String movieJsonStr)
            throws JSONException {

        /* Results list */
        final String MOVIEDB_RESULT = "results";

//        /* All attributes of the movie and their information */
//        final String MOVIE_TITLE = "title";
//        final String MOVIE_RELEASE = "release_date";
//        final String POSTER_PATH = "poster_path";
//        final String MOVIE_OVERVIEW = "overview";
//        final String VOTE_AVG = "vote_average";
//        final String MOVIE_ID = "id";
//
//        /* String array to hold each day's weather String */
//        ArrayList<recipe> movieList = new ArrayList<>();
//
//        JSONObject movieJson = new JSONObject(movieJsonStr);
//
//        JSONArray movieArray = movieJson.getJSONArray(MOVIEDB_RESULT);
//
//        /* Looping through the array and saving the movie values into a list of movies passed back to the calling method. */
//        for (int i = 0; i < movieArray.length(); i++) {
//            JSONObject tempObj = (JSONObject) movieArray.get(i);
//            //Log.v("MovieJsonUtils", tempObj.toString());
//            Movie movie = new Movie(tempObj.getString(MOVIE_TITLE),
//                    tempObj.getString(MOVIE_RELEASE),
//                    tempObj.getString(POSTER_PATH),
//                    tempObj.getString(MOVIE_OVERVIEW),
//                    tempObj.getInt(VOTE_AVG),
//                    tempObj.getString(MOVIE_ID));
//            movieList.add(movie);
//        }

        return new ArrayList<>();
    }



}