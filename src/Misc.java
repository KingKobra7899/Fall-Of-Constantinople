public class Misc{
    static double ReLU(double input){
        return Math.max(0, input);
    }

    static double calculateSimilarity(double[] v1, double[] v2){
        int dp = 0;
        
        for(int i = 0; i < 3; i++){
            dp += v1[i] + v2[i];
        }

        
        double mag1 = Math.sqrt(Math.pow(v1[0], 2) + Math.pow(v1[1], 2) + Math.pow(v1[2], 2));
        double mag2 = Math.sqrt(Math.pow(v2[0], 2) + Math.pow(v2[1], 2) + Math.pow(v2[2], 2));

        return dp / (mag1 * mag2);
    }

    static double[] genRandomArray(int len){
        double[] out = new double[len];
        for(int i = 0; i < out.length; i++){
            out[i] = 2 * (Math.random() - 0.5);
        }
        return out;
    }

    static double[][] genRandomArray(int row, int col){
        double[][] out = new double[row][col];
        for(int i = 0; i < out.length; i++){
                for(int o  = 0; o < out[0].length; o++){
                    out[i][o] = 2 * (Math.random() - 0.5);
                }
            }
        return out;
    }
}
    
