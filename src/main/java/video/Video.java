package video;

import javax.swing.*;

public class Video {
    private String pathOrigVideo;
    private static JFrame jFrame;
    protected static JProgressBar progressBar = new JProgressBar();



    public Video(boolean progressBar) {
        if (progressBar)
            openProgressBar();
    }

    public Video(String pathOrigVideo, boolean progressBar) {
        this.pathOrigVideo = pathOrigVideo;

        if (progressBar)
            openProgressBar();
    }


    private static void openProgressBar() {
        jFrame = new JFrame("loading...");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setSize(900,200);

        jFrame.add(progressBar);
    }

    public static void closeProgressBar() {
        jFrame.dispose();
    }

    public String getPathOrigVideo() {
        return pathOrigVideo;
    }

    public void setPathOrigVideo(String pathOrigVideo) {
        this.pathOrigVideo = pathOrigVideo;
    }
}
