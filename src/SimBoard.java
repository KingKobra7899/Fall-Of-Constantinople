
public class SimBoard extends Board {
    public SimBoard(Board b, NeuralNetwork B) {
        super(16, 3, B);
        Thing[][] copiedThings = deepCopy(b.getThings());
        super.p = (Player) b.getPlayer().copy();

        int[] pos = b.p.getPosition();
        copiedThings[pos[0]][pos[1]] = super.p;
        super.p.setPosition(pos[0], pos[1]);
        setData(copiedThings);
    }

    private Thing[][] deepCopy(Thing[][] original) {
        Thing[][] copy = new Thing[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = new Thing[original[i].length];
            for (int j = 0; j < original[i].length; j++) {
                if (original[i][j] != null) {
                    copy[i][j] = (Thing) original[i][j].copy();
                }
            }
        }
        return copy;
    }
}

