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
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length) {
            return -1; 
        }
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

    public void applyMove(Move m, boolean isPlayer){
        int id = m.getEntity().getId();
        
        if(isPlayer){
            if(m.getIsShoot()){
                /*
                 * TODO IMPLEMENT SHOOTING LOGIC
                 */
            }else{
                int[] currentPos = p.getPosition();
                int x = currentPos[0];
                int y = currentPos[1];
                board[x][y] = 0;
                x += m.getDX();
                y += m.getDY();

                x = Math.min(board.length - 1, x);
                y = Math.min(board.length - 1, y);
                x = Math.max(0, y);
                x = Math.max(0, x);

                board[x][y] = Board.Player;
                p.setPosition(x, y);
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

    public ArrayList<Move> getEntityMoves(Entity e) {
        ArrayList<Move> moves = new ArrayList<>();
        int currentX = e.getPosition()[0];
        int currentY = e.getPosition()[1];
        int speed = (int) e.getSpeed();
    
        // LEFT
        
        for (int dx = -1; dx >= -speed; dx--) {
            int x = currentX + dx;
            int val = getAtPos(x, currentY);
            if (val == -1 || (val != 0 && val != Board.Upgrade)) break;
            moves.add(new Move(e, dx, 0, false));
            if (val == Board.Upgrade) break; // Optionally stop at upgrade
        }
    
        // RIGHT
        for (int dx = 1; dx <= speed; dx++) {
            int x = currentX + dx;
            int val = getAtPos(x, currentY);
            if (val == -1 || (val != 0 && val != Board.Upgrade)) break;
            moves.add(new Move(e, dx, 0, false));
            if (val == Board.Upgrade) break;
        }
    
        // UP
        for (int dy = -1; dy >= -speed; dy--) {
            int y = currentY + dy;
            int val = getAtPos(currentX, y);
            if (val == -1 || (val != 0 && val != Board.Upgrade)) break;
            moves.add(new Move(e, 0, dy, false));
            if (val == Board.Upgrade) break;
        }
    
        // DOWN
        for (int dy = 1; dy <= speed; dy++) {
            int y = currentY + dy;
            int val = getAtPos(currentX, y);
            if (val == -1 || (val != 0 && val != Board.Upgrade)) break;
            moves.add(new Move(e, 0, dy, false));
            if (val == Board.Upgrade) break;
        }
    
        return moves;
    }
    
}