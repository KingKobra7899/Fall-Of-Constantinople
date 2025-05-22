import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Assets {
    public static void printFile(String file){
        File f = new File(file);
        try {
            Scanner scan = new Scanner(f);
            while(scan.hasNextLine()){
                System.out.println(scan.nextLine());
            }
            scan.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}