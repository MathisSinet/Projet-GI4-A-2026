package fr.flowly.geometry;

import javafx.geometry.Point2D;

public class VoronoiSite {
    private Point2D point;
    private int id;
    private String name;
    private int maxCapacity;
    private VoronoiCell cell;

    public VoronoiSite(int id, String name, double x, double y, int maxCapacity, VoronoiCell cell) {
        this.point = new Point2D(x, y);
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.cell = cell;
    }

    public double getX() {
        return point.getX();
    }

    public double getY() {
        return point.getY();
    }

    public Point2D getPoint() {
        return point;
    }

    public VoronoiCell getCell() {
        return cell;
    }

    public void setX(double x) {
        point = new Point2D(x, getY());
    }

    public void setY(double y) {
        point = new Point2D(getX(), y);
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