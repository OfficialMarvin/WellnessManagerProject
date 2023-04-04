import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DailyLog {

    private static double weight = 150.0; // in pounds
    private static double calorieLimit = 2000.0;
    private static final Map<LocalDate, Map<String, Double>> foodEntries = new HashMap<>();

    public void addFoodEntry(String foodName, double count) {
        Map<String, Double> entry = foodEntries.getOrDefault(LocalDate.now(), new HashMap<>());
        entry.put(foodName, entry.getOrDefault(foodName, 0.0) + count);
        foodEntries.put(LocalDate.now(), entry);
        saveLog();
    }

    public void removeFoodEntry(String foodName) {
        Map<String, Double> entry = foodEntries.get(LocalDate.now());
        if (entry != null) {
            entry.remove(foodName);
            foodEntries.put(LocalDate.now(), entry);
            saveLog();
        }
    }

    public static void saveLog() {
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
            LogView1.displayError("Error saving to CSV file: " + e.getMessage());
        }
    }

    public static void loadLog() {
        try (Scanner scanner = new Scanner(new File("log.csv"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                LocalDate date = LocalDate.of(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
                String type = fields[3];
                if (type.equals("w")) {
                    weight = Double.parseDouble(fields[4]);
                } else if (type.equals("c")) {
                    calorieLimit = Double.parseDouble(fields[4]);
                } else if (type.equals("f")) {
                    String foodName = fields[4];
                    double amount = Double.parseDouble(fields[5]);
                    Map<String, Double> foodMap = foodEntries.getOrDefault(date, new HashMap<>());
                    foodMap.put(foodName, amount);
                    foodEntries.put(date, foodMap);
                }
            }
        } catch (IOException e) {
            LogView1.displayError("Error loading CSV file: " + e.getMessage());
        }
    }

    public static void setCalorieLimit(double calorieLimit) {
        DailyLog.calorieLimit = calorieLimit;
    }

    public static void setWeight(double weight){
        DailyLog.weight = weight;
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