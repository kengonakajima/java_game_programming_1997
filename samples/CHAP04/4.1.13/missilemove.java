import java.awt.*;

public class missilemove extends java.applet.Applet
{
    missile mis = new missile( 50,50 );

    public boolean mouseDown( Event e , int x , int y ){
        mis.moveOnce();
        repaint();
        return true;
    }
    public void paint( Graphics g ){
        mis.drawThis( g );
    }
}
