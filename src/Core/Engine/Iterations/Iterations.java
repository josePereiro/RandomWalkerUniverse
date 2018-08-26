package Core.Engine.Iterations;

import Core.Engine.PositionValueBoard.LabeledPositionsBoard;
import Core.Engine.Tools.Tools;
import Core.Engine.Vector.Vector;

public class Iterations {


    /**
     * This method will iterate through a circular area inside this board. In all the iterations
     * it will call the action method of the Iterations object. So, to do a certain action only implement this
     * method. The method will have a reference to the current x and y position of the iteration and a reference
     * to owner board. Enjoy it :) <br>
     * To abort the iteration set true the break_ variable of the Iterations object.
     *
     * @param centerX                  the center x position of the Area to iterate
     * @param centerY                  the center y position of the Area to iterate
     * @param radius                   the radius of the circle
     * @param onIterationActionHandler the iteration handler...
     */
    public static void iterateCircularArea(int centerX, int centerY, int radius,
                                           OnIterationActionHandler onIterationActionHandler) {

        //|x| = radius
        if (onIterationActionHandler.break_) return;
        onIterationActionHandler.action(centerX - radius, centerY);
        if (onIterationActionHandler.break_) return;
        onIterationActionHandler.action(centerX + radius, centerY);

        int y;
        for (int x = 1 - radius; x < radius; x++) {

            //Diameter
            if (onIterationActionHandler.break_) return;
            onIterationActionHandler.action(centerX + x, centerY);

            y = Tools.roundedSqrt(radius * radius - x * x);

            for (int i = 0; i < y; i++) {

                //Upper half
                if (onIterationActionHandler.break_) return;
                onIterationActionHandler.action(centerX - x, centerY - y + i);
                //Downer half
                if (onIterationActionHandler.break_) return;
                onIterationActionHandler.action(centerX - x, centerY + y - i);
            }

        }
    }

    /**
     * This method will assume that the vertexes are sorted in clock direction. Otherwise wire
     * things might occur :). In the extraOne object we will pass the vertexes that limit the current
     * segment.
     *
     * @param vertexes
     * @param onIterationActionHandler
     * @param <eT2>
     * @param <eT3>
     */
    public static <eT2, eT3> void iteratePolygonPerimeter(Vector[] vertexes, OnIterationActionHandler<Vector[], eT2, eT3>
            onIterationActionHandler) {

        OnIterationActionHandler<Vector[], eT2, eT3> myHandler = new OnIterationActionHandler<Vector[], eT2, eT3>() {
            @Override
            public void action(int x, int y) {

                //Skipping last points
                if (x == onIterationActionHandler.getExtraOne()[1].x &&
                        y == onIterationActionHandler.getExtraOne()[1].y) return;
                onIterationActionHandler.action(x, y);
            }
        };

        Vector[] currentVertexes = new Vector[2];
        int vPlus1;
        for (int v = 0; v + 1 < vertexes.length; v++) {
            vPlus1 = v + 1;
            currentVertexes[0] = vertexes[v];
            currentVertexes[1] = vertexes[vPlus1];
            onIterationActionHandler.setExtraOne(currentVertexes);
            iterateLine(vertexes[v].x, vertexes[v].y, vertexes[vPlus1].x, vertexes[vPlus1].y, myHandler);
        }

        //Last segment
        currentVertexes[0] = vertexes[vertexes.length - 1];
        currentVertexes[1] = vertexes[0];
        onIterationActionHandler.setExtraOne(currentVertexes);
        iterateLine(currentVertexes[0].x, currentVertexes[0].y, currentVertexes[1].x, currentVertexes[1].y, myHandler);

    }

    /**
     * This method will iterate through a square area inside this board. In all the iterations
     * it will call the action method of the Iterations object. So, to do a certain action only implement this
     * method. The method will have a reference to the current x and y position of the iteration and a reference
     * to owner board. Enjoy it :) <br>
     * To abort the iteration set true the break_ variable of the Iterations object.
     *
     * @param centerX                  the center x position of the Area to iterate
     * @param centerY                  the center y position of the Area to iterate
     * @param size                     the size of the square
     * @param onIterationActionHandler the iteration handler...
     */
    public static void iterateSquareArea(int centerX, int centerY, int size,
                                         OnIterationActionHandler onIterationActionHandler) {

        int boxels = size * 2 + 1;
        int cornerX = centerX - size;
        int cornerY = centerY - size;

        for (int x = 0; x < boxels; x++) {
            for (int y = 0; y < boxels; y++) {

                if (onIterationActionHandler.break_) return;
                onIterationActionHandler.action(cornerX + x, cornerY + y);

            }
        }

    }

    /**
     * This method will iterate through a rectangular area inside this board. In all the iterations
     * it will call the action method of the Iterations object. So, to do a certain action only implement this
     * method. The method will have a reference to the current x and y position of the iteration and a reference
     * to owner board. Enjoy it :) <br>
     * To abort the iteration set true the break_ variable of the Iterations object.
     *
     * @param centerX                  the center x position of the Area to iterate
     * @param centerY                  the center y position of the Area to iterate
     * @param sizeX                    the size of the Area
     * @param sizeY                    the size of the Area
     * @param onIterationActionHandler the iteration handler...
     */
    public static void iterateRectangularArea(int centerX, int centerY, int sizeX, int sizeY,
                                              OnIterationActionHandler onIterationActionHandler) {

        int lineV = sizeY * 2 + 1;
        int lineH = sizeX * 2 + 1;
        int cornerX = centerX - sizeX;
        int cornerY = centerY - sizeY;

        for (int x = 0; x < lineH; x++) {
            for (int y = 0; y < lineV; y++) {

                if (onIterationActionHandler.break_) return;
                onIterationActionHandler.action(cornerX + x, cornerY + y);

            }
        }

    }

    /**
     * This method will iterate through a circular perimeter inside this board. In all the iterations
     * it will call the action method of the Iterations object. So, to do a certain action only implement this
     * method. The method will have a reference to the current x and y position of the iteration and a reference
     * to owner board. Enjoy it :) <br>
     * To abort the iteration set true the break_ variable of the Iterations object.
     *
     * @param centerX                  the center x position of the Area to iterate
     * @param centerY                  the center y position of the Area to iterate
     * @param radius                   the radius of the circle
     * @param onIterationActionHandler the iteration handler...
     */
    public static void iterateCircularPerimeter(int centerX, int centerY, int radius,
                                                OnIterationActionHandler onIterationActionHandler) {
        //|x| = radius
        if (onIterationActionHandler.break_) return;
        onIterationActionHandler.action(centerX - radius, centerY);
        if (onIterationActionHandler.break_) return;
        onIterationActionHandler.action(centerX + radius, centerY);

        int y, lastY = 0, dy;
        for (int x = radius - 1; x >= 0; x--) {

            y = Tools.roundedSqrt(radius * radius - x * x);
            dy = Math.abs(lastY - y);

            if (onIterationActionHandler.break_) return;
            onIterationActionHandler.action(centerX + x, centerY - y);
            if (onIterationActionHandler.break_) return;
            onIterationActionHandler.action(centerX + x, centerY + y);
            if (onIterationActionHandler.break_) return;
            onIterationActionHandler.action(centerX - x, centerY - y);
            if (onIterationActionHandler.break_) return;
            onIterationActionHandler.action(centerX - x, centerY + y);

            for (int i = 1; i < dy; i++) {

                if (onIterationActionHandler.break_) return;
                onIterationActionHandler.action(centerX + x, centerY - y + i);
                if (onIterationActionHandler.break_) return;
                onIterationActionHandler.action(centerX + x, centerY + y - i);
                if (onIterationActionHandler.break_) return;
                onIterationActionHandler.action(centerX - x, centerY - y + i);
                if (onIterationActionHandler.break_) return;
                onIterationActionHandler.action(centerX - x, centerY + y - i);

            }
            lastY = y;

        }
    }

    /**
     * This method will iterate through a square perimeter inside this board. In all the iterations
     * it will call the action method of the Iterations object. So, to do a certain action only implement this
     * method. The method will have a reference to the current x and y position of the iteration and a reference
     * to owner board. Enjoy it :) <br>
     * To abort the iteration set true the break_ variable of the Iterations object.
     *
     * @param centerX                  the center x position of the Area to iterate
     * @param centerY                  the center y position of the Area to iterate
     * @param size                     the size of the square
     * @param onIterationActionHandler the iteration handler...
     */
    public static void iterateSquarePerimeter(int centerX, int centerY, int size,
                                              OnIterationActionHandler onIterationActionHandler) {
        //Margins
        int lm = centerX - size;
        int rm = centerX + size;
        int um = centerY - size;
        int dm = centerY + size;

        //Corners
        if (onIterationActionHandler.break_) return;
        onIterationActionHandler.action(lm, um);
        if (onIterationActionHandler.break_) return;
        onIterationActionHandler.action(rm, um);
        if (onIterationActionHandler.break_) return;
        onIterationActionHandler.action(lm, dm);
        if (onIterationActionHandler.break_) return;
        onIterationActionHandler.action(rm, dm);

        for (int i = 1; i < size * 2; i++) {

            if (onIterationActionHandler.break_) return;
            onIterationActionHandler.action(lm + i, um);
            if (onIterationActionHandler.break_) return;
            onIterationActionHandler.action(lm, um + i);
            if (onIterationActionHandler.break_) return;
            onIterationActionHandler.action(rm, um + i);
            if (onIterationActionHandler.break_) return;
            onIterationActionHandler.action(lm + i, dm);

        }

    }

    /**
     * This method will iterate through a rectangular perimeter inside this board. In all the iterations
     * it will call the action method of the Iterations object. So, to do a certain action only implement this
     * method. The method will have a reference to the current x and y position of the iteration and a reference
     * to owner board. Enjoy it :) <br>
     * To abort the iteration set true the break_ variable of the Iterations object.
     *
     * @param centerX                  the center x position of the Area to iterate
     * @param centerY                  the center y position of the Area to iterate
     * @param sizeX                    the size of the Area
     * @param sizeY                    the size of the Area
     * @param onIterationActionHandler the iteration handler...
     */
    public static void iterateRectangularPerimeter(int centerX, int centerY, int sizeX, int sizeY,
                                                   OnIterationActionHandler onIterationActionHandler) {

        //Borders
        int ub = centerY - sizeY;
        int db = centerY + sizeY;
        int lb = centerX - sizeX;
        int rb = centerX + sizeX;


        //Upper line
        for (int x = 0; x < sizeX * 2 + 1; x++) {
            if (onIterationActionHandler.break_) return;
            onIterationActionHandler.action(lb + x, ub);

        }

        //Sides lines
        for (int y = 1; y < sizeY * 2; y++) {
            if (onIterationActionHandler.break_) return;
            onIterationActionHandler.action(lb, ub + y);
            if (onIterationActionHandler.break_) return;
            onIterationActionHandler.action(rb, ub + y);

        }

        //Downer line
        for (int x = 0; x < sizeX * 2 + 1; x++) {
            if (onIterationActionHandler.break_) return;
            onIterationActionHandler.action(lb + x, db);

        }
    }

    /**
     * The method ensure that the iteration occur from (x1,y1) to (x2,y2). So, the first iteration
     * coordinates are (x1,y1) and the last iteration coordinates are (x2,y2). Did you get it!!!
     *
     * @param x1                       start x
     * @param y1                       start y
     * @param x2                       final x
     * @param y2                       final y
     * @param onIterationActionHandler the handler
     */
    public static void iterateLine(int x1, int y1, int x2, int y2, OnIterationActionHandler onIterationActionHandler) {

        //v = mv' + c
        float m;
        float c;

        if (Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
            //thought Xs

            m = ((float) (y1 - y2)) / (x1 - x2);
            c = y1 - m * x1;
            float y;


            if (x1 < x2) {

                if (m == 0)
                    for (int x = x1; x <= x2; x++) {
                        //Action
                        if (onIterationActionHandler.break_) return;
                        onIterationActionHandler.action(x, y1);
                    }
                else
                    for (int x = x1; x <= x2; x++) {
                        y = m * x + c;
                        //Action
                        if (onIterationActionHandler.break_) return;
                        onIterationActionHandler.action(x, Math.round(y));

                    }

            } else if (x1 > x2) {

                if (m == 0)
                    for (int x = x1; x >= x2; x--) {
                        //Action
                        if (onIterationActionHandler.break_) return;
                        onIterationActionHandler.action(x, y1);
                    }
                else
                    for (int x = x1; x >= x2; x--) {
                        y = m * x + c;
                        //Action
                        if (onIterationActionHandler.break_) return;
                        onIterationActionHandler.action(x, Math.round(y));
                    }

            }

        } else {

            //thought Ys
            m = ((float) (x1 - x2)) / (y1 - y2);
            c = x1 - m * y1;
            float x;

            if (y1 < y2) {

                if (m == 0)
                    for (int y = y1; y <= y2; y++) {
                        //Action
                        if (onIterationActionHandler.break_) return;
                        onIterationActionHandler.action(x1, y);
                    }
                else
                    for (int y = y1; y <= y2; y++) {
                        x = m * y + c;
                        //Action
                        if (onIterationActionHandler.break_) return;
                        onIterationActionHandler.action(Math.round(x), y);

                    }

            } else if (y1 > y2) {

                if (m == 0)
                    for (int y = y1; y >= y2; y--) {
                        //Action
                        if (onIterationActionHandler.break_) return;
                        onIterationActionHandler.action(x1, y);
                    }
                else
                    for (int y = y1; y >= y2; y--) {
                        x = m * y + c;
                        //Action
                        if (onIterationActionHandler.break_) return;
                        onIterationActionHandler.action(Math.round(x), y);
                    }

            } else {
                //Action
                if (onIterationActionHandler.break_) return;
                onIterationActionHandler.action(x1, y1);
            }

        }


    }

    /**
     * Iterate thought a continue area labeled with a given targetLabel. Beside the custom action the
     * method needs to mark an already iterated point with a label, markLabel is for that.
     *
     * @param board                    the board where the area is located
     * @param targetLabel              the label of the interest area
     * @param markLabel                the label for marking checked points
     * @param startX                   the x where the iteration will start
     * @param startX                   the y where the iteration will start
     * @param onIterationActionHandler the iterationHandler
     */
    public static void iterateContinueArea(LabeledPositionsBoard board, int targetLabel, int markLabel, int startX,
                                           int startY, OnIterationActionHandler onIterationActionHandler) {
        //Check Point
        if (onIterationActionHandler.break_) return;
        if (board.isWithinBoard(startX, startY) &&
                markLabel != board.getLabelAt(startX, startY) &&
                targetLabel == board.getLabelAt(startX, startY)) {
            board.setLabel(markLabel, startX, startY);
            onIterationActionHandler.action(startX, startY);
        } else return;

        //Go for Neighbors
        iterateContinueArea(board, targetLabel, markLabel, startX + 1, startY, onIterationActionHandler);
        iterateContinueArea(board, targetLabel, markLabel, startX - 1, startY, onIterationActionHandler);
        iterateContinueArea(board, targetLabel, markLabel, startX, startY + 1, onIterationActionHandler);
        iterateContinueArea(board, targetLabel, markLabel, startX, startY - 1, onIterationActionHandler);
    }

    public static void iterate4Neighbors(int x, int y, OnIterationActionHandler onIterationActionHandler) {

        if (onIterationActionHandler.break_) return;
        onIterationActionHandler.action(x, y + 1);

        if (onIterationActionHandler.break_) return;
        onIterationActionHandler.action(x, y - 1);

        if (onIterationActionHandler.break_) return;
        onIterationActionHandler.action(x + 1, y);

        if (onIterationActionHandler.break_) return;
        onIterationActionHandler.action(x - 1, y);
    }

}
