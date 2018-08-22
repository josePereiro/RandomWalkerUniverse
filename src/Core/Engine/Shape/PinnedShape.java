//package Core.Engine.Shape;
//
//import Core.Engine.OnIterationActionHandler.OnIterationActionHandler;
//import Core.Engine.PositionValueBoard.PositionValueBoard;
//import Core.Engine.Vector.Vector;
//
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//
///**
// * This class handle a single shape with no transformation.
// */
//public class PinnedShape {
//
//    public static void main(String... args) {
//
//        Builder builder = new Builder();
//        builder.addVertex(-10, -10).
//                addVertex(55, -15).
//                addVertex(10, 54).
//                addVertex(-100, 15);
//        PinnedShape shape = builder.build();
//        int i = 0;
//
//    }
//
//    //Vertexes
//    Vector[] vertexes;
//
//    //Points
//    ShapePoint[] perimeterPoints;
//
//    //
//
//    //GeometryFields
//    int maxSpinRadius;
//    int mediumSpinRadius;
//    int area;
//    int perimeter;
//
//    public PinnedShape() {
//
//        //Arrays
//        vertexes = new Vector[]{null};
//        perimeterPoints = new ShapePoint[]{null};
//    }
//
//    public static class Builder implements javafx.util.Builder<PinnedShape> {
//
//        //Vectors
//        private ArrayList<Vector> tempVertexes;
//
//        //Helper
//        PositionValueBoard helperBoard;
//
//        public Builder() {
//            tempVertexes = new ArrayList<>();
//        }
//
//        public Builder addVertex(int relativeX, int relativeY) {
//            tempVertexes.add(new Vector(relativeX, relativeY));
//            return this;
//        }
//
//        @Override
//        public PinnedShape build() {
//
//            translateVertexesToCenter();
//
//            PinnedShape shape = new PinnedShape();
//            setVertexes(shape);
//            setMaxSpinradius(shape);
//            buildHelperBoard(shape);
//            setPerimeter(shape);
//
//            return shape;
//        }
//
//        private void setMaxSpinradius(PinnedShape shape) {
//            shape.maxSpinRadius = calculateMaxSpinRadius(shape);
//        }
//
//        private void buildHelperBoard(PinnedShape shape) {
//            helperBoard = new PositionValueBoard(shape.maxSpinRadius * 2 + 2, shape.maxSpinRadius * 2 + 2);
//        }
//
//        private void setPerimeter(PinnedShape shape) {
//
//            final ArrayList<ShapePoint> perimeterPoints = new ArrayList<>();
//
//            OnIterationActionHandler<Vector> setPerimeterShapePointsActionHandler =
//                    new OnIterationActionHandler<Vector>() {
//                        @Override
//                        public void action(int x, int y, PositionValueBoard board) {
//                            ShapePoint sp = new ShapePoint(x, y);
//                            sp.closerPerimeterPoint = sp;
//                            sp.distanceToCloserPerimeterPoint = 0;
//                            sp.distanceToCenter = Vector.getMagnitude(x, y);
//                            sp.closerPerimeterPointNormal = getExtraData();
//
//                            //TODO Deb
//                            if (board.isWithinBoard(x + board.getW() / 2, y + board.getH() / 2)) {
//                                board.setValue(5, x + board.getW() / 2, y + board.getH() / 2);
//                            }
//                            if (board.isWithinBoard(x + board.getW() / 2 + getExtraData().x,
//                                    y + board.getH() / 2 + getExtraData().y)) {
//                                board.setValue(5, x + board.getW() / 2 + getExtraData().x,
//                                        y + board.getH() / 2 + getExtraData().y);
//                            }
//
//                            perimeterPoints.add(sp);
//                        }
//                    };
//
//            Vector v1, v2;
//            for (int v = 0; v + 1 < shape.vertexes.length; v++) {
//                v1 = shape.vertexes[v];
//                v2 = shape.vertexes[v + 1];
//                setPerimeterShapePointsActionHandler.
//                        setExtraData(Vector.getNormalVector(new Vector(v1.x - v2.x, v1.y - v2.y),
//                                ShapePoint.DEFAULT_NORMAL_VECTOR_MAGNITUDE));
//                helperBoard.iterateLine(v1.x, v1.y, v2.x, v2.y, setPerimeterShapePointsActionHandler);
//                //deleting last points
//                perimeterPoints.remove(perimeterPoints.size() - 1);
//            }
//
//            v1 = shape.vertexes[shape.vertexes.length - 1];
//            v2 = shape.vertexes[0];
//            setPerimeterShapePointsActionHandler.
//                    setExtraData(Vector.getNormalVector(new Vector(v1.x - v2.x, v1.y - v2.y),
//                            ShapePoint.DEFAULT_NORMAL_VECTOR_MAGNITUDE));
//            helperBoard.iterateLine(v1.x, v1.y, v2.x, v2.y, setPerimeterShapePointsActionHandler);
//            //deleting last points
//            perimeterPoints.remove(perimeterPoints.size() - 1);
//
//            //TODO Deb
//            BufferedImage image = helperBoard.getImage();
//
//            //Setting perimeterPoints
//            shape.perimeterPoints = perimeterPoints.toArray(shape.perimeterPoints);
//
//        }
//
//        private void translateVertexesToCenter() {
//            Vector center = getCenter();
//            for (int v = 0; v < tempVertexes.size(); v++) {
//                tempVertexes.get(v).sub(center);
//            }
//        }
//
//        private Vector getCenter() {
//
//            int xSum = 0;
//            int ySum = 0;
//            for (int v = 0; v < tempVertexes.size(); v++) {
//                xSum += tempVertexes.get(v).x;
//                ySum += tempVertexes.get(v).y;
//            }
//
//            return new Vector(Math.round(xSum / tempVertexes.size()), Math.round(ySum / tempVertexes.size()));
//        }
//
//        private int calculateMaxSpinRadius(PinnedShape shape) {
//
//            int xMax = 0;
//            int yMax = 0;
//            int xAbs, yAbs;
//            for (int v = 0; v < shape.vertexes.length; v++) {
//                xAbs = Math.abs(shape.vertexes[v].x);
//                if (xMax < xAbs) {
//                    xMax = xAbs;
//                }
//                yAbs = Math.abs(shape.vertexes[v].y);
//                if (yMax < yAbs) {
//                    yMax = yAbs;
//                }
//            }
//
//            float mag = Vector.getMagnitude(xMax, yMax);
//            if (mag - (int) mag == 0.0f) {
//                return (int) mag;
//            } else {
//                return (int) mag + 1;
//            }
//
//        }
//
//        private void setVertexes(PinnedShape shape) {
//            shape.vertexes = this.tempVertexes.toArray(shape.vertexes);
//            tempVertexes = null;
//        }
//
//    }
//
//}
