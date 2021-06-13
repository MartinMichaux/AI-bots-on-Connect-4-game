import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MCTS {
    Connect_4 currentGame;
    Connect_4 simBoard;

    final int SIM_NBR = 5;
    int curSimNbr;

    ArrayList<GamePiece> possibleMoves;

    public MCTS(Connect_4 game){
        this.currentGame = game;
    }

    public String nextMove() throws CloneNotSupportedException, InterruptedException {
        possibleMoves = new ArrayList<>();
        getPossibleMoves();
        for (int i = 0; i < possibleMoves.size(); i++) {
            System.out.println("NEW PIECE");
            curSimNbr = 0;
            while(curSimNbr<SIM_NBR){
                System.out.println("NEW SIMULATION");
                currentGame.printBoard();
                //clone boards
                simBoard = new Connect_4();
                simBoard = currentGame.clone();
                simBoard.basicDifficulty.setSelected(true);
                simBoard.winner = null;
                simGame(possibleMoves.get(i));
                curSimNbr++;
            }
        }
        return getBestMove().name;
    }

    public void simGame(GamePiece firstMove) throws InterruptedException {
        //first move
        makeFirstMove(firstMove);

        while(!simBoard.playerHasWon){
            simBoard.printBoard();
            simBoard.computerInputMethod();
            simBoard.checkWinMethod();
        }

        System.out.println(simBoard.winner + " with move " + firstMove.name);

        if (simBoard.winner.equals("It's a Tie!")) {
            firstMove.ties++;
        }else if(simBoard.winner.equals("Yellow Wins!"))
            firstMove.firstPWins++;
        else
            firstMove.secondPWins++;
    }

    public void makeFirstMove(GamePiece move){
        simBoard.xMouse = move.getRow();
        simBoard.yMouse = move.getCol();
        simBoard.humanInputMethod();
    }

    public void getPossibleMoves(){
        /* Checks if the x coordinate of the last click, xMouse, is inside of first column [this is tested by checking
         * if xMouse is less than (to the left of) the right border of the column (retrieved by using .getBorder)]
         *
         * If it is then it will check each piece starting from the bottom until it finds one that has not been placed
         *
         * If the click is not within the the first column then it will check again with the next
         * column and so on until there are no columns left
         */
        for (Column column: currentGame.columnArray) {
            if(column.getPieceArray().size()!=0){
                GamePiece minRowPiece = GamePiece.copy(column.getPieceArray().get(0));
                for (GamePiece piece: column.getPieceArray()) {
                    if (piece.getFill().equals(Color.WHITE)&&piece.row>minRowPiece.row) {
                        minRowPiece = GamePiece.copy(piece);
                    }
                }
                possibleMoves.add(minRowPiece);
            }
        }
    }

    public GamePiece getBestMove(){
        GamePiece bestMove = null;
        if (currentGame.piecesOnBoard % 2 == 0) {
            bestMove = possibleMoves.get(0);
            for (int i = 0; i < possibleMoves.size(); i++) {
                if(possibleMoves.get(i).firstPWins> bestMove.firstPWins){
                    bestMove = possibleMoves.get(i);
                }
            }
        }
        else {
            bestMove = possibleMoves.get(0);
            for (int i = 0; i < possibleMoves.size(); i++) {
                if(possibleMoves.get(i).secondPWins> bestMove.secondPWins){
                    bestMove = possibleMoves.get(i);
                }
            }
        }
        return bestMove;
    }
}
