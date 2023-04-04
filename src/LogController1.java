import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class LogController1 {
    private BasicFood basicFoodModel;
    private DailyLog dailyLogModel;
    private FoodCollection foodCollectionModel;
    private Recipe recipeModel;
    private LogForm logForm;

    private LogController logController;

    private ArrayList<BasicFood> basicFoodArrayList = new ArrayList<>();

    public LogController1(BasicFood basicFoodModel, DailyLog dailyLogModel, FoodCollection foodCollectionModel,LogController logController,Recipe recipeModel, LogForm logForm){
        this.basicFoodModel = basicFoodModel;
        this.dailyLogModel = dailyLogModel;
        this.foodCollectionModel = foodCollectionModel;
        this.recipeModel = recipeModel;
        this.logController = logController;
        this.logForm = logForm;

        logForm.logView1().getAddButton().addActionListener(e ->{
            String foodName = logForm.logView1().getNameTxtField().getText();
            String calories = logForm.logView1().getCaloriesTxtfield().getText();
            String fat = logForm.logView1().getFatTxtField().getText();
            String protein = logForm.logView1().getProteinTxtField().getText();
            String carb = logForm.logView1().getCarbTxtField().getText();
            String weight = logForm.logView1().getWeightField().getText();

            String recipe = logForm.logView1().getRecipeField().getText();
            String ingredientsAndQuantity = logForm.logView1().getIngredientsField().getText();

            JRadioButton buttonForBasicFood = logForm.logView1().getBasicFoodRadioButton();
            JRadioButton buttonForRecipe = logForm.logView1().getRecipe();

            JTextArea areaForText = logForm.logView1().getTextArea1();

            String todaysDate = LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear();

            double caloriesNum = Double.parseDouble(calories);
            double fatNum = Double.parseDouble(fat);
            double proteinNum = Double.parseDouble(protein);
            double carbNum = Double.parseDouble(carb);
            double weightNum = Double.parseDouble(weight);

            if(buttonForBasicFood.isSelected()){
               BasicFood newBasicFood = new BasicFood(foodName,caloriesNum,fatNum,carbNum,proteinNum);
               logController.addBasicFood(foodName,caloriesNum,fatNum,carbNum,proteinNum);
               basicFoodArrayList.add(newBasicFood);
            }
            else if(buttonForRecipe.isSelected()){
                Recipe newRecipe = new Recipe(recipe);
                //FIGURE OUT HOW TO ADD INGREDIENTS AND NUMBERS, it does not need to be map I dont think. Cause that makes GUI so
                //hard to do.
            }

            logForm.logView1().getNameTxtField().setText(" ");
               logForm.logView1().getCaloriesTxtfield().setText(" ");
               logForm.logView1().getFatTxtField().setText(" ");
               logForm.logView1().getProteinTxtField().setText(" ");
               logForm.logView1().getCarbTxtField().setText(" ");
               logForm.logView1().getWeightField().setText(" ");
               logForm.logView1().getRecipeField().setText(" ");
               logForm.logView1().getIngredientsField().setText(" ");

                logForm.logView1().getTextArea1().setText(basicFoodArrayList.toString());



        });
    }


}
