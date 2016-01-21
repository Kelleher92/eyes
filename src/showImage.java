import javax.swing.*;
import java.awt.*;

/**
 * Created by temp2015 on 1/11/2016.
 */
public class showImage {
    public static void showImage(Image image) {
        JFrame editorFrame = new JFrame("Image Demo");
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon(image);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

        editorFrame.pack();
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setVisible(true);

    }

}
