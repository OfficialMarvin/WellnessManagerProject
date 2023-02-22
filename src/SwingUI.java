/*
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *
 * Acknowledgements
 */

/*
 * Swing UI class used for displaying the information from the
 * associated weather station object.
 *
 * This is an extension of JFrame, the outermost container in
 * a Swing application.
 *
 * Also implements Observer so we can receive update notifications
 * from the attached WeatherStation.
 */

import java.awt.Font ;
import java.awt.GridLayout ;

import javax.swing.JFrame ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;

import java.util.Observable ;
import java.util.Observer ;

public class SwingUI extends JFrame implements Observer {
    private JLabel celsiusField ;   // put current celsius reading here
    private JLabel kelvinField ;    // put current kelvin reading here

    private JLabel farenheitField;

    private JLabel inchesField;

    private JLabel millibarsField;

    /*
     * The weather station we are observing.
     */
    private final WeatherStation station ;

    /*
     * A Font object contains information on the font to be used to
     * render text.
     */
    private static Font labelFont =
        new Font(Font.SERIF, Font.PLAIN, 72) ;

    /*
     * Create and populate the SwingUI JFrame with panels and labels to
     * show the temperatures.
     */
    public SwingUI(WeatherStation station) {
        super("Weather Station") ;

        /*
         * Remember our weather station and register ourselves
         * as an observer.
         */
        this.station = station ;
        this.station.addObserver(this) ;

        /*
         * WeatherStation frame is a grid of 1 row by an indefinite
         * number of columns.
         */
        this.setLayout(new GridLayout(1,0)) ;

        /*
         * There are two panels, one each for Kelvin and Celsius, added to the
         * frame. Each Panel is a 2 row by 1 column grid, with the temperature
         * name in the first row and the temperature itself in the second row.
         */
        JPanel panel ;

        /*
         * Set up Kelvin display.
         */
        panel = new JPanel(new GridLayout(2,1)) ;
        this.add(panel) ;
        createLabel(" Kelvin ", panel) ;
        kelvinField = createLabel("", panel) ;

        /*
         * Set up Celsius display.
         */
        panel = new JPanel(new GridLayout(2,1)) ;
        this.add(panel) ;
        createLabel(" Celsius ", panel) ;
        celsiusField = createLabel("", panel) ;

        /*
        Set up Farenheit
         */
        panel = new JPanel(new GridLayout(2,1));
        this.add(panel);
        createLabel(" Farenheit", panel);
        farenheitField = createLabel("",panel);

        /*
        Set up Inches
         */
        panel = new JPanel(new GridLayout(2,1));
        this.add(panel);
        createLabel(" Inches", panel);
        inchesField = createLabel("",panel);

        /*
        Set up Millibars
         */
        panel = new JPanel(new GridLayout(2,1));
        this.add(panel);
        createLabel(" Millibars", panel);
        millibarsField = createLabel("",panel);
         /*
         * Set up the frame's default close operation pack its elements,
         * and make the frame visible.
         */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        this.pack() ;
        this.setVisible(true) ;
    }

    /*
     * Create a Label with the initial value <title>, place it in
     * the specified <panel>, and return a reference to the Label
     * in case the caller wants to remember it.
     */
    private JLabel createLabel(String title, JPanel panel) {
        JLabel label = new JLabel(title) ;

        label.setHorizontalAlignment(JLabel.CENTER) ;
        label.setVerticalAlignment(JLabel.TOP) ;
        label.setFont(labelFont) ;
        panel.add(label) ;

        return label ;
    }

    /*
     * Called when WeatherStation gets another reading.
     * The Observable should be the station; the Object
     * argument is ignored.
     */
    public void update(Observable obs, Object ignore) {
        /*
         * Check for spurious updates from unrelated objects.
         */
        if( station != obs ) {
            return ;
        }
        /*
         * Retrieve temperatures and save them in the
         * labels.
         */
        setKelvinJLabel( station.getKelvin() ) ;
        setCelsiusJLabel( station.getCelsius() ) ;
        setFarenheitField(station.getFarenheit());
        setInchesField(station.getInches());
        setMillibarsField(station.getMillibars());
    }

    /*
     * Set the label holding the Kelvin temperature.
     * Now private as it is set from inside the Observer.
     */
    private void setKelvinJLabel(double temperature) {
        kelvinField.setText(String.format("%6.2f", temperature)) ;
    }

    /*
     * Set the label holding the Celsius temperature.
     * Now private as it is set from inside the Observer.
     */
    private void setCelsiusJLabel(double temperature) {
        celsiusField.setText(String.format("%6.2f", temperature)) ;
    }

    private void setFarenheitField(double temperature){farenheitField.setText(String.format("%6.2f",temperature));}

    private void setInchesField(double temperature){inchesField.setText(String.format("%6.2f",temperature));}

    private void setMillibarsField(double temperature){millibarsField.setText(String.format("%6.2f",temperature));}


    /*
     * Start the application.
     */
    public static void main(String[] args) {
        WeatherStation ws = new WeatherStation() ;
        Thread thread = new Thread(ws) ;
        SwingUI ui = new SwingUI(ws) ;
        AWTUI awtui= new AWTUI(ws);
        TextUI textUI = new TextUI(ws);

        thread.start() ;
    }
}
