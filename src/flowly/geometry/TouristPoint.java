package flowly.geometry;

public class TouristPoint extends Point{
	private int id;
    private VoronoiSite closestSite;
    
    
    public TouristPoint(int id, double x, double y) {
        super(x, y);
        this.id = id;
        this.closestSite = null;
    }

    public int getId() {
        return id;
    }

    public VoronoiSite getClosestSite() {
        return closestSite;
    }

    public void setClosestSite(VoronoiSite site) {
        this.closestSite = site;
    }
}
