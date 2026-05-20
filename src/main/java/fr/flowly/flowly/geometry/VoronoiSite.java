package fr.flowly.flowly.geometry;

public class VoronoiSite extends Point {
    private int id;
    private String name;
    private int maxCapacity;

    public VoronoiSite(int id, String name, double x, double y, int maxCapacity) {
        super(x, y);
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}