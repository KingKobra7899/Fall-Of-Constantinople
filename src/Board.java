public class Board {
    private int[][] board;
    private Player p;
    private int difficulty;
    
    static int Player = 1;
    static int Enemy = 2;


    public Board(int size, int difficulty) {
        board = new int[size][size];
        this.difficulty = difficulty;
    }

    public void initPlayer(String type, int difficulty){
        p = new Player(type, difficulty);
        board[0][board.length] = Board.Player;
    }
}
