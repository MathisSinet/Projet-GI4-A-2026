package fr.flowly.geometry;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;

import javafx.geometry.Point2D;

public class VoronoiGenerator {
    List<VoronoiSite> points;
    HashMap<VoronoiSite, ArrayList<DelaunayTriangle>> sortedTriangles;
    HashMap<VoronoiSite, ArrayList<Edge<Point>>> polygons;
    Point2D corner;
    Point2D size;

    public HashMap<VoronoiSite, ArrayList<Edge<Point>>> getPolygons() {
        return polygons;
    }

    /**
     * Génère le polygone de Voronoï d'un sommet donné
     * @param point Sommet (germe)
     * @param triangles Triangles adjacents à ce sommet
     * @return
     */
    private ArrayList<Edge<Point>> voronoiPolygon(VoronoiSite point, ArrayList<DelaunayTriangle> triangles) {
        int triangleCount = triangles.size();
        if (triangleCount == 0) return new ArrayList<>();

        // On trie tout d'abord les triangles
        // Pour cela, on crée un hashset des triangles associés à chaque point
        HashSet<VoronoiSite> hasPredecessor = new HashSet<>();
        HashMap<VoronoiSite, DelaunayTriangle> triangleMap = new HashMap<>();
        // On remplit ces HashMap
        for (DelaunayTriangle triangle: triangles) {
            VoronoiSite p1 = triangle.nextVertex(point);
            VoronoiSite p2 = triangle.nextVertex(p1);
            hasPredecessor.add(p2);
            triangleMap.put(p1, triangle);
        }
        // On cherche à repérer le début de la chaine
        VoronoiSite start = null;
        for (VoronoiSite pointWithSuccessor: triangleMap.keySet()) {
            if (!hasPredecessor.contains(pointWithSuccessor)) {
                start = pointWithSuccessor;
                break;
            }
        }
        
        // On crée maintenant le polygone
        ArrayList<Edge<Point>> edges = new ArrayList<>();
        // Cas 1 : cycle
        if (start == null) {
            start = triangles.getFirst().nextVertex(point);
            VoronoiSite currentPoint = start;
            do {
                DelaunayTriangle t1 = triangleMap.get(currentPoint);
                VoronoiSite nextPoint = t1.nextVertex(currentPoint);
                DelaunayTriangle t2 = triangleMap.get(nextPoint);
                edges.add(new Edge<>(t1.getCenter(), t2.getCenter()));
                currentPoint = nextPoint;
            }
            while (currentPoint != start);
        }

        // Cas 2 : solution temporaire !
        // TODO implémenter une meilleure solution
        else {
            DelaunayTriangle firstTriangle = triangleMap.get(start);
            Point firstMP = point.midpoint(start);
            Point vec = start.subtract(point);
            Point normal = new Point(vec.getY(), -vec.getX());
            Point farPoint = firstMP.add(normal.multiply(2000)); // Solution temporaire !!!
            edges.add(new Edge<>(farPoint, firstTriangle.getCenter()));

            VoronoiSite currentPoint = start;
            while (true) {
                DelaunayTriangle t1 = triangleMap.get(currentPoint);
                VoronoiSite nextPoint = t1.nextVertex(currentPoint);
                DelaunayTriangle t2 = triangleMap.get(nextPoint);
                if (t2 == null) break;
                edges.add(new Edge<>(t1.getCenter(), t2.getCenter()));
                currentPoint = nextPoint;
            }
        }

        return edges;
    }

    public VoronoiGenerator(VoronoiSite[] points, List<DelaunayTriangle> triangles, Point2D corner, Point2D size) {
        this.points = Arrays.asList(points);
        this.corner = corner;
        this.size = size;

        // Création de listes vides pour les triangles adjacents à chaque point
        sortedTriangles = new HashMap<>();
        polygons = new HashMap<>();
        for (VoronoiSite point: points) {
            sortedTriangles.put(point, new ArrayList<>());
        }
        // Classement des triangles dans les tableaux de chaque points
        for (DelaunayTriangle triangle: triangles) {
            sortedTriangles.get(triangle.getP1()).add(triangle);
            sortedTriangles.get(triangle.getP2()).add(triangle);
            sortedTriangles.get(triangle.getP3()).add(triangle);
        }
        // Génération des polygones de Voronoï
        for (VoronoiSite point: points) {
            polygons.put(point, voronoiPolygon(point, sortedTriangles.get(point)));
        }
    }
}
