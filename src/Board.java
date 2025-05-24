import java.util.ArrayList;


public class Board {
    Thing[][] things;
    int difficulty;
    Player p;
    NeuralNetwork EnemyBrain;
    public Board(int size, int difficulty, NeuralNetwork B) {
        things = new Thing[size][size];
        this.difficulty = difficulty;
        EnemyBrain = B;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                things[x][y] = new Thing("empty", 0);
                if (difficulty == 1) {
                    if (Math.random() < 0.04) {
                        //things[x][y] = new Thing("upgrade", getRandomUpgrade());
                    } else if (Math.random() < 0.02) {
                        Enemy e = new Enemy(this, B);
                        e.setPosition(x, y);
                        things[x][y] = e;
                    }
                } else if (difficulty == 2) {
                    if (Math.random() < 0.02) {
                        //things[x][y] = new Thing("upgrade", getRandomUpgrade());
                    } else if (Math.random() < 0.04) {
                        Enemy e = new Enemy(this, B);
                        e.setPosition(x, y);
                        things[x][y] = e;
                    }
                } else if (difficulty == 3) {
                    if (Math.random() < 0.01) {
                        //things[x][y] = new Thing("upgrade", getRandomUpgrade());
                    } else if (Math.random() < 0.08) {
                        Enemy e = new Enemy(this, B);
                        e.setPosition(x, y);
                        things[x][y] = e;
                    }
                }
            }
        }

    }
    
    public NeuralNetwork getEnemyBrain() {
        return EnemyBrain;
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
        if (things[x][y].type.equals("upgrade")) {
            e.absorbUpgrade(things[x][y].val);
        }
        if(x < 0 || x >= things.length||y < 0 || y >= things.length){
            return false;
        }
        things[x][y] = things[pos[0]][pos[1]];
        things[pos[0]][pos[1]] = new Thing("empty", 0);
        e.setPosition(x, y);
        return true;
    }

    public void printBoard(){
        for(int y = 0; y < things.length; y++){
            System.out.print("|");
            for(int x = 0; x < things.length; x++){
                if (x == 15 && y == 0) {
                    System.out.print("░|");
                } else if(things[x][y].type.equals("empty")){
                    System.out.print("_|");
                }else if(things[x][y].type.equals("upgrade")){
                    System.out.print("U|");
                }else if(things[x][y].type.equals("enemy")){
                    System.out.print("E|");
                }else if(things[x][y].type.equals("player")){
                    System.out.print("@|");
                } else if (things[x][y].type.equals("wall")) {
                    System.out.print("█|");
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
            if (x + dX >= things.length || !(things[x + dX][y].type.equals("empty") || things[x + dX][y].equals("upgrade")))
                break;
            moves.add(new Move(e, dX, 0, false));
        }
        for (int dX = 1; dX < e.getSpeed(); dX++) {
            if (x - dX < 0 || !(things[x - dX][y].type.equals("empty")|| things[x - dX][y].equals("upgrade")))
                break;
            moves.add(new Move(e, -dX, 0, false));
        }
        for (int dY = 1; dY < e.getSpeed(); dY++) {
            if (y + dY >= things.length || !(things[x][y + dY].type.equals("empty")|| things[x][y + dY].equals("upgrade")))
                break;
            moves.add(new Move(e, 0, dY, false));
        }
        for (int dY = 1; dY < e.getSpeed(); dY++) {
            if (y - dY < 0 || !(things[x][y - dY].type.equals("empty")|| things[x][y - dY].equals("upgrade")))
                break;
            moves.add(new Move(e, 0, -dY, false));
        }
        for (int dX = 1; dX < e.getRange(); dX++) {
            if (x + dX >= things.length)
                break;
            moves.add(new Move(e, dX, 0, true));
        }
        for (int dX = 1; dX < e.getRange(); dX++) {
            if (x - dX < 0)
                break;
            moves.add(new Move(e, -dX, 0, true));
        }
        for (int dY = 1; dY < e.getRange(); dY++) {
            if (y + dY >= things.length)
                break;
            moves.add(new Move(e, 0, dY, true));
        }
        for (int dY = 1; dY < e.getRange(); dY++) {
            if (y - dY < 0)
                break;
            moves.add(new Move(e, 0, -dY, true));
        }
        return moves;
    }
    
    public void makeMove(Move m) {
        if (m.getIsShoot()) {
            if (m.getEntity().isPlayer) {
                int[] pos = m.getEntity().getPosition();
                if (things[pos[0] + m.getDX()][pos[1] + m.getDY()].type.equals("wall")) {
                    things[pos[0] + m.getDX()][pos[1] + m.getDY()].type = "empty";
                }
            } else {
                int dX = m.getDX();
                int[] pos = m.getEntity().getPosition();
                if (Math.abs(dX) > 0) {
                    if (dX > 0) {
                        for (int x = 0; x < dX; x++) {
                            things[pos[0] + x][pos[1]] = new Thing("wall", m.getEntity().getStrength());
                        }
                    } else {
                        for (int x = dX; x >= 0; x--) {
                            things[pos[0] + x][pos[1]] = new Thing("wall", m.getEntity().getStrength());
                        }
                    }
                } else {
                    int dY = m.getDY();
                    if (dY > 0) {
                        for (int y = 0; y < dY; y++) {
                            things[pos[0]][pos[1] + y] = new Thing("wall", m.getEntity().getStrength());
                        }
                    } else {
                        for (int y = dY; y >= 0; y--) {
                            things[pos[0]][pos[1] + y] = new Thing("wall", m.getEntity().getStrength());
                        }
                    }
                }
            }
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

    public double evaluate() {
        double score = 0;
        score += getEntityMoves(p).size() / 15;
        score -= Math.sqrt(Math.pow(p.getPosition()[0] - 15, 2) + Math.pow(p.getPosition()[1], 2)) / 22.627416998;
        return score;
    }

    public Thing[][] getThings() {
        return things;
    }

    public void setData(Thing[][] t) {
        things = t;
    }

}
