import java.time.LocalDate;
import java.util.List;


public class LogController {

    private final LogModel logModel;
    private final LogView1 logView;
    private final FoodCollection foodCollection;
    private DailyLog dailyLog;
    public static double height;

    public LogController(LogModel logModel, LogView1 logView, double height) {
        this.logModel = logModel;
        this.logView = logView;
        this.foodCollection = new FoodCollection();
        LogController.height = height;

        foodCollection.loadFoods();
        logModel.loadLogData("log.csv");

        selectDate(LocalDate.now());
    }

    public void addBasicFood(String name, double calories, double fat, double carb, double protein) {
        BasicFood food = new BasicFood(name, calories, fat, carb, protein);
        foodCollection.addFood(food);
        System.out.println("added basic food: " + food.getName());
    }

    public void addRecipe(String name, String IngredientsNQuantity) {
        Recipe recipe = new Recipe(name);
        String[] ingredientsNQuantity = IngredientsNQuantity.split(",");
        for (int i = 0; i < ingredientsNQuantity.length; i += 2) {
            String ingredientName = ingredientsNQuantity[i];
            double quantity = Double.parseDouble(ingredientsNQuantity[i + 1]);
            FoodComponent food = foodCollection.getFood(ingredientName);
            if (food != null) {
                recipe.addFood(food, quantity);
            } else {
                // Add the missing food as a basic food with 0's for the other arguments
                addBasicFood(ingredientName, 0, 0, 0, 0);
                food = foodCollection.getFood(ingredientName);
                recipe.addFood(food, quantity);
            }
        }
        foodCollection.addFood(recipe);
    }


    public void selectDate(LocalDate date) {
        dailyLog = logModel.loadLogData(String.valueOf(date));
        if (dailyLog == null) {
            dailyLog = new DailyLog();
            logModel.saveLogData(date, String.valueOf(dailyLog));
        }
        //logView.displayDailyLog(dailyLog);
    }

    public void updateWeight(double weight) {
        DailyLog.setWeight(weight);
    }

    public void updateCalorieLimit(double calorieLimit) {
        DailyLog.setCalorieLimit(calorieLimit);
    }

    public void addFoodToLog(String foodName, double count) {
        FoodComponent food = foodCollection.getFood(foodName);
        if (food != null) {
            dailyLog.addFoodEntry(foodName, count);
        } else {
            logView.displayError("Food not found: " + foodName);
        }
    }

    public void removeFoodFromLog(String foodName) {
        dailyLog.removeFoodEntry(foodName);
    }

    public void loadData() {
        foodCollection.loadFoods();
        DailyLog.loadLog();
    }

    public void saveData() {
        LocalDate date = LocalDate.now();
        foodCollection.saveFoods();
        DailyLog.saveLog();
    }

}