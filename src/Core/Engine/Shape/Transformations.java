package Core.Engine.Shape;

import Core.Engine.Vector.Vector;

import java.util.ArrayList;

/**
 * This class handle the transformations that a PinnedShape can
 * suffer, as translations and rotation.
 */
public class Transformations {

    //Translations
    ShapePoint[] rightTranslationNewPoints;
    ShapePoint[] rightTranslationOldPoints;

    ShapePoint[] leftTranslationOldPoints;
    ShapePoint[] leftTranslationNewPoints;

    ShapePoint[] upTranslationNewPoints;
    ShapePoint[] upTranslationOldPoints;

    ShapePoint[] downTranslationNewPoints;
    ShapePoint[] downTranslationOldPoints;


    public Transformations(PinnedShape shape) {

        ArrayList<ShapePoint> shapePoints = new ArrayList<>();
        for (ShapePoint perimeterPoint : shape.getPerimeterPoints()) {
            shapePoints.add(perimeterPoint);
        }

        //Translations
        //R
        ArrayList<ShapePoint> tempPoints;
        tempPoints = new ArrayList<>();
        Vector translatedPoint;
        for (ShapePoint shapePoint : shape.getPerimeterPoints()) {
        }


    }

    public ShapePoint[] getRightTranslationNewPoints() {
        return rightTranslationNewPoints;
    }

    public ShapePoint[] getRightTranslationOldPoints() {
        return rightTranslationOldPoints;
    }

    public ShapePoint[] getLeftTranslationOldPoints() {
        return leftTranslationOldPoints;
    }

    public ShapePoint[] getLeftTranslationNewPoints() {
        return leftTranslationNewPoints;
    }

    public ShapePoint[] getUpTranslationNewPoints() {
        return upTranslationNewPoints;
    }

    public ShapePoint[] getUpTranslationOldPoints() {
        return upTranslationOldPoints;
    }

    public ShapePoint[] getDownTranslationNewPoints() {
        return downTranslationNewPoints;
    }

    public ShapePoint[] getDownTranslationOldPoints() {
        return downTranslationOldPoints;
    }
}
