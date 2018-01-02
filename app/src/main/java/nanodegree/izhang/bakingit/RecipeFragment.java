package nanodegree.izhang.bakingit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import nanodegree.izhang.bakingit.Model.Ingredient;
import nanodegree.izhang.bakingit.Model.Recipe;
import nanodegree.izhang.bakingit.Model.Step;


public class RecipeFragment extends Fragment implements StepAdapter.OnItemClickListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "recipeId";

    private long mRecipeId;
    private Recipe mRecipe;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.tv_serving_size) TextView tv_servingsize;
    @BindView(R.id.rv_ingredient_list) RecyclerView list_ingredients;
    @BindView(R.id.rv_step_list) RecyclerView list_steps;

    public RecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeFragment.
     */
    public static RecipeFragment newInstance(long recipeId) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM, recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipeId = getArguments().getLong(ARG_PARAM);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);

        ButterKnife.bind(this, view);

        // Get the data
        Realm realm = Realm.getDefaultInstance();
        mRecipe = realm.where(Recipe.class).equalTo(getString(R.string.id), mRecipeId).findFirst();

        // Set the title to the recipe
        getActivity().setTitle(getString(R.string.recipe_fragment_label) + mRecipe.getName());

        // Set the other data views
        tv_servingsize.setText(mRecipe.getServings() + getString(R.string.recipe_fragment_servings));

        // Create the arraylist of strings (represents each ingredient)
        ArrayList<String> ingredientList = new ArrayList<>();
        for(Ingredient ingredient : mRecipe.getIngredientList()){
            ingredientList.add(ingredient.toString());
        }

        // Setup the ingredients listview
        list_ingredients.setLayoutManager(new LinearLayoutManager(view.getContext()));
        IngredientAdapter ingredientAdapter = new IngredientAdapter(ingredientList);
        list_ingredients.setAdapter(ingredientAdapter);

        // Step RecyclerView
        list_steps.setLayoutManager(new LinearLayoutManager(view.getContext()));
        List<Step> stepList = mRecipe.getStepList();
        StepAdapter stepAdapter = new StepAdapter((RealmList)stepList, this);
        stepAdapter.setRecipeId(mRecipeId);
        list_steps.setAdapter(stepAdapter);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(int stepId) {
        mListener.onStepItemClick(stepId);
    }

    public interface OnFragmentInteractionListener {
        void onStepItemClick(int stepId);
    }

}
