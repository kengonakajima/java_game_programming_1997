import java.awt.*;
public class scope extends java.applet.Applet
{
    int i = 20;
    public void init()
    {
        int j = 20;
        System.out.println( "A:" + i );
    }
    public boolean keyDown( Event e , int c )
    {
        System.out.println( "B:" + i );
        System.out.println( "C:" + j );  // これはコンパイルできない。
        return true;
    }
}

