package nanodegree.izhang.bakingit.Model;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ivanzhang on 11/2/17.
 */

public class Recipe extends RealmObject implements Serializable {

    private int id;
    private String name;
    private RealmList<Ingredient> ingredientList; // Each recipe has a list of ingredients
    private RealmList<Step> stepList; // Each recipe has a list of steps
    private int servings;
    private String image;

    public Recipe(){};

    public Recipe(int id, String name, RealmList<Ingredient> ingredientList, RealmList<Step> stepList, int servings, String image) {
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

    public long getId() {

        return id;
    }

    public void setId(int id) {
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

    public void setIngredientList(RealmList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(RealmList<Step> stepList) {
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
