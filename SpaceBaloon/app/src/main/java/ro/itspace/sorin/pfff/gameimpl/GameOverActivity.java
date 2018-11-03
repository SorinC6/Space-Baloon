package ro.itspace.sorin.pfff.gameimpl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import ro.itspace.sorin.pfff.MainActivity;
import ro.itspace.sorin.pfff.R;
import ro.itspace.sorin.pfff.accountthing.UsernameRegister;

public class GameOverActivity extends AppCompatActivity {

    private Button startGameAgainButton;
    private TextView displayScore;
    private String score;
    private Button highscoreButton;

    private int userScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_game_over );

        highscoreButton=findViewById( R.id.highscore_button_id );

        startGameAgainButton=findViewById( R.id.play_again_button_id );

        displayScore=findViewById( R.id.display_score_id );

        score=getIntent().getExtras().get( "score" ).toString();

        userScore=Integer.parseInt( score );

        displayScore.setText("Score: "+ score );


        highscoreButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent( GameOverActivity.this,MainActivity.class );
                startActivity( intent );
            }
        } );

        startGameAgainButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent=new Intent(GameOverActivity.this,StartGameActivity.class);
                startActivity( mainIntent );


            }
        } );

        }

}
