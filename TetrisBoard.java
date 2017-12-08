/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * A Pane in which tetris squares can be displayed.
 * 
 * @author pipWolfe
 */
public class TetrisBoard extends Pane{
	
	private TetrisSquare[][] squares = new TetrisSquare[Y_DIM_SQUARES][X_DIM_SQUARES];
	private int rowCompleted = 0;
	
    // The size of the side of a tetris square
    public static final int SQUARE_SIZE = 20;
    // The number of squares that fit on the screen in the x and y dimensions, 20, 30
    public static final int X_DIM_SQUARES = 20;
    public static final int Y_DIM_SQUARES = 30;

    /**
     * Sizes the board to hold the specified number of squares in the x and y
     * dimensions.
     */
    public TetrisBoard() {
        this.setPrefHeight(Y_DIM_SQUARES*SQUARE_SIZE);
        this.setPrefWidth(X_DIM_SQUARES*SQUARE_SIZE);
        BackgroundFill myBF = new BackgroundFill(Color.BLUEVIOLET, new CornerRadii(1),
            new Insets(0.0,0.0,0.0,0.0));// or null for the padding
        setBackground(new Background(myBF));

    }
    /**
     *  Check in the array, if the new locaiton is free
     *  If the new location equals to null, it is free and return true. Otherwise, return false.
     * @param location1
     * @param location2
     * @param location3
     * @param location4
     * @return
     */
    public boolean checkAvailability(Point2D location1, Point2D location2, Point2D location3, Point2D location4){
    	if (squares[(int)location1.getY()][(int)location1.getX()] == null && 
   				squares[(int)location2.getY()][(int)location2.getX()] == null&& 
   				squares[(int)location3.getY()][(int)location3.getX()] == null && 
   				squares[(int)location4.getY()][(int)location4.getX()] == null){
   			return true;
   		}
    	return false;
    }
    /**
     * Add tetris squares to the board
     * @param centerSquare
     * @param square2
     * @param square3
     * @param square4
     */
    public void addTetrisSquares(TetrisSquare centerSquare, TetrisSquare square2, TetrisSquare square3, TetrisSquare square4){	
    	squares[(int)centerSquare.getY()][(int)centerSquare.getX()] = centerSquare;
    	squares[(int)square2.getY()][(int)square2.getX()] = square2;
   		squares[(int)square3.getY()][(int)square3.getX()] = square3;
   		squares[(int)square4.getY()][(int)square4.getX()] = square4;
    }
    /**
     * Check if any row on the board is completed
     */
    public void checkCompleted(){  	
    	int occupiedSquares = 0;
    	int removedRow = 0;
    	// Check if any row is completed
    	for (int i = 0; i < Y_DIM_SQUARES; i++){
    		for (int j = 0; j < X_DIM_SQUARES; j++){	
    			if(squares[i][j] != null){ 
    				occupiedSquares++;
    			}
    		}
    		// If any row is completed, remove all squares in that row
    		if (occupiedSquares == X_DIM_SQUARES){
    			removedRow = i;		
    			for (int j = 0; j < X_DIM_SQUARES; j++){	
    				squares[i][j].removeFromDrawing();
    				squares[i][j] = null;
    			}
    			// Implement moveDown() methods to remove other squares down
    			moveDown(removedRow);
    			// Increment the number of completed row
    			rowCompleted++;
    		}	
    		// Before check the next row, set the occupiedSquares equal to 0
    		occupiedSquares = 0;
    	}
    }
    /**
     * After remove the completed row, move down all other squares above that row.
     * @param removedRow
     */
    public void moveDown(int removedRow){
    	// From bottom to top
    	for (int i = removedRow; i > 0; i--){
    		for (int j = 0; j < X_DIM_SQUARES; j++){
    			squares[i][j] = squares[i-1][j];
    			if (squares[i-1][j] != null){
    				squares[i][j].moveToTetrisLocation(squares[i-1][j].getX(), squares[i-1][j].getY()+1);
    			}
    			squares[i-1][j] = null;
    		}
    	}
    }
    /**
     * Every time we removed the row, the score should be increased by 100 
     * @return
     */
    public int getScore(){
    	int score = 0;
    	score = score + 100*rowCompleted;
    	return score;
    }    
}
