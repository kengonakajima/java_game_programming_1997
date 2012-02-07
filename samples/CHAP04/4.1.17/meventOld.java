import java.awt.*;

public class meventOld extends java.applet.Applet
{
    public boolean mouseDown( Event e , int x , int y )
    {
        getGraphics().drawOval( x,y,20,20 );
        return true;
    }
    public boolean mouseUp( Event e , int x , int y )
    {
        getGraphics().drawRect( x,y,20,20 );
        return true;
    }
}