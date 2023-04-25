import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogController1{
    private BasicFood basicFoodModel;
    private DailyLog dailyLogModel;
    private FoodCollection foodCollectionModel;
    private Recipe recipeModel;
    private LogForm logForm;

    LogModel logModel = new LogModel();
    LogView1 logView = new LogView1();
    double height = 1.75;

    LogController logController;

    private ArrayList<BasicFood> basicFoodArrayList = new ArrayList<>();


    public LogController1(BasicFood basicFoodModel, DailyLog dailyLogModel, FoodCollection foodCollectionModel,LogController logController,Recipe recipeModel, LogForm logForm) {
        this.basicFoodModel = basicFoodModel;
        this.dailyLogModel = dailyLogModel;
        this.foodCollectionModel = foodCollectionModel;
        this.recipeModel = recipeModel;
        this.logController = new LogController(logModel, logView, height);
        this.logForm = logForm;
        System.out.println(logModel);
        System.out.println(logView);
        System.out.println(this.logController);



        logForm.logView1().getComboBox1().addActionListener(e -> {
            String selectedOption = (String) logForm.logView1().getComboBox1().getSelectedItem();

            if (selectedOption.equals("Daily Food Items Consumed")) {
                logForm.logView1().setDisplay(DailyLog.getDayFood());

            } else if (selectedOption.equals("Daily Exercises Performed")) {
                logForm.logView1().setDisplay(DailyLog.getDayExer());
            } else if (selectedOption.equals("Daily Consumed Calories")) {
                logForm.logView1().setDisplay("Calories gained on day: "+ String.valueOf(DailyLog.getDayCals()));
            } else if (selectedOption.equals("Daily Expended Calories")) {
                logForm.logView1().setDisplay("Calories burned on day: "+ String.valueOf(DailyLog.getDayBurned()));
            } else if (selectedOption.equals("Daily Net Calories")) {
                logForm.logView1().setDisplay("Net calories on day: "+ String.valueOf(DailyLog.getDayCals()-DailyLog.getDayBurned()));
            } else if (selectedOption.equals("Daily Weight")) {
                logForm.logView1().setDisplay("Weight on day: "+ String.valueOf(DailyLog.getDayWeight()));
            } else if (selectedOption.equals("Daily Nutrition Breakdown")) {

            } else if (selectedOption.equals("Display Nothing")) {
                logForm.logView1().setDisplay("");
            }
        });


        logForm.logView1().getDateButton().addActionListener(e -> {
        String dateString = logForm.logView1().getDateField().getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        DailyLog.setCurrentDate(date);
                });



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

            String calLimit = logForm.logView1().getCalTxtField().getText();

            String exersize = logForm.logView1().getExerTxtField().getText();
            String burned = logForm.logView1().getBurnTxtField().getText();
            String ExerLog = logForm.logView1().getExerLogField().getText();
            String mins = logForm.logView1().getMinsField().getText();
            String foodCount = logForm.logView1().getFoodCountField().getText();

            JTextArea areaForText = logForm.logView1().getTextArea1();

            String todaysDate = LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear();

            System.out.println(foodName + todaysDate);

            double caloriesNum = 0.0;
            if (!calories.isEmpty()) {
                caloriesNum = Double.parseDouble(calories);
            }

            double fatNum = 0.0;
            if (!fat.isEmpty()) {
                fatNum = Double.parseDouble(fat);
            }

            double proteinNum = 0.0;
            if (!protein.isEmpty()) {
                proteinNum = Double.parseDouble(protein);
            }

            double carbNum = 0.0;
            if (!carb.isEmpty()) {
                carbNum = Double.parseDouble(carb);
            }

            double weightNum = 0.0;
            if (!weight.isEmpty()) {
                weightNum = Double.parseDouble(weight);
            }

            if(buttonForBasicFood.isSelected()){
               BasicFood newBasicFood = new BasicFood(foodName,caloriesNum,fatNum,carbNum,proteinNum);
                System.out.println("trying to add basic food" + foodName+caloriesNum+fatNum+carbNum+proteinNum);
                this.logController.addBasicFood(foodName,caloriesNum,fatNum,carbNum,proteinNum);
               basicFoodArrayList.add(newBasicFood);
                this.logController.saveData();
            }
            else if(buttonForRecipe.isSelected()){
                Recipe newRecipe = new Recipe(recipe);
                this.logController.addRecipe(recipe, ingredientsAndQuantity);
                this.logController.saveData();
            }

            if(!exersize.isEmpty() & !burned.isEmpty()){
                this.logController.addExer(exersize, Float.parseFloat(burned));
                System.out.println("exersize sent log1");
            }

            if(!weight.isEmpty()){
                System.out.println("NOT EMPTY");
                DailyLog.setWeight(weightNum);
            }

            if(!calories.isEmpty()){
                DailyLog.setCalorieLimit(caloriesNum);
            }

            if(!ExerLog.isEmpty() & !mins.isEmpty()){
                DailyLog.addExerciseEntry(ExerLog, Double.parseDouble(mins));
            }

            if(!foodCount.isEmpty()){
                String[] parts = foodCount.split(",");
                DailyLog.logFood(parts[0],Double.parseDouble(parts[1]));
            }
            logForm.logView1().getNameTxtField().setText(" ");
               logForm.logView1().getCaloriesTxtfield().setText(" ");
               logForm.logView1().getFatTxtField().setText(" ");
               logForm.logView1().getProteinTxtField().setText(" ");
               logForm.logView1().getCarbTxtField().setText(" ");
               logForm.logView1().getWeightField().setText(" ");
               logForm.logView1().getRecipeField().setText(" ");
               logForm.logView1().getIngredientsField().setText(" ");
            logForm.logView1().getCalTxtField().setText(" ");
            logForm.logView1().getBurnTxtField().setText(" ");
            logForm.logView1().getExerTxtField().setText(" ");
            logForm.logView1().getExerLogField().setText(" ");
            logForm.logView1().getMinsField().setText(" ");

                //logForm.logView1().getTextArea1().setText(basicFoodArrayList.toString());



        });
    }


}
