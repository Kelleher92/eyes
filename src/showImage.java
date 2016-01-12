import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

/**
 * Created by temp2015 on 1/11/2016.
 */
public class showImage {
    public static void showImage(int[] filter, Image image) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame editorFrame = new JFrame("Image Demo");
                editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                ImageObserver io = new ImageObserver() {
                    @Override
                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                        return false;
                    }
                };
                ImageProducer producer = new MemoryImageSource(image.getWidth(io), image.getHeight(io), overlayImage(filter, image, image.getHeight(io), image.getWidth(io)), 0, image.getWidth(io));
                Image result = Toolkit.getDefaultToolkit().createImage(producer);

                ImageIcon imageIcon = new ImageIcon(result);
                JLabel jLabel = new JLabel();
                jLabel.setIcon(imageIcon);
                editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

                editorFrame.pack();
                editorFrame.setLocationRelativeTo(null);
                editorFrame.setVisible(true);
            }
        });
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
                    myImage[y * width + x] = 0xffff0000;
            }
        }

        return myImage;
    }
}
