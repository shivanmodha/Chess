package ru.cs213.chess;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

public class Board extends View {
    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Board,0, 0);
        try {
            
        } finally {
            a.recycle();
        }
    }
}
