package fr.flowly.flowly.geometry;

import javafx.geometry.Point2D;
import java.util.List;

public class VoronoiCell {
    private List<Point2D> cellVertices;

    public VoronoiCell(List<Point2D> vertices) {
        this.cellVertices = vertices;
    }

    public List<Point2D> getCellVertices() {
        return cellVertices;
    }

    public void setCellVertices(List<Point2D> vertices) {
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