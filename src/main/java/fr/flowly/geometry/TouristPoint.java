package fr.flowly.geometry;

/**
 * Holds the representation of a tourist point
 */
public class TouristPoint extends Point {
    /**
     * Tourist point unique identifier
     */
	private int id;
    /**
     * Nearest Voronoi site
     */
    private VoronoiSite closestSite;
    
    /**
     * Builds a tourist point
     * @param id tourist point id
     * @param x x coordinate of the tourist
     * @param y y coordinate of the tourist
     */
    public TouristPoint(int id, double x, double y) {
        super(x, y);
        this.id = id;
        this.closestSite = null;
    }

    /**
     * @return unique identifier of the tourist
     */
    public int getId() {
        return id;
    }

    /**
     * @return the tourist's nearest site
     */
    public VoronoiSite getClosestSite() {
        return closestSite;
    }

    /**
     * Updates the nearest site of the tourist
     * @param site the new nearest site of the tourist
     */
    public void setClosestSite(VoronoiSite site) {
        this.closestSite = site;
    }
}
