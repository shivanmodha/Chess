package ru.cs213.chess.logic;

/**
* <h1>IllegalMoveException</h1>
* This class used to throw a exception if the move is not possible.
*
* @author  Shivan Modha & Nowshad Azimi
* @version 1.0
* @since   03-27-2019
*/
public class IllegalMoveException extends Exception {
	/**
	* serialVersionUID represents the class version
	*/
    private static final long serialVersionUID = 1L;
    
    public IllegalMoveException(String msg){
        super(msg);
    }

	public IllegalMoveException() {
    }
    
}