package video;

import org.bytedeco.javacv.*;

public class VideoConverter extends Video{
    public VideoConverter(String pathOrigVideo, boolean progressBar) {
        super(pathOrigVideo, progressBar);
    }

    /**
     * This method converts the video to the specified format<br>
     * FFmpeg Frame Grabber is used to get input data<br>
     * FFmpeg Frame Recorder is used to record output data<br><br>
     * It sets the frame rate, format, bitrate of audio, video and video codec similarly to the original<br><br>
     * provided that the original or conversion is not in "gif", "webm" format<br>
     * in this case, the video will be recorded with compatible parameters and there will be no audio
     *
     * @see Video
     * @see VideoConverter#convert(String, String, int[])
     */
    public void convert(String path, String format) throws FrameRecorder.Exception, FrameGrabber.Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(getPathOrigVideo());
        grabber.start();
        int[] size = {grabber.getImageWidth(), grabber.getImageHeight()};
        grabber.close();

        convert(path, format, size);
    }

    /**
     * This method converts the video to the specified format with the given size<br><br>
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
    public void convert(String path, String format, int[] x) throws FrameRecorder.Exception, FrameGrabber.Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(getPathOrigVideo());
        grabber.start();
        FrameRecorder recorder = new FFmpegFrameRecorder(path, x[0], x[1]);

        recorder.setFrameRate(grabber.getFrameRate());
        recorder.setFormat(format);

        if (! (format.contains("webm") || format.contains("gif") || getPathOrigVideo().contains("gif") ||
                getPathOrigVideo().contains("webm"))){

            recorder.setAudioBitrate(grabber.getAudioBitrate());
            recorder.setSampleFormat(grabber.getSampleFormat());
            recorder.setVideoBitrate(grabber.getVideoBitrate());
            recorder.setVideoCodec(grabber.getVideoCodec());

        }

        recorder.start();

        int i = 0;
        Frame frame;
        progressBar.setMaximum(grabber.getLengthInVideoFrames());
        boolean audio = grabber.hasAudio() && !(format.contains("webm")) || format.contains("WEBM") ||
                format.contains("gif") || getPathOrigVideo().contains("gif");

        while ( (frame = grabber.grabFrame(audio, true, true, false, true)) != null){
            progressBar.setValue(i++);
            recorder.record(frame);
        }
        Video.closeProgressBar();

        recorder.close();
        grabber.close();
    }

}
