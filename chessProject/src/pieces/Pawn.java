package pieces;
import board.*;
import game.Player;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public boolean isMoveValid(Cell from, Cell to, Player player, Board board) {
        if (!super.isMoveValid(from, to, player, board)) return false;
        if (getFrom() == null || getTo() == null) return false;
        if (this.isPathBlocked()) return false;
        return (isRegularMoveCorrect() || isCaptureCorrect());
    }

    private boolean isRegularMoveCorrect() {
        // first moves
        if (this.isWhite() && this.getFrom().getRank() == 1 && Math.abs(this.getFrom().getRank() - this.getTo().getRank()) <= 2) {
            if (this.isMoveVertical() && this.getTo().isEmpty()) {
                return this.isGoingForwards();
            }
        }
        if (!this.isWhite() && this.getFrom().getRank() == 6 && Math.abs(this.getFrom().getRank() - this.getTo().getRank()) <= 2) {
            if (this.isMoveVertical() && this.getTo().isEmpty()) {
                return this.isGoingForwards();
            }
        }

        //other moves than first
        if (Math.abs(this.getFrom().getRank() - this.getTo().getRank()) == 1) {
            if (this.isMoveVertical() && this.getTo().isEmpty()){
                return this.isGoingForwards();
            }
        }
        return false;
    }
    private boolean isCaptureCorrect() {
        if (Math.abs(this.getFrom().getRank() - this.getTo().getRank()) == 1 && Math.abs(this.getFrom().getFile() - this.getTo().getFile()) == 1) {
            if (!this.getTo().isEmpty() && this.getTo().getPiece().getColor() != this.getColor()) return isGoingForwards();
        }
        return false;
    }
    private boolean isGoingForwards() {
        if (this.isWhite() && this.getFrom().getRank() < this.getTo().getRank()) return true;
        if (!this.isWhite() && this.getFrom().getRank() > this.getTo().getRank()) return true;
        return false;
    }

}

