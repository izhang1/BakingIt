package nanodegree.izhang.bakingit.Model;

/**
 * Created by ivanzhang on 11/2/17.
 */

public class ingredient {

    private int quantity;
    private String measure;
    private String ingredients;

    public ingredient(int quantity, String measure, String ingredients){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredients = ingredients;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
