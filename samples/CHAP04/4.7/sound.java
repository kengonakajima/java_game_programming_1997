import java.applet.*;
import java.awt.*;

public class sound extends Applet
{
    AudioClip a , music;

    public void init()
    {
        a = getAudioClip( getDocumentBase() , "hi.au" );
        music = getAudioClip( getDocumentBase() , "spacemusic.au" );
        music.loop();   // �J��Ԃ��Ė炵�n�߂�B
    }
    public boolean mouseDown( Event e , int x , int y )
    {
        a.play();       // �炷�B
        return true;
    }
    public boolean keyDown( Event e , int c )
    {
        a.stop();       // �~�߂�B
        return true;
    }
}