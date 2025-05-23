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
        return dY;
    }

    public boolean getIsShoot(){
        return isShoot;
    }

    public boolean equals(Move m){
        return m.getIsShoot() == isShoot && m.getDX() == dX && m.getDY() == dY && entity.getId() == m.getEntity().getId();
    }

    public double[] Vector(){
        double[] vector = new double[3];
        vector[0] = isShoot ? 1 : 0;
        vector[1] = dX;
        vector[2] = dY;

        return vector;
    }

    public String toString(){
        String prefix = isShoot ? "S" : "M";

        if(dX == 0){
            if(dY > 0){
                return prefix + "S" + dY;
            }else{
                return prefix + "N" + Math.abs(dY);
            }
        }else{
            if(dX > 0){
                return prefix + "E" + dX;
            }else{
                return prefix + "W" + Math.abs(dX);
            }
        }
    }
}
