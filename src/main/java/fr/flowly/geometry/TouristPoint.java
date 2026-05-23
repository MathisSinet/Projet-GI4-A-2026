package fr.flowly.geometry;

import javafx.geometry.Point2D;

/**
 * Représente la position d'un touriste
 */
public class TouristPoint extends Point2D {
	private int id;
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
