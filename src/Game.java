import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Game {
    Board board;
    Scanner scan;
    boolean gameOver;

    int difficulty;


    public Game() {
        Assets.printFile("title.txt");
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
                Assets.printFile("infantry.txt");
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
                Assets.printFile("cavalry.txt");
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
                Assets.printFile("cannon.txt");;
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
        System.out.println("When making a move, do so on the format (Direction Distance) like (E4, N2, S1, or W4)");
        board.printBoard();
    }

    public int Run(boolean player){
        int score = 0;
        while(!gameOver){
            Turn(player);
            board.printBoard();
            if(board.getAtPos(board.getBoard().length, 0) == Board.Player){
                score = 100;
                if(player){

                }
                break;
            }
            if(board.getEntityMoves(board.getPlayer()).size() == 0){
                score = -100;
                if(player){

                }
                break;
            }
        }
        return 0;
    }

    public void playerTurn(){
        System.out.println("\nShoot (1) or Move (2)");
        ArrayList<Move> moves = board.getEntityMoves(board.getPlayer());
        int choice = scan.nextInt();
        
        if(choice == 2){
            System.out.println("Type your move");
            boolean validMove = false;
            scan.nextLine();
            String candidate = scan.nextLine();
            for(Move m: moves){
                    if(!m.getIsShoot() && m.toString().equals(candidate)){
                        board.applyMove(m, true);
                        validMove = true;
                        break;
                }
            }
            if(!validMove){
                System.out.println("Please select a valid move :)");
                playerTurn();
            }
        }
    }

    public void Turn(boolean isPlayer){
        if(isPlayer){
        System.out.println("\nShoot (1) or Move (2) or View Stats (3)?");
        ArrayList<Move> moves = board.getEntityMoves(board.getPlayer());
        int choice = scan.nextInt();
        if(choice == 2){
            System.out.println("Type your move");
            boolean validMove = false;
            scan.nextLine();
            String candidate = scan.nextLine();
            for(Move m: moves){
                if(!m.getIsShoot() && m.toString().equals(candidate)){
                    board.applyMove(m, true);
                    validMove = true;
                    break;
                }
            }
            if(!validMove){
                System.out.println("Please select a valid move :)");
                playerTurn();
            }
        }else if(choice == 1){
           
        }else if(choice == 3){
            board.getPlayer().print();
            playerTurn();
        }
        for(Enemy e: board.getEnemies()){
            if(board.getEntityMoves(e).size() > 0)
                board.applyMove(e.chooseMove(board), false);
        }
    }else{

    }
    }
}