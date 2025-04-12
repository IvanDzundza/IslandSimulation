package island;

public abstract class Animal {
    protected double weight;
    protected int maxCountPerCell;
    protected int speed;
    protected double foodNeeded;
    protected boolean isAlive = true;
    protected String icon;

    public abstract void eat(Location location);
    public abstract void move(Island island, int x, int y);
    public abstract void reproduce(Location location);

    public boolean isAlive() {
        return isAlive;
    }

    public String getIcon() {
        return icon;
    }
}