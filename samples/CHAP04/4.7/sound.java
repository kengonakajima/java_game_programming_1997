import java.applet.*;
import java.awt.*;

public class sound extends Applet
{
    AudioClip a , music;

    public void init()
    {
        a = getAudioClip( getDocumentBase() , "hi.au" );
        music = getAudioClip( getDocumentBase() , "spacemusic.au" );
        music.loop();   // 繰り返して鳴らし始める。
    }
    public boolean mouseDown( Event e , int x , int y )
    {
        a.play();       // 鳴らす。
        return true;
    }
    public boolean keyDown( Event e , int c )
    {
        a.stop();       // 止める。
        return true;
    }
}