package ru.cs213.chess;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class activity_recaps extends AppCompatActivity {
    ListView mList;
    int sort = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recaps);
        mList = findViewById(R.id.l_recaps_list);
        set();
    }
    public void set() {
        String[] list = getList(sort);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.listitems, list);
        mList.setAdapter(adapter);
        final activity_recaps self = this;
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = new File(getApplicationContext().getFilesDir(), ((TextView)view).getText() + "");
                FileInputStream inputStream;
                try {
                    inputStream = openFileInput(((TextView)view).getText() + "");
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;
                    String[] history = new String[0];
                    boolean ignoreflag = false;
                    while ((line = bufferedReader.readLine()) != null) {
                        if (!ignoreflag) {
                            history = line.split(":");
                        }
                        ignoreflag = true;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    // Create the activity
                    Intent intent = new Intent(self, activity_history.class);
                    intent.putExtra("HISTORY", history);
                    intent.putExtra("NAME", (((TextView)view).getText() + "").replace(".match", ""));
                    // Start the activity
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public String[] getList(int sortStyle) {
        ArrayList<File> list = new ArrayList<File>();
        File directory = getApplicationContext().getFilesDir();
        File[] f = directory.listFiles();
        for (int i = 0; i < f.length; i++) {
            if (f[i].getName().endsWith(".match")) {
                boolean added = false;
                for (int j = 0; j < list.size(); j++) {
                    if (sortStyle % 2 ==  0) {
                        if (f[i].getName().compareTo(list.get(j).getName()) < 0) {
                            added = true;
                            list.add(j, f[i]);
                            break;
                        }
                    } else {
                        if (f[i].lastModified() > list.get(j).lastModified()) {
                            added = true;
                            list.add(j, f[i]);
                            break;
                        }
                    }
                }
                if (!added) {
                    list.add(f[i]);
                }
            }
        }
        String[] _return = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            _return[i] = list.get(i).getName();
        }
        return _return;
    }
    public void onBack(View view) {
        this.finish();
    }
    public void onSort(View view) {
        sort++;
        set();
    }
    public void onClear(View view) {
        File directory = getApplicationContext().getFilesDir();
        File[] f = directory.listFiles();
        for (int i = 0; i < f.length; i++) {
            if (f[i].getName().endsWith(".match")) {
                f[i].delete();
            }
        }
        set();
    }
}
