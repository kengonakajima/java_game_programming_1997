import java.awt.*;
public class param extends java.applet.Applet
{
    int x,y;
    String text;

    public void init()
    {
        String s , t;

        s = getParameter( "yoko" );  // name=yoko‚É”½‰‚·‚éB
        t = getParameter( "tate" );  // name=tate
        x = Integer.parseInt( s );
        y = Integer.parseInt( t );

        text = getParameter( "text" );
    }
    public void paint( Graphics g ){
        g.drawString( text , x,y );
    }
}
