package fr.flowly.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import fr.flowly.geometry.*;

public class MapManager {
    private List<VoronoiSite> sites;
    private List<TouristPoint> tourists;
    private List<DelaunayTriangle> triangles;

    public MapManager() {
        this.sites = new ArrayList<>();
        this.tourists = new ArrayList<>();
        this.triangles = new ArrayList<>();
    }

    public void calculateDelaunayAndVoronoi() {

    }

    public void associateTouristsToSites() {
    	
        for (TouristPoint t : tourists) {
            VoronoiSite closest = null;
            double minDistance = Double.MAX_VALUE;
            
            for (VoronoiSite s : sites) {

                double dist = Math.pow(t.getX() - s.getX(), 2) + Math.pow(t.getY() - s.getY(), 2);
                if (dist < minDistance) {
                    minDistance = dist;
                    closest = s;
                }
            }
            t.setClosestSite(closest);
        }
    }

    public void bulkImportSites(File file) {

    }

    public void addRandomTourists(int quantity) {

        for (int i = 0; i < quantity; i++) {
            double randomX = Math.random() * 800;
            double randomY = Math.random() * 600;
            int nextId = tourists.size() + 1;
            tourists.add(new TouristPoint(nextId, randomX, randomY));
        }
        associateTouristsToSites(); 

    }

    public void addSite(VoronoiSite site) {
        this.sites.add(site);
        calculateDelaunayAndVoronoi();
    }

    public void removeSite(int siteId) {
        this.sites.removeIf(s -> s.getId() == siteId);
        calculateDelaunayAndVoronoi();
    }

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


    public List<VoronoiSite> getSites() { 
    	return sites; 
    }
    
    public List<TouristPoint> getTourists() { 
    	return tourists; 
    }
    
    public List<DelaunayTriangle> getTriangles() { 
    	return triangles; 
    }
}