package ro.itspace.sorin.pfff.gameimpl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ro.itspace.sorin.pfff.R;
import ro.itspace.sorin.pfff.User;

public class FlyingBalloonView extends View {

    private DatabaseReference userRef;


    private Bitmap balloon[]=new Bitmap[2];
    private int fishX=10;
    private int fishY;
    private int fishSpeed;

    private int canvasWidth, canvasHeight;

    private int score,lifeCounterOfFish;

    private int yellowX, yellowY,yellowSpeed=16;
    private Paint yellowPaint=new Paint(  );

    private int greenX,greenY,greenSpeed=20;
    private Paint greenPaint=new Paint(  );

    private int redX,redY,redSpeed=30;
    private Paint redPaint=new Paint(  );

    private boolean touch=false;

    private Bitmap backgroundImage;

    private Paint scorePaint=new Paint(  );

    private Bitmap life[]=new Bitmap[2];

    private String nume;
    private String id;




    public FlyingBalloonView(Context context, String name, String id) {
        super( context );

        this.nume=name;
        this.id=id;
        this.score=score;

        userRef=FirebaseDatabase.getInstance().getReference().child( "Users" ).child( id );

        balloon[0]= BitmapFactory.decodeResource( getResources(),R.drawable.balloon1 );
        balloon[1]= BitmapFactory.decodeResource( getResources(),R.drawable.balloon2 );

        backgroundImage=BitmapFactory.decodeResource( getResources() ,R.drawable.background2);

        yellowPaint.setColor( Color.YELLOW );
        yellowPaint.setAntiAlias( false );

        greenPaint.setColor( Color.GREEN );
        greenPaint.setAntiAlias( false );

        redPaint.setColor( Color.RED );
        redPaint.setAntiAlias( false );

        scorePaint.setColor( Color.WHITE );
        scorePaint.setTextSize( 70 );
        scorePaint.setTypeface( Typeface.DEFAULT_BOLD );
        scorePaint.setAntiAlias( true );

        life[0]=BitmapFactory.decodeResource( getResources(),R.drawable.hearts );
        life[1]=BitmapFactory.decodeResource( getResources(),R.drawable.heart_grey );

        fishY=550;

        score=0;

        lifeCounterOfFish=3;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw( canvas );


        canvasWidth=canvas.getWidth();
        canvasHeight=canvas.getHeight();

        canvas.drawBitmap( backgroundImage,0,0,null );

        int minFishY=balloon[0].getHeight();
        int maxFishY=canvasHeight-balloon[0].getHeight()*3;
        fishY=fishY+fishSpeed;

        if(fishY<minFishY){

            fishY=minFishY;
        }
        if(fishY>maxFishY){

            fishY=maxFishY;
        }
        fishSpeed=fishSpeed+2;

        if(touch){

            canvas.drawBitmap( balloon[1],fishX,fishY,null );
            touch=false;
        }
        else{
            canvas.drawBitmap( balloon[0],fishX,fishY,null );

        }



        yellowX=yellowX-yellowSpeed;

        if(hitBallChecker( yellowX,yellowY )){

            score-=10;
            yellowX= -100;
        }

        if(yellowX<0){
             yellowX=canvasWidth+21;
             yellowY= (int)(Math.floor( Math.random() * (maxFishY-minFishY) ))+minFishY;

        }
        canvas.drawCircle( yellowX,yellowY,25,yellowPaint );

        canvas.drawText( "Score : "+score,20, 60,scorePaint );

        greenX=greenX-greenSpeed;

        if(hitBallChecker( greenX,greenY )){

            score-=20;
            greenX= -100;
        }

        if(greenX<0){
            greenX=canvasWidth+21;
            greenY= (int)(Math.floor( Math.random() * (maxFishY-minFishY) ))+minFishY;

        }
        canvas.drawCircle( greenX,greenY,25,greenPaint );
        canvas.drawText( "Score : "+score,20, 60,scorePaint );


        redX=redX-redSpeed;

        if(hitBallChecker( redX,redY )){

            redY= -100;
            lifeCounterOfFish--;

            if(lifeCounterOfFish==0){
                Toast.makeText( getContext(), "Game Over", Toast.LENGTH_SHORT ).show();


                //Toast.makeText( getContext(), "Game Over", Toast.LENGTH_SHORT ).show();

                Intent gameOverIntent=new Intent( getContext(),GameOverActivity.class );
                gameOverIntent.putExtra( "score", score);

                User user=new User( this.nume,this.score );
                userRef.setValue( user );

                gameOverIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                //gameOverIntent.putExtra( "score",score );
                getContext().startActivity( gameOverIntent );

            }
        }

        if(redX<0){
            redX=canvasWidth+21;
            redY= (int)(Math.floor( Math.random() * (maxFishY-minFishY) ))+minFishY;

        }
        canvas.drawCircle( redX,redY,33,redPaint );

        canvas.drawText( "Score : "+score,20, 60,scorePaint );

        for(int i=0;i<3;i++){
            int x=(int)(380+life[0].getWidth() * 1.5 * i);
            int y=30;

            if(i<lifeCounterOfFish){
                canvas.drawBitmap( life[0],x,y,null );
            }
            else{
                canvas.drawBitmap( life[1],x,y,null );
            }
        }




//        canvas.drawBitmap( life[0],380,10,null );
//        canvas.drawBitmap( life[0],480,10,null );
//        canvas.drawBitmap( life[0],580,10,null );

}
    public boolean hitBallChecker(int x,int y){
        if(fishX<x && x< (fishX+balloon[0].getWidth()) && fishY<y && y<(fishY+balloon[0].getHeight() )){
            return true;
        }
        return false;
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            touch=true;

            fishSpeed=-25;
        }
        return true;
    }


    }
