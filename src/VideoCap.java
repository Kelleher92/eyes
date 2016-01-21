import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class VideoCap {
    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println(Core.NATIVE_LIBRARY_NAME);

        VideoCapture video = new VideoCapture("eyes.MP4");

        final boolean result = video.open("eyes.MP4");
        System.out.println(result);

        Mat frame = new Mat();
        video.read(frame);
        System.out.println("Captured Frame Width " + frame.width() + " Height " + frame.height());

        video.release();
    }
}   