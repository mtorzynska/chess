package pieces;
import game.*;
import board.Board;
import board.Cell;
import board.Color;

public class Knight extends Piece{

    public Knight(Color color) {
        super(color);
    }

    @Override
    public boolean isMoveValid(Cell from, Cell to, Player player, Board board) {
        if (!super.isMoveValid(from, to, player, board)) return false;
        if (getFrom() == null || getTo() == null) return false;
        int fileDiff = Math.abs(this.getTo().getFile() - this.getFrom().getFile());
        int rankDiff = Math.abs(this.getTo().getRank() - this.getFrom().getRank());
        return (rankDiff == 2 && fileDiff == 1) || (rankDiff == 1 && fileDiff == 2);
    }



}
