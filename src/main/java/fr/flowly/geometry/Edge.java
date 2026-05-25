package fr.flowly.geometry;

/**
 * Holds the representation of an edge between two points
 */
public class Edge<T extends Point> {
    /**
     * Ends of the edge
     */
    T v1, v2;

    /**
     * @return one end of the edge
     */
    public T getV1() {
        return v1;
    }

    /**
     * @return one end of the edge
     */
    public T getV2() {
        return v2;
    }

    /**
     * @return the length of the edge
     */
    public double length() {
        return v1.distance(v2);
    }

    /**
     * Builds an edge from two ends
     * @param v1 first end
     * @param v2 second end
     */
    public Edge(T v1, T v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        };
        if (obj instanceof Edge e) {
            return (
                v1.equals(e.getV1()) && v2.equals(e.getV2()) ||
                v1.equals(e.getV2()) && v2.equals(e.getV1())
            );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (v1.hashCode() >> 1) + (v2.hashCode() >> 1);
    }
}
