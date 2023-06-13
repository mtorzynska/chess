package pieces;
import board.*;
import game.Player;

public class Pawn extends Piece{

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public boolean isMoveValid(Cell from, Cell to, Player player, Board board) {
        if (!super.isMoveValid(from, to, player, board)) return false;
        if (getFrom() == null || getTo() == null) return false;
        if (this.isPathBlocked()) return false;

        if(this.isWhite() && this.getFrom().getRank() == 1 && Math.abs(this.getFrom().getRank() - this.getTo().getRank()) <= 2){
            if(this.isMoveVertical() && this.getFrom().getRank() < this.getTo().getRank() && this.getTo().isEmpty()){

                return true;
            }
        }
        if(!this.isWhite() && this.getFrom().getRank() == 6 && Math.abs(this.getFrom().getRank() - this.getTo().getRank()) <= 2) {
            if(this.isMoveVertical() && this.getFrom().getRank() > this.getTo().getRank() && this.getTo().isEmpty()){
                return true;
        }
        }
        if(Math.abs(this.getFrom().getRank() - this.getTo().getRank()) == 1) {
            if(this.isMoveVertical()){
                if(this.isWhite() && this.getFrom().getRank() < this.getTo().getRank() && this.getTo().isEmpty()) return true;
                if(!this.isWhite() && this.getFrom().getRank() > this.getTo().getRank() && this.getTo().isEmpty()) return true;
            }
        }
        //capturing
        if(Math.abs(this.getFrom().getRank() - this.getTo().getRank()) == 1 && Math.abs(this.getFrom().getFile() - this.getTo().getFile()) == 1){
            if(!this.getTo().isEmpty() && this.getTo().getPiece().getColor() != this.getColor()){
                if(this.isWhite() && this.getFrom().getRank() < this.getTo().getRank()) return true;
                if(!this.isWhite() && this.getFrom().getRank() > this.getTo().getRank()) return true;
            }
        }
        return false;
    }

}

