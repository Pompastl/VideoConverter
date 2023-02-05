# VideoConverter
this program uses the opencv 1.5.8 library

use case:
```java
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import video.VideoConverter;

public class Main {
    public static void main(String[] args) throws FrameRecorder.Exception, FrameGrabber.Exception {
        VideoConverter converter = new VideoConverter("/Users/pompat/Downloads/IMG_3941.MP4", false);
        converter.converter("dsa.mov", ".mov");
    }
}
```
