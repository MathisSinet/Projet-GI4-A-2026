package fr.flowly.geometry;

import java.util.List;

public class VoronoiCell {
    private Point center;
    private List<Point> cellVertices;

    public VoronoiCell(Point center, List<Point> vertices) {
        this.center = center;
        this.cellVertices = vertices;
    }

    public List<Point> getCellVertices() {
        return cellVertices;
    }

    public void setCellVertices(List<Point> vertices) {
        this.cellVertices = vertices;
    }
    
    public double getArea() { 
    	// TODO: implementation getArea
        return 0;
    }
    
    public int getTouristCount(List<TouristPoint> allTourists) { //int
    	// TODO: implementation getTouristCount
        return 0;
    }
    
    public double getDensity(List<TouristPoint> allTourists) {
    	// TODO: implementation getDensity
        return 0;
    }
    
    public double getDistanceMetrics(List<TouristPoint> allTourists) {
    	// TODO: implementation getDistanceMetrics
        return 0;
    }
}