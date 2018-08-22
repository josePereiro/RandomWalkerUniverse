package Core.Engine.Maths;

public class Tools {

    /**
     * @param d
     * @return
     */
    public static int roundedSqrt(float d) {
        return (int) Math.round(Math.sqrt(d));
    }

    public static int roundedSqrt(int i) {
        return (int) Math.round(Math.sqrt(i));
    }


}