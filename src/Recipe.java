import java.util.HashMap;
import java.util.Map;

public class Recipe implements FoodComponent {
    private String name;
    private Map<FoodComponent, Double> ingredients;

    public Recipe(String name) {
        this.name = name;
        this.ingredients = new HashMap<>();
    }

    public void addFood(FoodComponent food, double servings) {
        ingredients.put(food, servings);
    }
    public Map getIngredients(){
        return this.ingredients;
    }
    @Override
    public double getCalories() {
        double totalCalories = 0;
        for (Map.Entry<FoodComponent, Double> entry : ingredients.entrySet()) {
            totalCalories += entry.getKey().getCalories() * entry.getValue();
        }
        return totalCalories;
    }

    @Override
    public double getFat() {
        double totalFat = 0;
        for (Map.Entry<FoodComponent, Double> entry : ingredients.entrySet()) {
            totalFat += entry.getKey().getFat() * entry.getValue();
        }
        return totalFat;
    }

    @Override
    public double getCarb() {
        double totalCarb = 0;
        for (Map.Entry<FoodComponent, Double> entry : ingredients.entrySet()) {
            totalCarb += entry.getKey().getCarb() * entry.getValue();
        }
        return totalCarb;
    }

    @Override
    public double getProtein() {
        double totalProtein = 0;
        for (Map.Entry<FoodComponent, Double> entry : ingredients.entrySet()) {
            totalProtein += entry.getKey().getProtein() * entry.getValue();
        }
        return totalProtein;
    }

    @Override
    public String getName() {
        return name;
    }
}