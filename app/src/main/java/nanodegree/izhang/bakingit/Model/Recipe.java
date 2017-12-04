package nanodegree.izhang.bakingit.Model;

import java.util.List;

/**
 * Created by ivanzhang on 11/2/17.
 */

public class Recipe {

    private String id;
    private String name;
    private List<Ingredient> ingredientList; // Each recipe has a list of ingredients
    private List<Step> stepList; // Each recipe has a list of steps
    private int servings;
    private String image;

    public Recipe(String id, String name, List<Ingredient> ingredientList, List<Step> stepList, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredientList = ingredientList;
        this.stepList = stepList;
        this.servings = servings;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recipe: " + name + '\n' +
                "ID: " + id + '\n' +
                "Servings: " + servings + '\n' +
                "Images: " + image + '\n';
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
