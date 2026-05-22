package fr.flowly.geometry;

public class DelaunayTriangle extends Triangle {
    private VoronoiSite[] vertices = new VoronoiSite[3];

    public DelaunayTriangle(VoronoiSite s1, VoronoiSite s2, VoronoiSite s3) {
        super(s1.getPoint(), s2.getPoint(), s3.getPoint());
        this.vertices[0] = s1;
        this.vertices[1] = s2.getPoint().equals(getP2()) ? s2 : s3;
        this.vertices[2] = s2.getPoint().equals(getP2()) ? s3 : s2;
    }

    public VoronoiSite[] getVertices() {
        return vertices;
    }
    
    // Déplacer sur la classe de base?
    public void getArea() { //double
    	
    }
    
    public void getEdgeLengths() { //double[]
    	
    }
    
    public void getTouristCount() { //int
    	
    }
    
    public void getPopulationImbalance() { //double
    	
    }
}