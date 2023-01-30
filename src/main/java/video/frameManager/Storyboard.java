package video.frameManager;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

import java.util.ArrayList;

public class Storyboard extends VideoFrame {
    public int codec = -1;
    public Storyboard(String pathOrigVideo) {
        super(pathOrigVideo);
    }

    /**
     * This method returns the frame corresponding to the given <b>second</b>
     * @see Storyboard#getFrames(int, int)
     */
    public Frame getFrame(int second) {


        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(getPathOrigVideo());
        Frame frame;
        try {
            grabber.start();
            int videoLength = (int) Math.floor(grabber.getFrameRate() * grabber.getLengthInVideoFrames());
            int frameNumber = (int) Math.floor(grabber.getFrameRate() * second);
            codec = grabber.getVideoCodec();

            if (second < 0 || second > videoLength) {
                throw new RuntimeException("Invalid parameters");
            }

            grabber.setFrameNumber(frameNumber);

            frame = grabber.grabFrame(false, true, true, false, true);
            grabber.close();
        } catch (FrameGrabber.Exception e) {
            throw new RuntimeException(e);
        }

        return frame;
    }

    /**
     * This method returns a frame ArrayList corresponding to the time <b>interval</b>
     * @see Storyboard#getFrame(int) 
     */
    public ArrayList<Frame> getFrames(int secondStart, int secondEnd) {

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(getPathOrigVideo());
        ArrayList<Frame> frames = new ArrayList<>();

        try {
            grabber.start();
            int videoLength = (int) Math.floor(grabber.getFrameRate() * grabber.getLengthInVideoFrames());

            if (secondStart == secondEnd || secondStart < 0 || secondEnd < 0 || secondStart > secondEnd
            || secondEnd > videoLength)
                throw new RuntimeException("Invalid parameters");


            for (; secondStart != secondEnd; secondStart++)
                frames.add(getFrame(secondStart));

            grabber.close();
        } catch (FrameGrabber.Exception e) {
            throw new RuntimeException(e);
        }


        return frames;
    }
}
