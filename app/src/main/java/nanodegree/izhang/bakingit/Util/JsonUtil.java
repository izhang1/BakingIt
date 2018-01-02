package nanodegree.izhang.bakingit.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import nanodegree.izhang.bakingit.Model.Ingredient;
import nanodegree.izhang.bakingit.Model.Recipe;
import nanodegree.izhang.bakingit.Model.Step;

/**
 * Created by ivanzhang on 9/9/17.
 * utilities
 * - MovieJsonUtils class
 * - This class contains methods to parse JSON objects and pull out needed information for movies.
 * - This class contains methods to parse JSON objects and pull out needed information for trailers.

 */

public class JsonUtil {

    /**
     *  Parses the JSON for Movie data, saves the data and returns this as a list
     * @param dataStr
     * @return
     * @throws JSONException
     */
    public static ArrayList<Recipe> getRecipesFromJson(String dataStr)
            throws JSONException {

        /* All attributes involved in the JSON data */
        final String RECIPE_ID = "id";
        final String RECIPE_NAME = "name";
        final String RECIPE_INGREDIENTS = "ingredients";
        final String RECIPE_STEPS = "steps";
        final String RECIPE_SERVINGS = "servings";
        final String RECIPE_IMAGE = "image";

        final String INGREDIENT = "ingredient";
        final String INGREDIENT_QTY = "quantity";
        final String INGREDIENT_MEASURE = "measure";

        final String STEPS_ID = "id";
        final String STEPS_SHRT_DESC = "shortDescription";
        final String STEPS_DESC = "description";
        final String STEPS_VIDEO_URL = "videoURL";
        final String STEPS_THUMB_URL = "thumbnailURL";

        // Define the array
        JSONArray recipeJsonArr = new JSONArray(dataStr);

        // Loop through the array for reach Recipe
        // Pull the data of reach Recipe
        // Loop through arrays of ingredients and Step
        // Pull the data of each recipes and Step

        ArrayList<Recipe> recipeList = new ArrayList<>();

        for(int i = 0; i < recipeJsonArr.length(); i++){
            JSONObject recipeObj = (JSONObject) recipeJsonArr.get(i);
            int id = recipeObj.getInt(RECIPE_ID);
            String name = recipeObj.getString(RECIPE_NAME);
            int servings = recipeObj.getInt(RECIPE_SERVINGS);
            String image = recipeObj.getString(RECIPE_IMAGE);

            RealmList<Ingredient> ingredientList = new RealmList<>();
            JSONArray ingredientsArr = recipeObj.getJSONArray(RECIPE_INGREDIENTS);
            for(int j = 0 ; j < ingredientsArr.length(); j++){
                JSONObject tempObj = (JSONObject) ingredientsArr.get(j);

                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                Ingredient ingredient = realm.createObject(Ingredient.class);
                ingredient.setIngredients(tempObj.getString(INGREDIENT));
                ingredient.setMeasure(tempObj.getString(INGREDIENT_MEASURE));
                ingredient.setQuantity(tempObj.getInt(INGREDIENT_QTY));
                realm.commitTransaction();

                ingredientList.add(ingredient);
                // qty, measure, Ingredient
            }

            RealmList<Step> stepList = new RealmList<>();
            JSONArray stepsArr = recipeObj.getJSONArray(RECIPE_STEPS);
            for(int h = 0; h < stepsArr.length(); h++){
                JSONObject tempObj = (JSONObject) stepsArr.get(h);
                // id, short desc, desc, video, thumbnail

                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                Step step = realm.createObject(Step.class);
                step.setId(tempObj.getInt(STEPS_ID));
                step.setShortDescription(tempObj.getString(STEPS_SHRT_DESC));
                step.setDescription(tempObj.getString(STEPS_DESC));
                step.setVideoUrl(tempObj.getString(STEPS_VIDEO_URL));
                step.setThumbnailUrl(tempObj.getString(STEPS_THUMB_URL));

                realm.commitTransaction();

                stepList.add(step);

            }

            // Realm Code
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            Recipe recipe = realm.createObject(Recipe.class);
            recipe.setId(id);
            recipe.setName(name);
            recipe.setIngredientList(ingredientList);
            recipe.setStepList(stepList);
            recipe.setImage(image);
            recipe.setServings(servings);

            realm.commitTransaction();

            recipeList.add(recipe);

        }
        return recipeList;
    }



}