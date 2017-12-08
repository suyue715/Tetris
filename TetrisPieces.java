package tetris;
import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class TetrisPieces {
	/**
	 * data field
	 */
	// four squares, one center square and three related squares
	private TetrisSquare centerSquare;
	private TetrisSquare square2; 
	private TetrisSquare square3; 
	private TetrisSquare square4;
	// relative locations of three related pieces
	private Point2D relative2;
	private Point2D relative3;
	private Point2D relative4;
	private TetrisBoard board;
	// new location of the four squares
	private Point2D	newLocationOfCenterSquare;
	private Point2D	newLocationOfRelative2;
	private Point2D	newLocationOfRelative3;
	private Point2D	newLocationOfRelative4;
	
	private int centerSquareStartY = -3;
	private boolean gameOver = false;

	/**
	 * The constructor.
	 * Construct four squares
	 * @param board
	 */
	public TetrisPieces(TetrisBoard board){
		centerSquare = new TetrisSquare(board);
		// The start location of the center square should be in the top center of the board
		centerSquare.moveToTetrisLocation(board.X_DIM_SQUARES/2, centerSquareStartY);
		square2 = new TetrisSquare(board);
		square3 = new TetrisSquare(board);
		square4 = new TetrisSquare(board);
		this.board = board;
		generateRandomPiece();
	}
	/**
	 * Generate a random number to determine which shape of the tetris piece should be generated
	 */
	public void generateRandomPiece(){
		// Generate a random number from 0 to 6
		Random random = new Random();
		int randomNumber = random.nextInt(7);
		// According to different numbers, arrange squares to different shapes
		if (randomNumber == 0){
			squareShape();
		}
		if (randomNumber == 1){
			lShape();
		}
		if (randomNumber == 2){
			inverseLShape();
		}
		if (randomNumber == 3){
			zShape();
		}
		if (randomNumber == 4){
			inverseZShape();
		}
		if (randomNumber == 5){
			iShape();
		}
		if (randomNumber == 6){
			tShape();
		}
	}
	/**
	 * For the square shape, assign relative locations and move the three relative squares to assigned locations
	 */
	public void squareShape(){
		relative2 = new Point2D(-1, -1);
		relative3 = new Point2D(0, -1);
		relative4 = new Point2D(-1, 0);
		addRelatives(relative2, relative3, relative4);
	}
	/**
	 * For the L shape, assign relative locations and move the three relative squares to assigned locations
	 */
	public void lShape(){
		relative2 = new Point2D(-1, -1);
		relative3 = new Point2D(0, -1);
		relative4 = new Point2D(0, 1);
		addRelatives(relative2, relative3, relative4);
	}
	/**
	 * For the inverse L shape, assign relative locations and move the three relative squares to assigned locations
	 */
	public void inverseLShape(){
		relative2 = new Point2D(0, -1);
		relative3 = new Point2D(1, -1);
		relative4 = new Point2D(0, 1);
		addRelatives(relative2, relative3, relative4);
	}
	/**
	 * For the Z shape, assign relative locations and move the three relative squares to assigned locations
	 */
	public void zShape(){
		relative2 = new Point2D(-1, -1);
		relative3 = new Point2D(1, 0);
		relative4 = new Point2D(0, -1);
		addRelatives(relative2, relative3, relative4);
	}
	/**
	 * For the inverse Z shape, assign relative locations and move the three relative squares to assigned locations
	 */
	public void inverseZShape(){
		relative2 = new Point2D(-1, 0);
		relative3 = new Point2D(1, -1);
		relative4 = new Point2D(0, -1);
		addRelatives(relative2, relative3, relative4);
	}
	/**
	 * For the I shape, assign relative locations and move the three relative squares to assigned locations
	 */
	public void iShape(){
		relative2 = new Point2D(0, -1);
		relative3 = new Point2D(0, 1);
		relative4 = new Point2D(0, 2);
		addRelatives(relative2, relative3, relative4);
	}
	/**
	 * For the T shape, assign relative locations and move the three relative squares to assigned locations
	 */
	public void tShape(){
		relative2 = new Point2D(-1, 0);
		relative3 = new Point2D(1, 0);
		relative4 = new Point2D(0, 1);
		addRelatives(relative2, relative3, relative4);
	}	
	/**
	 * Calculate new locations first. If the new locations are available, move the piece to the left
	 */
	public void moveLeft(){
		// Calculate new locations
		newLocationOfCenterSquare = new Point2D(centerSquare.getX() -1, centerSquare.getY());
		newLocationOfRelative2 = new Point2D(square2.getX()-1, square2. getY());
		newLocationOfRelative3 = new Point2D(square3.getX()-1, square3. getY());
		newLocationOfRelative4 = new Point2D(square4.getX()-1, square4. getY());
		// Check if the new locations are free
		if (checkObstacles(newLocationOfCenterSquare, newLocationOfRelative2, 
				newLocationOfRelative3, newLocationOfRelative4)){
			// Move the center square to left, if the new locations are free
			centerSquare.moveToTetrisLocation(centerSquare.getX()-1, centerSquare.getY());			
			// Add those relatives
			addRelatives(relative2, relative3, relative4);
		}			
	}
	/**
	 * Calculate new locations first. If the new locations are available, move the piece to the right
	 */
	public void moveRight(){	
		newLocationOfCenterSquare = new Point2D(centerSquare.getX() + 1, centerSquare.getY());
		newLocationOfRelative2 = new Point2D(square2.getX()+1, square2.getY());
		newLocationOfRelative3 = new Point2D(square3.getX()+1, square3.getY());
		newLocationOfRelative4 = new Point2D(square4.getX()+1, square4.getY());	
		
		if (checkObstacles(newLocationOfCenterSquare, newLocationOfRelative2, 
				newLocationOfRelative3, newLocationOfRelative4)){
			centerSquare.moveToTetrisLocation(centerSquare.getX()+1, centerSquare.getY());
			addRelatives(relative2, relative3, relative4);
		}		
	}	
	/**
	 * Calculate new locations first. If the new locations are available, left rotate the piece
	 */
	public void rotateLeft(){	
		newLocationOfCenterSquare = new Point2D(centerSquare.getX(), centerSquare.getY());
		newLocationOfRelative2 = new Point2D(newLocationOfCenterSquare.getX()+relative2.getY(),
				newLocationOfCenterSquare.getY()-relative2.getX());
		newLocationOfRelative3 = new Point2D(newLocationOfCenterSquare.getX()+relative3.getY(),
				newLocationOfCenterSquare.getY()-relative3.getX());
		newLocationOfRelative4 = new Point2D(newLocationOfCenterSquare.getX()+relative4.getY(),
				newLocationOfCenterSquare.getY()-relative4.getX());
		
		if (checkObstacles(newLocationOfCenterSquare, newLocationOfRelative2, 
				newLocationOfRelative3, newLocationOfRelative4)){
			relative2 = new Point2D(relative2.getY(), -relative2.getX());
			relative3 = new Point2D(relative3.getY(), -relative3.getX());
			relative4 = new Point2D(relative4.getY(), -relative4.getX());
			addRelatives(relative2, relative3, relative4);
		}
	}
	/**
	 * Calculate new locations first. If the new locations are available, right rotate the piece
	 */
	public void rotateRight(){
		newLocationOfCenterSquare = new Point2D(centerSquare.getX(), centerSquare.getY());
		newLocationOfRelative2 = new Point2D(newLocationOfCenterSquare.getX()-relative2.getY(),
				newLocationOfCenterSquare.getY()+relative2.getX());
		newLocationOfRelative3 = new Point2D(newLocationOfCenterSquare.getX()-relative3.getY(),
				newLocationOfCenterSquare.getY()+relative3.getX());
		newLocationOfRelative4 = new Point2D(newLocationOfCenterSquare.getX()-relative4.getY(),
				newLocationOfCenterSquare.getY()+relative4.getX());
		
		if (checkObstacles(newLocationOfCenterSquare, newLocationOfRelative2, 
				newLocationOfRelative3, newLocationOfRelative4)){
			relative2 = new Point2D(-relative2.getY(), relative2.getX());
			relative3 = new Point2D(-relative3.getY(), relative3.getX());
			relative4 = new Point2D(-relative4.getY(), relative4.getX());
			addRelatives(relative2, relative3, relative4);
		}
	}
	/**
	 * New generated piece should automatically move down for every update
	 */
	public void moveDownward(){
		// Before move the piece down, check if the new locations are free
		if (checkDown()){
			// If the new locations are free, move the piece down
			centerSquare.moveToTetrisLocation(centerSquare.getX(), centerSquare.getY()+1);
			addRelatives(relative2, relative3, relative4);
		}	
	}
	/**
	 * By click the drop key, the tetris piece can drop down
	 */
	public void drop(){
		while (checkDown()){
			centerSquare.moveToTetrisLocation(centerSquare.getX(), centerSquare.getY()+1);
			addRelatives(relative2, relative3, relative4);		
		}
	}
	/**
	 * Move the three relative squares to assigned locations
	 * @param relative2
	 * @param relative3
	 * @param relative4
	 */
	public void addRelatives(Point2D relative2, Point2D relative3, Point2D relative4){
		square2.moveToTetrisLocation((int)(centerSquare.getX()+relative2.getX()), (int)(centerSquare.getY()+relative2.getY()));
		square3.moveToTetrisLocation((int)(centerSquare.getX()+relative3.getX()), (int)(centerSquare.getY()+relative3.getY()));
		square4.moveToTetrisLocation((int)(centerSquare.getX()+relative4.getX()), (int)(centerSquare.getY()+relative4.getY()));
	}

	/**
	 * checkDown() method can check if the new locations are free when the piece is going to move down
	 * @return
	 */
	public boolean checkDown(){
		// Calculate the new locations
		newLocationOfCenterSquare = new Point2D(centerSquare.getX(), centerSquare.getY() + 1);
		newLocationOfRelative2 = new Point2D(square2.getX(),square2.getY() + 1);
		newLocationOfRelative3 = new Point2D(square3.getX(),square3.getY() + 1);
		newLocationOfRelative4 = new Point2D(square4.getX(),square4.getY() + 1);
		
		//  For any square with new y coordinate that is less than 0, always return true (move it down).
		if ((newLocationOfCenterSquare.getY() < 0 || newLocationOfRelative2.getY() < 0 ||
				newLocationOfRelative3.getY() < 0 || newLocationOfRelative4.getY() < 0) && (!gameOver)){
			return true; 
		}
		// For any square with new y coordinate that equal 0, check if the game is over
		if (newLocationOfCenterSquare.getY() == 0 || newLocationOfRelative2.getY() == 0 ||
				newLocationOfRelative3.getY() == 0 || newLocationOfRelative4.getY() == 0){
			// If the new location is free, return true (game is not over, the piece can still move down)
			if (board.checkAvailability(newLocationOfCenterSquare, newLocationOfRelative2, newLocationOfRelative3,
					newLocationOfRelative4)){
				return true;
		 	}
			// If the new location is not free, return false (game is over and the piece cannot move down)
			else{
				gameOver = true;
				// Remove any square with negative y coordinate to show full message
				if (centerSquare.getY() < 0 ){
					centerSquare.removeFromDrawing();
				}
				if (square2.getY() < 0){
					square2.removeFromDrawing();
				}
				if (square3.getY() < 0){
					square3.removeFromDrawing();
				}
				if (square4.getY() < 0){
					square4.removeFromDrawing();
				}
				return false;
			}
		}
		// For any square with new y coordinate that is greater or equal to 30, always add to the board.
		if (newLocationOfCenterSquare.getY() >= 30 || newLocationOfRelative2.getY() >= 30 ||
				newLocationOfRelative3.getY() >= 30 || newLocationOfRelative4.getY() >= 30){
			board.addTetrisSquares(centerSquare, square2, square3, square4);
			return false; 
		}
		// Intermediate general check, if the new location is free, return true (move the piece down)
		if (board.checkAvailability(newLocationOfCenterSquare, newLocationOfRelative2, newLocationOfRelative3,
				newLocationOfRelative4)){
			return true;
	 	}
		// Intermediate general check, it the new location is not free, return false (do not move down)
		else {
	 		board.addTetrisSquares(centerSquare, square2, square3, square4);
	 		return false;	
	 	}
	}
	/**
	 * Return gameOver
	 * @return
	 */
	public boolean getGameOver(){
		return gameOver;
	}
	/**
	 *  checkObstacles() method can check if the new location is free, when the piece is going to move left/right and rotate
	 * @param newLocationOfCenterSquare
	 * @param newLocationOfRelative2
	 * @param newLocationofRelative3
	 * @param newLocationOfRelative4
	 * @return
	 */
	public boolean checkObstacles(Point2D newLocationOfCenterSquare, Point2D newLocationOfRelative2,
			Point2D newLocationofRelative3, Point2D newLocationOfRelative4){	
		// For any square with negative y coordinate, always move left/right and rotate
		if (newLocationOfCenterSquare.getY() < 0 || newLocationOfRelative2.getY() < 0 ||
				newLocationOfRelative3.getY() < 0 || newLocationOfRelative4.getY() < 0){
			return true; 
		}
		// When this piece is on the right/left edge, return false (cannot move)
		if (newLocationOfCenterSquare.getX() < 0 || newLocationOfCenterSquare.getX() > board.X_DIM_SQUARES-1 ||
				newLocationOfRelative2.getX() < 0 || newLocationOfRelative2.getX() > board.X_DIM_SQUARES-1 ||
				newLocationOfRelative3.getX() < 0 || newLocationOfRelative3.getX() > board.X_DIM_SQUARES-1 ||
				newLocationOfRelative4.getX() < 0 || newLocationOfRelative4.getX() > board.X_DIM_SQUARES-1){
			return false; 
		}
		// Intermediate general check, if the new location is free, return true (move)
		if (board.checkAvailability(newLocationOfCenterSquare, newLocationOfRelative2, newLocationOfRelative3,
				newLocationOfRelative4)){
			return true;
 		}   
		// Intermediate general check, if the new location is not free, return false (do no move)
		else{
			return false;	
		}
	}
}