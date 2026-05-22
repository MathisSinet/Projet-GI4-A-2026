package fr.flowly.geometry;

public class VoronoiSite extends Point {
    private int id;
    private String name;
    private int maxCapacity;
    private VoronoiCell cell;

    public VoronoiSite(double x, double y) {
        super(x, y);
    }

    public VoronoiSite(int id, String name, double x, double y, int maxCapacity, VoronoiCell cell) {
        super(x, y);
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.cell = cell;
    }

    public VoronoiCell getCell() {
        return cell;
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