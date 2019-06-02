//package test.NotJUnitTests.ProcessingTests;
//
//import Core.World.Vector2D;
//import Core.World.World;
//import processing.core.PApplet;
//
//public class RandomWalkerTests extends PApplet {
//
//    public static void main(String[] args) {
//        PApplet.main("test.NotJUnitTests.ProcessingTests.RandomWalkerTests");
//    }
//
//
//    @Override
//    public void settings() {
//        size(300, 600);
//    }
//
//    Vector2D walker;
//    Vector2D tendency;
//
//    @Override
//    public void setup() {
//        World.Statics.Caches.initCaches();
//        walker = vector2Dcache.getPositive(width / 2, height / 2);
//    }
//
//    @Override
//    public void draw() {
//        tendency = vector2Dcache.getPositive(mouseX, mouseY);
//        walker = randomFloatCache.getNextStep(walker,
//                vector2Dcache.get(0, 0));
//        background(255);
//        ellipse(walker.x, walker.y, 5, 5);
//    }
//}
