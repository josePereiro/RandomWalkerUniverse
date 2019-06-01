package Core.Basics.RandomnessHandler;

import Core.Basics.Collections.CircularIntSet;
import Core.Basics.Vector2D.Vector2D;

import java.util.Random;

public class RandomnessHandler {

    public static CircularIntSet randomBuffer;

    public static void setRandomBuffer(int bufferLength, int rBound){

        Random r = new Random();
        int[] data = new int[bufferLength];
        for (int i = 0; i < data.length; i++) {
            data[i] = r.nextInt(rBound);
        }

        randomBuffer = new CircularIntSet(data);
    }

//    public static int getNextStep(Vector2D Tendency){

//    }

}
