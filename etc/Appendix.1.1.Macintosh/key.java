import java.applet.Applet;
import java.awt.*;
public class key extends Applet
{
    public boolean keyDown( Event e , int c )
    {
        System.out.println( c );
        return true;
    }

}
