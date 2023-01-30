package video;

import javax.swing.*;
import java.io.File;

public class Video {
    public final static String DOWNLOAD_PATH = System.getProperty("user.home") + File.separator + "Downloads" + File.separator;
    public static String TEMP_PATH = "";
    private final String pathOrigVideo;
    private static JFrame jFrame;
    private static JProgressBar progressBar;

    public Video(String pathOrigVideo) {
        this.pathOrigVideo = pathOrigVideo;
    }

    /**
     * Input parameter: string indicating the path to the original video<br><br>
     * Boolean progressBar indicates: will the download progress be <b>graphically</b> displayed
     */
    public Video(String pathOrigVideo, boolean progressBar) {
        this.pathOrigVideo = pathOrigVideo;

        if (progressBar)
            openProgressBar(pathOrigVideo);
    }


    private static void openProgressBar(String text) {
        jFrame = new JFrame(text);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setSize(900,200);
        progressBar = new JProgressBar();

        jFrame.add(progressBar);
    }
    protected static void setProgress(int progress) {
        if (progressBar != null)
            progressBar.setValue(progress);
    }
    protected static void setProgressMax(int max) {
        if (progressBar != null)
            progressBar.setMaximum(max);
    }

    public static void closeProgressBar() {
        jFrame.dispose();
    }

    public String getPathOrigVideo() {
        return pathOrigVideo;
    }


    public static void setTempPath(String tempPath) {
        TEMP_PATH = tempPath;
    }
}
