package pieces;
import board.*;
import game.Player;


public class Rook extends Piece
{
    public Rook(Color color) {
        super(color);
    }

    @Override
    public boolean isMoveValid(Cell from, Cell to, Player player, Board board) {
        if (!super.isMoveValid(from, to, player, board)) return false;
        if (getFrom() == null || getTo() == null) return false;
        if (isPathBlocked()) return false;
        return isMoveHorizontal() || isMoveVertical();
    }

}



