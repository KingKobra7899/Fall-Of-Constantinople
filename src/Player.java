import java.util.ArrayList;

public class Player extends Entity {
    String type;
    int difficulty;
    public Player(String type, int difficulty) {
        super(type, difficulty);
        super.isPlayer = true;

        this.type = type;
        this.difficulty = difficulty;
    }
    
    public Move chooseMove(Board b) {
        ArrayList<Move> moves = b.getEntityMoves(this);
        SimBoard sim = new SimBoard(b, b.getEnemyBrain());
        Move best = new Move(this, 0, 0, true);
        double bestScore = Double.NEGATIVE_INFINITY;
        
        for (Move m : moves) {
            sim.makeMove(new Move(sim.p, m.getDX(), m.getDY(), m.getIsShoot()));
            double score = sim.evaluate();
            if (score > bestScore) {
                bestScore = score;
                best = m;
            }
            sim = new SimBoard(b, b.getEnemyBrain());
        }
        
        return best;
    }

    @Override
    public Player copy() {
        return new Player(type, difficulty);
    }
}