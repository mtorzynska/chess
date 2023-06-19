package pieces;
import board.*;
import game.Player;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Color color) {
        super(color);
    }

    @Override
    public boolean isMoveValid(Cell from, Cell to, Player player, Board board) {
        if (!super.isMoveValid(from, to, player, board)) return false;
        if (getFrom() == null || getTo() == null) return false;
        if (this.isPathBlocked()) return false;
        if (Math.abs(this.getFrom().getFile() - this.getTo().getFile()) > 1 ||
                Math.abs(this.getFrom().getRank() - this.getTo().getRank()) > 1) return false;
        return isMoveHorizontal() || isMoveDiagonal() || isMoveVertical();
    }

    public boolean isChecked(Board board, Player player) {
        setPlayer(player);
        Cell kingsPosition = board.findCell(new King(getColor()));
        Cell originalFrom = getFrom();
        Cell originalTo = getTo();
        Player attacker;
        Color opponentColor;
        if (getPlayer().getColor() == Color.WHITE) {
            attacker = new Player(Color.BLACK);
            opponentColor = Color.BLACK;
        } else {
            attacker = new Player(Color.WHITE);
            opponentColor = Color.WHITE;
        }
        List<Cell> opponentPieces;
        opponentPieces = getAllPieces(board, opponentColor);
        for (Cell spot : opponentPieces) {
            if (spot.getPiece().isMoveValid(spot, kingsPosition, attacker, board)) {
                setAttackerOnKing(spot);
                setFrom(originalFrom);
                setTo(originalTo);
                return true;
            }
        }
        setFrom(originalFrom);
        setTo(originalTo);
        return false;
    }

    public boolean canEscape(Board board) {
        /* iterating over all possible moves of the king and looking for a valid escape */
        Cell kingsPosition = board.findCell(new King(getColor()));
        Cell originalFrom = super.getFrom();
        Cell originalTo = super.getTo();
        boolean isStillChecked;
        for (int rank = -1; rank <= 1; rank++) {
            for (int file = -1; file <= 1; file++) {
                if (file == 0 && rank == 0) continue; //omitting current king position
                if (kingsPosition.getRank() + rank > 7 || kingsPosition.getFile() + file > 7 ||
                        kingsPosition.getRank() + rank < 0 || kingsPosition.getFile() + file < 0) continue;
                Cell to = (board.gameBoard[kingsPosition.getRank() + rank][kingsPosition.getFile() + file]);
                Piece currentOccupier = to.getPiece();

                if (this.isMoveValid(kingsPosition, to, getPlayer(), board)) {
                    // move king temporarily
                    board.updateBoard(kingsPosition, to);
                    isStillChecked = isChecked(board, getPlayer()); //is king still checked after potential move
                    //resume board's state
                    board.updateBoard(to, kingsPosition);
                    to.setPiece(currentOccupier);
                    if (!isStillChecked) {
                        super.setFrom(originalFrom);
                        super.setTo(originalTo);
                        return true;
                    }
                }
            }
        }
        super.setFrom(originalFrom);
        super.setTo(originalTo);
        return false;
    }

    public boolean canBeDefended(Board board) {
        /* iterating over the board to get pieces and see if they can capture the attacker */
        boolean isStillChecked;
        Cell originalFrom = super.getFrom();
        Cell originalTo = super.getTo();
        Cell attackingCell = getAttackerOnKing();
        Piece attackingPiece = attackingCell.getPiece();

        List<Cell> playersPieces = getAllPieces(board, getColor());
        for (Cell spot : playersPieces) {
            if (spot.getPiece().isMoveValid(spot, attackingCell, getPlayer(), board)) {
                //temporarily make the move
                board.updateBoard(spot, attackingCell);
                isStillChecked = isChecked(board, getPlayer());

                board.updateBoard(attackingCell, spot); //resume board's state
                attackingCell.setPiece(attackingPiece);
                super.setFrom(originalFrom);
                super.setTo(originalTo);

                if (!isStillChecked) return true; //a piece can capture the attacker
            }
        }
        super.setFrom(originalFrom);
        super.setTo(originalTo);
        return false;
    }

    public boolean canBeShielded(Board board) {
        /* iterating over pieces and checking if they can move to any place in the path between
         *  king and the attacking piece */
        Cell originalFrom = super.getFrom();
        Cell originalTo = super.getTo();
        Cell kingsPosition = board.findCell(new King(getColor()));
        Cell attackingCell = getAttackerOnKing();
        if (attackingCell.getPiece() instanceof Knight) return false;

        //determine the path between king and attacker
        int rankDiff = Math.abs(attackingCell.getRank() - kingsPosition.getRank());
        int fileDiff = Math.abs(attackingCell.getFile() - kingsPosition.getFile());
        int rankDirection = Integer.compare(attackingCell.getRank(), kingsPosition.getRank());
        int fileDirection = Integer.compare(attackingCell.getFile(), kingsPosition.getFile());

        List<Cell> playersPieces = getAllPieces(board, getColor());
        for (Cell possibleDefender : playersPieces) {
            for (int i = 1; i < rankDiff; i++) {
                for (int j = 1; j < fileDiff; j++) {
                    int rank = kingsPosition.getRank() + (i * rankDirection);
                    int file = kingsPosition.getFile() + (i * fileDirection);
                    if (file > 7 || file < 0 || rank > 7 || rank < 0) continue;
                    Cell defendingSpot = board.gameBoard[rank][file];
                    if (possibleDefender.getPiece().isMoveValid(possibleDefender, defendingSpot, getPlayer(), board)) {
                        super.setFrom(originalFrom);
                        super.setTo(originalTo);
                        return true;
                    }
                }
            }
        }
        super.setFrom(originalFrom);
        super.setTo(originalTo);
        return false;
    }

    public boolean isCheckMate(Board board, Player player) {
        setPlayer(player);
        if (this.isChecked(board, getPlayer())) {
            return !this.canBeDefended(board) && !canEscape(board) && !canBeShielded(board);
        }
        return false;
    }

    public boolean isStaleMate(Board board, Player player) {
        setPlayer(player);
        List<Cell> playersPieces = getAllPieces(board, getPlayer().getColor());

        if (isChecked(board, getPlayer())) return false;

        for (Cell piece : playersPieces){
            for (int rank = 0; rank < 8; rank++){
                for (int file = 0; file < 8; file++){
                    Cell possibleTo = board.gameBoard[rank][file];
                    if (piece.getPiece().isMoveValid(piece, possibleTo, getPlayer(), board)) {
                        //checking if the move that player makes puts the king in a check
                        Piece occupier = possibleTo.getPiece();
                        board.updateBoard(piece, possibleTo);
                        if (isChecked(board, getPlayer())){
                            board.updateBoard(possibleTo, piece);
                            possibleTo.setPiece(occupier);
                        }
                        else {
                            //if a move is valid and doesn't put the king in a check, it is not stalemate
                            board.updateBoard(possibleTo, piece);
                            possibleTo.setPiece(occupier);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}

