package ru.cs213.chess;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_game extends Activity {
    private static String TAG = "[activity][game]";
    private Board mBoard;
    private Button mUndo;
    private Button mHistoryBtn;
    private Button mAI;
    private TextView mPlayer;
    private ArrayList<String> mHistory = new ArrayList<String>();
    private Boolean setDraw = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        setFinishOnTouchOutside(false);
        mBoard = findViewById(R.id.l_game_board);
        mHistory.add(mBoard.getState());
        mHistoryBtn = findViewById(R.id.l_game_history);
        mUndo = findViewById(R.id.l_game_undo);
        mAI = findViewById(R.id.l_game_ai);
        final activity_game self = this;
        mBoard.setOnStateChangedListener(new OnStateChangedListener() {
            @Override
            public void onStateChanged(String newState, char newPlayer, char winner, char check) {
                mHistory.add(newState);
                if (mHistory.size() > 2) {
                    mUndo.setEnabled(true);
                }
                setCurrentPlayer(newPlayer);
                if (winner != ' ') {
                    mHistoryBtn.setEnabled(false);
                    mUndo.setEnabled(false);
                    mAI.setEnabled(false);
                    setCurrentWinner(winner);
                    AlertDialog.Builder b = new AlertDialog.Builder(activity_game.this);
                    b.setMessage("Do you want to save this match?");
                    if (winner == 'w') {
                        b.setTitle("White Wins!");
                    } else if (winner == 'b') {
                        b.setTitle("Black Wins!");
                    } else if (winner == 'd') {
                        b.setTitle("Nobody Wins!");
                    }
                    LayoutInflater inflator = LayoutInflater.from(getApplicationContext());
                    b.setView(inflator.inflate(R.layout.dialog_gameover, null));
                    b.setPositiveButton("Yes, Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            self.finish();
                        }
                    });
                    b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            self.finish();
                        }
                    });
                    AlertDialog dialog = b.create();
                    dialog.show();
                }
                if (setDraw) {
                    setDraw = false;
                    mHistoryBtn.setText("Resign");
                } else {
                    mHistoryBtn.setText("Draw");
                }
            }
            @Override
            public void snack(String message) {
                Toast.makeText(activity_game.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        mPlayer = findViewById(R.id.l_game_player);
        setCurrentPlayer('w');
    }
    public void onUndo(View view) {
        mHistory.remove(mHistory.size() - 1);
        mHistory.remove(mHistory.size() - 1);
        mBoard.setState(mHistory.get(mHistory.size() - 1));
        mHistory.remove(mHistory.size() - 1);
        mUndo.setEnabled(false);
    }
    public void onHistory(View view) {
        if (mHistoryBtn.getText() == "Draw") {
            setDraw = true;
        }
        if (mHistoryBtn.getText() == "Resign") {
            mBoard.draw();
        }
    }
    public void onQuit(View view) {
        this.finish();
    }
    public void onAI(View view) {
        mBoard.randomMove();
    }
    private void setCurrentWinner(char player) {
        if (player == 'w') {
            mPlayer.setText(R.string.winner_w);
        } else if (player == 'b') {
            mPlayer.setText(R.string.winner_b);
        } else if (player == 'd') {
            mPlayer.setText("Nobody Wins!");
        }
    }
    private void setCurrentPlayer(char player) {
        if (player == 'w') {
            mPlayer.setText(R.string.turn_w);
        } else if (player == 'b') {
            mPlayer.setText(R.string.turn_b);
        }
    }
}