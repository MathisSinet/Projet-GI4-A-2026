package fr.flowly.geometry;

/**
 * Holds the representation of a Voronoi Site
 */
public class VoronoiSite extends Point {
    /**
     * Unique identifier of the site
     */
    private int id;
    /**
     * Name of the site
     */
    private String name;
    /**
     * Maxmimum capacity of the site
     */
    private int maxCapacity;
    /**
     * Voronoi Cell corresponding to the site
     */
    private VoronoiCell cell;

    /**
     * Creates a new Voronoi Site with only (x,y) coordinates
     * @param x x coordinate of the site
     * @param y y coordinate of the site
     */
    public VoronoiSite(double x, double y) {
        super(x, y);
    }

    /**
     * Creates a new Voronoi Site
     * @param id unique identifier of the site
     * @param name name of the site
     * @param x x coordinate of the site
     * @param y y coordinate of the site
     * @param maxCapacity maximum capacity of the site
     * @param cell Voronoi cell of the site
     */
    public VoronoiSite(int id, String name, double x, double y, int maxCapacity, VoronoiCell cell) {
        super(x, y);
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.cell = cell;
    }

    /**
     * Returns the site's cell
     * @return the site's cell
     */
    public VoronoiCell getCell() {
        return cell;
    }

    /**
     * Updates the site's Voronoi cell
     * @param cell new Voronoi cell
     */
    public void setCell(VoronoiCell cell) {
        this.cell = cell;
    }

    /**
     * Returns the site's unique identifier
     * @return the site's unique identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the site
     * @return the name of the site
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the maximum capacity of the site
     * @return the maximum capacity of the site
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Updates the maximum capacity of the site
     * @param maxCapacity the new max capacity of the site
     */
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}