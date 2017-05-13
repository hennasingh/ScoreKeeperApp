package artist.web.scorekeeperapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //static variables to serialize game data when the phone rotates
    private static final String STARK_HEALTH_BAR = "starkHealth";
    private static final String TARG_HEALTH_BAR = "targHealth";
    private static final String STARK_SCORE = "starkScore";
    private static final String TARG_SCORE = "targScore";
    private static final String MESSAGE_BOARD = "messageBoard";
    private static final String HITS_STARK = "hitScoreSt";
    private static final String HITS_TARG = "hitScoreTarg";
    private String[] displayMessages = {"Good Hit", "You Rock!!", "You are on Fire", "UhhUuuuHuuuu"};
    //variables to calculate progress bar value
    private ProgressBar healthStark, healthTargaryen;

    //random array to send messages when user clicks on a hit
    //random array to calculate attack intensity
    private int[] attackMeasures = {10, 20, 25, 30};
    //Textviews to set and get scores, counters in the game
    private TextView scoreStark;
    private TextView scoreTargaryen;
    private TextView message;
    private TextView hitScoreStark;
    private TextView hitScoreTarg;

    //variables to calculate score, number of hits and progressbar values
    private int teamS = 0;
    private int teamT = 0;
    private int hitCounterSt = 0;
    private int hitCounterTarg = 0;
    private int maxHealthStark = 100;
    private int maxHealthTargaryen = 100;

    //variable to get new button reference
    private Button newGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShimmerFrameLayout layout = (ShimmerFrameLayout) findViewById(R.id.shimmerlayout);
        layout.startShimmerAnimation();

        healthStark = (ProgressBar) findViewById(R.id.healthBarStark);
        healthTargaryen = (ProgressBar) findViewById(R.id.healthBarTargaryen);
        scoreStark = (TextView) findViewById(R.id.teamStark);
        scoreTargaryen = (TextView) findViewById(R.id.teamTarg);
        message = (TextView) findViewById(R.id.message);
        hitScoreStark = (TextView) findViewById(R.id.hitStark);
        hitScoreTarg = (TextView) findViewById(R.id.hitTarg);
        newGame = (Button) findViewById(R.id.reset);
    }

    /**
     * This method is called when user clicks on Attack Image Button
     *
     * @param
     */
    public void attackOpponent(View view) {

        int randomValue1 = (int) (Math.random() * 4);
        int attackValue = attackMeasures[randomValue1];

        int randomValue2 = (int) (Math.random() * 4);
        String attackMsg = displayMessages[randomValue2];

        Random randomGenerator = new Random();
        if (randomGenerator.nextBoolean()) {

            hitCounterSt++;
            displayScoreTeamStark(attackValue, attackMsg);

        } else {
            hitCounterTarg++;
            displayScoreTeamTarg(attackValue, attackMsg);
        }
    }

    /**
     * This method is internally called by the attackOpponent Method
     * to display the team Stark score randomly selected by the attackOpponent
     * Method
     *
     * @param score     is an integer number randomly calculated to hit the opposite
     *                  team with that intensity
     * @param attackMsg is a string msg to display when a user hits
     */
    public void displayScoreTeamStark(int score, String attackMsg) {
        maxHealthTargaryen = maxHealthTargaryen - score;
        teamS = teamS + score;
        if (maxHealthTargaryen <= 0 || teamS >= 100) {
            gameOver();
        }
        healthTargaryen.setProgress(maxHealthTargaryen);
        scoreStark.setText(String.valueOf(teamS));
        message.setText(attackMsg);
        hitScoreStark.setText(String.valueOf(hitCounterSt));
    }

    /**
     * This method is internally called by the attackOpponent Method
     * to display the team Targayen score randomly selected by the attackOpponent
     * Method
     *
     * @param score     is an integer number randomly calculated to hit the opposite
     *                  team with that intensity
     * @param attackMsg is a string msg to display when a user hits
     */
    public void displayScoreTeamTarg(int score, String attackMsg) {
        maxHealthStark = maxHealthStark - score;
        teamT = teamT + score;
        if (maxHealthStark <= 0 || teamT >= 100) {
            gameOver();
        }
        healthStark.setProgress(maxHealthStark);
        scoreTargaryen.setText(String.valueOf(teamT));
        message.setText(attackMsg);
        hitScoreTarg.setText(String.valueOf(hitCounterTarg));
    }

    /**
     * This method is called when the health bar of either team goes zero or
     * team score is greater than equal to 100
     */
    public void gameOver() {
        if (maxHealthStark <= 0 || teamT >= 100) {
            healthStark.setProgress(maxHealthStark);
            scoreTargaryen.setText(String.valueOf(teamT));
            AlertDialog.Builder winAlert = new AlertDialog.Builder(this);
            winAlert.setTitle("Game Over");
            winAlert.setMessage("House Targaryen Won");
            winAlert.setIcon(R.drawable.targaryen_team);
            winAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    newMatch(newGame);
                }
            });
            winAlert.create();
            winAlert.show();
            message.setText("House Targaryen Won!!");
        } else {
            healthTargaryen.setProgress(maxHealthTargaryen);
            scoreStark.setText(String.valueOf(teamS));
            AlertDialog.Builder winAlert = new AlertDialog.Builder(this);
            winAlert.setTitle("Game Over");
            winAlert.setMessage("House Stark Won");
            winAlert.setIcon(R.drawable.starks_team);
            winAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    newMatch(newGame);
                }
            });
            winAlert.create();
            winAlert.show();
            message.setText("House Stark Won!!");
        }
    }

    /**
     * This method is called when the user clicks on New button
     * for a new Match
     *
     * @param view is the button view passed when its clicked
     */
    public void newMatch(View view) {
        teamS = 0;
        teamT = 0;
        hitCounterTarg = 0;
        hitCounterSt = 0;
        scoreStark.setText(String.valueOf(teamS));
        hitScoreStark.setText(String.valueOf(hitCounterSt));
        healthStark.setProgress(100);
        scoreTargaryen.setText(String.valueOf(teamT));
        hitScoreTarg.setText(String.valueOf(hitCounterTarg));
        healthTargaryen.setProgress(100);
        message.setText("");
    }

    /**
     * This is to save the state of the game when the phone
     * is rotated to opposite mode
     *
     * @param outState is bundle variable to serialize all scores and game data
     */

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STARK_HEALTH_BAR, maxHealthStark);
        outState.putInt(TARG_HEALTH_BAR, maxHealthTargaryen);
        outState.putInt(STARK_SCORE, teamS);
        outState.putInt(TARG_SCORE, teamT);
        outState.putString(MESSAGE_BOARD, message.getText().toString());
        outState.putInt(HITS_STARK, hitCounterSt);
        outState.putInt(HITS_TARG, hitCounterTarg);
    }

    /**
     * This is called when activity is recreated after screen rotation
     *
     * @param savedInstanceState is a variable to deserialize saved game data
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        healthStark = (ProgressBar) findViewById(R.id.healthBarStark);
        healthTargaryen = (ProgressBar) findViewById(R.id.healthBarTargaryen);
        scoreStark = (TextView) findViewById(R.id.teamStark);
        scoreTargaryen = (TextView) findViewById(R.id.teamTarg);
        message = (TextView) findViewById(R.id.message);
        hitScoreStark = (TextView) findViewById(R.id.hitStark);
        hitScoreTarg = (TextView) findViewById(R.id.hitTarg);

        scoreStark.setText(String.valueOf(savedInstanceState.getInt(STARK_SCORE)));
        scoreTargaryen.setText(String.valueOf(savedInstanceState.getInt(TARG_SCORE)));
        healthStark.setProgress(savedInstanceState.getInt(STARK_HEALTH_BAR));
        healthTargaryen.setProgress(savedInstanceState.getInt(TARG_HEALTH_BAR));
        message.setText(savedInstanceState.getString(MESSAGE_BOARD));
        hitScoreStark.setText(String.valueOf(savedInstanceState.getInt(HITS_STARK)));
        hitScoreTarg.setText(String.valueOf(savedInstanceState.getInt(HITS_TARG)));
    }
}
