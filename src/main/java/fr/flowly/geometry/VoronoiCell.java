package fr.flowly.geometry;

import java.util.List;

/**
 * Holds the representation of a Voronoi cell
 */
public class VoronoiCell {
    /**
     * Location of the main site of the cell
     */
    private Point mainSite;
    /**
     * Vertices of the cell
     */
    private List<Point> cellVertices;
    /**
     * Cached area of the cell
     */
    private Double areaCache = null;

    /**
     * Creates a new Voronoi cell
     * @param mainSite location of the main site of the cell
     * @param vertices vertices of the cell
     */
    public VoronoiCell(Point mainSite, List<Point> vertices) {
        this.mainSite = mainSite;
        this.cellVertices = vertices;
    }

    /**
     * Updates the cell's main site
     * @param newSite new location of the cell's main site
     */
    public void setMainSite(Point newSite) {
        mainSite = newSite;
        invalidateArea();
    }

    /**
     * @return vertices of the cell
     */
    public List<Point> getCellVertices() {
        return cellVertices;
    }

    /**
     * Updates the cell's vertices
     * @param vertices new vertices
     */
    public void setCellVertices(List<Point> vertices) {
        this.cellVertices = vertices;
        invalidateArea();
    }

    /**
     * Computes the area of the cell
     * @return area of the cell
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
     * Returns the area of the cell
     * @return area of the cell
     */
    public double getArea() { 
        if (areaCache == null) {
            areaCache = calculateArea();
        }
        return areaCache;
    }

    /**
     * Invalidates the cached area of the cell
     */
    private void invalidateArea() {
        areaCache = null;
    }

    /**
     * Returns the number of tourists inside the cell
     * 
     * This function uses {@code TouristPoint.getClosestSite}
     * @param allTourists tourists
     * @return number of tourists inside the cell
     */
    public int getTouristCount(List<TouristPoint> allTourists) {
        int count = 0;
        for (TouristPoint tourist: allTourists) {
            if (tourist.getClosestSite().getCell() == this) {
                count += 1;
            }
        }
        return count;
    }
    
    /**
     * Returns the density of tourists inside the cell
     * @param allTourists tourists
     * @return density of tourists inside the cell
     */
    public double getDensity(List<TouristPoint> allTourists) {
    	return getTouristCount(allTourists) / getArea();
    }
    
    /**
     * Returns info about the distances between the tourists and the main site
     * @param allTourists tourists
     * @return [min distance, max distance, average distance]
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