import java.lang.Math;
import java.util.ArrayList;



public class Board {
    private int[][] board;
    private Player p;
    private int difficulty;
    private ArrayList<Enemy> enemies;

    static int Player = 0b100;
    static int Enemy = 0b110;
    static int Wall = 0b101; // last three digits correspond to strergth of wall (001 is 1) (010 is 2) (011 is 3), etc
    static int Upgrade = 0b111; // last three correspond to what the upgrade upgrades (100  for speed) (010 for range), (001 for strength) any combination
    
    public Player getPlayer(){return this.p;}

    public Board(int size, int difficulty) {
        board = new int[size][size];
        this.difficulty = difficulty;
        int maxEnemies = difficulty * 3;
        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                if(Math.random() < 0.1){
                    board[x][y] = randomUpgrade();
                }
            }
        }
        int numEnemies = 0;
        
        while(numEnemies <= maxEnemies){
            for(int x = 0; x < size; x++){
                for(int y = 0; y < size; y++){
                    if(Math.random() < 0.01){
                        board[x][y] = Board.Enemy;
                        numEnemies++;
                    }
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
            if(Math.random() < 0.33){
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

    public void applyMove(Move m){
        int id = m.getEntity().getId();
        if(id == p.getId()){
            if(m.getIsShoot()){
                /*
                 * TODO IMPLEMENT SHOOTING LOGIC
                 */
            }else{
                int[] currentPos = p.getPosition();
                board[currentPos[0]][currentPos[1]] = 0;
                currentPos[0] += m.getDX();
                currentPos[1] += m.getDY();
                board[currentPos[0]][currentPos[1]] = Board.Player;
                p.setPosition(currentPos[0], currentPos[1]);
            }
        }else{
            for(int i = 0; i < enemies.size(); i++){
                Enemy e = enemies.get(i);
                
                if(e.getId() == id){
                    if(m.getIsShoot()){
                        /*
                         * TODO IMPLEMENT SHOOTING LOGIC
                        */
                    }else{
                        int[] currentPos = p.getPosition();
                        board[currentPos[0]][currentPos[1]] = 0;
                        currentPos[0] += m.getDX();
                        currentPos[1] += m.getDY();
                        board[currentPos[0]][currentPos[1]] = Board.Player;
                        p.setPosition(currentPos[0], currentPos[1]);
                    }
                    break;
                }
            }
        }
    }

    public ArrayList<Move> getEntityMoves(Entity e){
        ArrayList<Move> moves = new ArrayList<Move>();
        int currentX = e.getPosition()[0];
        int currentY = e.getPosition()[1];
        
        for(int x = 0; x > (int) e.getSpeed(); x--){
            if(board[currentX + x][currentY] != 0){
                break;
            }
            moves.add(new Move(e, x, 0, false));
        }
        for(int x = 0; x < e.getSpeed(); x++){
            if(board[currentX + x][currentY] != 0){
                break;
            }
            moves.add(new Move(e, x, 0, false));
        }

        return moves;
    }
}