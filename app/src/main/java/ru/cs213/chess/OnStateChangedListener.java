package ru.cs213.chess;

public abstract class OnStateChangedListener {
    public abstract void onStateChanged(String newState, char newPlayer, char winner, char check);
}
