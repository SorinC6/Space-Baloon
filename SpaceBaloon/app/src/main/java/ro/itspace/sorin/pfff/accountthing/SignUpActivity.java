package ro.itspace.sorin.pfff.accountthing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ro.itspace.sorin.pfff.MainActivity;
import ro.itspace.sorin.pfff.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailRegisteredText;
    private EditText passwordRegisteredText;
    private Button forgetPasswordButton;
    private Button registeredButton;
    private Button alreadyRegisteredButton;

    private ProgressBar progressBar;

    private FirebaseAuth auth;
    private DatabaseReference rootRef;
    private String currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );


        initializeFields();

        sentToLoginActivity();

        sentToResetPassword();

        auth=FirebaseAuth.getInstance();
        rootRef=FirebaseDatabase.getInstance().getReference().child("Users");


        registeredButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=emailRegisteredText.getText().toString();
                String password=passwordRegisteredText.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //create user

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    currentUser=auth.getCurrentUser().getUid();
                                    rootRef.child(currentUser);
                                    Toast.makeText(SignUpActivity.this, "Register succesfully !", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivity.this, UsernameRegister.class));
                                    //finish();
                                }
                            }
                        });


            }
        } );
    }

    @Override
    protected void onResume() {
        super.onResume();

        progressBar.setVisibility( View.GONE );
    }

    private void initializeFields() {
        emailRegisteredText=findViewById( R.id.email_register_id );
        passwordRegisteredText=findViewById( R.id.password_register_id );
        forgetPasswordButton=findViewById( R.id.forgot_password_register_button_id );
        registeredButton=findViewById( R.id.button_register_id );
        alreadyRegisteredButton=findViewById( R.id.already_registreted_button_id );

        progressBar=findViewById( R.id.progressBar );
    }

    private void sentToLoginActivity() {
        alreadyRegisteredButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin=new Intent( SignUpActivity.this,LoginActivity.class );
                startActivity( intentLogin );
            }
        } );
    }

    private void sentToResetPassword() {
        forgetPasswordButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgetActivity=new Intent( SignUpActivity.this,ResetPasswordActivity.class );
                startActivity( forgetActivity );
            }
        } );
    }
}
