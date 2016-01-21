import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.IOException;

public class processFrame {
    static sobel sobelObject;
    static nonmax nonMaxSuppressionObject;
    static circleHough circleHoughObject;
    static hystThresh hystThreshObject;

    public static Image processFrame(Image image, int radius) throws IOException {
        sobelObject = new sobel();
        nonMaxSuppressionObject = new nonmax();
        hystThreshObject = new hystThresh();
        circleHoughObject = new circleHough();

        ImageObserver io = new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        };

        int height = image.getHeight(io);
        int width = image.getWidth(io);

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

        ImageProducer producer = new MemoryImageSource(image.getWidth(io), image.getHeight(io), overlayImage(orig, image, image.getHeight(io), image.getWidth(io)), 0, image.getWidth(io));
        Image result = Toolkit.getDefaultToolkit().createImage(producer);

        return result;
    }

    public static int[] overlayImage(int[] input, Image image, int height, int width) {

        int[] myImage = new int[width * height];

        PixelGrabber grabber = new PixelGrabber(image, 0, 0, width, height, myImage, 0, width);
        try {
            grabber.grabPixels();
        } catch (InterruptedException e2) {
            System.out.println("error: " + e2);
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if ((input[y * width + x] & 0xff) > 0)
                    myImage[y * width + x] = 0x00ff0000;
            }
        }

        return myImage;
    }

}