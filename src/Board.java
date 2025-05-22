import java.util.ArrayList;

public class Board {
    Thing[][] things;
    int difficulty;
    Player p;

    public Board(int size, int difficulty) {
        things = new Thing[size][size];
        this.difficulty = difficulty;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                things[x][y] = new Thing("empty", 0);
                if (difficulty == 1) {
                    if (Math.random() < 0.04) {
                        things[x][y] = new Thing("upgrade", getRandomUpgrade());
                    } else if (Math.random() < 0.02) {
                        things[x][y] = new Enemy(this);
                    }
                } else if (difficulty == 2) {
                    if (Math.random() < 0.02) {
                        things[x][y] = new Thing("upgrade", getRandomUpgrade());
                    } else if (Math.random() < 0.04) {
                        things[x][y] = new Enemy(this);
                    }
                } else if (difficulty == 3) {
                    if (Math.random() < 0.01) {
                        things[x][y] = new Thing("upgrade", getRandomUpgrade());
                    } else if (Math.random() < 0.08) {
                        things[x][y] = new Enemy(this);
                    }
                }
            }
        }

    }
    

    public void initPlayer(String type){
        things[0][things.length - 1] = new Player(type, difficulty);
        p = (Player) things[0][things.length - 1];
        p.setPosition(0, things.length - 1);
    }

    public boolean moveE(int dX, int dY, Entity e){
        int[] pos = e.getPosition();
        int x = pos[0] + dX;
        int y = pos[1] + dY;

        if(x < 0 || x >= things.length||y < 0 || y >= things.length){
            return false;
        }
        things[x][y] = things[pos[0]][pos[1]];
        things[pos[0]][pos[1]] = new Thing("empty", 0);
        e.setPosition(x, y);
        return true;
    }

    public void applyMove(Move m){
        for(int x = 0; x < things.length; x++){
            for(int y = 0; y < things.length; y++){
                if(things[x][y].type == "enemy" || things[x][y].type == "player"){
                    if(!m.isShoot){
                        Entity e = (Entity) things[x][y];
                        if(m.getEntity().getId() == e.getId()){
                            moveE(m.getDX(), m.getDY(), e);
                        }
                    }else{
                        //TODO Implement shooting
                    }
                }
            }  
        }
    }

    public void printBoard(){
        for(int y = 0; y < things.length; y++){
            System.out.print("|");
            for(int x = 0; x < things.length; x++){
                if(things[x][y].type.equals("empty")){
                    System.out.print("_|");
                }else if(things[x][y].type.equals("upgrade")){
                    System.out.print("U|");
                }else if(things[x][y].type.equals("enemy")){
                    System.out.print("E|");
                }else if(things[x][y].type.equals("player")){
                    System.out.print("@|");
                }
            }
            System.out.println("");
        }
    }

    public int getRandomUpgrade() {
        double prob = Math.random();
        if (prob >= 0.7) {
            prob = Math.random();
            if (prob < 0.333) {
                return 0b100;
            } else if (prob < 0.66) {
                return 0b010;
            } else {
                return 0b001;
            }
        } else if (prob > 0.1) {
            prob = Math.random();
            if (prob < 0.333) {
                return 0b110;
            } else if (prob < 0.66) {
                return 0b011;
            } else {
                return 0b101;
            }
        } else {
            return 0b111;
        }
    }

    public Player getPlayer() {
        return p;
    }

    public ArrayList<Move> getEntityMoves(Entity e) {
        ArrayList<Move> moves = new ArrayList<Move>();
        int x = e.getPosition()[0];
        int y = e.getPosition()[1];
        for (int dX = 1; dX < e.getSpeed(); dX++) {
            if (x + dX >= things.length || !things[x + dX][y].type.equals("empty"))
                break;
            moves.add(new Move(e, dX, 0, false));
        }
        for (int dX = 1; dX < e.getSpeed(); dX++) {
            if (x - dX < 0 || !things[x - dX][y].type.equals("empty"))
                break;
            moves.add(new Move(e, -dX, 0, false));
        }
        for (int dY = 1; dY < e.getSpeed(); dY++) {
            if (y + dY >= things.length || !things[x][y + dY].type.equals("empty"))
                break;
            moves.add(new Move(e, 0, dY, false));
        }
        for (int dY = 1; dY < e.getSpeed(); dY++) {
            if (y - dY < 0 || !things[x][y - dY].type.equals("empty"))
                break;
            moves.add(new Move(e, 0, -dY, false));
        }
        for (int dX = 1; dX < e.getRange(); dX++) {
            if (x + dX >= things.length || !things[x + dX][y].type.equals("empty"))
                break;
            //moves.add(new Move(e, dX, 0, true));
        }
        for (int dX = 1; dX < e.getRange(); dX++) {
            if (x - dX < 0 || !things[x - dX][y].type.equals("empty"))
                break;
            //moves.add(new Move(e, -dX, 0, true));
        }
        for (int dY = 1; dY < e.getRange(); dY++) {
            if (y + dY >= things.length || !things[x][y + dY].type.equals("empty"))
                break;
            //moves.add(new Move(e, 0, dY, true));
        }
        for (int dY = 1; dY < e.getRange(); dY++) {
            if (y - dY < 0 || !things[x][y - dY].type.equals("empty"))
                break;
            //moves.add(new Move(e, 0, -dY, true));
        }
        return moves;
    }
    
    public void makeMove(Move m) {
        if (m.getIsShoot()) {
            //shooting
        } else {
            moveE(m.getDX(), m.getDY(), m.getEntity());
        }
    }
    
    public ArrayList<Enemy> getEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        for (int x = 0; x < things.length; x++) {
            for (int y = 0; y < things.length; y++) {
                if (things[x][y].type.equals("enemy")) {
                    enemies.add((Enemy) things[x][y]);
                }
            }
        }
        return enemies;
    }

    public int[] getNumeric() {
        int[] out = new int[things.length * things.length];
        for (int x = 0; x < things.length; x++) {
            for (int y = 0; y < things.length; y++) {
                String type = things[x][y].type;
                if (type.equals("enemy")) {
                    out[x + y * 16] = 1;
                } else if (type.equals("player")) {
                    out[x + y * 16] = 2;
                } else if (type.equals("upgrade")) {
                    out[x + y * 16] = 3;
                } else if (type.equals("wall")) {
                    out[x + y * 16] = 4;
                } else {
                    out[x + y * 16] = 0;
                }
            }
        }
        return out;
    }

}
