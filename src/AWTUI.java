/*
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *
 * Acknowledgements
 */

/*
 * AWT UI class used for displaying the information from the
 * associated weather station object.
 * This is an extension of JFrame, the outermost container in
 * a AWT application.
 */

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

public class AWTUI extends Frame implements Observer {
    public Label celsiusField ;   // put current celsius reading here
    public Label kelvinField ;    // put current kelvin reading here

    private final WeatherStation station ;


    /*
     * A Font object contains information on the font to be used to
     * render text.
     */


    private static Font labelFont =
        new Font(Font.SERIF, Font.PLAIN, 72) ;

    /*
     * Create and populate the AWTUI JFrame with panels and labels to
     * show the temperatures.
     */
    public AWTUI(WeatherStation station) {
        super("Weather Station") ;
        this.station = station;
        station.addObserver(this);
        this.setLocation(500,500);

        /*
         * WeatherStation frame is a grid of 1 row by an indefinite number
         * of columns.
         */
        setLayout(new GridLayout(1,0)) ;

        /*
         * There are two panels, one each for Kelvin and Celsius, added to the
         * frame. Each Panel is a 2 row by 1 column grid, with the temperature
         * name in the first row and the temperature itself in the second row.
         */

        /*
         * Set up Kelvin display.
         */
        Panel panel = new Panel(new GridLayout(2,1)) ;
        add(panel) ;
        setLabel(" Kelvin ", panel) ;
        kelvinField = setLabel("", panel) ;

        /*
         * Set up Celsius display.
         */
        panel = new Panel(new GridLayout(2,1)) ;
        add(panel) ;
        setLabel(" Celsius ", panel) ;
        celsiusField = setLabel("", panel) ;

        /*
         * Set up the window's default close operation and pack its elements.
         */
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent windowEvent){
                        System.exit(0);
                    }        
                });

        /*
         * Pack the components in this frame and make the frame visible.
         */
        pack() ;
        setVisible(true) ;
    }

    /*
     * Create a Label with the initial value <title>, place it in
     * the specified <panel>, and return a reference to the Label
     * in case the caller wants to remember it.
     */
    private Label setLabel(String title, Panel panel) {
        Label label = new Label(title) ;

        label.setAlignment(Label.CENTER) ;
        label.setFont(labelFont) ;
        panel.add(label) ;

        return label ;
    }

    public void update(Observable o, Object arg) {
        if(station != o){
            return;
        }
        celsiusField.setText(String.valueOf(station.getCelsius()));
        kelvinField.setText(String.valueOf(station.getKelvin()));
    }
}
