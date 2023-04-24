import java.time.LocalDate;

public class LogModel{
    DailyLog loadLogData(String fileName) {
        DailyLog.loadLog();
        return null;
    }

    void saveLogData(LocalDate date, String fileName) {
        DailyLog.saveLog();
    }

    void loadFoodData(String fileName) {
        FoodCollection.loadFoods();

    }

    void saveFoodData(String fileName) {
        FoodCollection.saveFoods();
    }

}

