package video;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;

public class VideoFrame extends Video{
    public VideoFrame(String pathOrigVideo, boolean progressBar) {
        super(pathOrigVideo, progressBar);
    }

    public BufferedImage getImage(int min, int second) throws FrameGrabber.Exception {
        return getImage((min * 60) + second);
    }

    public BufferedImage getImage(int second) {

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(getPathOrigVideo());
        Frame frame;

        try {
            grabber.start();
            int frameNumber = (int) (grabber.getFrameRate() * second);


            grabber.setFrameNumber(frameNumber);
            frame = grabber.grabImage();
            grabber.close();

            Video.closeProgressBar();
        } catch (FrameGrabber.Exception e) {
            Video.closeProgressBar();
            throw new RuntimeException(e);
        }


        return new Java2DFrameConverter().convert(frame);
    }
}
