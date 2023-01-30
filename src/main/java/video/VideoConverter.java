package video;

import org.bytedeco.javacv.*;

public class VideoConverter extends Video{
    public VideoConverter(String pathOrigVideo, boolean progressBar) {
        super(pathOrigVideo, progressBar);
    }

    /**
     * This method converts the video to the specified format with the <b>size of the original</b><br>
     * FFmpeg Frame Grabber is used to get input data<br>
     * FFmpeg Frame Recorder is used to record output data<br><br>
     * It sets the frame rate, format, bitrate of audio, video and video codec similarly to the original<br><br>
     * provided that the original or conversion is not in "gif", "webm" format<br>
     * in this case, the video will be recorded with compatible parameters and there will be no audio
     *
     * @see Video
     * @see VideoConverter#converter(String, String, int[])
     */
    public void converter(String path, String format) throws FrameRecorder.Exception, FrameGrabber.Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(getPathOrigVideo());
        grabber.start();
        int[] size = {grabber.getImageWidth(), grabber.getImageHeight()};
        grabber.close();

        converter(path, format, size);
    }


    /**
     * This method converts the video to the specified format <b>with the given size</b><br><br>
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
     * It sets the frame rate, format, bitrate of audio, video and video codec similarly to the original<br><br>
     * provided that the original or conversion is not in "gif", "webm" format<br>
     * in this case, the video will be recorded with compatible parameters and there will be no audio
     *
     * @see Video
     */
    public void converter(String path, String format, int[] x) throws FrameRecorder.Exception, FrameGrabber.Exception {
        path = path.contains(format) ? path : path + "." + format;
        
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(getPathOrigVideo());
        grabber.start();
        boolean hasAudio = grabber.getAudioChannels() > 0;
        
        FrameRecorder recorder = new FFmpegFrameRecorder(path, x[0], x[1]);
        recorder.setFrameRate(grabber.getFrameRate());
        recorder.setFormat(format);

        if (hasAudio)
            recorder.setAudioChannels(grabber.getAudioChannels());

        recorder.start();
        int i = 0;
        Frame frame;

        while ( (frame = grabber.grabFrame(hasAudio, true, true, false, true)) != null){
            setProgress(i++);
            recorder.record(frame);
        }
        Video.closeProgressBar();

        recorder.close();
        grabber.close();
    }

}
