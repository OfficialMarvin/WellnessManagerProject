import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class LogController {

    private final LogModel logModel;
    private final LogView1 logView;
    private final FoodCollection foodCollection;
    private DailyLog dailyLog;
    public static double height; // in meters

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
    }

    public void addRecipe(String name, List<String> foodNames, List<Double> foodCounts) {
        Recipe recipe = new Recipe(name);
        for (int i = 0; i < foodNames.size(); i++) {
            FoodComponent food = foodCollection.getFood(foodNames.get(i));
            if (food != null) {
                recipe.addFood(food, foodCounts.get(i));
            } else {
                logView.displayError("Food not found: " + foodNames.get(i));
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
        logView.displayDailyLog(dailyLog);
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

    public void saveData() {
        LocalDate date = LocalDate.now();
        foodCollection.saveFoods();
        logModel.saveLogData(date,"log.csv");
    }
}