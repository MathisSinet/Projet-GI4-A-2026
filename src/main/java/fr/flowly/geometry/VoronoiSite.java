package fr.flowly.geometry;

/**
 * Site de Voronoi
 */
public class VoronoiSite extends Point {
    /**
     * Identifiant du site
     */
    private int id;
    /**
     * Nom du site
     */
    private String name;
    /**
     * Capacité maximale du site
     */
    private int maxCapacity;
    /**
     * Cellule des environs du site
     */
    private VoronoiCell cell;

    /**
     * Crée un nouveau site de Voronoi vierge (solution technique uniquement)
     * @param x
     * @param y
     */
    public VoronoiSite(double x, double y) {
        super(x, y);
    }

    /**
     * Crée un nouveau site de Voronoi
     * @param id Identifiant du site
     * @param name Nom du site
     * @param x Coordonnée x du site
     * @param y Coordonnée y du site
     * @param maxCapacity Capacité maximale du site
     * @param cell Cellule du site
     */
    public VoronoiSite(int id, String name, double x, double y, int maxCapacity, VoronoiCell cell) {
        super(x, y);
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.cell = cell;
    }

    public VoronoiCell getCell() {
        return cell;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}