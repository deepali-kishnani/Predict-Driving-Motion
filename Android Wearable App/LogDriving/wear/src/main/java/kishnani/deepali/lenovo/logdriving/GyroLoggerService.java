package kishnani.deepali.lenovo.logdriving;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.util.FloatMath.*;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import static java.lang.Math.sqrt;

/**
 * Created by Lenovo on 10/2/2015.
 */
public class GyroLoggerService extends Service implements SensorEventListener{

    private long lastUpdate = 0;
    public static void AppendLog(String filename, String text) {
        File log;
        log = new File("sdcard/" + filename);
        if (!log.exists()) {
            try {
                log.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(log, true));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // String sLogEntry = Log.Tag(severity) + text;
            String sLogEntry = text;
            writer.append(sLogEntry);
            writer.newLine();
            writer.close();
            System.out.println(sLogEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

        private PowerManager.WakeLock wakeLock;
        public static SensorManager mSensorManager;
        public static SensorEventListener sens;
        private Sensor mGravitySensor;
        private Sensor mRotationSensor;
        private long attempt =0;
        private long counter =0;
        public GyroLoggerService()
        {
        }

        @Override
        public IBinder onBind(Intent intent)
        {
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }


        @Override
        public void onCreate()
        {
            Log.v("LOG", "service onCreate()");
        }



        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("LOG", "service onStartCommand()");
            String message=intent.getStringExtra(MainActivity.MSG);
            if(message.equals("Start")) {

                wakeLock = ((PowerManager) getSystemService(POWER_SERVICE)).newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelock");
                wakeLock.acquire();

                mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

                mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                mSensorManager.registerListener(this, mRotationSensor, SensorManager.SENSOR_DELAY_NORMAL);

            }
            attempt++;
            sens = GyroLoggerService.this;
        return Service.START_STICKY;
    }


        public void OnPause(){
            mSensorManager.unregisterListener(this);
        }

        @Override
        public void onDestroy() {

        mSensorManager.unregisterListener(this);
        wakeLock.release();
        Log.v("LOG", "service onDestroy()");

    }


        @Override
        public void onSensorChanged(SensorEvent event)
        {
            String output = String.format("X:%s Y:%s Z:%s at %s", event.values[0], event.values[1], event.values[2], event.timestamp);

            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

            if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
                return;
            }

            String x = Float.toString(event.values[0]);
            String y = Float.toString(event.values[1]);
            String z = Float.toString(event.values[2]);

            Double accel = sqrt(event.values[0] * event.values[0] + event.values[1] * event.values[1] + event.values[2] * event.values[2]);

            long millis = System.currentTimeMillis();


// In onSensorChanged:
            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 1000) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                AppendLog("GyroLogDriving5", "\t" + currentDateTimeString + "\t" + accel + "\t");

            }

            counter += 1;


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    }