import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static LogController logController;

    public static void main(String[] args) {

        // initialize logcontroller with model and view
        LogModel logModel = new LogModel();
        LogView1 logView = new LogView1();
        LogForm logForm = new LogForm();
        logForm.setVisible(true);

        double height = 1.75; // Set the height to 1.75 meters for example purposes
        logController = new LogController(logModel, logForm.logView1(), height);

        // load data
        logController.loadData();

        while (true) {
            // choices
            System.out.println("Select an option: ");
            System.out.println("1. Change selected date");
            System.out.println("2. Add food item to log");
            System.out.println("3. Remove food item from log");
            System.out.println("4. Change daily calorie limit and weight");
            System.out.println("5. Add new basic food");
            System.out.println("6. Add new recipe");
            System.out.println("7. View food log for selected date");
            System.out.println("8. Save data and exit");
            System.out.println();

            // input
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    // change date
                    System.out.println("Enter the date to select (yyyy-mm-dd): ");
                    String dateStr = scanner.nextLine();
                    LocalDate date = LocalDate.parse(dateStr);
                    logController.selectDate(date);
                    System.out.println("Selected date: " + date);
                    break;
                case 2:
                    // log food item
                    System.out.println("Enter the name of the food to add: ");
                    String foodName = scanner.nextLine();
                    System.out.println("Enter the quantity of servings: ");
                    double count = scanner.nextDouble();
                    scanner.nextLine(); // Consume the remaining newline character
                    logController.addFoodToLog(foodName, count);
                    break;
                case 3:
                    // unlog food item
                    System.out.println("Enter the name of the food to remove: ");
                    foodName = scanner.nextLine();
                    logController.removeFoodFromLog(foodName);
                    break;
                case 4:
                    // update weight and calorie limit
                    System.out.println("Enter your weight in kilograms: ");
                    double weight = scanner.nextDouble();
                    System.out.println("Enter your calorie limit for the day: ");
                    double calorieLimit = scanner.nextDouble();
                    scanner.nextLine(); // Consume the remaining newline character
                    logController.updateWeight(weight);
                    logController.updateCalorieLimit(calorieLimit);
                    break;
                case 5:
                    // add food
                    System.out.println("Enter the name of the food: ");
                    foodName = scanner.nextLine();
                    System.out.println("Enter the number of calories per serving: ");
                    int calories = scanner.nextInt();
                    System.out.println("Enter the number of grams of fat per serving: ");
                    double fat = scanner.nextDouble();
                    System.out.println("Enter the number of grams of carbohydrates per serving: ");
                    double carbs = scanner.nextDouble();
                    System.out.println("Enter the number of grams of protein per serving: ");
                    double protein = scanner.nextDouble();
                    scanner.nextLine(); // Consume the remaining newline character
                    logController.addBasicFood(foodName, calories, fat, carbs, protein);
                    break;
                case 6:
                    // add recipe
                    System.out.println("Enter the name of the recipe: ");
                    String recipeName = scanner.nextLine();
                    System.out.println("Enter the number of servings in the recipe: ");
                    double recipeServings = scanner.nextDouble();
                    scanner.nextLine(); // Consume the remaining newline character

                    List<String> ingredients = new ArrayList<>();
                    List<Double> quantities = new ArrayList<>();

                    boolean addIngredients = true;
                    while (addIngredients) {
                        System.out.println("Enter the name of an ingredient, or 'done' to finish:");
                        String ingredientName = scanner.nextLine();
                        if (ingredientName.equalsIgnoreCase("done")) {
                            addIngredients = false;
                        } else {
                            System.out.println("Enter the quantity of " + ingredientName + " in grams:");
                            double ingredientQuantity = scanner.nextDouble();
                            scanner.nextLine(); // Consume the remaining newline character
                            ingredients.add(ingredientName);
                            quantities.add(ingredientQuantity);
                        }
                    }

                    logController.addRecipe(recipeName, ingredients, quantities);
                    break;
                case 7:
                    // log for specific date
                    //logView.printLog(logController.getLogForSelectedDate());
                    System.out.println("work in progress");
                    break;
                case 8:
                    // Save & exit
                    logController.saveData();
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }
}
