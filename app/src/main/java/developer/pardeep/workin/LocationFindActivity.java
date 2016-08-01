package developer.pardeep.workin;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LocationFindActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks,View.OnClickListener,LocationListener  {

    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private Location currentLocation;
    TextView latTextView,longTextView,dateTextView,addressTextView;
    private LocationRequest locationRequest;

    protected final static String requesting_update="requesting-update";
    protected final static String location_key="location-key";
    protected final static String last_update_time="last-update-time";

    protected static String latLabel;
    protected static String longLabel;
    protected static String timeLabel;

    protected static String lastUpdateTime="";

    private boolean setLocationUpdateChoice=false;

    AddressReceiver addressReceiver;
    private static String addressLocationOutput;

    private boolean addressRequested=false;


    Button buttonLocation,startLocationUpdateButton,stopLocationUpdateButton,fetchLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_find);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();


        buttonLocation=(Button)findViewById(R.id.buttonLastLocation);
        latTextView=(TextView)findViewById(R.id.textViewLattitude);
        longTextView=(TextView)findViewById(R.id.textViewLongitude);
        startLocationUpdateButton=(Button)findViewById(R.id.startButton);
        stopLocationUpdateButton=(Button)findViewById(R.id.stopButton);
        dateTextView=(TextView)findViewById(R.id.textViewLDate);
        fetchLocationButton=(Button)findViewById(R.id.fetchAddress);
        addressTextView=(TextView)findViewById(R.id.textViewAddress);

        fetchLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchLocationAddrees();
            }
        });

        startLocationUpdateButton.setOnClickListener(this);
        stopLocationUpdateButton.setOnClickListener(this);


        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation();
            }
        });

        if(googleApiClient==null) {
            googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        }

        updateValuesBundle(savedInstanceState);
        createLocationRequest();
        /*Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                updateData();
            }
        }, 0, 6000);*/

    }

    private void updateValuesBundle(Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            if(savedInstanceState.keySet().contains(requesting_update)){
                setLocationUpdateChoice=savedInstanceState.getBoolean(requesting_update);
                setButtonEnable();
            }
            if(savedInstanceState.keySet().contains(location_key)){
                currentLocation=savedInstanceState.getParcelable(location_key);

            }
            if(savedInstanceState.keySet().contains(lastUpdateTime)){
                lastUpdateTime=savedInstanceState.getString(last_update_time);
            }
            updateData();
        }

    }

    protected void createLocationRequest(){
        locationRequest=new LocationRequest();
        locationRequest.setInterval(300000);
        locationRequest.setFastestInterval(100000);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
    }



    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(googleApiClient.isConnected() && setLocationUpdateChoice){
            startLocationUpdate();
        }
    }


    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.action_settings){
            displayRightMenu();
            return true;
        }
        return false;
    }

    private void displayRightMenu() {
        startActivity(new Intent(LocationFindActivity.this, LocationHistoryActivity.class));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Toast.makeText(LocationFindActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnected(Bundle bundle) {

        // displayLocation();

        if(currentLocation==null){
            currentLocation=LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            lastUpdateTime= DateFormat.getDateTimeInstance().format(new Date());
            updateData();
        }
        if(setLocationUpdateChoice){
            startLocationUpdate();
        }
        if(lastLocation!=null){
            if(!Geocoder.isPresent()){
                Toast.makeText(LocationFindActivity.this, "Geocoder is not Available", Toast.LENGTH_SHORT).show();
                return;
            }
            if(addressRequested){
               // startIntentServices();
            }
        }


    }



    private void displayLocation() {
        lastLocation= LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if(lastLocation!=null){
            String latitude= "Latitude :"+String.valueOf(lastLocation.getLatitude());
            String longitude="Longitude :"+String.valueOf(lastLocation.getLongitude());
            String date="Date :"+ DateFormat.getDateTimeInstance().format(new Date());
            latTextView.setText(latitude);
            longTextView.setText(longitude);
            dateTextView.setText(date);

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(LocationFindActivity.this, "Connection suspended", Toast.LENGTH_SHORT).show();
        googleApiClient.connect();

    }

    @Override
    public void onClick(View v) {
        if(v==startLocationUpdateButton){
            startLocationUpdateHandler();
        }
        else if(v==stopLocationUpdateButton) {
            stopLocationUpdateHandler();
        }

    }

    private void stopLocationUpdateHandler() {
        if(setLocationUpdateChoice){
            setLocationUpdateChoice=false;
            setButtonEnable();
            stopLocationUpdate();
        }

    }

    private void setButtonEnable() {
        if(setLocationUpdateChoice){
            startLocationUpdateButton.setEnabled(false);
            stopLocationUpdateButton.setEnabled(true);
        }
        else {
            stopLocationUpdateButton.setEnabled(false);
            startLocationUpdateButton.setEnabled(true);
        }

    }

    private void startLocationUpdateHandler() {

        if(!setLocationUpdateChoice){
            setLocationUpdateChoice=true;
            setButtonEnable();
            startLocationUpdate();
        }
    }

    private void stopLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, (LocationListener) this);

    }

    private void startLocationUpdate() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (LocationListener) this);

    }
    private void updateData(){
        latTextView.setText("Latitude Udate:"+String.valueOf(currentLocation.getLatitude()));
        longTextView.setText("Longitude Update:" + String.valueOf(currentLocation.getLongitude()));

        dateTextView.setText("Date Upadte :" + lastUpdateTime);
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation=location;
        lastUpdateTime=DateFormat.getDateTimeInstance().format(new Date());
        updateData();
        fetchAddress();
        Toast.makeText(LocationFindActivity.this, "Location Update", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putBoolean(requesting_update,setLocationUpdateChoice);
        savedInstanceState.putParcelable(location_key, currentLocation);
        savedInstanceState.putString(last_update_time, lastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    /*protected void startIntentServices(){
        Intent intent=new Intent(this,FetchAddress.class);
        intent.putExtra(Constants.RECEIVER,addressReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA,lastLocation);
        startService(intent);
    }*/

    public class AddressReceiver extends ResultReceiver {

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public AddressReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            addressLocationOutput=resultData.getString(Constants.RESULT_DATA_KEY);
            updateAddress();

            if(resultCode==Constants.SUCCESS_RESULT){
                Toast.makeText(LocationFindActivity.this, "Location Found", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void fetchLocationAddrees(){

        if(googleApiClient.isConnected() && (currentLocation!=null ||lastLocation!=null) ){
            System.out.println("Connected");
            // startIntentServices();

            fetchAddress();
        }
        else {
            Toast.makeText(LocationFindActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
        }
        // addressRequested=true;
        // updateAddress();

    }

    private void fetchAddress() {
        Geocoder geocoder=new Geocoder(this, Locale.getDefault());

        //Address location=currentLocation;
        List<Address> listAddress=null;

        try {
            listAddress=geocoder.getFromLocation(currentLocation.getLatitude(),currentLocation.getLongitude(),1);

            if(listAddress.size()==0 && listAddress==null){

            }
            else {
                String addressLine="";
                Address address=listAddress.get(0);
                ArrayList<String> arrayList=new ArrayList<String>();
                for(int i=0;i<address.getMaxAddressLineIndex();i++){
                    addressLine=addressLine+" "+address.getAddressLine(i);
                }
                System.out.println("Location :" + addressLine);
                addressTextView.setText(addressLine);

                if(HistoryLocationContent.listItemCount<10){
                    new HistoryLocationContent(addressLine+"\n"+DateFormat.getDateTimeInstance().format(new Date()));
                }
            }

        }
        catch (IOException e){
            e.printStackTrace();
            Toast.makeText(LocationFindActivity.this, "Connectivity Error", Toast.LENGTH_SHORT).show();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }



    }

    private void updateAddress() {

        addressTextView.setText(addressLocationOutput);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_find, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(LocationFindActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
