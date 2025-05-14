import java.lang.Math;



public class Board {
    private int[][] board;
    private Player p;
    private int difficulty;
    

    static int Player = 0b100;
    static int Enemy = 0b110;
    static int Wall = 0b101; // last three digits correspond to strergth of wall (001 is 1) (010 is 2) (011 is 3), etc
    static int Upgrade = 0b111; // last three correspond to what the upgrade upgrades (100  for speed) (010 for range), (001 for strength) any combination

    public Board(int size, int difficulty) {
        board = new int[size][size];
        this.difficulty = difficulty;
        
        for(int x = 1; x < size; x++){
            for(int y = 1; y < size; y++){
                if(Math.random() < 0.1){
                    board[x][y] = randomUpgrade();
                }
            }
        }
    }

    public static String booleanToString(boolean val){
        if(val){
            return "1";
        }else{
            return "0";
        }
    }

    public void initPlayer(String type){
        p = new Player(type, difficulty);
        board[0][board.length - 1] = Board.Player;
        p.setPosition(0, board.length - 1);
    }

    public int getAtPos(int x, int y){
        int val = board[x][y];
        int bits = 0;
        if(val>>3 == 0){
            bits = val;
        }else {
            bits = val >> 3;
        }
        if(bits == Board.Player){
            return Board.Player;
        } if(bits == Board.Enemy){
            return Board.Enemy;
        } if(bits == Board.Wall){
            return Board.Wall;
        } if(bits == Board.Upgrade){
            return Board.Upgrade;
        }

        return 0;
    }

    public int randomUpgrade(){
        boolean upgradeSpeed = false;
        boolean upgradeStrength = false;
        boolean upgradeRange = false;
        int upgrade = 0;

        while(upgrade > 0){
            if(Math.random() < 0.50){
                upgradeSpeed = true;
            }

            if(upgradeSpeed){
                if(Math.random() < 0.11){
                    upgradeStrength = true;
                }
            }else if(Math.random() < 0.33){
                upgradeStrength = true;
            }

        if(upgradeSpeed || upgradeStrength){
                if(upgradeSpeed || upgradeStrength && Math.random() < 0.11/3.0){
                    upgradeRange = true;
                }else{
                    if(Math.random() < 0.11){
                        upgradeRange = true;
                    }    
                }
            }else if(Math.random() < 0.33){
                upgradeRange = true;
            }

            upgrade = Integer.parseInt(booleanToString(upgradeSpeed) + booleanToString(upgradeStrength) + booleanToString(upgradeRange), 2);
        }

        upgrade = (Board.Upgrade << 3) | upgrade;
        //System.out.println(Integer.toBinaryString(upgrade));
        return upgrade;
    }

    public void printBoard(){
        for(int y = 0; y < board[0].length; y++){
            for(int x = 0; x < board.length; x++){
                int square = getAtPos(x, y);
                if(square == 0){
                    System.out.print("|¯");
                }else{
                    if(square == Board.Player){
                        System.out.print("|P");
                    }else if(square == Board.Upgrade){
                        //System.out.println(Integer.toBinaryString(square));
                        System.out.print("|U");
                    }else if(square == Board.Enemy){
                        System.out.print("|E");
                    }else{
                        System.out.print("|X");
                    }
                }
            }
            System.out.println("|");
        }

        for(int i = 0; i < board.length; i++){
            System.out.print(" ¯");
        }
    }
}
