import java.util.ArrayList;

public class Enemy extends Entity{
    NeuralNetwork Brain;
    
    public Enemy(Board b){
        super(3, 3, 3, new int[2]);
        Brain = new NeuralNetwork(b.squishBoard().length, 16, 8, 3);
    }

    public Move chooseMove(Board b){
        ArrayList<Move> moves = b.getEntityMoves(this);
        Move choice = Brain.chooseMove(b.squishBoard(), moves);
        return(choice);
    }
}
