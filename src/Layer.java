import java.util.Random;

public class Layer {
    int inputLen, outputLen;
    double[] biases;
    double[][] weights;

    public Layer(int nodesIn, int nodesOut){
        inputLen = nodesIn;
        outputLen = nodesOut;

        weights = new double[inputLen][outputLen];
        biases = new double[outputLen];
    }

    void mutate(double degree){
        Random r = new Random();
        for(int i = 0; i < biases.length; i++){
            biases[i] += r.nextGaussian() * degree;
        }
        for(int i = 0; i < inputLen; i++){
            for(int o = 0; o < outputLen; o++){
                weights[i][o] += r.nextGaussian() * degree;
            }
        }

    }

    void setBiases(double[] biases){
        this.biases = biases;
    }
    void setWeights(double[][] weights){
        this.weights = weights;
    }

    public double[] output(double[] input){
        double[] output = new double[outputLen];
        
        for(int nodeOut = 0; nodeOut < outputLen; nodeOut++){
            double out = biases[nodeOut];
            
            for(int nodeIn = 0; nodeIn < inputLen; nodeIn++){
                out += input[nodeIn] * weights[nodeIn][nodeOut];
            }
            
            output[nodeOut] = Misc.ReLU(out);
        }
        return output;
    }

    public String toString(){
        String bias = "";
        for(int i = 0; i < biases.length; i++){
            bias += biases[i];
            if( i + 1 < biases.length)
                bias += ",";
        }
        bias += "\n";
        String weight = "weights: {\n";
        for(int i = 0; i < inputLen; i++){
            for(int o = 0; o < outputLen; o++){
                String key = i+","+o+"," +weights[i][o];
                weight += key + "\n";
            }
        }
        weight += "}";
        return bias + weight;
    }
}
