package artist.web.scorekeeperapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static final String STARK_HEALTH_BAR = "starkHealth";
    static final String TARG_HEALTH_BAR = "targHealth";
    static final String STARK_SCORE ="starkScore";
    static final String TARG_SCORE = "targScore";
    static final String MESSAGE_BOARD= "messageBoard";
    static final String HITS_STARK ="hitScoreSt";
    static final String HITS_TARG ="hitScoreTarg";

    private ProgressBar healthStark, healthTargaryen;

    private int[] attackMeasures={10,20,25,30};
    private int randomValue, attackValue;


    private TextView scoreStark;
    private TextView scoreTargaryen;
    private TextView message;
    private TextView hitScoreStark;
    private TextView hitScoreTarg;

    private int teamS=0;
    private int teamT=0;
    private int hitCounterSt=0;
    private int hitCounterTarg=0;

    private int maxHealthStark =100;
    private int maxHealthTargaryen=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShimmerFrameLayout layout = (ShimmerFrameLayout) findViewById(R.id.shimmerlayout);
        layout.startShimmerAnimation();

        healthStark = (ProgressBar)findViewById(R.id.healthBarStark);
        healthTargaryen=(ProgressBar)findViewById(R.id.healthBarTargaryen);
        scoreStark = (TextView)findViewById(R.id.teamStark);
        scoreTargaryen = (TextView)findViewById(R.id.teamTarg);
        message = (TextView)findViewById(R.id.message);
        hitScoreStark=(TextView)findViewById(R.id.hitStark);
        hitScoreTarg=(TextView)findViewById(R.id.hitTarg);

     }

    public void attackOpponent(View view){

        randomValue = (int)(Math.random()*4);
        attackValue = attackMeasures[randomValue];

        Random randomGenerator = new Random();
        if(randomGenerator.nextBoolean()) {

            hitCounterSt++;
            displayScoreTeamStark(attackValue);

        }
        else{
            hitCounterTarg++;
            displayScoreTeamTarg(attackValue);
        }
     }

       public void displayScoreTeamStark(int score){
           maxHealthTargaryen = maxHealthTargaryen-score;
           teamS= teamS + score;
           if(maxHealthTargaryen<=0||teamS>=100){
               gameOver();
           }
           healthTargaryen.setProgress(maxHealthTargaryen);
           scoreStark.setText(String.valueOf(teamS));
           hitScoreStark.setText(String.valueOf(hitCounterSt));
       }

       public void displayScoreTeamTarg(int score){
           maxHealthStark=maxHealthStark-score;
           teamT = teamT +score;
           if(maxHealthStark<=0||teamT>=100){
               gameOver();
           }
           healthStark.setProgress(maxHealthStark);
           scoreTargaryen.setText(String.valueOf(teamT));
           hitScoreTarg.setText(String.valueOf(hitCounterTarg));
       }
    public void gameOver(){
        if(maxHealthStark<=0||teamT>=100){
            healthStark.setProgress(maxHealthStark);
            scoreTargaryen.setText(String.valueOf(teamT));
            message.setText("House Targaryen Won" +"\n" + "Game Over");


        }else {
            healthTargaryen.setProgress(maxHealthTargaryen);
            scoreStark.setText(String.valueOf(teamS));
            message.setText("House Stark Won" + "\n" + "Game Over");
        }

    }

       public void newMatch(View view){
           teamS=0;
           teamT=0;
           hitCounterTarg=0;
           hitCounterSt=0;
           scoreStark.setText(String.valueOf(teamS));
           hitScoreStark.setText(String.valueOf(hitCounterSt));
           healthStark.setProgress(100);
           scoreTargaryen.setText(String.valueOf(teamT));
           hitScoreTarg.setText(String.valueOf(hitCounterTarg));
           healthTargaryen.setProgress(100);
           message.setText("");
       }

       public void onSaveInstanceState(Bundle outState){
           super.onSaveInstanceState(outState);
           outState.putInt(STARK_HEALTH_BAR,maxHealthStark );
           outState.putInt(TARG_HEALTH_BAR,maxHealthTargaryen);
           outState.putInt(STARK_SCORE,teamS );
           outState.putInt(TARG_SCORE,teamT );
           outState.putString(MESSAGE_BOARD,message.getText().toString());
           outState.putInt(HITS_STARK,hitCounterSt);
           outState.putInt(HITS_TARG,hitCounterTarg);


       }

       public void onRestoreInstanceState(Bundle savedInstanceState){

           healthStark = (ProgressBar)findViewById(R.id.healthBarStark);
           healthTargaryen=(ProgressBar)findViewById(R.id.healthBarTargaryen);
           scoreStark = (TextView)findViewById(R.id.teamStark);
           scoreTargaryen = (TextView)findViewById(R.id.teamTarg);
           message = (TextView)findViewById(R.id.message);
           hitScoreStark=(TextView)findViewById(R.id.hitStark);
           hitScoreTarg=(TextView)findViewById(R.id.hitTarg);

               scoreStark.setText(String.valueOf(savedInstanceState.getInt(STARK_SCORE)));
               scoreTargaryen.setText(String.valueOf(savedInstanceState.getInt(TARG_SCORE)));
               healthStark.setProgress(savedInstanceState.getInt(STARK_HEALTH_BAR));
               healthTargaryen.setProgress(savedInstanceState.getInt(TARG_HEALTH_BAR));
               message.setText(savedInstanceState.getString(MESSAGE_BOARD));
               hitScoreStark.setText(String.valueOf(savedInstanceState.getInt(HITS_STARK)));
               hitScoreTarg.setText(String.valueOf(savedInstanceState.getInt(HITS_TARG)));

       }

}
