package developer.pardeep.workin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleLogIn extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private static String userGmailInfo="";

    Button googleLoginButton;
    TextView googleLoginResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_log_in);
        googleLoginButton=(Button)findViewById(R.id.imageButtonGoogleLogin);
        googleLoginResult=(TextView)findViewById(R.id.textViewGoogleLoginActivity);

        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestProfile().requestEmail().build();



        //googleApiClient=new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Auth.GOOGLE_SIGN_IN_API).build();
        // googleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(MainActivity.this,MainActivity.this).

        googleApiClient = new GoogleApiClient.Builder(GoogleLogIn.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });
    }

    private void googleSignIn() {
        Intent signIn=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signIn, RC_SIGN_IN);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_google_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(GoogleLogIn.this, "Connection not established!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult googleSignInResult=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(googleSignInResult.isSuccess()){
                handleSignInRequest(googleSignInResult);
            }
            else{
                Toast.makeText(getApplicationContext(),"Something wrong", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void handleSignInRequest(GoogleSignInResult googleSignInResult) {

        Toast.makeText(getApplicationContext(), "Handle Result :" + googleSignInResult.isSuccess(), Toast.LENGTH_LONG).show();
        if(googleSignInResult.isSuccess()){
            GoogleSignInAccount googleSignInAccount=googleSignInResult.getSignInAccount();
            // userIdInfo=googleSignInAccount.getEmail() + "\n"+googleSignInAccount.getDisplayName();
            //System.out.println("User info :"+userIdInfo);

            String userName=googleSignInAccount.getDisplayName();
            String userEmail=googleSignInAccount.getEmail();
            Uri tag=googleSignInAccount.getPhotoUrl();
            String id=googleSignInAccount.getId();
            String userIdInfo=userName+"\n"+userEmail;
            System.out.println(userName);
            System.out.println(userEmail);
            userGmailInfo="Name :"+userName+"\n"+"Email ID:"+userEmail+"\n"+"Photo url :"+tag+"\n"+"ID :"+id+"\n";

            googleLoginResult.setText(userGmailInfo);
            // Log.d("User email :",googleSignInAccount.getEmail().toString());
            // Log.d("User name  :",googleSignInAccount.getDisplayName().toString());
        }
        else {
            Toast.makeText(getApplicationContext(),"Sign In failed",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(GoogleLogIn.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
