package fr.flowly.geometry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Génère une triangulation de Delaunay
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
     * Génère une triangulation de Delaunay
     * @param points sites à trianguler
     */
    public DelaunayBWGen(VoronoiSite[] points) {
        visited = new ArrayList<>();
        triangles = new ArrayList<>();
        supertriangle = DelaunayTriangle.supertriangle(points);
        triangles.add(supertriangle);

        for (VoronoiSite point: points) {
            visited.add(point);

            // ETAPE 1 : Repérage des triangles à supprimer
            List<DelaunayTriangle> badTriangles = new ArrayList<>();
            for (DelaunayTriangle triangle: triangles) {
                // On vérifie si le nouveau point est dans le cercle circonscrit du triangle
                if (point.distance(triangle.getCenter()) < triangle.getRadius()) {
                    badTriangles.add(triangle);
                }
            }
            // ETAPE 2: On détermine le polynôme englobant
            List<Edge<VoronoiSite>> polygon = new ArrayList<>();
            // On regarde chaque triangle à supprimer
            for (DelaunayTriangle badTriangle: badTriangles) {
                // On regarde chaque côté
                for (Edge<VoronoiSite> edge: badTriangle.getEdges()) {
                    boolean toAdd = true;
                    // On regarde si ce côté est partagé par un autre triangle à supprimer
                    for (DelaunayTriangle otherBadTriangle: badTriangles) {
                        if (!badTriangle.equals(otherBadTriangle) && otherBadTriangle.hasEdge(edge)) {
                            toAdd = false;
                            break;
                        }
                    }
                    // S'il est unique, alors il fait partie du polynôme englobant
                    if (toAdd) polygon.add(edge);
                }
            }
            // ETAPE 3: On supprime les triangles à supprimer
            Iterator<DelaunayTriangle> iter = triangles.listIterator();
            while (iter.hasNext()) {
                DelaunayTriangle triangle = iter.next();
                if (badTriangles.contains(triangle)) {
                    iter.remove();
                }
            }
            // ETAPE 4: On triangule le trou polygonal
            for (Edge<VoronoiSite> edge: polygon) {
                triangles.add(new DelaunayTriangle(point, edge.getV1(), edge.getV2()));
            }
        }
        // Nettoyage
        Iterator<DelaunayTriangle> iter = triangles.listIterator();
        while (iter.hasNext()) {
            DelaunayTriangle triangle = iter.next();
            if (triangle.shareVertex(supertriangle)) {
                iter.remove();
            }
        }
    }
}
