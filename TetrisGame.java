/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import javafx.scene.paint.Color;

/**
 * This should be implemented to include your game control.
 * 
 * @author pipWolfe
 */
public class TetrisGame {
	private final Tetris tetrisApp;
	private TetrisPieces tetris;
	private TetrisBoard board;
	private int score;
	private boolean gameOver = false;

	/**
	 * Initialize the game. Remove the example code and replace with code that
	 * creates a random piece.
	 * 
	 * @param tetrisApp
	 *            A reference to the application (use to set messages).
	 * @param board
	 *            A reference to the board on which Squares are drawn
	 */
	public TetrisGame(Tetris tetrisApp, TetrisBoard board) {
		/**
		 * create four tetris squares
		 */
		this.board = board;
		tetris = new TetrisPieces(board);

		this.tetrisApp = tetrisApp;
		// You can use this to show the score, etc.
		tetrisApp.setMessage("Game has started!");
	}
	/**
	 * Generate a new random piece
	 */
	public void generateNewPiece() {
		if (!gameOver){
			tetris = new TetrisPieces(board);
		}
	}
	/**
	 * Animate the game, by moving the current tetris piece down.
	 */
	void update() {
//		System.out.println("updating");
		
		tetris.checkDown();
		gameOver = tetris.getGameOver();
		
		// If gameOver is true, stop
		if (gameOver){
			tetrisApp.setMessage("Game over :(" + "     " + "Your final score is " + score);
			System.out.println("Game over!");
		}
		// If gameOver is not true and tetri piece can move down
		else if ((!gameOver) && tetris.checkDown()) {
			// move it down
			tetris.moveDownward();
		} 
		// If gameOver is not true and tetris cannot move down
		else if ((!gameOver) && (!tetris.checkDown())){
			// Check if any row is completed
			board.checkCompleted();
			// Check if the score should be increased
			score = board.getScore();
			// Update the score
			tetrisApp.setMessage("Score: " + score);
			// Generate a new piece
			generateNewPiece();
		}
	}

	/**
	 * Move the current tetris piece left.
	 */
	void left() {
		System.out.println("left key was pressed!");
		if (!gameOver){
			tetris.moveLeft();
		}
	}

	/**
	 * Move the current tetris piece right.
	 */
	void right() {
		System.out.println("right key was pressed!");
		if (!gameOver){
			tetris.moveRight();
		}
	}

	/**
	 * Drop the current tetris piece.
	 */
	void drop() {
		System.out.println("drop key was pressed!");
		if (!gameOver){
			tetris.drop();
			}
	}

	/**
	 * Rotate the current piece counter-clockwise.
	 */
	void rotateLeft() {
		System.out.println("rotate left key was pressed!");
		if (!gameOver){
			tetris.rotateLeft();
		}
	}

	/**
	 * Rotate the current piece clockwise.
	 */
	void rotateRight() {
		System.out.println("rotate right key was pressed!");
		if (!gameOver){
			tetris.rotateRight();
		}
	}

}
