//import java.util.ArrayList;

import java.util.ArrayList;

public class Enemy extends Entity{
    NeuralNetwork Brain;
    
    public Enemy(Board b, NeuralNetwork B){
        super(3, 3, 3, new int[2]);
        Brain = B;
        super.isPlayer = false;
    }

    public Move chooseMove(Board b){
        ArrayList<Move> moves = b.getEntityMoves(this);
        if (moves.size() == 0) {
            return new Move(this, 0, 0, false);
        }
        Move choice = Brain.chooseMove(b.getNumeric(), moves);
        return(choice);
    }
}
