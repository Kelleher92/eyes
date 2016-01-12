import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

public class processFrame {
    static sobel sobelObject;
    static nonmax nonMaxSuppressionObject;
    static circleHough circleHoughObject;
    static hystThresh hystThreshObject;


    public static void main(String args[]) throws IOException {
        sobelObject = new sobel();
        nonMaxSuppressionObject = new nonmax();
        hystThreshObject = new hystThresh();
        circleHoughObject = new circleHough();

        Image image = ImageIO.read(new File("frame2.png"));
        ImageObserver io = new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        };

        int height = image.getHeight(io);
        int width = image.getWidth(io);

        // radius to look for and number of matches to display
        int radius = 25;

        int[] orig = new int[width * height];

        // grabs pixels from 'image' and fills array 'orig'
        PixelGrabber grabber = new PixelGrabber(image, 0, 0, width, height, orig, 0, width);
        try {
            grabber.grabPixels();
        } catch (InterruptedException e2) {
            System.out.println("error: " + e2);
        }

        sobelObject.init(orig, width, height);
        orig = sobelObject.process();
        double direction[] = new double[width * height];
        direction = sobelObject.getDirection();

        nonMaxSuppressionObject.init(orig, direction, width, height);
        orig = nonMaxSuppressionObject.process();

        // values going in here as upper and lower hysteresis limits (80 & 100)
        hystThreshObject.init(orig, width, height, 80, 100);
        orig = hystThreshObject.process();

        System.out.println(width + " " + height + " " + radius);
        circleHoughObject.init(orig, width, height, radius);
        orig = circleHoughObject.process();

        showImage.showImage(orig, image);
    }

}