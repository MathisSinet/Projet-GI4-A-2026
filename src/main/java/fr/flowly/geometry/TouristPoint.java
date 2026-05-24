package fr.flowly.geometry;

/**
 * Représente la position d'un touriste
 */
public class TouristPoint extends Point {
    /**
     * Identifiant du touriste
     */
	private int id;
    /**
     * Site de Voronoi le plus proche
     */
    private VoronoiSite closestSite;
    
    public TouristPoint(int id, double x, double y) {
        super(x, y);
        this.id = id;
        this.closestSite = null;
    }

    /**
     * @return l'identifiant unique du touriste
     */
    public int getId() {
        return id;
    }

    /**
     * @return le site le plus proche du touriste
     */
    public VoronoiSite getClosestSite() {
        return closestSite;
    }

    public void setClosestSite(VoronoiSite site) {
        this.closestSite = site;
    }
}
