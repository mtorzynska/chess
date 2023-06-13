package game;
import board.Board;
import board.Cell;
import board.Color;
import pieces.*;
import java.util.Random;

public class Player{
    private final Color color;

    public Player(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return color;
    }

    public String generateMove(){
        Random random = new Random();

        char fileFrom = (char)('a' + random.nextInt(8));
        int rankFrom = random.nextInt(8) + 1;
        char fileTo = (char)('a' + random.nextInt(8));
        int rankTo = random.nextInt(8) + 1;

        return String.format("%c%d-%c%d", fileFrom, rankFrom, fileTo, rankTo);
    }

    public boolean makeMove(Cell from, Cell to, Board board){
        King king = (King) board.findCell(new King(getColor())).getPiece();
        if (!from.getPiece().isMoveValid(from, to, this, board)) return false;
        if (king.isChecked(board, this)){
            //update board to see if king is still checked
            Piece occupier = to.getPiece();
            board.updateBoard(from, to);
            if (king.isChecked(board, this)){
                board.updateBoard(to, from); //if king is still checked, move cannot be made
                to.setPiece(occupier);
                return false;
            }
            else{
                return true;
            }
        }
        //checking if player didn't move into a check
        Piece occupier = to.getPiece();
        board.updateBoard(from, to);
        if (king.isChecked(board, this)){
            board.updateBoard(to, from);
            to.setPiece(occupier);
            return false;
        }
        return true;
    }
}
