import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

public class processFrame {
    static circleHough circleHoughObject;

    public static void main(String args[]) throws IOException {
        circleHoughObject = new circleHough();
        Image image = ImageIO.read(new File("shapes2.png"));
        ImageObserver io = new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        };

        int height = image.getHeight(io);
        int width = image.getWidth(io);

        int radius = 60;
        int lines = 2;

        int[] orig = new int[width * height];

        PixelGrabber grabber = new PixelGrabber(image, 0, 0, width, height, orig, 0, width);
        try {
            grabber.grabPixels();
        } catch (InterruptedException e2) {
            System.out.println("error: " + e2);
        }

        System.out.println(width + " " + height + " " + radius);
        circleHoughObject.init(orig, width, height, radius);
        circleHoughObject.setLines(lines);
        orig = circleHoughObject.process();

        showImage.showImage(orig, image);
    }

}