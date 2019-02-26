package Core.Engine.Vector;

/**
 * Just a pair of values x,y and some tools methods...
 */
public class Vector implements Localizable {

    public int x;
    public int y;

    public Vector(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public boolean equals(Vector v) {
        return v.x == x && v.y == y;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass() && ((Vector) obj).x == x && ((Vector) obj).y == y;
    }

    public Vector getCopy() {
        return new Vector(x, y);
    }

    public Vector scalarMult(float factor) {
        x = (int) (x * factor);
        y = (int) (y * factor);
        return this;
    }

    public Vector add(Vector v) {
        x += v.x;
        y += v.y;
        return this;
    }

    public Vector add(int v) {
        x += v;
        y += v;
        return this;
    }

    public int magnitudeExp2() {
        return x * x + y * y;
    }

    public float getMagnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector sub(Vector v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    /**
     * Returns the normal vector of a given one. I returns the normal vector positioned at the right
     * of the reference. The method will return a vector with the same magnitude if the reference.
     *
     * @param vector          The reference vector
     * @param magnitudeFactor This factor will be multiplied to the magnitude of the normal vector
     * @return
     */
    public static Vector getNormalVector(Vector vector, int magnitudeFactor) {
        return new Vector(-vector.y * magnitudeFactor, vector.x * magnitudeFactor);
    }

    /**
     * Returns the normal vector of a given one. I returns the normal vector positioned at the right
     * of the reference. The method will return a vector with the same magnitude if the reference.
     *
     * @param vector The reference vector
     * @return
     */
    public static Vector getNormalVector(Vector vector) {
        return new Vector(-vector.y, vector.x);
    }

    /**
     * Returns the normal vector of a given one. I returns the normal vector positioned at the right
     * of the reference.
     *
     * @param vector          The reference vector
     * @param targetMagnitude the magnitude desire for the normal vector
     * @return
     */
    public static Vector getNormalVector(Vector vector, float targetMagnitude) {
        float factor = targetMagnitude / vector.getMagnitude();
        return new Vector(-Math.round(vector.y * factor), Math.round(vector.x * factor));
    }

    public static float getMagnitude(Vector v) {
        return (float) Math.sqrt(v.x * v.x + v.y * v.y);
    }

    public static float getDistance(Vector v1, Vector v2) {
        return getMagnitude(v1.x - v2.x, v1.y - v2.y);
    }

    public static float getDistance(int x1, int y1, int x2, int y2) {
        return getMagnitude(x1 - x2, y1 - y2);
    }

    public static float getMagnitude(int x, int y) {
        return (float) Math.sqrt(x * x + y * y);
    }

    public static void setMagnitude(float targetMagnitude, Vector vector) {
        float factor = targetMagnitude / vector.getMagnitude();
        vector.x = Math.round(vector.x * factor);
        vector.y = Math.round(vector.y * factor);
    }

    public static int getHashCode(int x, int y) {
        String s = x + "," + y;
        return s.hashCode();
    }

    @Override
    public String toString() {
        return "(x = " + x + ", y = " + y + ")";
    }

    @Override
    public int hashCode() {
        return getHashCode(x, y);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}