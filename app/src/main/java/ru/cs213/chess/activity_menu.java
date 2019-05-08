/**
 * @author Shivan Modha
 * @description Initial activity that launches on app load
 */
package ru.cs213.chess;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
/**
 * Main activity that spawns up as soon as the application loads
 */
public class activity_menu extends AppCompatActivity {
    // Static variables for class differentiation
    private static String TAG = "[activity][main]";
    // Variables to maintain application state

    /**
     * Initial creation method for the activity lifecycle
     * @param savedInstanceState State object to recreate the activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the activity to fullscreen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Apply the resources layout
        setContentView(R.layout.activity_main);
    }

    /**
     * Launch a new activity to play the game
     * @param view Invoker object
     */
    public void newGame(View view) {
        // Create the activity
        Intent intent = new Intent(this, activity_game.class);
        // Start the activity
        startActivity(intent);
    }
    public void recaps(View view) {
        // Create the activity
        Intent intent = new Intent(this, activity_recaps.class);
        // Start the activity
        startActivity(intent);
    }
}
