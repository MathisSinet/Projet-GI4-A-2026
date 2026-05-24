package fr.flowly.geometry;

import java.util.List;

/**
 * Représente une cellule de Voronoi
 */
public class VoronoiCell {
    /**
     * Site principal de la cellule (utilisé uniquement pour calculer l'aire)
     */
    private Point mainSite;
    /**
     * Sommets de la cellule
     */
    private List<Point> cellVertices;
    /**
     * Valeur en cache de l'aire de la cellule
     */
    private Double areaCache = null;

    /**
     * Crée une nouvelle cellule de Voronoi
     * @param mainSite site de la cellule
     * @param vertices sommets de la cellule
     */
    public VoronoiCell(Point mainSite, List<Point> vertices) {
        this.mainSite = mainSite;
        this.cellVertices = vertices;
    }

    /**
     * Met à jour le centre de la cellule
     * @param newSite nouveau centre de la cellule
     */
    public void setMainSite(Point newSite) {
        mainSite = newSite;
        invalidateArea();
    }

    /**
     * @return les sommets de la cellule
     */
    public List<Point> getCellVertices() {
        return cellVertices;
    }

    /**
     * Met à jour les sommets de la cellule.
     * <p>Cette fonction consomme la liste `vertices`</p>
     * @param vertices nouveaux sommets
     */
    public void setCellVertices(List<Point> vertices) {
        this.cellVertices = vertices;
        invalidateArea();
    }

    /**
     * Calcule l'aire de la cellule
     * @return l'aire de la cellule
     */
    private double calculateArea() {
        int vertexCount = getCellVertices().size();
        double area = 0;
    	for (int i = 0; i < vertexCount; i++) {
            area += mainSite.area(getCellVertices().get(i), getCellVertices().get((i+1) % vertexCount));
        }
        return area;
    }
    
    /**
     * Renvoie l'aire de la cellule
     * @return l'aire de la cellule
     */
    public double getArea() { 
        if (areaCache == null) {
            areaCache = calculateArea();
        }
        return areaCache;
    }

    /**
     * Invalide l'aire de la cellule
     * (par exemple si le diagramme a été modifié)
     */
    private void invalidateArea() {
        areaCache = null;
    }

    /**
     * Renvoie le nombre de touristes présents dans la cellule
     * Cette fonction se base sur les valeurs de tourist.getClosestSite
     * @param allTourists touristes
     * @return nombre de touristes présents dans la cellule
     */
    public int getTouristCount(List<TouristPoint> allTourists) { //int
        int count = 0;
        for (TouristPoint tourist: allTourists) {
            if (tourist.getClosestSite().getCell() == this) {
                count += 1;
            }
        }
        return count;
    }
    
    /**
     * Renvoie la densité de touristes dans la zone
     * @param allTourists touristes
     * @return densité de touristes dans la zone
     */
    public double getDensity(List<TouristPoint> allTourists) {
    	return getTouristCount(allTourists) / getArea();
    }
    
    /**
     * Renvoie des informations sur les distances des touristes au site principal
     * @param allTourists
     * @return [distance minimale, distance maximale, distance moyenne]
     */
    public double[] getDistanceMetrics(List<TouristPoint> allTourists) {
    	int count = 0;
        double minDist = Double.MAX_VALUE;
        double maxDist = Double.MIN_VALUE;
        double distSum = 0;
        for (TouristPoint tourist: allTourists) {
            if (tourist.getClosestSite().getCell() == this) {
                double dist = tourist.distance(mainSite);
                count += 1;
                minDist = Math.min(minDist, dist);
                maxDist = Math.max(maxDist, dist);
                distSum += dist;
            }
        }
        return new double[]{minDist, maxDist, distSum / count};
    }
}