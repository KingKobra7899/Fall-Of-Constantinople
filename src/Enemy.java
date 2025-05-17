import java.util.ArrayList;

public class Enemy extends Entity{
    public Enemy(){
        super(3, 3, 3, new int[2]);
    }

    public Move chooseMove(Board b){
        ArrayList<Move> moves = b.getEntityMoves(this);
        return(moves.get((int) (Math.random() * moves.size())));
    }
}
