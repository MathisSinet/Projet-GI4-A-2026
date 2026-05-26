package fr.flowly.geometry;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;

/**
 * Technical class to generate a Voronoi diagram from a Delaunay triangulation
 */
public class VoronoiGenerator {
    /**
     * Voronoi sites
     */
    private final List<VoronoiSite> points;
    /**
     * List of triangles adjacent to each Voronoi site
     */
    private final HashMap<VoronoiSite, ArrayList<DelaunayTriangle>> classifiedTriangles;
    /**
     * Voronoi polygons for each site
     */
    private final HashMap<VoronoiSite, ArrayList<Point>> polygons;
    /**
     * Bottom left corner of the map
     */
    private final Point mapCorner;
    /**
     * Size of the map
     */
    private final Point mapSize;

    /**
     * Returns the generated polygons
     * @return the generated polygons
     */
    public HashMap<VoronoiSite, ArrayList<Point>> getPolygons() {
        return polygons;
    }

    /**
     * Intercepts a half-line from a point inside the map to the border of the map
     * @param startPoint starting point
     * @param vector direction vector
     * @return intercept point
     */
    private Point calculateEdgeIntercept(Point startPoint, Point vector) {
        Point xIntercept, yIntercept;
        if (vector.getX() < 0) {
            // Intercept the left border
            xIntercept = startPoint.add(vector.multiply((startPoint.getX() - mapCorner.getX())/ (-vector.getX())));
        }
        else if (vector.getX() > 0) {
            // Intercept the right border
            xIntercept = startPoint.add(vector.multiply((mapCorner.getX() + mapSize.getX() - startPoint.getX()) / vector.getX()));
        }
        else {
            xIntercept = null;
        }

        if (vector.getY() < 0) {
            // Intercept the bottom border
            yIntercept = startPoint.add(vector.multiply((startPoint.getY() - mapCorner.getY())/ (-vector.getY())));
        }
        else if (vector.getY() > 0) {
            // Intercept the top border
            yIntercept = startPoint.add(vector.multiply((mapCorner.getY() + mapSize.getY() - startPoint.getY()) / vector.getY()));
        }
        else {
            yIntercept = null;
        }

        if (xIntercept == null) return yIntercept;
        if (yIntercept == null) return xIntercept;

        if (startPoint.distance(xIntercept) < startPoint.distance(yIntercept)) {
            return xIntercept;
        }
        else {
            return yIntercept;
        }
    }

    /**
     * From a given point on the border of the map, returns a number between 0 and 3
     * corresponding to which border it is in. <br>
     * 0 - bottom <br>
     * 1 - right <br>
     * 2 - top <br>
     * 3 - left <br>
     * 
     * If a point is on the corner, will return the next border CCW
     * @param point point to test
     * @return number corresponding to the border
     */
    private int getBorderId(Point point) {
        int ret = 0;
        final double EPSILON = 0.00001;

        if (Math.abs(point.getX() - mapCorner.getX() - mapSize.getX()) < EPSILON) {
            ret = 1;
        }
        if (Math.abs(point.getY() - mapCorner.getY() - mapSize.getY()) < EPSILON) {
            ret = 2;
        }
        if (Math.abs(point.getX() - mapCorner.getX()) < EPSILON) {
            ret = 3;
        }
        if (Math.abs(point.getY() - mapCorner.getY()) < EPSILON 
            && point.getX() - mapCorner.getX() < mapSize.getX() / 2) {
                ret = 0;
        }

        return ret;
    }

    /**
     * Closes a polygon
     * @param currentPoint first point of the segment to close
     * @param goalPoint last point of the segment to close
     * @param polygon polygon
     */
    private void closePolygon(Point currentPoint, Point goalPoint, List<Point> polygon) {
        int currentBorder = getBorderId(currentPoint);
        final int goalBorder = getBorderId(goalPoint);

        while (currentBorder != goalBorder) {
            polygon.add(switch (currentBorder) {
                case 0 -> mapCorner.add(new Point(mapSize.getX(), 0));
                case 1 -> mapCorner.add(mapSize);
                case 2 -> mapCorner.add(new Point(0, mapSize.getY()));
                default -> mapCorner;
            });
            currentBorder = (currentBorder + 1) % 4;
        }
    }

    /**
     * Generates the Voronoi polygon for a given site
     * @param site Site
     * @param triangles Adjacent Delaunay triangles
     * @return the Voronoi polygon for the site
     */
    private ArrayList<Point> voronoiPolygon(VoronoiSite site, ArrayList<DelaunayTriangle> triangles) {
        if (triangles.size() == 0) return new ArrayList<>();

        // First, we "sort" the triangles
        final HashSet<VoronoiSite> hasPredecessor = new HashSet<>();
        final HashMap<VoronoiSite, DelaunayTriangle> triangleMap = new HashMap<>();
        for (DelaunayTriangle triangle: triangles) {
            VoronoiSite p1 = triangle.nextVertex(site);
            VoronoiSite p2 = triangle.nextVertex(p1);
            hasPredecessor.add(p2);
            triangleMap.put(p1, triangle);
        }
        // We search the start of the chain, in case the site is on the edge of the triangulation
        VoronoiSite start = null;
        for (VoronoiSite pointWithSuccessor: triangleMap.keySet()) {
            if (!hasPredecessor.contains(pointWithSuccessor)) {
                start = pointWithSuccessor;
                break;
            }
        }
        
        // We create the polygon
        ArrayList<Point> polygon = new ArrayList<>();
        // Case 1: the site is not on the edge of the triangulation
        if (start == null) {
            start = triangles.getFirst().nextVertex(site);
            VoronoiSite currentPoint = start;
            do {
                DelaunayTriangle currentTriangle = triangleMap.get(currentPoint);
                VoronoiSite nextPoint = currentTriangle.nextVertex(currentPoint);
                polygon.add(currentTriangle.getCenter());
                currentPoint = nextPoint;
            }
            while (currentPoint != start);
        }

        // Case 2: the Voronoi site is on the edge
        else {
            final Point firstMP = site.midpoint(start);
            final Point firstVec = start.subtract(site);
            final Point firstNormal = new Point(firstVec.getY(), -firstVec.getX());
            final Point firstEdgePoint = calculateEdgeIntercept(firstMP, firstNormal);
            polygon.add(firstEdgePoint);

            VoronoiSite currentPoint = start;
            while (true) {
                DelaunayTriangle currentTriangle = triangleMap.get(currentPoint);
                if (currentTriangle == null) {
                    break;
                }
                VoronoiSite nextPoint = currentTriangle.nextVertex(currentPoint);
                polygon.add(currentTriangle.getCenter());
                currentPoint = nextPoint;
            }

            final Point lastMP = site.midpoint(currentPoint);
            final Point lastVec = currentPoint.subtract(site);
            final Point lastNormal = new Point(-lastVec.getY(), lastVec.getX());
            final Point lastEdgePoint = calculateEdgeIntercept(lastMP, lastNormal);
            polygon.add(lastEdgePoint);

            closePolygon(lastEdgePoint, firstEdgePoint, polygon);
        }

        return polygon;
    }

    /**
     * Generates the Voronoi diagram from a previous Delaunay triangulation
     * @param sites Voronoi sites
     * @param triangles Delaunay triangles
     * @param corner Bottom-left corner of the map
     * @param size Size of the map
     */
    public VoronoiGenerator(VoronoiSite[] sites, List<DelaunayTriangle> triangles, Point corner, Point size) {
        this.points = Arrays.asList(sites);
        this.mapCorner = corner;
        this.mapSize = size;

        classifiedTriangles = new HashMap<>();
        polygons = new HashMap<>();
        for (VoronoiSite point: sites) {
            classifiedTriangles.put(point, new ArrayList<>());
        }
        // Classification of triangles
        for (DelaunayTriangle triangle: triangles) {
            classifiedTriangles.get(triangle.getP1()).add(triangle);
            classifiedTriangles.get(triangle.getP2()).add(triangle);
            classifiedTriangles.get(triangle.getP3()).add(triangle);
        }
        // Generation of Voronoi polygons
        for (VoronoiSite point: sites) {
            polygons.put(point, voronoiPolygon(point, classifiedTriangles.get(point)));
        }
    }
}
