import marvin.image.MarvinImage;
import marvin.video.MarvinJavaCVAdapter;
import marvin.video.MarvinVideoInterface;
import marvin.video.MarvinVideoInterfaceException;
import org.opencv.core.Core;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MarvinVideo {
    static MarvinVideoInterface videoAdapter;
    static MarvinImage videoFrame;
    static Image frame;
    static int radius = 25;

    public static void main(String[] args) throws MarvinVideoInterfaceException {

        // Create the VideoAdapter used to load the video file
        videoAdapter = new MarvinJavaCVAdapter();
        videoAdapter.loadResource("eyes.mp4");

        try {
            while (true) {
                // Request a video frame
                videoFrame = videoAdapter.getFrame();
                frame = videoFrame.getBufferedImage();
                Image output = processFrame.processFrame(frame, radius);
                BufferedImage buffered = toBufferedImage(output);

                showImage.showImage(buffered);
            }
        } catch (MarvinVideoInterfaceException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
