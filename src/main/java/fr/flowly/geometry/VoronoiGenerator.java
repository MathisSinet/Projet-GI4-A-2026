package fr.flowly.geometry;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;

import javafx.geometry.Point2D;

// TODO Translate comments once the class is finished

/**
 * Classe technique utilisée pour générer le diagramme de Voronoi à partir d'une triangulation de Delaunay
 * déjà réalisée avec `DelaunayBWGen
 */
public class VoronoiGenerator {
    /**
     * Sites de Voronoi
     */
    List<VoronoiSite> points;
    /**
     * Liste des triangles adjacents à chaque site de Voronoi
     */
    HashMap<VoronoiSite, ArrayList<DelaunayTriangle>> classifiedTriangles;
    /**
     * Polygones de Voronoi
     */
    HashMap<VoronoiSite, ArrayList<Point>> polygons;
    /**
     * Coin supérieur gauche de la carte
     */
    Point2D mapCorner;
    /**
     * Vecteur de taille de la carte
     */
    Point2D mapSize;

    public HashMap<VoronoiSite, ArrayList<Point>> getPolygons() {
        return polygons;
    }

    /**
     * Génère le polygone de Voronoï d'un sommet donné
     * @param point Sommet (germe)
     * @param triangles Triangles adjacents à ce sommet
     * @return
     */
    private ArrayList<Point> voronoiPolygon(VoronoiSite point, ArrayList<DelaunayTriangle> triangles) {
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
        ArrayList<Point> polygon = new ArrayList<>();
        // Cas 1 : cycle
        if (start == null) {
            start = triangles.getFirst().nextVertex(point);
            VoronoiSite currentPoint = start;
            do {
                DelaunayTriangle t1 = triangleMap.get(currentPoint);
                VoronoiSite nextPoint = t1.nextVertex(currentPoint);
                DelaunayTriangle t2 = triangleMap.get(nextPoint);
                polygon.add(t1.getCenter());
                currentPoint = nextPoint;
            }
            while (currentPoint != start);
        }

        // Cas 2 : solution temporaire !
        // TODO implémenter une meilleure solution
        else {
            // On génère un point très éloigné sur la médiatrice du premier segment reliant
            // le centre de la cellule au premier point de la chaine
            Point firstMP = point.midpoint(start);
            Point vec = start.subtract(point);
            Point normal = new Point(vec.getY(), -vec.getX());
            Point farPoint = firstMP.add(normal.multiply(2000)); // Solution temporaire !!!
            polygon.add(farPoint);

            VoronoiSite currentPoint = start;
            while (true) {
                DelaunayTriangle t1 = triangleMap.get(currentPoint);
                VoronoiSite nextPoint = t1.nextVertex(currentPoint);
                DelaunayTriangle t2 = triangleMap.get(nextPoint);
                if (t2 == null) break;
                polygon.add(t1.getCenter()); // t1->t2
                currentPoint = nextPoint;
            }

            // TODO: fermer le polygone
        }

        return polygon;
    }

    public VoronoiGenerator(VoronoiSite[] points, List<DelaunayTriangle> triangles, Point2D corner, Point2D size) {
        this.points = Arrays.asList(points);
        this.mapCorner = corner;
        this.mapSize = size;

        // Création de listes vides pour les triangles adjacents à chaque point
        classifiedTriangles = new HashMap<>();
        polygons = new HashMap<>();
        for (VoronoiSite point: points) {
            classifiedTriangles.put(point, new ArrayList<>());
        }
        // Classement des triangles dans les tableaux de chaque points
        for (DelaunayTriangle triangle: triangles) {
            classifiedTriangles.get(triangle.getP1()).add(triangle);
            classifiedTriangles.get(triangle.getP2()).add(triangle);
            classifiedTriangles.get(triangle.getP3()).add(triangle);
        }
        // Génération des polygones de Voronoï
        for (VoronoiSite point: points) {
            polygons.put(point, voronoiPolygon(point, classifiedTriangles.get(point)));
        }
    }
}
