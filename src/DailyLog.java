import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.Month;
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

    public static String getDayExer() {
        StringBuilder dayExercises = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("log.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    LocalDate date = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    if (date.equals(currentDate) && parts[3].equals("e")) {
                        String exerciseName = parts[4];
                        double minutes = Double.parseDouble(parts[5]);
                        double caloriesBurned = Exercises.calculateCaloriesBurned(exerciseName, weight, minutes);
                        dayExercises.append(String.format("%s: %.2f minutes burned %.0f calories%n", exerciseName, minutes, caloriesBurned));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading log.csv: " + e.getMessage());
        }
        return dayExercises.toString();
    }

    public static double getDayBurned() {
        StringBuilder dayBurned = new StringBuilder();
        double total = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader("log.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    LocalDate date = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    if (date.equals(currentDate) && parts[3].equals("e")) {
                        double minutes = Double.parseDouble(parts[5]);
                        double caloriesBurned = Exercises.calculateCaloriesBurned(parts[4], weight, minutes);
                        total += caloriesBurned;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading log.csv: " + e.getMessage());
        }
        dayBurned.append("Daily calories burned: " + total);
        return total;
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

    public static boolean checkIfLineExists(String filePath, String lineToCheck) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.equals(lineToCheck)) {
                    reader.close();
                    return true;
                }
            }

            reader.close();
        } catch (IOException e) {
            LogView1.displayError("Error reading CSV file: " + e.getMessage());
        }

        return false;
    }

    public static void saveLog() {
        try {
            String logFilePath = "log.csv";
            String weightLine = String.format("%04d,%02d,%02d,w,%.2f\n", currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth(), weight);
            String calorieLine = String.format("%04d,%02d,%02d,c,%.2f\n", LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth(), calorieLimit);

            if (!checkIfLineExists(logFilePath, weightLine) || !checkIfLineExists(logFilePath, calorieLine)) {
                FileWriter writer = new FileWriter(logFilePath, true);

                if (!checkIfLineExists(logFilePath, weightLine)) {
                    writer.write(weightLine);
                }

                if (!checkIfLineExists(logFilePath, calorieLine)) {
                    writer.write(calorieLine);
                }

                //Exercises.saveExercises();
                FoodCollection.saveFoods();
                writer.close();
            }
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

    public static void logFood(String name, double count) {
        String entry = String.format("%d,%d,%d,f,%s,%.1f%n", currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth(), name, count);
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("log.csv"), StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            writer.write(entry);
        } catch (IOException e) {
            System.err.println("Error writing to log.csv: " + e.getMessage());
        }
    }

    public static String getDayFood() {
        StringBuilder dayFoods = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("log.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    LocalDate date = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    if (date.equals(currentDate) && parts[3].equals("f")) {
                        String foodName = parts[4];
                        double count = Double.parseDouble(parts[5]);
                        double caloriesExpended = count * getCaloriesForFood(foodName); // Assuming you have a method to get calories for a given food
                        dayFoods.append(String.format("%s: %.0f count gained %.0f calories%n", foodName, count, caloriesExpended));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading log.csv: " + e.getMessage());
        }
        return dayFoods.toString();
    }

    public static double getCaloriesForFood(String foodName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("foods.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[1].equals(foodName)) {
                    return Double.parseDouble(parts[2]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading foods.csv: " + e.getMessage());
        }
        return 0;
    }

    public static double getDayCals() {
        StringBuilder dayCals = new StringBuilder();
        double total = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader("log.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    LocalDate date = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    if (date.equals(currentDate) && parts[3].equals("f")) {
                        String foodName = parts[4];
                        double count = Double.parseDouble(parts[5]);
                        double calories = getCaloriesForFood(foodName) * count;
                        total += calories;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading log.csv: " + e.getMessage());
        }
        dayCals.append(String.format("Total calories gained: %.0f", total));
        return total;
    }

    public static double getDayWeight() {
        try (BufferedReader br = new BufferedReader(new FileReader("log.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[3].equals("w")) {
                    LocalDate date = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    if (date.equals(currentDate)) {
                        return Double.parseDouble(parts[4]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

}