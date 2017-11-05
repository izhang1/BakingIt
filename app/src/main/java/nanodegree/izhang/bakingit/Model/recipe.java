package nanodegree.izhang.bakingit.Model;

import java.util.List;

/**
 * Created by ivanzhang on 11/2/17.
 */

public class recipe {

    private String id;
    private String name;
    private List<ingredient> ingredientList;
    private List<steps> stepsList;
    private int servings;
    private String image;

    public recipe(String id, String name, List<ingredient> ingredientList, List<steps> stepsList, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredientList = ingredientList;
        this.stepsList = stepsList;
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

    public List<ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<steps> getStepsList() {
        return stepsList;
    }

    public void setStepsList(List<steps> stepsList) {
        this.stepsList = stepsList;
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
