public class Player extends Entity {

    public Player(String type, int difficulty){
        double diff_penalty = ((difficulty-1) / 10.0);
        double speed = 0;
        double range = 0;
        double strength = 0;

        int[] pos = new int[2];

        if(type.equals("Süvari")){
            speed = 4 - diff_penalty;
            range = 2 - diff_penalty;
            strength = 1 - diff_penalty;
        }else if(type.equals("Topçu")){
            speed = 1 - diff_penalty;
            range = 3 - diff_penalty;
            strength = 3 - diff_penalty;
        }else if(type.equals("Yeŋiçeri")){
            speed = 3 - diff_penalty;
            range = 2 - diff_penalty;
            strength = 2 - diff_penalty;
        }

        super(speed, range, strength, 5 - diff_penalty, pos);
    }
}
