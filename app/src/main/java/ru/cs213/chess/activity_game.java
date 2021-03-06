package ru.cs213.chess;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
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
                if (setDraw) {
                    setDraw = false;
                    mHistoryBtn.setText("Resign");
                } else {
                    mHistoryBtn.setText("Draw");
                }
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
                            EditText t = ((AlertDialog)dialog).findViewById(R.id.editText);
                            String name = t.getText().toString();
                            String filename = name + ".match";
                            String contents = "";
                            for (String h : mHistory) {
                                contents += ":" + h;
                            }
                            contents = contents.substring(1);
                            FileOutputStream outputStream;
                            try {
                                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                                outputStream.write(contents.getBytes());
                                outputStream.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //self.finish();
                        }
                    });
                    b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //self.finish();
                        }
                    });
                    AlertDialog dialog = b.create();
                    dialog.show();
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
        } else if (mHistoryBtn.getText() == "Resign") {
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