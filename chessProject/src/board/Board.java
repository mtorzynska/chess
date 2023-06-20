package board;
import game.Player;
import pieces.*;

public class Board{
    public Cell[][] gameBoard;

    public Board(){
        this.gameBoard = new Cell[8][8];
        initCells();
        initPieces();
    }

    private void initCells(){
        for(int rank=0; rank<8; rank++){
            for(int file=0; file<8; file++){
                gameBoard[rank][file] = new Cell(rank, file);
            }
        }
    }

    private void initPieces(){
        for(int file=0; file<8; file++){
            gameBoard[1][file].setPiece(new Pawn(Color.WHITE));
            gameBoard[6][file].setPiece(new Pawn(Color.BLACK));
        }
        gameBoard[0][0].setPiece(new Rook(Color.WHITE));
        gameBoard[0][7].setPiece(new Rook(Color.WHITE));
        gameBoard[7][0].setPiece(new Rook(Color.BLACK));
        gameBoard[7][7].setPiece(new Rook(Color.BLACK));

        gameBoard[0][1].setPiece(new Knight(Color.WHITE));
        gameBoard[0][6].setPiece(new Knight(Color.WHITE));
        gameBoard[7][1].setPiece(new Knight(Color.BLACK));
        gameBoard[7][6].setPiece(new Knight(Color.BLACK));

        gameBoard[0][2].setPiece(new Bishop(Color.WHITE));
        gameBoard[0][5].setPiece(new Bishop(Color.WHITE));
        gameBoard[7][2].setPiece(new Bishop(Color.BLACK));
        gameBoard[7][5].setPiece(new Bishop(Color.BLACK));

        gameBoard[0][3].setPiece(new Queen(Color.WHITE));
        gameBoard[7][3].setPiece(new Queen(Color.BLACK));

        gameBoard[0][4].setPiece(new King(Color.WHITE));
        gameBoard[7][4].setPiece(new King(Color.BLACK));
    }

    public void printBoard() {
        System.out.print("   ");
        for (int i = 0; i < 8; i++) System.out.print((char) ('A' + i) + "    ");
        System.out.println();

        for (int row = 7; row >= 0; row--) {
            System.out.print(row + 1 + " ");
            for (int col = 0; col < 8; col++) System.out.print(gameBoard[row][col] + " ");
            System.out.println(row + 1);
        }

        System.out.print("   ");
        for (int i = 0; i < 8; i++) System.out.print((char) ('A' + i) + "    ");
        System.out.println();
    }

    public void updateBoard(Cell from, Cell to){
        if((from.getPiece() instanceof Pawn && from.getPiece().isWhite() && to.getRank() == 7) ||
                (from.getPiece() instanceof Pawn && !from.getPiece().isWhite() && to.getRank() == 0)){
            //promoting a pawn - automatically a queen for simplicity
            this.gameBoard[to.getRank()][to.getFile()].setPiece(new Queen(from.getPiece().getColor()));
        }
        else{
            this.gameBoard[to.getRank()][to.getFile()].setPiece(from.getPiece());
        }
        from.emptyOutCell();
    }

    public Cell findCell(Piece piece){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if (this.gameBoard[i][j].getPiece() == null) continue;
                if (this.gameBoard[i][j].getPiece().getClass() == piece.getClass() && this.gameBoard[i][j].getPiece().getColor() == piece.getColor()) {
                    return gameBoard[i][j];
                }
            }
        }
        return null;
    }

}
