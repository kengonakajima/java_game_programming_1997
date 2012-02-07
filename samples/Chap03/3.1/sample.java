import java.awt.*;

public class sample extends java.applet.Applet
{
    int clickx = 0 , clicky = 0;

    public void paint( Graphics g  )
    {
        g.drawString( "Hello!" , clickx , clicky );
    }

    public boolean mouseDown( Event e , int x , int y )
    {
        repaint();
        return true;
    }
}