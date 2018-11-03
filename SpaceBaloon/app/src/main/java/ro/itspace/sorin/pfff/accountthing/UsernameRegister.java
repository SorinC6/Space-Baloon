package ro.itspace.sorin.pfff.accountthing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ro.itspace.sorin.pfff.MainActivity;
import ro.itspace.sorin.pfff.R;
import ro.itspace.sorin.pfff.User;
import ro.itspace.sorin.pfff.gameimpl.StartGameActivity;

public class UsernameRegister extends AppCompatActivity {

    private EditText userName;
    private Button registerUserNameButton;
    private String name;
    private int score;


    private FirebaseAuth auth;
    private String currentUserID;

    private DatabaseReference databaseUserRef;

    private Button settingsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_username_register );

//        final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
//        setDataToView(user);


        // currentUserID=auth.getCurrentUser().getUid();
        auth=FirebaseAuth.getInstance();

        databaseUserRef=FirebaseDatabase.getInstance().getReference().child( "Users" );


        initializeField();

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UsernameRegister.this,MainActivity.class));
            }
        });



        registerUserNameButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addUserToDatabase();

                Toast.makeText( UsernameRegister.this, "Welcome", Toast.LENGTH_SHORT ).show();
                Intent mainIntent=new Intent( UsernameRegister.this,StartGameActivity.class );
                 mainIntent.putExtra( "idd",currentUserID );
                 mainIntent.putExtra( "name",name );

                startActivity( mainIntent );
            }
        } );
    }

    private void addUserToDatabase() {


             name = userName.getText().toString();


        currentUserID  = auth.getCurrentUser().getUid();

        User user = new User(name,score );

        databaseUserRef.child( currentUserID ).setValue( user );

    }

    private void setDataToView(FirebaseUser user) {
       // userName.setText(databaseUserRef.child(currentUserID);
    }


    private void initializeField () {

        userName = findViewById( R.id.user_name_id );
        registerUserNameButton = findViewById( R.id.start_game_button_id );
        settingsButton=findViewById(R.id.settings_button_id);


    }


    public String getCurrentUserID() {
        return currentUserID;
    }
}
