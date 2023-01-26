package video;

import org.bytedeco.javacv.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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



    public void create(String path, String format) throws IOException {
        BufferedImage image = ImageIO.read(new File(getPathOrigVideo()));

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(path, image.getWidth(), image.getHeight());
        recorder.setFrameRate(30);
        recorder.setFormat(format);
        recorder.start();

        progressBar.setMaximum(60);
        for (int i = 0; i != 60; i++) {
            Frame frame = new Java2DFrameConverter().convert(image);
            image = ImageIO.read(new File(getPathOrigVideo()));
            recorder.record(frame);
            progressBar.setValue(i);
        }

        Video.closeProgressBar();
        recorder.close();
    }
}
