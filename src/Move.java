public class Move {
    Entity entity;
    boolean isShoot;
    int dX, dY;

    public Move(Entity e, int dX, int dY, boolean isShoot){
        entity = e;
        this.dX = dX;
        this.dY = dY;
        this.isShoot = isShoot;
    }

    public Entity getEntity(){
        return entity;
    }

    public int getDX(){
        return dX;
    }

    public int getDY(){
        return dX;
    }

    public boolean getIsShoot(){
        return isShoot;
    }

    public boolean equals(Move m){
        return m.getIsShoot() == isShoot && m.getDX() == dX && m.getDY() == dY && entity.getId() == m.getEntity().getId();
    }

    public String toString(){
        return "Dx: " + dX +", Dy: " + dY;
    }
}
