package pieces;
import board.*;
import game.Player;

import java.util.ArrayList;
import java.util.List;

import static board.Color.*;

public abstract class Piece{
    private final Color color;
    private Board board;
    private Player player;
    private Cell from;
    private Cell to;
    private Cell attackerOnKing;

    public Piece(Color color){
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean isWhite(){
        return this.getColor() == WHITE;
    }
    public boolean isMoveValid(Cell from, Cell to, Player player, Board board){
        setBoard(board);
        setFrom(from);
        setTo(to);
        setPlayer(player);
        if (getFrom() == null || getTo() == null) return false;
        if (this.getFrom().isEmpty()) return false; //empty spot
        if (this.getPlayer().getColor() != this.getFrom().getPiece().getColor()) return false; //moving not your color
        if (!this.getTo().isEmpty() && this.getFrom().getPiece().getColor() == this.getTo().getPiece().getColor()) return false; //moving your piece to a spot already occupied by your piece
        return true;
    }

    public Cell getTo() {
        return to;
    }
    public void setTo(Cell to) {
        this.to = to;
    }

    public Cell getFrom() {
        return from;
    }
    public void setFrom(Cell from) {
        this.from = from;
    }

    public boolean isMoveHorizontal(){
        if (getFrom() == null || getTo() == null) return false;
        return this.getFrom().getRank() == this.getTo().getRank() && isInRange(this.getTo());
    }
    public boolean isMoveVertical(){
        if (getFrom() == null || getTo() == null) return false;
        return this.getFrom().getFile() == this.getTo().getFile() && isInRange(this.getTo());
    }
    public boolean isMoveDiagonal(){
        if (getFrom() == null || getTo() == null) return false;
        int fileDiff = Math.abs(this.getTo().getFile() - this.getFrom().getFile());
        int rankDiff = Math.abs(this.getTo().getRank() - this.getFrom().getRank());
        return fileDiff == rankDiff && isInRange(this.getTo());
    }

    public boolean isPathBlocked() {
        if (isMoveHorizontal()) {
            int start = getFrom().getFile();
            int end = getTo().getFile();
            int rank = getFrom().getRank();
            if(getFrom().getFile() < getTo().getFile()) {
                for (int file = start + 1; file < end; file++) { //for the white pieces
                    if (!getBoard().gameBoard[rank][file].isEmpty()) {
                        return true;
                    }
                }
            }
            else {
                for (int file = start - 1; file > end; file--) { //for the black pieces
                    if (!getBoard().gameBoard[rank][file].isEmpty()) {
                        return true;
                    }
                }
            }
        } else if (isMoveVertical()) {
            int start = getFrom().getRank();
            int end = getTo().getRank();
            int file = getFrom().getFile();
            if(getFrom().getRank() < getTo().getRank()){ //for the white pieces
                for (int rank = start + 1; rank < end; rank++) {
                    if (!getBoard().gameBoard[rank][file].isEmpty()) {
                        return true;
                    }
                }
            }
            else {
                for (int rank = start - 1; rank > end; rank--) { //for the black pieces
                    if (!getBoard().gameBoard[rank][file].isEmpty()) {
                        return true;
                    }
                }
            }
        } else if (isMoveDiagonal()) {
            int startFile = getFrom().getFile();
            int endFile = getTo().getFile();
            int startRank = getFrom().getRank();
            int endRank = getTo().getRank();

            int fileDirection = Integer.compare(getTo().getFile(), getFrom().getFile());//indicating which way player is trying to move
            int rankDirection = Integer.compare(getTo().getRank(), getFrom().getRank());

            int file = startFile + fileDirection;
            int rank = startRank + rankDirection;

            while (file != endFile && rank != endRank) {
                if (!getBoard().gameBoard[rank][file].isEmpty()) {
                    return true;
                }
                file += fileDirection;
                rank += rankDirection;
            }
        }
        return false;
    }

    public boolean isInRange(Cell to) {
        if (from.getFile() > 7 || from.getFile() < 0) return false;
        if (to.getRank() > 7 || to.getRank() < 0) return false;
        return true;
    }

    public List<Cell> getAllPieces(Board board, Color color) {
        List<Cell> opponentPieces = new ArrayList<>();

        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Cell cell = board.gameBoard[rank][file];

                if (!cell.isEmpty() && cell.getPiece().getColor() == color) {
                    opponentPieces.add(cell);
                }
            }
        }
        return opponentPieces;
    }

    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    public Cell getAttackerOnKing() {
        return attackerOnKing;
    }
    public void setAttackerOnKing(Cell attackerOnKing) {
        this.attackerOnKing = attackerOnKing;
    }
}

