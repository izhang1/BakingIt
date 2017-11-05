package nanodegree.izhang.bakingit;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nanodegree.izhang.bakingit.Model.Recipe;

/**
 * Created by ivanzhang on 11/5/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> mRecipeData;
    private Context context;

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipeViewHolder(view);
    }

    // Load any data here into the cardview
    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipeData.get(position);
        holder.textView.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        if(mRecipeData != null){
            return mRecipeData.size();
        }else{
            return 0;
        }
    }

    public void setData(ArrayList<Recipe> data){
        this.mRecipeData = data;
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
        }
    }


}
