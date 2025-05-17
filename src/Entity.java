public class Entity {
    private double speed;
    private double range;
    private double strength;
    private int[] position;
    static int numEntities = 0;
    private int id;

    public Entity(double speed, double range, double strength, int[] position) {
        this.speed = speed;
        this.range = range;
        this.strength = strength;
        this.position = position;
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

    public void absorbUpgrade(int upgrade){
        //takes in last three bits and applies upgrades accordingly

        if(upgrade % 2 == 0){
            speed++;
        }
        upgrade /= 2;
        if(upgrade % 2 == 0){
            range++;
        }
        upgrade /= 2;
        if(upgrade % 2 == 0){
            strength++;
        }
    }
}
