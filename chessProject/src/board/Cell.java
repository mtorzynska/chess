package board;
import pieces.*;

public class Cell {
    private Piece piece = null;
    private int file; //column (A-H)
    private int rank; //row (1-8)

    public Cell(int rank, int file){
        this.setRank(rank);
        this.setFile(file);
    }
    public Cell(Piece piece, int rank, int file){
        this.setRank(rank);
        this.setFile(file);
        this.setPiece(piece);
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    public Piece getPiece() {
        return this.piece;
    }

    public int getFile() {
        return this.file;
    }
    public int getRank() {
        return this.rank;
    }

    public void setFile(int file) {
        this.file = file;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isEmpty() {
        return !(this.getPiece() instanceof King) && !(this.getPiece() instanceof Queen) && !(this.getPiece() instanceof Rook)
                && !(this.getPiece() instanceof Bishop) && !(this.getPiece() instanceof Pawn) && !(this.getPiece() instanceof Knight);
    }

    public void emptyOutCell(){
        this.piece = null;
    }

    @Override
    public String toString() {
        if (this.isEmpty()) return "[  ]";
        else if (this.getPiece() instanceof King) {
            if (this.piece.getColor() == Color.WHITE) return "[WK]";
            else return "[BK]";
        }
        else if (this.getPiece() instanceof Queen) {
            if (this.piece.getColor() == Color.WHITE) return "[WQ]";
            else return "[BQ]";
        }
        else if (this.getPiece() instanceof Rook) {
            if (this.piece.getColor() == Color.WHITE) return "[WR]";
            else return "[BR]";
        }
        else if (this.getPiece() instanceof Bishop) {
            if (this.piece.getColor() == Color.WHITE) return "[WB]";
            else return "[BB]";
        }
        else if (this.getPiece() instanceof Knight) {
            if (this.piece.getColor() == Color.WHITE) return "[WS]";
            else return "[BS]"; //S for skoczek
        }
        else if (this.getPiece() instanceof Pawn) {
            if (this.piece.getColor() == Color.WHITE) return "[WP]";
            else return "[BP]";
        }
        else return "[  ]";
    }
}
