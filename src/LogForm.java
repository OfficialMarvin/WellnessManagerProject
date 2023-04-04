import javax.swing.*;

public class LogForm extends JFrame {
    private final LogView1 logView1;



    public LogView1 getLogView1() {
        return logView1;
    }

    public LogForm(){
        this.logView1 = new LogView1();

        JPanel content = logView1.getMyPanel();

        this.setContentPane(content);
        this.pack();

        this.setTitle("Log Gui");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public LogView1 logView1() {return logView1;}
}
