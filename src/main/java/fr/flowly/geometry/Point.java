package fr.flowly.geometry;

import javafx.geometry.Point2D;

public class Point {
    private Point2D inner;

    public double getX() {
        return inner.getX();
    }

    public double getY() {
        return inner.getY();
    }

    public void setX(double x) {
        inner = new Point2D(x, getY());
    }

    public void setY(double y) {
        inner = new Point2D(getX(), y);
    }

    public Point add(Point p2) {
        return new Point(inner.add(p2.inner));
    }

    public Point subtract(Point p2) {
        return new Point(inner.subtract(p2.inner));
    }

    public Point multiply(double factor) {
        return new Point(inner.multiply(factor));
    }

    public Point midpoint(Point p2) {
        return new Point(inner.midpoint(p2.inner));
    }

    public double dotProduct(Point p2) {
        return inner.dotProduct(p2.inner);
    }

    public double angle(Point p2, Point p3) {
        return inner.angle(p2.inner, p3.inner);
    }

    public double distance(Point p2) {
        return inner.distance(p2.inner);
    }

    private Point(Point2D point) {
        inner = point;
    }

    public Point(double x, double y) {
        this(new Point2D(x, y));
    }

    @Override
    public String toString() {
        return inner.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Point point) {
            return inner.equals(point.inner);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return inner.hashCode();
    }
}
