package fr.flowly.geometry;

import java.util.List;

public class VoronoiCell {
    private List<Point> cellVertices;

    public VoronoiCell(List<Point> vertices) {
        this.cellVertices = vertices;
    }

    public List<Point> getCellVertices() {
        return cellVertices;
    }

    public void setCellVertices(List<Point> vertices) {
        this.cellVertices = vertices;
    }
    
    public void getArea() { //double
    	
    }
    
    public void getTouristCount(List<TouristPoint> allTourists) { //int
    	
    }
    
    public void getDensity(List<TouristPoint> allTourists) { //double
    	
    }
    
    public void getDistanceMetrics(List<TouristPoint> allTourists) { //double
    	
    }
}