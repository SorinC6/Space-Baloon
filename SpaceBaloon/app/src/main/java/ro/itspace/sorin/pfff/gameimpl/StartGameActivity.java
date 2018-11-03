package ro.itspace.sorin.pfff.gameimpl;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import ro.itspace.sorin.pfff.User;

public class StartGameActivity extends AppCompatActivity {

    private FlyingBalloonView gameView;
    private Handler handler=new Handler(  );
    private final static long INTERVAL=30;

    private String currentUserid;
    private String name;

    private FirebaseAuth auth;
    //private FirebaseDatabase root;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        auth=FirebaseAuth.getInstance();
        currentUserid=auth.getCurrentUser().getUid();

        readDataFromFirebase();


        //String id=getIntent().getExtras().get( "idd" ).toString();


        //String name=getIntent().getExtras().getString( "name" ).toString();

        gameView=new FlyingBalloonView( this ,name,currentUserid);
        setContentView( gameView );

        Timer timer=new Timer(  );
        timer.schedule( new TimerTask() {
            @Override
            public void run() {
                handler.post( new Runnable() {
                    @Override
                    public void run() {
                        gameView.invalidate();
                    }
                } );
            }
        },0,INTERVAL );
    }

    private void readDataFromFirebase() {
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users");

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User post = dataSnapshot.getValue(User.class);
                name=post.getUserName();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
