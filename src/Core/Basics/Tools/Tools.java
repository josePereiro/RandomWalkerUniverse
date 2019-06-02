package Core.Basics.Tools;

public class Tools {

    public static class CollectionsOps {

        public static float mean(float[] floats) {
            float s = 0;
            for (int i = 0; i < floats.length; i++) {
                s += floats[i];
            }
            return s / floats.length;
        }

    }

}
