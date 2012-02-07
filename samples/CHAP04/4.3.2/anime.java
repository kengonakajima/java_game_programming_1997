import java.awt.*;
public class anime extends java.applet.Applet implements Runnable
{
    Thread t;
    int x = 0;
    public void init()
    {
        t = new Thread(this);
        t.start();
    }
    public void paint( Graphics g ){
        g.fillRect( x , 50, 200, 200 );
    }
    public void run()
    {
        while(true){
            x++;
            try{
                Thread.sleep( 100 );
            }catch( InterruptedException e ) {}
            repaint();
        }
    }
}
