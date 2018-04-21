package kishnani.deepali.lenovo.logdriving;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;
    public static final String MSG = "state_msg";

    private SensorManager mSensorManager;
    private Sensor mSensor;
    int counter = 0;
    static TextView text_gyro;
    static TextView edit_values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor);


        text_gyro = (TextView) findViewById(R.id.text_title);
        edit_values = (TextView) findViewById(R.id.text_title);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {

            text_gyro.setText("Gyroscope: Available");
        } else {

            text_gyro.setText("Gyroscope: Not Available");
        }

        final Button testButton = (Button) findViewById(R.id.button);
        testButton.setTag(1);
        testButton.setText("Start");
        // final Intent intent=new Intent(MainActivity.class,GyroLoggerService.class)
        final Intent intent = new Intent(MainActivity.this, GyroLoggerService.class);

        testButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final int status = (Integer) v.getTag();
                if (status == 1) {
                    testButton.setText("Stop");
                    v.setTag(0); //pause
                    edit_values.setText("registered");
                    String message = "Start";
                    intent.putExtra(MSG, message);

                    startService(intent);

                    //mSensorManager.registerListener(sensorlistener, mSensor, SensorManager.SENSOR_DELAY_FASTEST);

                } else if (status == 2) {
                    edit_values.setText("resumed");
                    testButton.setText("Stop");
                    v.setTag(0); //pause
                    String message = "Resume";
                    intent.putExtra(MSG, message);
                    startService(intent);
                } else {
                    testButton.setText("Resume");
                    v.setTag(1); //pause
                   GyroLoggerService.mSensorManager.unregisterListener(GyroLoggerService.sens);;
                    edit_values.setText("unregistered");

                }
            }
        });
    }

}