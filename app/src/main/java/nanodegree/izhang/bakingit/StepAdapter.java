package nanodegree.izhang.bakingit;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;
import io.realm.RealmRecyclerViewAdapter;
import nanodegree.izhang.bakingit.Model.Step;

/**
 * Created by ivanzhang on 12/16/17.
 *
 * StepAdapter
 *  - Adapter class for steps shown on the DetailActivity
 *  - Requires implementation of the onItemClickListener interface
 *
 */

public class StepAdapter extends RealmRecyclerViewAdapter<Step, StepAdapter.StepViewHolder> {

    private RealmList<Step> mStepData;
    private Context context;
    private final StepAdapter.OnItemClickListener mListener;

    private static final int INTRO_ID = 0;

    public interface OnItemClickListener{
        void onItemClick(int stepId);
    }

    public StepAdapter(RealmList<Step> data, OnItemClickListener listener) {
        super(data, false);
        this.mListener = listener;
        this.mStepData = data;
    }

    public void setRecipeId(long recipeId){
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
    public class StepViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.recipe_card) CardView recipeCard;
        @BindView(R.id.tv_recipe_name) TextView textView;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(getAdapterPosition());
                }
            });
        }


    }


}
