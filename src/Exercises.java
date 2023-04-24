import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Exercises {
    private List<Exercise> exerciseList;

    public Exercises() {
        exerciseList = new ArrayList<>();
    }

    public void loadExercises() {
        Path path = Paths.get("exercise.csv");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals("e")) {
                    String name = parts[1];
                    float calories = Float.parseFloat(parts[2]);
                    exerciseList.add(new Exercise(name, calories));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading exercise.csv: " + e.getMessage());
        }
    }

    public void saveExercises() {
        Path path = Paths.get("exercise.csv");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Exercise exercise : exerciseList) {
                writer.write("e," + exercise.getName() + "," + exercise.getCalories());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing exercise.csv: " + e.getMessage());
        }
    }
}
