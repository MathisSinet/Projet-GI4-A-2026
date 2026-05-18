package flowly.viewcontroller;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import java.util.List;
import flowly.geometry.*;

public class MapView extends Canvas {
    private double zoomFactor;
    private double offsetX;
    private double offsetY;

    public MapView() {
    }

    public void drawSites(List<VoronoiSite> sites) {
    }

    public void drawTourists(List<TouristPoint> tourists) {
    }

    public void drawVoronoi(List<VoronoiCell> cells) {
    }

    public void drawDelaunay(List<DelaunayTriangle> triangles) {
    }

    public void handleMouseDragged(MouseEvent event) {
    }

    public void handleMouseMoved(MouseEvent event) {
    }
}