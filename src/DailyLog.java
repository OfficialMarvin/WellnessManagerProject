import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DailyLog {

    private double weight; // in kilograms
    private double calorieLimit;
    private final Map<String, Double> foodEntries = new HashMap<>();

    public void addFoodEntry(String foodName, double count) {
        Double existingCount = foodEntries.get(foodName);
        if (existingCount == null) {
            existingCount = 0.0;
        }
        foodEntries.put(foodName, existingCount + count);
    }

    public void removeFoodEntry(String foodName) {
        foodEntries.remove(foodName);
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }


    public boolean checkCalorieLimit(FoodCollection foods){
        double totalCalories=0.0;

        for(Map.Entry<String, Double> entry :foodEntries.entrySet()){
            FoodComponent fc=foods.getFood(entry.getKey());
            totalCalories+=fc.getCalories()*entry.getValue();
        }
        return totalCalories<=calorieLimit;
    }

    public boolean isUnderWeight(){
        // arbitrary limit to consider underweight: BMI < 18.5
        final int UNDERWEIGHT_BMI_THRESHOLD=18;

        try{
            double bmiValue=(weight/(LogController.height*LogController.height));

            if(bmiValue<UNDERWEIGHT_BMI_THRESHOLD)
                return true;
            else
                return false;

        }catch(ArithmeticException e){
            System.out.println("Please enter valid height values");
        }

        return false;//default

    }







    public void setCalorieLimit(double calorieLimit) {
        this.calorieLimit = calorieLimit;
    }

    public double getCalorieLimit() {
        return calorieLimit;
    }

    //Getter fro food entries
    public Map<String, Double> getFoodEntries(){
        return foodEntries;
    }

}