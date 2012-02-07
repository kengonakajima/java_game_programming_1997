import java.awt.*;
public class getimage extends java.applet.Applet
{
    Image img;
    public void init()
    {
        img = getImage( getDocumentBase() , "kawai.jpg" );
    }
    public void paint( Graphics g )
    {
        g.drawImage( img , 30, 30, null );
    }

}
