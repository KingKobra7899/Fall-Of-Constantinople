public class Thing {
    String type;
    int val;

    public Thing(String type, int val) {
        this.type = type;
        this.val = val;
    }
    
    public Thing copy() {
        return new Thing(type, val);
    }
}
