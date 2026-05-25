package fr.flowly.geometry;

import javafx.geometry.Point2D;

/**
 * Holds the representation of a 2D point or vector
 * 
 * This class is a wrapper around the {@link javafx.geometry.Point2D} class
 */
public class Point {
    /**
     * Inner Point2D javafx object
     */
    private Point2D inner;

    /**
     * @return the x coordinate of the point
     */
    public double getX() {
        return inner.getX();
    }

    /**
     * @return the y coordinate of the point
     */
    public double getY() {
        return inner.getY();
    }

    /**
     * Sets the x coordinate of the point
     * @param x new x coordinate
     */
    public void setX(double x) {
        inner = new Point2D(x, getY());
    }

    /**
     * Sets the y coordinate of the point
     * @param y new y coordinate
     */
    public void setY(double y) {
        inner = new Point2D(getX(), y);
    }

    /**
     * Returns a point with the coordinates of the specified point added to the
     * coordinates of this point.
     * @param p2 the point whose coordinates are to be added
     * @return the point with added coordinates
     */
    public Point add(Point p2) {
        return new Point(inner.add(p2.inner));
    }

    /**
     * Returns a point with the coordinates of the specified point subtracted
     * from the coordinates of this point.
     * @param p2 the point whose coordinates are to be subtracted
     * @return the point with subtracted coordinates
     */
    public Point subtract(Point p2) {
        return new Point(inner.subtract(p2.inner));
    }

    /**
     * Returns a point with the coordinates of this point multiplied
     * by the specified factor
     * @param factor the factor multiplying the coordinates
     * @return the point with multiplied coordinates
     */
    public Point multiply(double factor) {
        return new Point(inner.multiply(factor));
    }

    /**
     * Returns a point which lies in the middle between this point and the
     * specified point.
     * @param p2 the other endpoint
     * @return the point in the middle
     */
    public Point midpoint(Point p2) {
        return new Point(inner.midpoint(p2.inner));
    }

    /**
     * Computes dot (scalar) product of the vector represented by this instance
     * and the specified vector.
     * @param p2 the other vector
     * @return the dot product of the two vectors
     */
    public double dotProduct(Point p2) {
        return inner.dotProduct(p2.inner);
    }

    /**
     * Computes the angle (in degrees) between the three points with this point
     * as a vertex.
     * @param p2 one point
     * @param p3 other point
     * @return angle between the vectors (this, p2) and (this, p3) measured
     *         in degrees, {@code NaN} if the three points are not different
     *         from one another
     */
    public double angle(Point p2, Point p3) {
        return inner.angle(p2.inner, p3.inner);
    }

    /**
     * Computes the distance between this point and the specified {@code point}.
     *
     * @param point the other point
     * @return the distance between this point and the specified {@code point}.
     */
    public double distance(Point point) {
        return inner.distance(point.inner);
    }

    /**
     * Computes the area of the triangle formed by the three points
     * @param p2 second vertex
     * @param p3 third vertex
     * @return area of the triangle
     */
    public double area(Point p2, Point p3) {
        return 0.5 * Math.abs(
            this.getX() * (p2.getY() - p3.getY()) +
            p2.getX() * (p3.getY() - this.getY()) +
            p3.getX() * (this.getY() - p2.getY())
        );
    }

    /**
     * Builds a point from a given {@link javafx.geometry.Point2D} point
     * @param point
     */
    private Point(Point2D point) {
        inner = point;
    }

    /**
     * Builds a point from the given coordinates.
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
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
