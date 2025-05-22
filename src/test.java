public class test {
    public static void main(String[] args) {
        NeuralNetwork nnet = new NeuralNetwork(4,4,4,3);
        String filename = "test.nnet";
        nnet.toFile(filename);
    }
}
