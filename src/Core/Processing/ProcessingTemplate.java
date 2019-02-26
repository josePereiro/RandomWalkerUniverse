package Core.Processing;

import Core.Engine.Exceptions.IllegalVertexesCountException;
import Core.Engine.Exceptions.IllegalVertexesPositionException;
import Core.Engine.Iterations.Iterations;
import Core.Engine.Iterations.OnIterationActionHandler;
import Core.Engine.Shape.Shape;
import Core.Engine.Tools.Tools;
import Core.Engine.Vector.Vector;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

import java.util.Random;

public class ProcessingTemplate extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Processing.ProcessingTemplate");
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    OnIterationActionHandler onIterationActionHandler;

    @Override
    public void setup() {

        onIterationActionHandler = new OnIterationActionHandler() {
            @Override
            public void action(int x, int y) {
                point(x, y);
            }
        };

    }

    @Override
    public void draw() {

        background(255);
        stroke(0);
    }

    public void drawLine(){
        
    }

}