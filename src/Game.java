import java.util.Locale;
import java.util.Scanner;

public class Game {
    Board board;
    Scanner scan;
    boolean gameOver;

    int difficulty;


    public Game(boolean isAuto) {
        if(!isAuto){
            Assets.printFile("/Users/K-Dog/IdeaProjects/Game/src/title.txt");
            scan = new Scanner(System.in).useLocale(Locale.US);
            System.out.println("Choose your difficulty");
            System.out.println("1. Kolay (Easy)");
            System.out.println("2. Orta (Medium)");
            System.out.println("3. Efsanevi (Hard)");

            boolean difficultyChosen = false;

            while(!difficultyChosen){
                difficulty = scan.nextInt();
                if (difficulty == 1) {
                    board = new Board(16, difficulty);
                    difficultyChosen = true;
                } else if (difficulty == 2) {
                    board = new Board(16, difficulty);
                    difficultyChosen = true;
                } else if (difficulty == 3) {
                    board = new Board(16, difficulty);
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
                    Assets.printFile("/Users/K-Dog/IdeaProjects/Game/src/infantry.txt");
                    System.out.println("____________________");
                    System.out.println("|Speed    |███     |");
                    System.out.println("|Range    |███     |");
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
                    Assets.printFile("/Users/K-Dog/IdeaProjects/Game/src/cavalry.txt");
                    System.out.println("____________________");
                    System.out.println("|Speed    |█████   |");
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
                    Assets.printFile("/Users/K-Dog/IdeaProjects/Game/src/cannon.txt");
                    System.out.println("____________________");
                    System.out.println("|Speed    |██      |");
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
            System.out.println("To make a move, type the action (M for move, S for shoot), then direction (EWSN), and then distance");
            System.out.println("Hit enter to confirm");
            scan.nextLine(); 
            board.printBoard();
            }else{
                double prob = Math.random();
                if (prob < 0.33) {
                    board = new Board(16, 1);
                } else if(prob > 0.66) {
                    board = new Board(16, 2);
                } else {
                    board = new Board(16, 3);
                }

                prob = Math.random();
                if (prob < 0.33) {
                    board.initPlayer("Yeŋiçeri");
                } else if(prob > 0.66) {
                    board.initPlayer("Süvari");
                } else {
                    board.initPlayer("Topçu");
                }
            }
    }

    public void Run(boolean isSim){
        while(true){
            Turn(isSim);
        }
    }

    public void Turn(boolean isAuto){
        if (isAuto) {
            
        } else {
            boolean correct = false;
            while (!correct) {
                String choice = scan.nextLine();
                for (Move m : board.getEntityMoves(board.getPlayer())) {
                    if (m.toString().equals(choice)) {
                        board.makeMove(m);
                        correct = true;
                        break;
                    }
                }
            }
            for (Enemy e : board.getEnemies()) {
                board.makeMove(e.chooseMove(board));
            }
            board.printBoard();
        }
    }
}