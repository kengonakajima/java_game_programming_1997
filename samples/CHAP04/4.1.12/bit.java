import java.awt.*;
public class bit extends java.applet.Applet
{
    public void init()
    {

        System.out.println( 1024 | 500 );
        System.out.println( 1024 & 500 );
        System.out.println( (-1) & 500 );
        System.out.println( 430 << 4 );
        System.out.println(  100 >> 1 );
        System.out.println(  -200 >> 1 );
        System.out.println(  -200 >>> 1 ); // ‚±‚ê‚Í‰½‚¾‚ë‚¤H
    }
}