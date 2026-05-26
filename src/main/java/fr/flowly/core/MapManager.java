package fr.flowly.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import fr.flowly.geometry.*;

/**
 * Class that manages the map
 */
public class MapManager {
    /**
     * Voronoi (tourist) sites of the map
     */
    private List<VoronoiSite> sites;
    /**
     * Tourists (user points) in the map
     */
    private List<TouristPoint> tourists;
    /**
     * Delaunay triangulation of the map
     */
    private List<DelaunayTriangle> triangles;

    /**
     * Bottom left corner of the map
     */
    private final Point corner;
    /**
     * Size of the map
     */
    private final Point size;

    /**
     * Returns the bottom left corner of the map
     * @return bottom left corner of the map
     */
    public Point getCorner() {
        return corner;
    }

    /**
     * Returns the size of the map as a vector
     * @return size of the map
     */
    public Point getSize() {
        return size;
    }

    /**
     * Creates a new instance of the map manager
     * @param corner bottom left corner of the map
     * @param size size of the map
     */
    public MapManager(Point corner, Point size) {
        this.sites = new ArrayList<>();
        this.tourists = new ArrayList<>();
        this.triangles = new ArrayList<>();
        this.corner = corner;
        this.size = size;
    }

    /**
     * Recomputes the Delaunay-Voronoi diagrams
     */
    public void calculateDelaunayAndVoronoi() {
        DelaunayBWGen delaunayGenerator = new DelaunayBWGen(sites);
        triangles = delaunayGenerator.getTriangles();
        VoronoiGenerator voronoiGenerator = new VoronoiGenerator(sites, triangles, corner, size);
        for (VoronoiSite site: sites) {
            site.setCell(new VoronoiCell(site, voronoiGenerator.getPolygons().get(site)));
        }
        associateTouristsToSites();
    }

    /**
     * Associates tourists to the correct sites
     */
    public void associateTouristsToSites() {
        for (TouristPoint t : tourists) {
            VoronoiSite closest = null;
            double minDistance = Double.MAX_VALUE;
            
            for (VoronoiSite s : sites) {

                double dist = t.distance(s);
                if (dist < minDistance) {
                    minDistance = dist;
                    closest = s;
                }
            }
            t.setClosestSite(closest);
        }
    }

    /**
     * Imports tourists in bulk
     * @param file TODO
     */
    public void bulkImportSites(File file) {
        // TODO
    }

    /**
     * Adds random tourists to the map for testing purposes
     * @param quantity number of tourists to add
     */
    public void addRandomTourists(int quantity) {
        for (int i = 0; i < quantity; i++) {
            double randomX = corner.getX() + Math.random() * size.getX();
            double randomY = corner.getY() + Math.random() * size.getY();
            int nextId = tourists.size() + 1;
            tourists.add(new TouristPoint(nextId, randomX, randomY));
        }
        associateTouristsToSites(); 
    }

    /**
     * Adds a site to the map
     * @param site site to add
     */
    public void addSite(VoronoiSite site) {
        this.sites.add(site);
        calculateDelaunayAndVoronoi();
    }

    /**
     * Removes a site from the map
     * @param siteId id of the site to remove
     */
    public void removeSite(int siteId) {
        this.sites.removeIf(s -> s.getId() == siteId);
        calculateDelaunayAndVoronoi();
    }

    /**
     * Moves a site (technical requirement)
     * @param siteId id of the site to move
     * @param newX new x coordinate of the site
     * @param newY new y coordinate of the site
     */
    public void moveSite(int siteId, double newX, double newY) {
        for (VoronoiSite s : sites) {
            if (s.getId() == siteId) {
                s.setX(newX);
                s.setY(newY);
                break;
            }
        }
        calculateDelaunayAndVoronoi();
    }


    /**
     * Returns the list of Voronoi sites of the map
     * @return list of Voronoi sites of the map
     */
    public List<VoronoiSite> getSites() { 
    	return sites; 
    }
    
    /**
     * Returns the tourists points in the map
     * @return tourist points in the map
     */
    public List<TouristPoint> getTourists() { 
    	return tourists; 
    }
    
    /**
     * Returns the Delaunay triangles of the map
     * @return Delaunay triangles of the map
     */
    public List<DelaunayTriangle> getTriangles() { 
    	return triangles; 
    }
}