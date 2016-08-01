package developer.pardeep.workin;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PacerActiviy extends AppCompatActivity implements SensorEventListener,View.OnClickListener {

    private SensorManager sensorManager;
    private Sensor accelerateSensor;
    private  static long lastTime=0;
    private static int startingValue=0;
    private static int lastValue=1000;
    private static final int thresholdValue=10;
    private float lastX,lastY,lastZ;
    TextView counterTextView;

    TextView xAxis,yAxis,zAxis;

    Button buttonPause,buttonResume;
    private static boolean isPauseButtonActivate=false;
    private static float[] gravityComp=new float[3];
    private static float[] linearAcceleration=new float[3];
    final float alpha=0.8f;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacer_activiy);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        counterTextView=(TextView)findViewById(R.id.counterValue);
        buttonPause=(Button)findViewById(R.id.pauseButton);
        buttonResume=(Button)findViewById(R.id.playButton);
        buttonPause.setOnClickListener(this);
        buttonResume.setOnClickListener(this);

        xAxis=(TextView)findViewById(R.id.xvalueTextView);
        yAxis=(TextView)findViewById(R.id.yvalueTextView);
        zAxis=(TextView)findViewById(R.id.zvalueTextView);

        buttonResume.setEnabled(false);
        buttonResume.setVisibility(View.GONE);

        //  Initialise sensor

        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerateSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this,accelerateSensor,SensorManager.SENSOR_DELAY_NORMAL);




    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerateSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor=event.sensor;
        if(sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            float x=event.values[0];
            float y=event.values[1];
            float z=event.values[2];

            /*gravityComp[0]=lowPassFilter(x,gravityComp[0]);
            gravityComp[1]=lowPassFilter(y,gravityComp[1]);
            gravityComp[2]=lowPassFilter(z,gravityComp[2]);

            linearAcceleration[0]=x-gravityComp[0];
            linearAcceleration[1]=y-gravityComp[1];
            linearAcceleration[2]=z-gravityComp[2];*/
            long currentTimeValue=System.currentTimeMillis();

            //System.out.println("Current time :"+currentTimeValue);

            if((currentTimeValue-lastTime)>100){



                long differenceTime=currentTimeValue-lastTime;
                lastTime=differenceTime;
                // float speed=Math.abs(gravityComp[0]+gravityComp[1]+gravityComp[2]-lastX-lastY-lastZ)/differenceTime*10000;
                float speed=Math.abs(x+y+z-lastX-lastY-lastZ)/differenceTime*10000;

                // float speed=Math.abs(linearAcceleration[0]+linearAcceleration[1]+linearAcceleration[2]-lastX-lastY-lastZ)/differenceTime*10000;

                System.out.println(Math.abs(speed));
                if(speed>5 ) {
                    startingValue=startingValue+1;
                    counterTextView.setText(String.valueOf(startingValue));
                   /*xAxis.setText(""+gravityComp[0]);
                    yAxis.setText(""+gravityComp[1]);
                    zAxis.setText(""+gravityComp[2]);

                        lastX = gravityComp[0];
                        lastY = gravityComp[1];
                        lastZ = gravityComp[2];
                xAxis.setText(""+linearAcceleration[0]);
                yAxis.setText(""+linearAcceleration[1]);
                zAxis.setText(""+linearAcceleration[2]);

                lastX = linearAcceleration[0];
                lastY = linearAcceleration[1];
                lastZ = linearAcceleration[2];*/
                    xAxis.setText(""+x);
                    yAxis.setText(""+y);
                    zAxis.setText(""+z);

                    lastX = x;
                    lastY = y;
                    lastZ = z;


                }

            }

        }




    }

    private void displayStepCounter() {
        startingValue++;
        if(startingValue<lastValue){
            counterTextView.setText(""+startingValue);
        }
    }

    private float lowPassFilter(float x, float v) {
        return alpha*v+(1-alpha)*x;
    }

    private void onStepCount() {

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View v) {
        if(v==buttonPause){
            isPauseButtonActivate=true;
            buttonPause.setEnabled(false);
            buttonPause.setVisibility(View.GONE);
            buttonResume.setEnabled(true);
            buttonResume.setVisibility(View.VISIBLE);
            sensorManager.unregisterListener(this);
        }
        else if(v==buttonResume){
            if(isPauseButtonActivate){
                isPauseButtonActivate=false;
                buttonResume.setEnabled(false);
                buttonResume.setVisibility(View.GONE);
                buttonPause.setEnabled(true);
                buttonPause.setVisibility(View.VISIBLE);
                sensorManager.registerListener(this,accelerateSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(PacerActiviy.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
