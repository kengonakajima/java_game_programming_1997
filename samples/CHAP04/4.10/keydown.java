import java.awt.*;
public class keydown extends java.applet.Applet
{
    public boolean keyDown( Event e , int key ){
        System.out.println( "keydown" + key );
        return true;
    }
    public boolean keyUp( Event e , int key ){
        System.out.println( "keyup" + key );
        return true;
    }
}
