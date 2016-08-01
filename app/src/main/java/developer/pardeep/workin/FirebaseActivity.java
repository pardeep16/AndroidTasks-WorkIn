package developer.pardeep.workin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class FirebaseActivity extends AppCompatActivity {


    EditText editUserName,editUserEmail,editUserPhone;
    Button saveButton,getButton;
    TextView textViewDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
        Toolbar tool=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        editUserName=(EditText)findViewById(R.id.editTextName);
        editUserEmail=(EditText)findViewById(R.id.editTextEmail);
        editUserPhone=(EditText)findViewById(R.id.editTextPhone);
        saveButton=(Button)findViewById(R.id.buttonSubmit);
        getButton=(Button)findViewById(R.id.buttonGetData);
        textViewDisplay=(TextView)findViewById(R.id.textGetUsers);

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);

       // firebaseRef=new Firebase("https://workin-778c3.firebaseio.com/");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editUserName.getText().toString().length()>3 && editUserEmail.getText().toString().length()>0 && editUserPhone.getText().toString().length()==10){
                    saveUserProfile();
                }
                else {
                    Toast.makeText(FirebaseActivity.this, "Please fill details !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllUsers();
            }
        });
    }

    private void getAllUsers() {
       Firebase firebaseRead=new Firebase("https://workin-778c3.firebaseio.com/");
        firebaseRead.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NewUserProfile newUserProfile = snapshot.getValue(NewUserProfile.class);
                    textViewDisplay.append(newUserProfile.getUserName() + "\n" + newUserProfile.getUserEmail() + "\n" + newUserProfile.getUserMobile() + "\n");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(FirebaseActivity.this, "error :" + firebaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserProfile() {
        System.out.println("Hello");
       Firebase firebaseWrite=new Firebase("https://workin-778c3.firebaseio.com/");
        NewUserProfile newUser=new NewUserProfile();
        newUser.setUserName(editUserName.getText().toString());
        newUser.setUserEmail(editUserEmail.getText().toString());
        newUser.setUserMobile(editUserPhone.getText().toString());

        firebaseWrite.child("user").setValue(newUser, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    Toast.makeText(FirebaseActivity.this, "Successfully saved", Toast.LENGTH_SHORT).show();
                    editUserName.setText("");
                    editUserPhone.setText("");
                    editUserEmail.setText("");
                } else {
                    Toast.makeText(FirebaseActivity.this, "Not saved Try again!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //System.out.println("save");
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(FirebaseActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
