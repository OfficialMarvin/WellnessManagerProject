import javax.swing.*;

public class LogView1 {
    private JPanel panel1;
    private JPanel jPanel;
    private JRadioButton basicFoodRadioButton;
    private JRadioButton Recipe;
    private JButton addButton;
    private JTextField nameTxtField;
    private JTextField proteinTxtField;
    private JTextField caloriesTxtfield;
    private JTextField carbTxtField;
    private JTextField fatTxtField;
    private JLabel nameLbl;
    private JLabel Calories;
    private JLabel fatLbl;
    private JLabel carbLbl;
    private JLabel proteinLbl;
    private JTextField recipeField;
    private JTextField ingredientsField;
    private JLabel recipeLbl;
    private JLabel ingredientsLbl;
    private JTextField weightField;
    private JLabel weightLbl;
    private JTextArea textArea1;


    public JPanel getjPanel() {
        return jPanel;
    }

    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }

    public JRadioButton getBasicFoodRadioButton() {
        return basicFoodRadioButton;
    }

    public void setBasicFoodRadioButton(JRadioButton basicFoodRadioButton) {
        this.basicFoodRadioButton = basicFoodRadioButton;
    }

    public JRadioButton getRecipe() {
        return Recipe;
    }

    public void setRecipe(JRadioButton recipe) {
        Recipe = recipe;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public JTextField getNameTxtField() {
        return nameTxtField;
    }

    public void setNameTxtField(JTextField nameTxtField) {
        this.nameTxtField = nameTxtField;
    }

    public JTextField getProteinTxtField() {
        return proteinTxtField;
    }

    public void setProteinTxtField(JTextField proteinTxtField) {
        this.proteinTxtField = proteinTxtField;
    }

    public JTextField getCaloriesTxtfield() {
        return caloriesTxtfield;
    }

    public void setCaloriesTxtfield(JTextField caloriesTxtfield) {
        this.caloriesTxtfield = caloriesTxtfield;
    }

    public JTextField getCarbTxtField() {
        return carbTxtField;
    }

    public void setCarbTxtField(JTextField carbTxtField) {
        this.carbTxtField = carbTxtField;
    }

    public JTextField getFatTxtField() {
        return fatTxtField;
    }

    public void setFatTxtField(JTextField fatTxtField) {
        this.fatTxtField = fatTxtField;
    }

    public JLabel getNameLbl() {
        return nameLbl;
    }

    public void setNameLbl(JLabel nameLbl) {
        this.nameLbl = nameLbl;
    }

    public JLabel getCalories() {
        return Calories;
    }

    public void setCalories(JLabel calories) {
        Calories = calories;
    }

    public JLabel getFatLbl() {
        return fatLbl;
    }

    public void setFatLbl(JLabel fatLbl) {
        this.fatLbl = fatLbl;
    }

    public JLabel getCarbLbl() {
        return carbLbl;
    }

    public void setCarbLbl(JLabel carbLbl) {
        this.carbLbl = carbLbl;
    }

    public JLabel getProteinLbl() {
        return proteinLbl;
    }

    public void setProteinLbl(JLabel proteinLbl) {
        this.proteinLbl = proteinLbl;
    }

    public JTextField getRecipeField() {
        return recipeField;
    }

    public void setRecipeField(JTextField recipeField) {
        this.recipeField = recipeField;
    }

    public JTextField getIngredientsField() {
        return ingredientsField;
    }

    public void setIngredientsField(JTextField ingredientsField) {
        this.ingredientsField = ingredientsField;
    }

    public JLabel getRecipeLbl() {
        return recipeLbl;
    }

    public void setRecipeLbl(JLabel recipeLbl) {
        this.recipeLbl = recipeLbl;
    }

    public JLabel getIngredientsLbl() {
        return ingredientsLbl;
    }

    public void setIngredientsLbl(JLabel ingredientsLbl) {
        this.ingredientsLbl = ingredientsLbl;
    }

    public JTextField getWeightField() {
        return weightField;
    }

    public void setWeightField(JTextField weightField) {
        this.weightField = weightField;
    }

    public JLabel getWeightLbl() {
        return weightLbl;
    }

    public void setWeightLbl(JLabel weightLbl) {
        this.weightLbl = weightLbl;
    }

    public JTextArea getTextArea1() {
        return textArea1;
    }

    public void setTextArea1(JTextArea textArea1) {
        this.textArea1 = textArea1;
    }

    void displayDailyLog(DailyLog dailyLog) {

    }

    void displayNutritionBreakdown(double fatPercentage, double carbPercentage, double proteinPercentage) {

    }

    static void displayError(String message) {

    }


    public JPanel getMyPanel(){
        return panel1;
    }

}