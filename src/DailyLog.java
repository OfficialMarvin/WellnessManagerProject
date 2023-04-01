import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DailyLog {

    private double weight = 150.0; // in pounds
    private double calorieLimit = 2000.0;
    private final Map<LocalDate, Map<String, Double>> foodEntries = new HashMap<>();

    public void addFoodEntry(String foodName, double count) {
        Map<String, Double> entry = foodEntries.getOrDefault(LocalDate.now(), new HashMap<>());
        entry.put(foodName, entry.getOrDefault(foodName, 0.0) + count);
        foodEntries.put(LocalDate.now(), entry);
        saveToCSVFile();
    }

    public void removeFoodEntry(String foodName) {
        Map<String, Double> entry = foodEntries.get(LocalDate.now());
        if (entry != null) {
            entry.remove(foodName);
            foodEntries.put(LocalDate.now(), entry);
            saveToCSVFile();
        }
    }

    public void saveToCSVFile() {
        try {
            FileWriter writer = new FileWriter("log.csv");
            writer.write(String.format("%04d,%02d,%02d,w,%.2f\n", LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth(), weight));
            writer.write(String.format("%04d,%02d,%02d,c,%.2f\n", LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth(), calorieLimit));
            for (Map.Entry<LocalDate, Map<String, Double>> dateEntry : foodEntries.entrySet()) {
                LocalDate date = dateEntry.getKey();
                for (Map.Entry<String, Double> foodEntry : dateEntry.getValue().entrySet()) {
                    writer.write(String.format("%04d,%02d,%02d,f,%s,%.2f\n", date.getYear(), date.getMonthValue(), date.getDayOfMonth(), foodEntry.getKey(), foodEntry.getValue()));
                }
            }
            writer.close();
        } catch (IOException e) {
            LogView.displayError("Error saving to CSV file: " + e.getMessage());
        }
    }
    public double setWeight(){

    }
    public double checkCalorieLimit(FoodCollection foods) {
        double totalCalories = 0.0;
        Map<String, Double> entry = foodEntries.get(LocalDate.now());
        if (entry != null) {
            for (Map.Entry<String, Double> foodEntry : entry.entrySet()) {
                FoodComponent fc = foods.getFood(foodEntry.getKey());
                totalCalories += fc.getCalories() * foodEntry.getValue();
            }
        }
        return totalCalories;
    }
}