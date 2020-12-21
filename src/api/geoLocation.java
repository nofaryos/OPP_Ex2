package api;

/**
 * This class implements the interface of geo_location.
 * geoLocation represents a geo location x,y,z aka Point3D of node in the graph.
 * Each geoLocation has value of X coordinate, value of Y coordinate and value of Z coordinate.
 */

public class geoLocation implements geo_location {

    private double x;
    private double y;
    private double z;

    /**
     * Constructor to create a new geoLocation:
     */

    public geoLocation(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * Constructor to create a new geoLocation:
     * @param x
     * @param y
     * @param z
     */
    public  geoLocation(double x,double y,double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * A copy constructor.
     * @param gl - geo_location.
     */

    public geoLocation(geo_location gl){
        this.x = gl.x();
        this.y = gl.y();
        this.z = gl.z();
    }

    /**
     * This function return the value of X coordinate of this geoLocation.
     * @return x
     */

    @Override
    public double x() {
        return this.x;
    }

    /**
     * This function return the value of Y coordinate of this geoLocation.
     * @return y
     */

    @Override
    public double y() {
        return this.y;
    }

    /**
     * This function return the value of Z coordinate of this geo location.
     * @return z
     */

    @Override
    public double z() {
        return this.z;
    }

    /**
     * This function return the distance between two geo locations.
     * @param gl
     * @return distance
     */

    @Override
    public double distance(geo_location gl) {
        return Math.sqrt(Math.pow(this.x - gl.x(),2) + Math.pow(this.y - gl.y(),2) + Math.pow(this.z - gl.z(),2));
    }

    /**
     * This function returns true if two geo locations are equal to each other.
     * @param o object
     * @return true/false
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof geoLocation)) return false;
        geoLocation that = (geoLocation) o;
        if (this.x != that.x()){
            return false;
        }
        if (this.y != that.y()){
            return false;
        }
        return this.z==that.z();
    }

    /**
     * Print function
     * @return String
     */

    @Override
    public String toString() {
        return x + "," + y + "," + z ;
    }
}
