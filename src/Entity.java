public class Entity {
    private double speed;
    private double range;
    private double strength;
    private int[] position;
    private double health;
    public Entity(double speed, double range, double strength, double health, int[] position) {
        this.speed = speed;
        this.range = range;
        this.strength = strength;
        this.position = position;
        this.health = health;
    }
}
