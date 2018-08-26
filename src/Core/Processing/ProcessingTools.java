package Core.Processing;

import javafx.scene.chart.BubbleChart;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.awt.image.BufferedImage;

public class ProcessingTools {


    public static PImage zoom(int x, int y, int w, int h, int zoom, PApplet context) {

        PImage area = context.get(x, y, w, h);
        area.resize(area.width * zoom, area.height * zoom);
        return area;
    }

    public static PImage createPImage(BufferedImage image, PApplet context) {
        PImage pImage = context.createImage(image.getWidth(), image.getHeight(), PConstants.RGB);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                pImage.set(x, y, image.getRGB(x, y));
            }
        }
        return pImage;
    }

}
