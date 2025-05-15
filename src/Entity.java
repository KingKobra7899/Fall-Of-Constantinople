public class Entity {
    private double speed;
    private double range;
    private double strength;
    private int[] position;
    private double health;
    static int numEntities = 0;
    private int id;

    public Entity(double speed, double range, double strength, double health, int[] position) {
        this.speed = speed;
        this.range = range;
        this.strength = strength;
        this.position = position;
        this.health = health;
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

    public Move createMove(boolean isShoot, int dX, int dY){
        return new Move(this, dX, dY, isShoot);
    }
}
