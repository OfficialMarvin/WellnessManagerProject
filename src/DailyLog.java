import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DailyLog {

    private static double weight = 150.0; // in pounds
    private static double calorieLimit = 2000.0;
    private static final Map<LocalDate, Map<String, Double>> foodEntries = new HashMap<>();
    private static final Map<LocalDate, List<String>> exerciseEntries = new HashMap<>();
    private static LocalDate currentDate = LocalDate.now();

    public static void setCurrentDate(LocalDate date) {
        DailyLog.currentDate = date;
        System.out.println(DailyLog.currentDate);
    }

    public static void addExerciseEntry(String exerciseName, double minutes) {
        List<String> entry = exerciseEntries.getOrDefault(currentDate, new ArrayList<>());
        entry.add(String.format("%s,%.2f", exerciseName, minutes));
        exerciseEntries.put(currentDate, entry);
        DailyLog.saveLog();
    }

    public void removeExerciseEntry(String exerciseName, double minutes) {
        List<String> entry = exerciseEntries.get(currentDate);
        if (entry != null) {
            entry.remove(String.format("%s,%.2f", exerciseName, minutes));
            exerciseEntries.put(currentDate, entry);
            saveLog();
        }
    }

    public void addFoodEntry(String foodName, double count) {
        Map<String, Double> entry = foodEntries.getOrDefault(currentDate, new HashMap<>());
        entry.put(foodName, entry.getOrDefault(foodName, 0.0) + count);
        foodEntries.put(currentDate, entry);
        saveLog();
    }

    public void removeFoodEntry(String foodName) {
        Map<String, Double> entry = foodEntries.get(currentDate);
        if (entry != null) {
            entry.remove(foodName);
            foodEntries.put(currentDate, entry);
            saveLog();
        }
    }

    private static boolean checkIfLineExists(FileWriter writer, String line) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("log.csv"));
        String csvLine;
        while ((csvLine = reader.readLine()) != null) {
            if (csvLine.equals(line)) {
                return true;
            }
        }
        return false;
    }

    public static void saveLog() {
        try {
            FileWriter writer = new FileWriter("log.csv", true);
            if(checkIfLineExists(writer, String.format("%04d,%02d,%02d,w,%.2f\n", currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth(), weight)) == false){
                writer.write(String.format("%04d,%02d,%02d,w,%.2f\n", currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth(), weight));
            }
            if(checkIfLineExists(writer,String.format("%04d,%02d,%02d,c,%.2f\n", LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth(), calorieLimit)) == false){
                writer.write(String.format("%04d,%02d,%02d,c,%.2f\n", LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth(), calorieLimit));
            }
            //Exercises.saveExercises();
            FoodCollection.saveFoods();
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
                else if (type.equals("e")) {
                    String exerciseName = fields[4];
                    double minutes = Double.parseDouble(fields[5]);
                    List<String> exerciseList = exerciseEntries.getOrDefault(date, new ArrayList<>());
                    exerciseList.add(String.format("%s,%.2f", exerciseName, minutes));
                    exerciseEntries.put(date, exerciseList);
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
        System.out.println("weight changed");
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
        List<String> exerciseEntry = exerciseEntries.get(LocalDate.now());
        if (exerciseEntry != null) {
            for (String exercise : exerciseEntry) {
                String[] parts = exercise.split(",");
                String exerciseName = parts[0];
                double minutes = Double.parseDouble(parts[1]);
                Exercise ex = Exercises.getExercise(exerciseName);
                if (ex != null) {
                    totalCalories -= ex.getCalories() * minutes;
                } else {
                    System.err.println("Exercise not found: " + exerciseName);
                }
            }
        }
        return totalCalories;
    }
}