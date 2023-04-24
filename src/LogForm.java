import javax.swing.*;

public class LogForm extends JFrame {
    private final LogView1 logView1;
    private BasicFood basicFoodModel;
    private DailyLog dailyLogModel;
    private FoodCollection foodCollectionModel;
    private Recipe recipeModel;
    private LogForm logForm;

    private LogController logController;



    public LogView1 getLogView1() {
        return logView1;
    }

    public LogForm(){
        this.logView1 = new LogView1();

        LogController1 logController1 = new LogController1(basicFoodModel, dailyLogModel, foodCollectionModel, logController, recipeModel, this);

        JPanel content = logView1.getMyPanel();

        this.setContentPane(content);
        this.pack();

        this.setTitle("Log Gui");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public LogView1 logView1() {return logView1;}
}
