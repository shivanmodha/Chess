package ru.cs213.chess.logic;

import java.util.HashMap;

/**
* <h1>CPoint</h1>
* This class represents a location on the board in the FileRank format.
*
* @author  Shivan Modha & Nowshad Azimi
* @version 1.0
* @since   03-27-2019
*/
public class CPoint {
    public static final char[] NUM_TO_ALPH = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    public static final HashMap<Character, Integer> ALPH_TO_NUM = new HashMap<Character, Integer>() {
        private static final long serialVersionUID = 1L;    // Clear an warning

        {
            put('a', 0);
            put('b', 1);
            put('c', 2);
            put('d', 3);
            put('e', 4);
            put('f', 5);
            put('g', 6);
            put('h', 7);
        };
    };
    private char file = 'a';
    private int rank = 0;
    
    /**
     * Contructor for CPoint, using File and Rank
     * @param file char representing the location's file
     * @param rank int representing the location's rank
     */
    public CPoint(char file, int rank) {
        this.file = file;
        this.rank = rank;
    }
    
    /**
     * Converts the file into an integer
     * @return int converted file
     */
    public int getX() {
        return (int)ALPH_TO_NUM.get(this.file);
    }

    /**
     * Returns the raw value of x
     * @return char raw file
     */
    public char getRawX() {
        return this.file;
    }
    
    /**
     * Returns the raw value of y
     * @return int raw rank
     */
    public int getRawY() {
        return this.rank;
    }
    
    /**
     * Converts the rank into an integer
     * @return int converted rank
     */
    public int getY() {
        return 8 - this.rank;
    }
    
    /**
     * Sets the file
     * @param file char new file for this CPoint
     */
    public void setX(char file) {
        if (file < 'a' || file > 'h') return;
        this.file = file;
    }
    
    /**
     * Sets the file
     * @param file int new file for this CPoint
     */
    public void setX(int file) {
        if (file < 0 || file > 7) return;

        this.file = NUM_TO_ALPH[file];
    }
    
    /**
     * Compares two CPoints with eachother
     */
    public boolean equals(Object o) {
        if (o instanceof CPoint) {
            CPoint n = (CPoint)o;
            return this.file == n.file && this.rank == n.rank;
        } else {
            return false;
        }
    }
    public String toString() {
        return "" + this.file + this.rank;
    }
}