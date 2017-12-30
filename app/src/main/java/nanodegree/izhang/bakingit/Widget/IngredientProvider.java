package nanodegree.izhang.bakingit.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import io.realm.Realm;
import nanodegree.izhang.bakingit.Model.Ingredient;
import nanodegree.izhang.bakingit.Model.Recipe;
import nanodegree.izhang.bakingit.R;

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
    private int mAppWidgetId;

    public IngredientProvider(Context context, Intent intent){
        this.mContext = context;
        this.mIntent = intent;
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
    }

    @Override
    public void onCreate() {
        getRecipeData();
    }

    private void getRecipeData(){
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
        SharedPreferences savedRecipe = mContext.getSharedPreferences(mContext.getApplicationContext().getString(R.string.widget_pref_key), Context.MODE_PRIVATE);
        long recipeId = savedRecipe.getLong(mContext.getApplicationContext().getString(R.string.widget_recipe_id), INVALID_RECIPE_ID);

        return recipeId;
    }

    @Override
    public void onDataSetChanged() {
        getRecipeData();
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

        remoteView.setTextColor(R.id.tv_recipe_name, mContext.getColor(R.color.cardview_light_background));

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
