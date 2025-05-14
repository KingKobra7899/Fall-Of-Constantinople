import java.util.Scanner;
public class Game {
    Board board;
    Scanner scanner = new Scanner(System.in);
    int difficulty;


    public Game() {
        Assets.printTitle();

        System.out.println("Choose your difficulty");
        System.out.println("1. Tavuk (Easy)");
        System.out.println("2. Delikanlı (Medium)");
        System.out.println("3. Sezar (Hard)");

        boolean difficultyChosen = false;

        while(!difficultyChosen) {
            difficulty = scanner.nextInt();
            if (difficulty == 1) {
                board = new Board(32);
                difficultyChosen = true;
            } else if (difficulty == 2) {
                board = new Board(16);
                difficultyChosen = true;
            } else if (difficulty == 3) {
                board = new Board(8);
                difficultyChosen = true;
            } else if (!difficultyChosen) {
                System.out.println("Please choose a valid difficulty");
            }
        }
    }
}
