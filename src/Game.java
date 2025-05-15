import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Game {
    Board board;
    Scanner scan;
    boolean gameOver;

    int difficulty;


    public Game() {
        Assets.printTitle();
        scan = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("Choose your difficulty");
        System.out.println("1. Kolay (Easy)");
        System.out.println("2. Orta (Medium)");
        System.out.println("3. Efsanevi (Hard)");

        boolean difficultyChosen = false;

        while(!difficultyChosen){
            difficulty = scan.nextInt();
            if (difficulty == 1) {
                board = new Board(32, difficulty);
                difficultyChosen = true;
            } else if (difficulty == 2) {
                board = new Board(16, difficulty);
                difficultyChosen = true;
            } else if (difficulty == 3) {
                board = new Board(8, difficulty);
                difficultyChosen = true;
            } else{
                System.out.println("Please choose a valid difficulty");
            }
        }
        System.out.println("\n\nChoose your character");
        System.out.println("1. Süvari (Cavalry)");
        System.out.println("2. Topçu (Artillery)");
        System.out.println("3. Yeŋiçeri (Infantry)");
        scan.nextLine();

        boolean charSelected = false;
        while(!charSelected){
            int type = scan.nextInt();
            if(type==3){
                Assets.printInfrantry();
                System.out.println("____________________");
                System.out.println("|Speed    |███     |");
                System.out.println("|Range    |██      |");
                System.out.println("|Strength |██      |");
                System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
                System.out.println("Type 'c' to confirm");
                scan.nextLine();
                if(scan.nextLine().equals("c")){
                    charSelected = true;
                    board.initPlayer("Yeŋiçeri");
                }else{
                    System.out.println("\n\nChoose your character");
                    System.out.println("1. Süvari (Cavalry)");
                    System.out.println("2. Topçu (Artillery)");
                    System.out.println("3. Yeŋiçeri (Infantry)");
                }
            }else if(type == 1){
                Assets.printCavalry();
                System.out.println("____________________");
                System.out.println("|Speed    |████    |");
                System.out.println("|Range    |██      |");
                System.out.println("|Strength |█       |");
                System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
                System.out.println("Type 'c' to confirm");
                scan.nextLine();
                if(scan.nextLine().equals("c")){
                    charSelected = true;
                    board.initPlayer("Süvari");
                }else{
                    System.out.println("\n\nChoose your character");
                    System.out.println("1. Süvari (Cavalry)");
                    System.out.println("2. Topçu (Artillery)");
                    System.out.println("3. Yeŋiçeri (Infantry)");
                }
            }else if(type == 2){
                Assets.printCannon();
                System.out.println("____________________");
                System.out.println("|Speed    |█       |");
                System.out.println("|Range    |███     |");
                System.out.println("|Strength |███     |");
                System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
                System.out.println("Type 'c' to confirm");
                scan.nextLine();
                if(scan.nextLine().equals("c")){
                    board.initPlayer("Topçu");
                    charSelected = true;
                }else{
                    System.out.println("\n\nChoose your character");
                    System.out.println("1. Süvari (Cavalry)");
                    System.out.println("2. Topçu (Artillery)");
                    System.out.println("3. Yeŋiçeri (Infantry)");
                }
            }
        }
        board.printBoard();
    }

    public void Run(){
        while(!gameOver){
            Turn();
            board.printBoard();
        }
    }

    public void Turn(){
        System.out.println("\nShoot (1) or Move (2)?");
        boolean isShoot = scan.nextInt() == 1;
        ArrayList<Move> moves = board.getEntityMoves(board.getPlayer());
        if(!isShoot){
            boolean validMove = false;
            while(!validMove){
            System.out.println("You can move a maximum of " + (int) board.getPlayer().getSpeed() + " tiles");
            System.out.println("Would you like to move up/down (1) or side-to-side? (2)");
            int axis = scan.nextInt();
            System.out.println("How many tiles would you like to move?");
            int d = scan.nextInt();

            if(axis == 1){
                for(Move move: moves){
                    if(!move.isShoot){
                        if(move.dX == d){
                            validMove = true;
                            board.applyMove(move);
                            break;
                        }
                    }
                }
            }else{
                for(Move move: moves){
                    if(!move.isShoot){
                        if(move.dY == d){
                            validMove = true;
                            board.applyMove(move);
                            break;
                        }
                    }
                }
            }
            
        }
    }
    }
}