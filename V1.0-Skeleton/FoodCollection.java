import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodCollection {
    private Map<String, FoodComponent> foods = new HashMap<>();

    public void addFood(FoodComponent food) {
        foods.put(food.getName(), food);
    }

    public FoodComponent getFood(String name) {
        return foods.get(name);
    }

    /**
     * Load data from given 'fileName' and create corresponding BasicFood or Recipe objects.
     */
    public void loadFoods(String fileName){
        Path filePath = Paths.get(fileName);
        if(!Files.exists(filePath)){
            System.out.println("File not exists!");
            return;
        }

        try(BufferedReader reader = Files.newBufferedReader(filePath)){

            String line;
            while((line=reader.readLine()) !=null){

                String[] fields = line.split(",");

                if(fields.length == 5){ // only two fields present, this must be a basic food
                    String name = fields[0].trim();
                    double calories = Double.parseDouble(fields[1]);
                    double fat = Double.parseDouble(fields[2]);
                    double carb = Double.parseDouble(fields[3]);
                    double protien = Double.parseDouble(fields[4]);
                    addFood(new BasicFood(name, calories, fat, carb, protien));

                }else{ // We assume its recipe with multiple ingredients and their counts

                    String name = fields[0].trim();

                    Recipe recipe= new Recipe(name);

                    for(int i=1;i<fields.length-1; i+=2 ){
                        FoodComponent ingredient=getFood(fields[i]);
                        double count=Double.parseDouble(fields[i+1]);

                        if(ingredient==null)
                            throw new RuntimeException("Ingredient "+fields[i]+" is missing in the collection.");

                        recipe.addFood(ingredient,count);

                    }
                    addFood(recipe);
                }

            }

        }catch(IOException e){
            System.out.println(e);//handle exception in better way to inform user of IO error..
        }
    }


    public void saveFoods(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (FoodComponent food : foods.values()) {
                if (food instanceof BasicFood) {
                    writer.write(food.getName() + "," + food.getCalories());
                } else if (food instanceof Recipe) {
                    StringBuilder sb = new StringBuilder();
                    sb.append((String) ((Recipe) food).getName());

                    List<FoodComponent> ingredientList=new ArrayList<>( ((Recipe)food).getIngredients().keySet());

                    // write each recipe's ingredient and their count in "name,count" format
                    for(FoodComponent ingredient:ingredientList){
                        sb.append(","+ingredient.getName()+","+((Recipe)food).getIngredients().get(ingredient));
                    }

                    writer.write(sb.toString());
                }

                writer.newLine();
            }

        } catch(IOException e ){
            System.out.println(e);
            //handle or propagate exception..
        }
    }

}