import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Game g = new Game(true, new NeuralNetwork(16 * 16, 512, 256, 128, 64, 16, 3));
        System.out.println(Arrays.toString(g.Run(true)));
        
    }
}