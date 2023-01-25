package video;

import org.bytedeco.javacv.*;

public class VideoConverter extends Video{
    public VideoConverter(String pathOrigVideo, boolean progressBar) {
        super(pathOrigVideo, progressBar);
    }

    public void convert(String path, String format) throws FrameRecorder.Exception, FrameGrabber.Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(getPathOrigVideo());
        grabber.start();
        int[] size = {grabber.getImageWidth(), grabber.getImageHeight()};
        grabber.close();

        convert(path, format, size);
    }

    public void convert(String path, String format, int[] x) throws FrameRecorder.Exception, FrameGrabber.Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(getPathOrigVideo());
        grabber.start();
        FrameRecorder recorder = new FFmpegFrameRecorder(path, x[0], x[1]);

        recorder.setFrameRate(grabber.getFrameRate());
        recorder.setFormat(format);

        if (! (format.contains("webm") || format.contains("WEBM"))){
            recorder.setAudioBitrate(grabber.getAudioBitrate());
            recorder.setSampleFormat(grabber.getSampleFormat());
            recorder.setVideoBitrate(grabber.getVideoBitrate());
            recorder.setVideoCodec(grabber.getVideoCodec());
        }

        recorder.start();

        int i = 0;
        Frame frame;
        progressBar.setMaximum(grabber.getLengthInVideoFrames());
        boolean audio = grabber.hasAudio();
        while ( (frame = grabber.grabFrame(audio, true, true, false, true)) != null){
            progressBar.setValue(i++);
            recorder.record(frame);
        }
        Video.closeProgressBar();

        recorder.close();
        grabber.close();

    }
}
