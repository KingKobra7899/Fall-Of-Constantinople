import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class NeuralNetwork {
    ArrayList<Layer> layers = new ArrayList<Layer>();
    int[] arch;
    public NeuralNetwork(int... layerSizes){
        arch = layerSizes;
        for(int i = 0; i < layerSizes.length - 1; i++){
            Layer l = new Layer(layerSizes[i], layerSizes[i + 1]);
            l.setBiases(Misc.genRandomArray(layerSizes[i + 1]));
            l.setWeights(Misc.genRandomArray(layerSizes[i], layerSizes[i + 1]));
            layers.add(l);
        }
    }
    
    public double[] calculateOutput(double[] input){
        for(Layer layer : layers){
            input = layer.output(input);
        }

        return input;
    }

    public Move chooseMove(int[] board, ArrayList<Move> legalMoves){
        double[] choice = calculateOutput(Arrays.stream(board).asDoubleStream().toArray());
        double maxSim = -1;
        int indexMax = -1;
        
        for(int i = 0; i < legalMoves.size(); i++){
            double[] vec = legalMoves.get(i).Vector();
            double sim = Misc.calculateSimilarity(vec, choice);
            if(sim > maxSim){
                maxSim = sim;
                indexMax = i;
            }
        }
        
        return legalMoves.get(indexMax);
    }

    public ArrayList<Layer> getLayers(){
        return layers;
    }

    public void setLayers(ArrayList<Layer> l){
        layers = l;
    }

    public boolean toFile(String filename){
        File output = new File(filename);
        FileWriter fWriter;
        try {
            fWriter = new FileWriter(output);
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        
        String txt = "";
        for(int i = 0; i < arch.length; i++){
            txt += arch[i];
            if(i + 1 < arch.length){
                txt += ",";
            }
        }
        txt += "\n";
        for(int i = 0; i < layers.size(); i++){
          txt += "layer"+(i+1)+"{\n";
          txt += layers.get(i).toString();
          txt +="}\n";
        }
        try {
            fWriter.write(txt);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            fWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static NeuralNetwork fromFile(String filename){
        try {
            File f = new File(filename);
            Scanner myReader = new Scanner(f);
            String[] arr = myReader.nextLine().split(",");
            int[] arch = new int[arr.length];

            for(int i = 0; i < arr.length; i++){
                arch[i] = Integer.parseInt(arr[i]);
            }

            NeuralNetwork nn = new NeuralNetwork(arch);
            ArrayList<Layer> layers = nn.getLayers();
            int layer = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.startsWith("layer")){
                    String[] bias = myReader.nextLine().split(",");
                    double[] biasArr = new double[bias.length];
                    
                    for(int i = 0; i < bias.length; i++){
                        biasArr[i] = Double.parseDouble(bias[i]);
                    }
                    layers.get(layer).setBiases(biasArr);
                }else if(data.startsWith("weights")){
                    double[][] weights = new double[arch[layer]][arch[layer + 1]];
                    for(int n = 0; n < arch[layer] * arch[layer + 1]; n++){
                        String[] line = myReader.nextLine().split(",");
                        int idx1 = Integer.parseInt(line[0]);
                        int idx2 = Integer.parseInt(line[1]);

                        weights[idx1][idx2] = Double.parseDouble(line[2]);
                        
                    }
                    layers.get(layer).setWeights(weights);
                    layer++;
                }else{
                    if(myReader.hasNextLine())
                        myReader.nextLine();
                }
            }
            myReader.close();
            nn.setLayers(layers);
            return(nn);
        }catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }
}
