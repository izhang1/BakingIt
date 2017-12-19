package nanodegree.izhang.bakingit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import nanodegree.izhang.bakingit.Model.Recipe;
import nanodegree.izhang.bakingit.Model.Step;

/**
 * Created by ivanzhang on 12/16/17.
 */

public class StepAdapter extends RealmRecyclerViewAdapter<Step, StepAdapter.StepViewHolder> {

    private RealmList<Step> mStepData;
    private long recipeId;
    private Context context;

    private static int INTRO_ID = 0;

    public StepAdapter(RealmList<Step> data, boolean autoUpdate) {
        super(data, autoUpdate);
        this.mStepData = data;
        if(mStepData.size() > 0){
            Log.v("TAG", "YEY");
        }
    }

    public void setRecipeId(long recipeId){
        this.recipeId = recipeId;
    }


    public void setData(RealmList<Step> data){
        this.mStepData = data;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapter.StepViewHolder holder, int position) {
        Step step = mStepData.get(position);
        if(step.getId() == INTRO_ID){
            holder.textView.setText(step.getShortDescription());
        }else{
            holder.textView.setText(step.getId() + ". " + step.getShortDescription());
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
    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.recipe_card)
        CardView recipeCard;
        @BindView(R.id.tv_recipe_name)
        TextView textView;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            recipeCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Add this
            Step step = mStepData.get(getAdapterPosition());
            //Toast.makeText(context, "Recipe clicked!" + step.getDescription().toString(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, StepActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(context.getString(R.string.param_step_id), step.getId());
            bundle.putLong(context.getString(R.string.param_recipe_id), recipeId);
            intent.putExtras(bundle);

            context.startActivity(intent);
        }
    }


}
