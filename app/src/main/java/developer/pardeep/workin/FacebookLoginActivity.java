package developer.pardeep.workin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FacebookLoginActivity extends AppCompatActivity {

    ImageButton facebookLoginButton;
    TextView facebookResultTextView;
   public CallbackManager callbackManager;
    private static String userIdInfo;
    private static String userAuthInfo;

    private FacebookSdk facebookSdk;
    private static String facebook_id,first_Name,full_name,last_name,profile_image,email_id,gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        facebookLoginButton=(ImageButton)findViewById(R.id.imageButtonFacebookLogin);
        facebookResultTextView=(TextView)findViewById(R.id.textViewFacebookActivity);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager=CallbackManager.Factory.create();

        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFacebookLogin();
            }
        });
    }

    private void onFacebookLogin() {
       // facebookLoginButton.setReadPermissions("public_profile", "email", "user_friends");

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(FacebookLoginActivity.this, "Login Sucess.." + "\n" + "User Id:" + loginResult.getAccessToken().getUserId() + "\n " + "Auth id:" + loginResult.getAccessToken().getToken(), Toast.LENGTH_SHORT).show();
                userIdInfo = loginResult.getAccessToken().getUserId();
                userAuthInfo = loginResult.getAccessToken().getToken();
                System.out.println(userIdInfo);
                System.out.println(userAuthInfo);
                //facebookResultTextView.setText("User Id :" + userIdInfo + "\n" + "User Auth :" + userAuthInfo);
                //
                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    facebook_id = profile.getId();
                    first_Name = profile.getFirstName();
                    last_name = profile.getLastName();
                    full_name = profile.getName();
                    profile_image = profile.getProfilePictureUri(400, 400).toString();


                    System.out.println(facebook_id);
                    System.out.println(first_Name);
                    System.out.println(last_name);
                    System.out.println(full_name);
                    System.out.println(profile_image);

                    facebookResultTextView.setText("User Id :" + userIdInfo + "\n" + "User Auth :" + userAuthInfo +
                                    "User Name :" + full_name + "\n" +
                                    "Profile Id:" + facebook_id + "\n" +
                                    "Pic Url :" + profile_image + "\n"
                    );
                }
                //Toast.makeText(FacebookLogin.this,"Wait...",Toast.LENGTH_SHORT).show();
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    email_id = object.getString("email");
                                    gender = object.getString("gender");
                                    //Start new activity or use this info in your project.

                                    Toast.makeText(FacebookLoginActivity.this, "email :" + email_id + "\n" + "name is :" + full_name, Toast.LENGTH_SHORT).show();
                                 /*   i.putExtra("type","facebook");
                                    i.putExtra("facebook_id",facebook_id);
                                    i.putExtra("f_name",first_Name);
                                   // i.putExtra("m_name",m_name);
                                    i.putExtra("l_name",l_name);
                                    i.putExtra("full_name",full_name);
                                    i.putExtra("profile_image",profile_image);
                                    i.putExtra("email_id",email_id);
                                    i.putExtra("gender",gender);*/


                                    // progress.dismiss();
                                    // startActivity(i);
                                    finish();
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    //  e.printStackTrace();
                                }

                            }

                        });

                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(FacebookLoginActivity.this, "Login Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(FacebookLoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProfileInfo() {
        GraphRequest graphRequest=GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                JSONObject jsonObject1=graphResponse.getJSONObject();
                try {
                   if(jsonObject1!=null){
                       String data=jsonObject.getString("link");
                       String userName= jsonObject1.getString("name");
                       String emaail=jsonObject1.getString("email");
                       facebookResultTextView.setText("\n"+"profile link"+data+"\n"+"Name :"+userName+"\n"+"email Id :"+emaail);
                   }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_facebook_login, menu);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(FacebookLoginActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
