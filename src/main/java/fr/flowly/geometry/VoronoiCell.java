package fr.flowly.geometry;

import java.util.List;

public class VoronoiCell {
    private Point center;
    private List<Point> cellVertices;
    private Double areaCache = null;

    public VoronoiCell(Point center, List<Point> vertices) {
        this.center = center;
        this.cellVertices = vertices;
    }

    public List<Point> getCellVertices() {
        return cellVertices;
    }

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
            area += center.area(getCellVertices().get(i), getCellVertices().get((i+1) % vertexCount));
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

    private void invalidateArea() {
        areaCache = null;
    }
    
    public int getTouristCount(List<TouristPoint> allTourists) { //int
    	// TODO: implementation getTouristCount
        return 0;
    }
    
    public double getDensity(List<TouristPoint> allTourists) {
    	return getTouristCount(allTourists) / getArea();
    }
    
    public double getDistanceMetrics(List<TouristPoint> allTourists) {
    	// TODO: implementation getDistanceMetrics
        return 0;
    }
}