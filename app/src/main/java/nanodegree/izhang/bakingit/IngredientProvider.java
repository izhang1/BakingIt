package nanodegree.izhang.bakingit;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.realm.Realm;
import nanodegree.izhang.bakingit.Model.Ingredient;
import nanodegree.izhang.bakingit.Model.Recipe;

/**
 * Created by ivanzhang on 12/28/17.
 */

public class IngredientProvider implements RemoteViewsService.RemoteViewsFactory {
    private static long INVALID_RECIPE_ID = -1;
    private static long RECIPE_NAME_POSITION = 0;
    private static String TAG = "IngredientProvider";

    private Context mContext;
    private Intent mIntent;
    private ArrayList<String> mIngredients;
    private String mRecipeName;

    public IngredientProvider(Context context, Intent intent){
        Log.v(TAG, "Constructor");

        this.mContext = context;
        this.mIntent = intent;
    }

    @Override
    public void onCreate() {
        Log.v(TAG, "On Create");
        long recipeId = getCurrentSavedRecipe();

        // Check if the recipeID is valid or not
        if(recipeId != INVALID_RECIPE_ID){
            Realm.init(mContext);
            Realm realm = Realm.getDefaultInstance();
            Recipe recipe = realm.where(Recipe.class).equalTo(mContext.getString(R.string.id), recipeId).findFirst();
            mRecipeName = recipe.getName();
            mIngredients = new ArrayList<>();
            for(Ingredient ingredient : recipe.getIngredientList()){
                mIngredients.add(ingredient.toString());
            }
        }

    }

    public long getCurrentSavedRecipe(){
        SharedPreferences savedRecipe = mContext.getSharedPreferences(mContext.getApplicationContext().getString(R.string.widget_pref), 0);
        long recipeId = savedRecipe.getLong(mContext.getApplicationContext().getString(R.string.widget_recipe_id), INVALID_RECIPE_ID);
        return recipeId;
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mIngredients != null){
            return mIngredients.size();
        }else{
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item);

        // If position is the first one, set the title
        if(position == RECIPE_NAME_POSITION){
            String recipeTitle = "Recipe: " + mRecipeName;
            remoteView.setTextViewText(R.id.tv_recipe_name, recipeTitle);
        }else{
            String ingredient = mIngredients.get(position);
            remoteView.setTextViewText(R.id.tv_recipe_name, ingredient);
        }

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}