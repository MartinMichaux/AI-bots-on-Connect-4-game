import javafx.scene.shape.Circle;

public class GamePiece extends Circle{
	String name;
	char letter;
	int number;
	int col;
	int row;
	boolean used = false;
	int firstPWins = 0;
	int secondPWins = 0;
	int ties = 0;
	
	//Rows and columns start at 1, not 0
	public GamePiece(char letter, int number){
		super();
		this.letter = letter;
		this.number = number;
		name = Character.toString(letter) + number;
		row = letter - 'A' + 1;
		col = number;		
	}
	
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}

	public static GamePiece copy( GamePiece other ) {
		GamePiece newPiece = new GamePiece(other.letter, other.number);
		newPiece.firstPWins = other.firstPWins;
		newPiece.secondPWins = other.secondPWins;
		newPiece.ties = other.ties;
		newPiece.name = other.name;
		newPiece.col = other.col;
		newPiece.row = other.row;

		return newPiece;
	}
}
