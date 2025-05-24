public class Entity extends Thing{
    private double speed;
    private double range;
    private double strength;
    private int[] position;
    static int numEntities = 0;
    boolean isPlayer;
    private int id;

    public Entity(double speed, double range, double strength, int[] position) {
        super("enemy", 0);
        this.speed = speed;
        this.range = range;
        this.strength = strength;
        this.position = position;
        id = numEntities;
        numEntities++;
    }

    public Entity(String type, int difficulty){ //constructor for player
        super("player", 0);
        double diff_penalty = ((difficulty-1) / 10.0);
        speed = 0;
        range = 0;
        strength = 0;

        position = new int[2];

        if(type.equals("Süvari")){
            speed = 5 - diff_penalty;
            range = 2 - diff_penalty;
            strength = 1 - diff_penalty;
        }else if(type.equals("Topçu")){
            speed = 2 - diff_penalty;
            range = 3 - diff_penalty;
            strength = 3 - diff_penalty;
        }else if(type.equals("Yeŋiçeri")){
            speed = 3 - diff_penalty;
            range = 3 - diff_penalty;
            strength = 2 - diff_penalty;
        }
        id = numEntities;
        numEntities++;
    }

    public void setPosition(int x, int y){
        position[0] = x;
        position[1] = y;
    }

    public int[] getPosition(){
        return this.position;
    }

    public int getId(){
        return id;
    }

    public double getSpeed(){
        return this.speed;
    }

    public double getRange(){
        return this.range;
    }

    public int getStrength(){
        return (int) (strength + 0.5);
    }

    public Move createMove(boolean isShoot, int dX, int dY){
        return new Move(this, dX, dY, isShoot);
    }

    public void print(){
        int maxScore = (int) Math.round(Math.max(speed, range));
        maxScore = (int) Math.round(Math.max(maxScore, strength));
        for(int i = 0; i < 12 + maxScore; i++){
            System.out.print("_");
        }
        System.out.println("");
        for(int i = 0; i < 3; i++){
            if(i == 0){
                System.out.print("|Speed    |");
            }else if(i == 1){
                System.out.print("|Range    |");
            }else{
                System.out.print("|Strength |");
            }
            for(int n = 0; n <= maxScore; n++){
                if(i == 0){
                    if( n <= speed){
                        System.out.print("█");
                    }else if(n == maxScore){
                        System.out.print("|");
                    }else{
                        System.out.print(" ");
                    }
                }else if(i == 1){
                    if( n <= range){
                        System.out.print("█");
                    }else if(n == maxScore){
                        System.out.print("|");
                    }else{
                        System.out.print(" ");
                    }
                }else{
                    if( n <= strength){
                        System.out.print("█");
                    }else if(n == maxScore){
                        System.out.print("|");
                    }else{
                        System.out.print(" ");
                    }
                }
            }
            System.out.println("");
        }
        for(int i = 0; i < 12 + maxScore; i++){
            System.out.print("¯");
        }
    }

    public void absorbUpgrade(int upgrade) {

        print();
        if (upgrade % 2 == 0) {
            speed++;
        }
        upgrade /= 2;
        if (upgrade % 2 == 0) {
            range++;
        }
        upgrade /= 2;
        if (upgrade % 2 == 0) {
            strength++;
        }
        print();
    }
    
    @Override
    public Entity copy() {
        return new Entity(speed, range, strength, position);
    }
}
