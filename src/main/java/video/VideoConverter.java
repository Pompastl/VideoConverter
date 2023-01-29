package video;

import org.bytedeco.javacv.*;

public class VideoConverter extends Video{
    public VideoConverter(String pathOrigVideo, boolean progressBar) {
        super(pathOrigVideo, progressBar);
    }

    /**
     * This method redirects the <b>conversion to a suitable method</b><br>
     * The video is recorded in a <b>similar size</b> to the original
     *
     * @see Video
     * @see VideoConverter#convertDeleteSound(String, String, int[])
     */
    public void convert(String path, String format) throws FrameRecorder.Exception, FrameGrabber.Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(getPathOrigVideo());
        grabber.start();
        int[] size = {grabber.getImageWidth(), grabber.getImageHeight()};
        grabber.close();

        convert(path, format, size);
    }

    /**
     * This method redirects the <b>conversion to a suitable method</b><br>
     * The video is recorded in a <b>given size</b>
     *
     * @see Video
     * @see VideoConverter#convertDeleteSound(String, String, int[])
     */
    public void convert(String path, String format, int[] x) throws FrameRecorder.Exception, FrameGrabber.Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(getPathOrigVideo());
        grabber.start();
        setProgressMax(grabber.getLengthInVideoFrames());
        grabber.close();

        if (format.contains("gif") || format.contains("webm") || getPathOrigVideo().contains("gif")
                || getPathOrigVideo().contains("webm"))
            convertDeleteSound(path, format, x);
    }

    /**
     * This method converts the video to the specified format with the <b>given size</b><br><br>
     * setting video sizes
     * <pre>
     * <code>
     * public void exampleMethod(int[] x) {
     *          FrameRecorder recorder = new FFmpegFrameRecorder(path, x[0], x[1]);
     * }
     * </code>
     * </pre>
     * FFmpeg Frame Grabber is used to get input data<br>
     * FFmpeg Frame Recorder is used to record output data<br><br>
     * It sets the frame rate, video similarly to the original<br>
     * but <b>without audio</b>
     *
     * @see Video
     * @see VideoConverter#convert(String, String) 
     */
    public void convertDeleteSound(String path, String format, int[] x) throws FrameRecorder.Exception, FrameGrabber.Exception {
        path = path.contains(format) ? path : path + "." + format;
        
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(getPathOrigVideo());
        grabber.start();
        
        FrameRecorder recorder = new FFmpegFrameRecorder(path, x[0], x[1]);
        recorder.setFrameRate(grabber.getFrameRate());
        recorder.setFormat(format);
        recorder.start();

        int i = 0;
        Frame frame;

        while ( (frame = grabber.grabFrame(false, true, true, false, true)) != null){
            setProgress(i++);
            recorder.record(frame);
        }
        Video.closeProgressBar();

        recorder.close();
        grabber.close();
    }

}
