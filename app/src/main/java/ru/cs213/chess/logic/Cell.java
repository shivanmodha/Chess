package ru.cs213.chess.logic;

/**
* <h1>Cell</h1>
* The Cell Class holds the graphics for each rectangle for the chess board.
*
* @author  Shivan Modha & Nowshad Azimi
* @version 1.0
* @since   03-27-2019
*/
public class Cell {
	/**
	* Graphics for the board when the cell is black
	*/
    public static final String BLACK = "##";
    
    /**
	* Graphics for the board when the cell is white
	*/
    public static final String WHITE = "  ";
    
    /**
	* Contains the pieces with two characters or Black/White graphics
	*/
    private char[] data = new char[]{' ', ' '};
    public Cell(char d1, char d2) {
        data[0] = d1;
        data[1] = d2;
    }
    public Cell(char[] _data) {
        this(_data[0], _data[1]);
    }
    public Cell(String _data) {
        this(_data.charAt(0), _data.charAt(1));
    }
    public String render() {
        return "" + data[0] + data[1];
    }
}