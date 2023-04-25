import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class FoodCollection {
    private static Map<String, FoodComponent> foods = new HashMap<>();

    public static void addFood(FoodComponent food) {
        foods.put(food.getName(), food);
    }

    public static FoodComponent getFood(String name) {
        return foods.get(name);
    }

    private static FoodCollection instance = null;

    public static FoodCollection getInstance() {
        if (instance == null) {
            instance = new FoodCollection();
        }
        return instance;
    }


    public static void loadFoods(){
        Path filePath = Paths.get("foods.csv");

        try(BufferedReader reader = Files.newBufferedReader(filePath)){
            String line;
            while((line=reader.readLine()) !=null){
                String[] fields = line.split(",");

                if(fields[0].equals("b")){
                    String name = fields[1].trim();
                    double calories = Double.parseDouble(fields[2]);
                    double fat = Double.parseDouble(fields[3]);
                    double carb = Double.parseDouble(fields[4]);
                    double protien = Double.parseDouble(fields[5]);
                    addFood(new BasicFood(name, calories, fat, carb, protien));
                } else if (fields[0].equals("r")) {
                    String name = fields[1].trim();
                    Recipe recipe = new Recipe(name);
                    for(int i = 2; i < fields.length - 1; i += 2 ){
                        FoodComponent ingredient = getFood(fields[i]);
                        double count = Double.parseDouble(fields[i+1]);
                        if(ingredient == null)
                            throw new RuntimeException("Ingredient " + fields[i] + " is missing in the collection.");
                        recipe.addFood(ingredient, count);
                    }
                    addFood(recipe);
                } else {
                    throw new RuntimeException("Invalid line format in the file.");
                }
            }
        } catch(IOException e){
            System.out.println(e);
        }
    }

    public static void saveFoods() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("foods.csv"))) {
            for (FoodComponent food : foods.values()) {
                if (food instanceof BasicFood) {
                    writer.write("b," + food.getName() + "," + food.getCalories() + "," + ((BasicFood)food).getFat() + "," + ((BasicFood)food).getCarb() + "," + ((BasicFood)food).getProtein());
                } else if (food instanceof Recipe) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("r," + ((Recipe) food).getName());
                    for (Map.Entry<FoodComponent, Double> entry : ((Recipe) food).getIngredients().entrySet()) {
                        sb.append("," + entry.getKey().getName() + "," + entry.getValue());
                    }
                    writer.write(sb.toString());
                }
                writer.newLine();
            }
        } catch(IOException e){
            System.out.println(e);
        }
    }


}

