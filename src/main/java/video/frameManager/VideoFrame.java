package video.frameManager;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import video.Video;

import java.awt.image.BufferedImage;

public class VideoFrame extends Video {
    public VideoFrame(String pathOrigVideo, boolean progressBar) {
        super(pathOrigVideo, progressBar);
    }

    public VideoFrame(String pathOrigVideo) {
        super(pathOrigVideo);
    }

    public BufferedImage getImage(int min, int second) {
        return getImage((min * 60) + second);
    }

    public BufferedImage getImage(int second) {
        setProgressMax(2);

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(getPathOrigVideo());
        Frame frame;

        try {
            grabber.start();
            int frameNumber = (int) (grabber.getFrameRate() * second);

            setProgress(1);

            grabber.setFrameNumber(frameNumber);
            frame = grabber.grabImage();
            grabber.close();

            setProgress(2);
            Video.closeProgressBar();
        } catch (FrameGrabber.Exception e) {
            Video.closeProgressBar();
            throw new RuntimeException(e);
        }

        return new Java2DFrameConverter().convert(frame);
    }
}
