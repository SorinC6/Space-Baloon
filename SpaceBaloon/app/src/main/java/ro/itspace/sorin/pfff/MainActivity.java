package ro.itspace.sorin.pfff;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import ro.itspace.sorin.pfff.accountthing.LoginActivity;
import ro.itspace.sorin.pfff.accountthing.SignUpActivity;

public class MainActivity extends AppCompatActivity {

    private TextView emailTextView;
    private EditText emailEditTextInput;
    private EditText oldPasswordEditTextInput;
    private EditText newPasswordTextInput;
    private Button changeButton;
    private Button removeButton;
    private Button changePaswordButton;
    private Button removeUserButton;
    private Button signOutButton;
    private ProgressBar progressBar;

    private FirebaseAuth auth;
    private FirebaseDatabase rootRef;
    private String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        initializeFields();


        final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);


        auth=FirebaseAuth.getInstance();

        oldPasswordEditTextInput.setVisibility( View.GONE );
        emailEditTextInput.setVisibility( View.GONE );
        newPasswordTextInput.setVisibility( View.GONE );
        changeButton.setVisibility( View.GONE );
        removeButton.setVisibility( View.GONE );

        if(progressBar != null){
            progressBar.setVisibility( View.GONE );
        }

        changePaswordButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailEditTextInput.setVisibility( View.GONE );

                newPasswordTextInput.setVisibility( View.VISIBLE );
                changeButton.setVisibility( View.VISIBLE );
                removeButton.setVisibility( View.GONE );
                
                
            }
        } );
        
        changeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               progressBar.setVisibility( View.VISIBLE );
               if( user != null && !newPasswordTextInput.getText().toString().trim().equals( "" )){
                   if(newPasswordTextInput.getText().toString().trim().length()< 6){
                       newPasswordTextInput.setError( "Password too short, enter minimum  6 characters" );
                       progressBar.setVisibility( View.GONE );
                   }
                   else{
                       user.updatePassword( newPasswordTextInput.getText().toString().trim() )
                               .addOnCompleteListener( new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if(task.isSuccessful()){
                                           Toast.makeText( MainActivity.this, "Update Succesfully", Toast.LENGTH_SHORT ).show();
                                           signOut();
                                           progressBar.setVisibility( View.GONE );
                                       }
                                       else{
                                           Toast.makeText( MainActivity.this, "Failed to update", Toast.LENGTH_SHORT ).show();
                                           progressBar.setVisibility( View.GONE );
                                       }
                                   }
                               } );
                   }
               }else{
                   if(newPasswordTextInput.getText().toString().trim().equals( "" )){
                       newPasswordTextInput.setError( "Enter Password" );
                       progressBar.setVisibility( View.GONE );
                   }
               }
            }
        } );

        removeUserButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility( View.VISIBLE );
                if(user != null){
                    user.delete()
                            .addOnCompleteListener( new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText( MainActivity.this, "Your profile is deteted, create a account now", Toast.LENGTH_SHORT ).show();
                                        startActivity( new Intent(MainActivity.this, SignUpActivity.class) );
                                        finish();
                                        progressBar.setVisibility( View.GONE );
                                    }
                                    else{
                                        Toast.makeText( MainActivity.this, "Failed to delete your account", Toast.LENGTH_SHORT ).show();
                                        progressBar.setVisibility( View.GONE );
                                    }
                                }
                            } );
                }
            }
        } );





        signOutButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        } );
    }

    private void setDataToView(FirebaseUser user) {
        emailTextView.setText( "User Email: "+user.getEmail() );
    }

    private void initializeFields() {

        emailTextView=findViewById( R.id.user_email_id );
        emailEditTextInput=findViewById( R.id.old_email_id );
        oldPasswordEditTextInput=findViewById( R.id.password_input_for_reset );
        newPasswordTextInput=findViewById( R.id.new_password_input_for_reset );
        changeButton=findViewById( R.id.change_button_id );
        removeButton=findViewById( R.id.remove_button_id );
        changePaswordButton=findViewById( R.id.change_user_password_button_id );
        removeUserButton=findViewById( R.id.remove_user_button_id );
        signOutButton=findViewById( R.id.sign_out_user_id );
        progressBar=findViewById( R.id.progressBar );

    }

    private void signOut() {

        auth.signOut();
        //Toast.makeText( MainActivity.this, "333333333", Toast.LENGTH_SHORT ).show();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();

    }




//    public void setDataToView(FirebaseUser user) {
//        this.dataToView = dataToView;
//    }
}
