package ru.cs213.chess.logic;

import java.util.ArrayList;
import java.util.Scanner;

/**
* <h1>Chess</h1>
* The Main Chess Program that initializes the game and draws the board.
*
* @author  Shivan Modha & Nowshad Azimi
* @version 1.0
* @since   03-27-2019
*/
public class Chess {
	/**
	* It is a board type instance, which will allow the changes during the game.
	*/
	private Board ChessBoard;
	
	/**
	* Contains the list of all the different pieces from the game
	*/
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	
	/**
	* The players who's turn is next, in the beginning it is set to white
	*/
	private char currentPlayer = 'w';
	
	/**
	* Indicates if the game is still being played
	*/
	private boolean inGame = true;
	
	/**
	* Indicates the game is going to draw
	*/
	private boolean drawMode = false;
	
	/**
	* Instance of King that is black
	*/
	private King blackKing;
	/**
	* Instance of King that is white
	*/
	private King whiteKing;
	
	/**
	 * Instance of next promotion
	 */
	private char promotionType = 'Q';
	/**
	   * This main method is used create the instance of Chess.
	   * @param args unused 
	   * @return Nothing.
	   */
	public static void main(String[] args) {
		new Chess();
	}
	
	
	public Chess() {
		Scanner scanner = new Scanner(System.in);
		setup();
		render();
		while (inGame) {
			String input = scanner.nextLine();
			System.out.println();
			parse(input);
			render();
		}
		scanner.close();
	}
	
	 /**
	   * This method is used parse through text the user is inputing.
	   * @param input This parameter has the String the user entered
	   * @return Nothing.
	   */
	public void parse(String input) {
		/**
		   * Holds array of 2, indicating the moves the user is attempting to make
		   */
		String[] arr = input.split(" ");
		/**
		   * Used to print if there is an error
		   */
		String error = "";
		if (arr.length == 1) {
			if (drawMode && arr[0].equals("draw")) {
				inGame = false;
				System.out.println(((currentPlayer == 'w') ? "White" : "Black") + " wins");
			} else if (arr[0].equals("resign")) {
				inGame = false;
				System.out.println(((currentPlayer == 'b') ? "White" : "Black") + " wins");
			} else {
				error = "Invalid input";
			}
		} else if (arr.length >= 2) {
			drawMode = false;
			if (arr[0].length() == 2 && arr[1].length() == 2) {
				char p11 = arr[0].charAt(0);
				int p12 = -1;
				try {
					p12 = Integer.parseInt("" + arr[0].charAt(1));
				} catch (Exception e) {

				}
				if (CPoint.ALPH_TO_NUM.containsKey(p11) && p12 > 0 && p12 < 9) {
					char p21 = arr[1].charAt(0);
					int p22 = -1;
					try {
						p22 = Integer.parseInt("" + arr[1].charAt(1));
					} catch (Exception e) {
						
					}
					if (CPoint.ALPH_TO_NUM.containsKey(p21) && p22 > 0 && p22 < 9) {
						try {
							ChessBoard.move(currentPlayer, new CPoint(p11, p12), new CPoint(p21, p22));
						} catch (IllegalMoveException e) {
							error = e.getMessage();
						}
					} else {
						error = "Position doesn't exist";
					}
				} else {
					error = "Position doesn't exist";
				}
			} else {
				error = "Invalid input";
			}
			if (arr.length >= 3) {
				if (arr[2].equals("draw?")) {
					drawMode = true;
				} else if (arr[2].toUpperCase().equals("Q")) {
					promotionType = 'Q';
				} else if (arr[2].toUpperCase().equals("N")) {
					promotionType = 'N';
				} else if (arr[2].toUpperCase().equals("R")) {
					promotionType = 'R';
				} else if (arr[2].toUpperCase().equals("B")) {
					promotionType = 'B';
				}
			}
		}
		if (error.equals("")) {
			currentPlayer = (currentPlayer == 'w') ? ('b') : ('w');
		} else {
			System.out.println(error);
			System.out.println("");
		}
	}
	/**
	   * This method is used to set up the pieces in the chess board
	   * @return Nothing.
	   */
	public void setup() {
		ChessBoard = new Board();
		pieces = new ArrayList<Piece>();
		// Create the black pawns
		for (int i = 0; i < 8; i++) {
			pieces.add(new Pawn('b', new CPoint(CPoint.NUM_TO_ALPH[i], 7)));
		}
		// Create the white pawns
		for (int i = 0; i < 8; i++) {
			pieces.add(new Pawn('w', new CPoint(CPoint.NUM_TO_ALPH[i], 2)));
		}
		// Create the kings
		whiteKing = new King('w', new CPoint('e', 1));
		blackKing = new King('b', new CPoint('e', 8));
		pieces.add(whiteKing);
		pieces.add(blackKing);
		ChessBoard.setWhiteKing(whiteKing);
		ChessBoard.setBlackKing(blackKing);
		// Create the queens
		pieces.add(new Queen('w', new CPoint('d', 1)));
		pieces.add(new Queen('b', new CPoint('d', 8)));
		// Create the bishops
		pieces.add(new Bishop('w', new CPoint('c', 1)));
		pieces.add(new Bishop('w', new CPoint('f', 1)));
		pieces.add(new Bishop('b', new CPoint('c', 8)));
		pieces.add(new Bishop('b', new CPoint('f', 8)));
		// Create the knights
		pieces.add(new Knight('w', new CPoint('b', 1)));
		pieces.add(new Knight('w', new CPoint('g', 1)));
		pieces.add(new Knight('b', new CPoint('b', 8)));
		pieces.add(new Knight('b', new CPoint('g', 8)));
		// Create the Rooks
		pieces.add(new Rook('w', new CPoint('a', 1)));
		pieces.add(new Rook('w', new CPoint('h', 1)));
		pieces.add(new Rook('b', new CPoint('a', 8)));
		pieces.add(new Rook('b', new CPoint('h', 8)));
	}
	/**
	   * This method is used to draw the chess board along with the pieces
	   * @return Nothing.
	   */
	public void render() {
		ChessBoard.clear();
		for (int i = pieces.size() - 1; i >= 0; i--) {
			Piece p = pieces.get(i);
			if (p instanceof Pawn && (p.getLocation().getRawY() == 1 || p.getLocation().getRawY() == 8)) {
				if (promotionType == 'Q') {
					pieces.add(new Queen(p.getPlayer(), p.getLocation()));
				} else if (promotionType == 'N') {
					pieces.add(new Knight(p.getPlayer(), p.getLocation()));
				} else if (promotionType == 'B') {
					pieces.add(new Bishop(p.getPlayer(), p.getLocation()));
				} else if (promotionType == 'R') {
					pieces.add(new Rook(p.getPlayer(), p.getLocation()));
				}
				pieces.remove(p);
			}
		}
		for (Piece p : pieces) {
			if (p instanceof Pawn) {

			} else {
				if (p.getEnpassant() == true) {
					p.setEnpassant(false);
				}
			}
		}
		for (Piece p : pieces) {
			p.render(ChessBoard, currentPlayer);
		}
		if (inGame) {
			ChessBoard.render();
			// Check for a check/checkmate
			if (!ChessBoard.emulate('w')) {
				System.out.println("Checkmate");
				System.out.println("Black wins");
				inGame = false;
			} else if (!ChessBoard.emulate('b')) {
				System.out.println("Checkmate");
				System.out.println("White wins");
				inGame = false;
			} else if (ChessBoard.isWhiteChecked()) {
				if (ChessBoard.emulate('w')) {
					System.out.println("Check");
				} else {
					System.out.println("Checkmate");
					System.out.println("Black wins");
					inGame = false;
				}
			} else if (ChessBoard.isBlackChecked()) {
				if (ChessBoard.emulate('b')) {
					System.out.println("Check");
				} else {
					System.out.println("Checkmate");
					System.out.println("White wins");
					inGame = false;
				}
			}
			if (inGame) {
				System.out.print(((currentPlayer == 'w') ? ("White's") : ("Black's")) + " move: ");
			}
		}
	}
}