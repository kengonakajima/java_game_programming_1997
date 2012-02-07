import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.applet.*;

public class game extends java.applet.Applet implements Runnable
{
    Thread thread;
    Image double_buffer;
    Graphics dg;

    int cron=0;
    int width,height; // 単位はピクセル
    int interval = 500 ; // ミリ秒

    // 変更可能箇所 
    public void init()
    {
        width = size().width;
        height = size().height;
        thread = new Thread(this);
        double_buffer = createImage( width , height );
        dg = double_buffer.getGraphics();
        thread.start();
        // 変更可能箇所
    }

    public void paint( Graphics g )
    {
        g.drawImage( double_buffer , 0 , 0 , null );
    }
    public void update( Graphics g )
    {
        paint( g );
    }
    public void run()
    {
        while( true )
        {
            cron++;

            try{
                Thread.sleep( interval );
            }catch( InterruptedException e ){}

            doIt();
            repaint();
        }
    }
    void doIt()
    {
        // 変更可能箇所
        // ここにプログラムを加えていってみよう。
        dg.drawString( Integer.toString( cron ), 50 , cron*10 );
    }
}