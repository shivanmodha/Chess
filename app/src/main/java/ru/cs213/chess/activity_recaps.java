package ru.cs213.chess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class activity_recaps extends AppCompatActivity {
    ListView mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recaps);
        mList = findViewById(R.id.l_recaps_list);
        String[] list = getList(0);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.listitems, list);
        mList.setAdapter(adapter);
    }
    public String[] getList(int sortStyle) {
        ArrayList<File> list = new ArrayList<File>();
        File directory = getApplicationContext().getFilesDir();
        File[] f = directory.listFiles();
        for (int i = 0; i < f.length; i++) {
            if (f[i].getName().endsWith(".match")) {
                boolean added = false;
                for (int j = 0; j < list.size(); j++) {
                    if (sortStyle == 0) {
                        if (f[i].getName().compareTo(list.get(j).getName()) > 0) {
                            added = true;
                            list.add(f[j]);
                            break;
                        }
                    } else {
                        if (f[i].lastModified() < list.get(j).lastModified()) {
                            added = true;
                            list.add(f[j]);
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

    }
}
