package test.NotJUnitTests.ProcessingTests;

import Core.World.World;
import processing.core.PApplet;

/**
 * Simple tools for visual debugging!!!
 */
public class ProcessingTools {

    public static class Drawers {

        public static final class WorldDrawer extends Drawer {

            public WorldDrawer(World world, PApplet context) {
                super(context);
            }

            @Override
            public void draw() {

            }
        }

        public abstract static class Drawer {

            int xoffset, yoffset;
            PApplet context;

            public Drawer(int xoffset, int yoffset, PApplet context) {
                this.xoffset = xoffset;
                this.yoffset = yoffset;
                this.context = context;
            }

            public Drawer(PApplet context) {
                this.context = context;
                xoffset = 0;
                yoffset = 0;
            }

            public abstract void draw();

            public PApplet getContext() {
                return context;
            }

            public int getXoffset() {
                return xoffset;
            }

            public void setXoffset(int xoffset) {
                this.xoffset = xoffset;
            }

            public int getYoffset() {
                return yoffset;
            }

            public void setYoffset(int yoffset) {
                this.yoffset = yoffset;
            }
        }

    }
}
