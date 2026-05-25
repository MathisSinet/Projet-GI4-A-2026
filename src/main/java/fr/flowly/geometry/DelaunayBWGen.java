package fr.flowly.geometry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Technical class to generate a Delaunay triangulation
 */
public class DelaunayBWGen {
    private List<VoronoiSite> visited;
    private List<DelaunayTriangle> triangles;
    private DelaunayTriangle supertriangle;

    public List<DelaunayTriangle> getTriangles() {
        return triangles;
    }

    public DelaunayTriangle getSupertriangle() {
        return supertriangle;
    }

    /**
     * Generates a Delaunay triangulation using the Bowyer-Watson algorithm
     * @param points sites to triangulate
     */
    public DelaunayBWGen(VoronoiSite[] points) {
        visited = new ArrayList<>();
        triangles = new ArrayList<>();
        supertriangle = DelaunayTriangle.supertriangle(points);
        triangles.add(supertriangle);

        for (VoronoiSite point: points) {
            visited.add(point);

            // STEP 1: Gather which triangles are to be deleted
            List<DelaunayTriangle> badTriangles = new ArrayList<>();
            for (DelaunayTriangle triangle: triangles) {
                // We check if the new site is inside the triangle's circumcircle
                if (point.distance(triangle.getCenter()) < triangle.getRadius()) {
                    badTriangles.add(triangle);
                }
            }
            // STEP 2: We determine the encompassing polygon
            List<Edge<VoronoiSite>> polygon = new ArrayList<>();
            // We check each triangle to delete
            for (DelaunayTriangle badTriangle: badTriangles) {
                // We check each edge
                for (Edge<VoronoiSite> edge: badTriangle.getEdges()) {
                    boolean toAdd = true;
                    // We check if this edge is shared by another triangle to delete
                    for (DelaunayTriangle otherBadTriangle: badTriangles) {
                        if (!badTriangle.equals(otherBadTriangle) && otherBadTriangle.hasEdge(edge)) {
                            toAdd = false;
                            break;
                        }
                    }
                    // If this edge is unique, then it is part of the encompassing polygon
                    if (toAdd) polygon.add(edge);
                }
            }
            // STEP 3: We delete the bad triangles
            Iterator<DelaunayTriangle> iter = triangles.listIterator();
            while (iter.hasNext()) {
                DelaunayTriangle triangle = iter.next();
                if (badTriangles.contains(triangle)) {
                    iter.remove();
                }
            }
            // STEP 4: We triangulate the polygonal hole
            for (Edge<VoronoiSite> edge: polygon) {
                triangles.add(new DelaunayTriangle(point, edge.getV1(), edge.getV2()));
            }
        }
        // Cleanup
        Iterator<DelaunayTriangle> iter = triangles.listIterator();
        while (iter.hasNext()) {
            DelaunayTriangle triangle = iter.next();
            if (triangle.shareVertex(supertriangle)) {
                iter.remove();
            }
        }
    }
}
