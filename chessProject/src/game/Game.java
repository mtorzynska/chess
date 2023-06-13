package game;
import board.*;
import pieces.*;
import game.*;

import java.util.Random;
import java.util.Scanner;

public class Game{
    private final Board board;
    private Player humanPlayer;
    private Player computerPlayer;
    private Player currentPlayer;
    private Cell from;
    private Cell to;

    public Game() {
        this.board = new Board();
        assignColors();
        if (humanPlayer.getColor() == Color.WHITE) currentPlayer = humanPlayer;
        else currentPlayer = computerPlayer;
    }

    public void startGame() {
        board.printBoard();
        while (true) {
            boolean canGameContinue = false; //game can continue after the player makes a valid move

            if (currentPlayer == humanPlayer) {
                System.out.println("Your move!");
                while (!canGameContinue) {
                    getInput();
                    if (getFrom().isEmpty()) continue;
                    canGameContinue = currentPlayer.makeMove(getFrom(), getTo(), board);
                    if (isGameOver()) break;
                }
                board.printBoard();
                currentPlayer = computerPlayer;
            }
            else {
                System.out.println("Opponent's move!");
                while (!canGameContinue) {
                    String move = currentPlayer.generateMove();
                    convertInput(move);
                    if (getFrom().isEmpty()) continue;
                    canGameContinue = currentPlayer.makeMove(getFrom(), getTo(), board);
                    if (isGameOver()) break;
                }
                currentPlayer = humanPlayer;
                board.printBoard();
                if (isGameOver()) break;
            }
        }
        }

    private boolean isGameOver() {
        Player whitePlayer;
        Player blackPlayer;
        King whiteKing = (King) board.findCell(new King(Color.WHITE)).getPiece();
        King blackKing = (King) board.findCell(new King(Color.BLACK)).getPiece();
        if (humanPlayer.getColor() == Color.WHITE)
        {
            whitePlayer = humanPlayer;
            blackPlayer = computerPlayer;
        }
        else {
            whitePlayer = computerPlayer;
            blackPlayer = humanPlayer;
        }
        if ((whiteKing.isStaleMate(board, whitePlayer)) || (blackKing.isStaleMate(board, blackPlayer))) {
            System.out.println("PAT!!!!!!!!!!!!!!!!!!!!!!");
            return true;
        }
        if (whiteKing.isCheckMate(board, whitePlayer) || blackKing.isCheckMate(board, blackPlayer)){
            System.out.println("MAT!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return true;
        }
        return false;
    }

    private void getInput() {
        while (true) {
            Scanner playerInput = new Scanner(System.in);
            System.out.println("Enter move (eg. 'c1-f4'): ");
            String input = (playerInput.nextLine());
            if (convertInput(input)) break;
        }
    }

    private boolean convertInput(String input) {
        if (!input.matches("[a-h][1-8]-[a-h][1-8]")) return false;
        setFrom(board.gameBoard[input.charAt(1) - '1'][input.charAt(0) - 'a']);
        setTo(board.gameBoard[input.charAt(4) - '1'][input.charAt(3) - 'a']);
        return true;
    }

    private void assignColors(){
        Random random = new Random();
        if(random.nextBoolean()){
            this.humanPlayer = new Player(Color.WHITE);
            this.computerPlayer = new Player(Color.BLACK);
        }
        else{
            this.humanPlayer = new Player(Color.BLACK);
            this.computerPlayer = new Player(Color.WHITE);
        }
    }

    public Cell getTo() {
        return to;
    }
    public Cell getFrom() {
        return from;
    }
    public void setFrom(Cell from) {
        this.from = from;
    }
    public void setTo(Cell to) {
        this.to = to;
    }

}
