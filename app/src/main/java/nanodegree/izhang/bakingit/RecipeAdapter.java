package nanodegree.izhang.bakingit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import nanodegree.izhang.bakingit.Model.Recipe;

/**
 * Created by ivanzhang on 11/5/17.
 */

public class RecipeAdapter extends RealmRecyclerViewAdapter<Recipe, RecipeAdapter.RecipeViewHolder> {

    private RealmResults<Recipe> mRecipeData;
    private Context context;

    public RecipeAdapter(RealmResults<Recipe> data, boolean autoUpdate) {
        super(data, autoUpdate);
        this.mRecipeData = data;
        if(mRecipeData.size() > 0){
            Log.v("TAG", "YEY");
        }
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
        holder.textView.setText(recipe.getName());
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
    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CardView recipeCard;
        private TextView textView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeCard = (CardView) itemView.findViewById(R.id.recipe_card);
            textView = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            recipeCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Add this
            Recipe recipe = mRecipeData.get(getAdapterPosition());
            Toast.makeText(context, "Recipe clicked!" + recipe.toString(), Toast.LENGTH_SHORT).show();

            // Passing the recipe data and starting the bundle
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(context.getString(R.string.passed_recipe_id), recipe.getId());
            context.startActivity(intent);

        }
    }


}
