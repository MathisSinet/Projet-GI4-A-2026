package fr.flowly.geometry;

import java.util.List;
import java.util.ArrayList;

public class DelaunayTriangle {
    private final VoronoiSite p1, p2, p3;
    /**
     * Centre du cercle circonscrit
     */
    private final Point center;
    /**
     * Rayon du cercle circonscrit
     */
    private final double radius;

    /**
     * Génère un supertriangle à partir des points donnés.
     * Utile pour générer la triangulation de Delaunay
     * @param points Points
     * @return un supertriangle englobant tous les points
     */
    public static DelaunayTriangle supertriangle(Point[] points) {
        final double EPSILON = 100;
        double xmin = Double.POSITIVE_INFINITY;
        double xmax = Double.NEGATIVE_INFINITY;
        double ymin = Double.POSITIVE_INFINITY;
        double ymax = Double.NEGATIVE_INFINITY;
        for (Point point: points) {
            xmin = Math.min(xmin, point.getX());
            xmax = Math.max(xmax, point.getX());
            ymin = Math.min(ymin, point.getY());
            ymax = Math.max(ymax, point.getY());
        }
        return new DelaunayTriangle(
            new VoronoiSite(xmin - (xmax - xmin), ymin - (ymax - ymin)),
            new VoronoiSite(4*xmax - 3*xmin + 3*EPSILON, ymin - (ymax - ymin)),
            new VoronoiSite(xmin - (xmax - xmin), 4*ymax - 3*ymin + 3*EPSILON)
        );
    }

    public VoronoiSite getP1() {
        return p1;
    }

    public VoronoiSite getP2() {
        return p2;
    }

    public VoronoiSite getP3() {
        return p3;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    private Point calculateCenter() {
        final Point vec1 = p2.subtract(p1);
        final Point vec2 = p3.subtract(p1);
        final Point mid1 = p1.midpoint(p2);
        final Point mid2 = p1.midpoint(p3);
        final double det = vec1.getX() * vec2.getY() - vec1.getY() * vec2.getX();
        if (det == 0.) {
            return p2.midpoint(p3);
        }
        final double A = vec1.dotProduct(mid1) / det;
        final double B = vec2.dotProduct(mid2) / det;
        return new Point(A * vec2.getY() - B * vec1.getY(), -A * vec2.getX() + B * vec1.getX());
    }

    public boolean hasEdge(Edge<VoronoiSite> edge) {
        Point v1 = edge.getV1();
        Point v2 = edge.getV2();
        return (
            p1.equals(v1) && (p2.equals(v2) || p3.equals(v2)) ||
            p2.equals(v1) && (p3.equals(v2) || p1.equals(v2)) ||
            p3.equals(v1) && (p1.equals(v2) || p2.equals(v2))
        );
    }

    public List<Edge<VoronoiSite>> getEdges() {
        return new ArrayList<>(List.of(
            new Edge<>(p1, p2),
            new Edge<>(p2, p3),
            new Edge<>(p3, p1)
        ));
    }

    public boolean shareVertex(DelaunayTriangle triangle) {
        return p1.equals(triangle.getP1()) || p1.equals(triangle.getP2()) || p1.equals(triangle.getP3()) ||
            p2.equals(triangle.getP1()) || p2.equals(triangle.getP2()) || p2.equals(triangle.getP3()) ||
            p3.equals(triangle.getP1()) || p3.equals(triangle.getP2()) || p3.equals(triangle.getP3());
    }

    public VoronoiSite nextVertex(VoronoiSite vertex) {
        if (vertex.equals(p1)) {
            return p2;
        }
        if (vertex.equals(p2)) {
            return p3;
        }
        return p1;
    }
    public VoronoiSite previousVertex(VoronoiSite vertex) {
        if (vertex.equals(p1)) {
            return p3;
        }
        if (vertex.equals(p3)) {
            return p2;
        }
        return p1;
    }

    public DelaunayTriangle(VoronoiSite pt1, VoronoiSite pt2, VoronoiSite pt3) {
        if (pt1.angle(pt2, pt3) < 180) {
            p1 = pt1;
            p2 = pt2;
            p3 = pt3;
        }
        else {
            p1 = pt1;
            p2 = pt3;
            p3 = pt2;
        }
        center = calculateCenter();
        radius = center.distance(pt1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        };
        if (obj instanceof DelaunayTriangle t) {
            return (
                p1.equals(t.getP1()) && p2.equals(t.getP2()) && p3.equals(t.getP3()) ||
                p1.equals(t.getP2()) && p2.equals(t.getP3()) && p3.equals(t.getP1()) ||
                p1.equals(t.getP3()) && p2.equals(t.getP1()) && p3.equals(t.getP2())
            );
        }
        return false;
    }
    
    // Déplacer sur la classe de base?
    public void getArea() { //double
    	
    }
    
    public void getEdgeLengths() { //double[]
    	
    }
    
    public void getTouristCount() { //int
    	
    }
    
    public void getPopulationImbalance() { //double
    	
    }
}