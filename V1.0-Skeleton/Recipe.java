import java.util.HashMap;
import java.util.Map;

public class Recipe implements FoodComponent {
    private String name;
    private Map<FoodComponent, Double> ingredients = new HashMap<>();

    public Recipe(String name) {
        this.name = name;
    }

    public void addFood(FoodComponent food, double count) {
        if (food instanceof BasicFood) {
            ingredients.put(food, count);
        } else if (food instanceof Recipe) {
            for (Map.Entry<FoodComponent, Double> ingredient : ((Recipe) food).getIngredients().entrySet()) {
                addFood(ingredient.getKey(), ingredient.getValue() * count);
            }
        }
    }

    // Implementing methods from FoodComponent interface
    @Override
    public double getCalories() {
        double calories = 0.0;
        for(Map.Entry<FoodComponent, Double> entry : ingredients.entrySet()) {
            calories += entry.getKey().getCalories() * entry.getValue();
        }
        return calories;
    }

    @Override
    public double getFat() {
        double fat = 0.0;
        for(Map.Entry<FoodComponent, Double> entry : ingredients.entrySet()) {
            fat += entry.getKey().getCalories() * entry.getValue();
        }
        return fat;
    }

    @Override
    public double getCarb() {
        return 0;
    }

    @Override
    public double getProtein() {
        return 0;
    }

    @Override
    public <string> string getName() {
        return null;
    }


    // Getter method for retrieving recipe's all ingredients and their respective amount
    public Map<FoodComponent, Double> getIngredients(){
        return ingredients;
    }
}