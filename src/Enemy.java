//import java.util.ArrayList;

import java.util.ArrayList;

public class Enemy extends Entity{
    NeuralNetwork Brain;
    
    public Enemy(Board b){
        super(3, 3, 3, new int[2]);
        Brain = new NeuralNetwork(16 * 16, 128, 16, 3);
    }

    public Move chooseMove(Board b){
        ArrayList<Move> moves = b.getEntityMoves(this);
        Move choice = Brain.chooseMove(b.getNumeric(), moves);
        System.out.println(choice.toString());
        return(choice);
    }
}
