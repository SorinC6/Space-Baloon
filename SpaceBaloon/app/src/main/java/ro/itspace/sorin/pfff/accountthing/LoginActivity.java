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

import ro.itspace.sorin.pfff.MainActivity;
import ro.itspace.sorin.pfff.R;

public class LoginActivity extends AppCompatActivity {

    private EditText mailTextLogin;
    private EditText passwordTextLogin;
    private Button forgetPasswordButton;
    private Button loginButton;
    private Button getRegistredButton;

    private ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        mAuth=FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            startActivity( new Intent( LoginActivity.this,UsernameRegister.class ) );
            finish();
        }

        mAuth=FirebaseAuth.getInstance();

        initializeFields();

        sentToResetPassword();

        sentToSignupActivity();

        //posibil sa i-au din nou setCOntentView si instanta auth

        loginButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=mailTextLogin.getText().toString();
                final String password=passwordTextLogin.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility( View.VISIBLE );

                //autentificare user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        passwordTextLogin.setError("password too short");
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Errorrrrrrr", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, UsernameRegister.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });


            }
        } );
    }



    private void initializeFields() {
        mailTextLogin=findViewById( R.id.email_login_id );
        passwordTextLogin=findViewById( R.id.password_login_id );
        forgetPasswordButton=findViewById( R.id.forgot_password_login_button_id );
        loginButton=findViewById( R.id.button_login_id );
        getRegistredButton=findViewById( R.id.already_registreted_login_button_id );

        progressBar=findViewById( R.id.progressBar );
    }

    private void sentToSignupActivity() {
        getRegistredButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegistered=new Intent( LoginActivity.this,SignUpActivity.class );
                startActivity( intentRegistered );
            }
        } );
    }

    private void sentToResetPassword() {
        forgetPasswordButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResetPasword=new Intent( LoginActivity.this,ResetPasswordActivity.class );
                startActivity( intentResetPasword );
            }
        } );
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}


