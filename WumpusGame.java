
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;

public class WumpusGame {

    private int boardSize;
    private int wumpus;
    private Space[][] board;
    private Random random = new Random();
    private HashMap probabilityGeneration;
    private int[] prob;
    private byte[][] perceptBoard;
    private final byte BREEZE = 0b00000001;
    private final byte STENTCH = 0b0000010;
    private final byte BUMP = 0b00000100;
    private final byte GLITTER = 0b00001000;
    private final byte WUMPUS = 0b00010000;
    private final byte PIT = 0b00100000;
    private final byte SCREAM = 0b01000000;
    private PrintWriter out = new PrintWriter(new File("PerceptBoard.txt"));

    public WumpusGame(int boardSize, int[] prob) throws FileNotFoundException{
        this.boardSize = boardSize;
        this.prob = prob;
        perceptBoard = new byte[boardSize][boardSize];
        board = new Space[boardSize][boardSize];
        setBoard();
        initializeBoard();
        PrintStream out = new PrintStream(new FileOutputStream("world.txt"));
        System.setOut(out);
        printBoards();
    }

    public byte act(Action action) {
        //public method for explorer to call when it makes a move

        return new byte[]; //returns the percepts of the explorer's location after the action is performed
    }

    private void setBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Space();
            }
        }
    }

    private void initializeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = board[i].length - 1; j >= 0; j--) {
                chooseState(i, j);
            }
        }
    }
    
    private void chooseState(int x, int y){

        if((random.nextInt(100) + 1) <= prob[0]){
            placePit(x, y);
        }
        else if((random.nextInt(100) + 1) <= prob[1]){
            placeObstacle(x, y);
        }
        else if((random.nextInt(100) + 1) <= prob[2]){
            placeWumpus(x, y);
        }
    }
    
    private void placePercept(int x, int y, byte percept){
        perceptBoard[x][y] |= percept;
    }
    
    private void placeAdjacentPercept(int x, int y, byte percept){
        if(x > 0){
            placePercept(x-1, y, percept);
        }
        if(x < boardSize - 1){
            placePercept(x+1, y, percept);
        }
        if(y > 0){
            placePercept(x, y-1, percept);
        }
        if(y < boardSize - 1){
            placePercept(x, y+1, percept);
        }
    }

    private void placeObstacle(int x, int y) {
        board[x][y].setHasObstacle(true);
        placePercept(x,y,BUMP);
    }

    private void placePit(int x, int y) {
        board[x][y].setHasHole(true);
        placeAdjacentPercept(x, y, BREEZE);
        placePercept(x, y, PIT);
    }

    private void placeGold(int x, int y) {
        board[x][y].setHasGold(true);
        placePercept(x, y, GLITTER);
    }

    private void placeWumpus(int x, int y) {
        board[x][y].toggleWumpus();
        placeAdjacentPercept(x, y, STENTCH);
        placePercept(x,y,WUMPUS);
        wumpus++;
    }

    private void checkBlockedStart() {

    }

    private void printBoards(){
        printBoard();
        printPerceptBoard();
    }
    private void printPerceptBoard(){
        out.println(boardSize);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                out.print(perceptBoard[i][j] +" ");
            }
            out.println();
        }
        out.close();
    }
    private void printBoard() {
        System.out.println(boardSize);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].isHasWumpus()) {
                    System.out.print("W ");
                } else if(board[i][j].isHasHole()) {
                    System.out.print("H ");
                }
                else if(board[i][j].isHasObstacle()){
                    System.out.print("I ");
                }
                else{
                    System.out.print("0 ");
                }
            }
            System.out.println("");
        }
    }
}
