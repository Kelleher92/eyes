import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class processVideo {
    // radius to look for and number of matches to display
    static int radius = 25;

    public static void main(String args[]) throws IOException {
        Image image = ImageIO.read(new File("frame2.png"));
        Image output = processFrame.processFrame(image, radius);

        BufferedImage buffered = toBufferedImage(output);

        showImage.showImage(buffered);

        Boolean flag = ImageIO.write(buffered, "png", new File("output.png"));
        System.out.println(flag);

        System.out.println(System.getProperty("java.library.path"));
    }

    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
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