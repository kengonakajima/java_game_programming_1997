import java.awt.*;

public class tdlr extends java.applet.Applet implements Runnable
{
    Thread t;
    public void init(){
        t = new Thread( this );
        t.start();
    }
    public void update( Graphics g ){
        paint(g );
    }
    public void run()
    {
        for(int i=0;i<size().width;i++){
            for(int j=0;j<size().height;j++){
                getGraphics().fillRect( j , i  , 1, 1 );
                try{
                    Thread.sleep(10);
                }catch( InterruptedException e ){}
                repaint();
            }
        }
    }
}
