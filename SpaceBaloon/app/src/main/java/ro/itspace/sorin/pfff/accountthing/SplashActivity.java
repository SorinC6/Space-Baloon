package ro.itspace.sorin.pfff.accountthing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ro.itspace.sorin.pfff.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );

        Thread thread=new Thread(  ){
            @Override
            public void run() {

                try{
                    sleep( 4000 );
                }catch(Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent mainIntent=new Intent( SplashActivity.this,LoginActivity.class );
                    startActivity( mainIntent );

                }

            }
        };

        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }
}
