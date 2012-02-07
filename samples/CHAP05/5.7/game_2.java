import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class game_2 extends java.applet.Applet implements Runnable
{
    Thread thread;
    Image double_buffer;
    Graphics dg;

    int cron=0;

    int width , height;
    int interval = 100;    // �~���b 
    moshin mos;    

    public void init()
    {

        thread = new Thread(this);
        width = size().width;
        height = size().height;
        double_buffer = createImage( width , height );
        dg = double_buffer.getGraphics();
        mos = new moshin( width, height , 16 );
        thread.start();
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
            } catch( InterruptedException e ){}
            doIt();
            repaint();
        }
    }
    double mouse_x , mouse_y;
    public boolean mouseMove( Event e , int x , int y )
    {
        // �}�E�X���ړ����邽�тɈʒu���L�^�B
        mouse_x = (double) x;
        mouse_y = (double) y;
        return true;
    }
    void doIt()
    {
        mos.Move(mouse_x , mouse_y);
        
        dg.setColor( Color.black );
        
        int x = (int)mos.getX();
        int y = (int)mos.getY();
        double r = mos.getTheta();

        // �������킩��悤�ɐ��������B
        dg.drawOval( x-8 , y-8 , 16 , 16 );
        dg.drawLine( x , y , x + (int)( Math.cos( r )*20) , 
                     y+(int)(Math.sin(r)*20) );
    }
    public void destroy()
    {
        thread.stop();
    }
}
