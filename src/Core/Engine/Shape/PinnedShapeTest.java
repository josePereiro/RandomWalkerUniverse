//package Core.Engine.Shape;
//
//import Core.Engine.Tools.Tools;
//import Core.Engine.Vector.Vector;
//import processing.core.PApplet;
//import processing.core.PImage;
//
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//import java.util.Random;
//
//public class PinnedShapeTest extends PApplet {
//
//    public static void main(String... args) {
//        //Write your own main class path
//        PApplet.main("Core.Engine.Shape.PinnedShapeTest");
//    }
//
//    @Override
//    public void settings() {
//        size(500, 500);
//    }
//
//
//    @Override
//    public void setup() {
//
//        imageMode(CENTER);
//
//    }
//
//    PImage pImage;
//
//    @Override
//    public void draw() {
//
//        background(255);
//        if (pImage != null)
//            image(pImage, width / 2, height / 2);
//    }
//
//    @Override
//    public void mousePressed() {
//        super.mousePressed();
//
//        Random r = new Random();
//        int vertexesCount = r.nextInt(100 - 3) + 3;
//        PinnedShape.PolygonalShapeBuilder builder = new PinnedShape.PolygonalShapeBuilder();
//        Vector[] vertexes = Tools.getClockDirectionVertexes(100, vertexesCount);
//        for (int v = 0; v < vertexes.length; v++) {
//            builder.addVertex(vertexes[v].x, vertexes[v].y);
//        }
//
//        PinnedShape shape = builder.build();
//        BufferedImage image = shape.getFullColorImage();
//        pImage = new PImage(image);
//        pImage.resize(width,height);
//        System.out.println(shape);
//    }
//}