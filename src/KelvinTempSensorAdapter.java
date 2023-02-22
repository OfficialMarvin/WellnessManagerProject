public class KelvinTempSensorAdapter implements ITempSensor{
    private KelvinTempSensor kelvinTemp;

    public KelvinTempSensorAdapter (KelvinTempSensor kelvinTemp){
        this.kelvinTemp = kelvinTemp;
    }


    @Override
    public int reading() {
        return kelvinTemp.reading();
    }
}
