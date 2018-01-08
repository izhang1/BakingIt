package nanodegree.izhang.bakingit;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import nanodegree.izhang.bakingit.Model.Recipe;
import nanodegree.izhang.bakingit.Widget.RecipeWidget;

/**
 * Created by ivanzhang on 11/5/17.
 *
 *  RecipeAdapter
 *  - Adapter class for ingredients shown on the HomeActivity
 *  - Implements both onItemClick and onItemLongClick
 */

public class RecipeAdapter extends RealmRecyclerViewAdapter<Recipe, RecipeAdapter.RecipeViewHolder> {

    private RealmResults<Recipe> mRecipeData;
    private Context context;

    public RecipeAdapter(RealmResults<Recipe> data) {
        super(data, false);
        this.mRecipeData = data;
    }

    public void setData(RealmResults<Recipe> data){
        this.mRecipeData = data;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipeData.get(position);
        holder.textView.setText(recipe != null ? recipe.getName() : null);

        // Show the recipe image if there's a valid image
        if(!TextUtils.isEmpty(recipe.getImage())){
            holder.imageRecipe.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(recipe.getImage())
                    .into(holder.imageRecipe);
        }
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    /**
     * RecipeViewHolder class - inner
     * - Initializes the view and sets the value as well as implements the onclick functions
     *
     */
    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        @BindView(R.id.recipe_card) CardView recipeCard;
        @BindView(R.id.tv_recipe_name) TextView textView;
        @BindView(R.id.imageview_recipe) ImageView imageRecipe;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            recipeCard.setOnClickListener(this);
            recipeCard.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Recipe recipe = mRecipeData.get(getAdapterPosition());

            // Passing the recipe data and starting the bundle
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(context.getString(R.string.passed_recipe_id), recipe.getId());
            context.startActivity(intent);

        }

        @Override
        public boolean onLongClick(View v) {

            // Gather and save the data
            Recipe recipe = mRecipeData.get(getAdapterPosition());
            SharedPreferences preferences = context.getSharedPreferences(context.getApplicationContext().getString(R.string.widget_pref_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong(context.getApplicationContext().getString(R.string.widget_recipe_id), recipe.getId());
            editor.commit();

            // Sent the intent and update widget
            AppWidgetManager widgetMgr = AppWidgetManager.getInstance(context);
            int widgetIds[] = widgetMgr.getAppWidgetIds(
                    new ComponentName(context, RecipeWidget.class));
            widgetMgr.notifyAppWidgetViewDataChanged(widgetIds, R.id.list_ingredients);

            // Snackbar showing completed change
            Snackbar snackbar = Snackbar
                    .make(v,  "Widget changed to show recipe: " + recipe.getName(), Snackbar.LENGTH_SHORT);

            snackbar.show();

            return true;
        }
    }

}
