import java.applet.Applet;
import java.awt.*;
public class OffScr extends Applet implements Runnable
{
        Thread t;
        int x = 0;
        Image ofscr;            // 仮想画面
        Graphics ofscr_g;       // 仮想画面を操作するためのGraphics

        public void init()
        {
                t = new Thread( this );
                t.start();
                ofscr = createImage( 300,300);
                ofscr_g = ofscr.getGraphics();
        }
        public void paint( Graphics g )
        {
		ofscr_g.setColor( Color.white );
		ofscr_g.fillRect( 0 ,0 , 300,300);
		ofscr_g.setColor( Color.black );
                ofscr_g.fillRect( x,50,200,200);
                g.drawImage( ofscr , 0 , 0 , null );
        }
        public void update( Graphics g )
        {
                // 何も手を加えないと、ここに消去するプログラムが含まれるのです。
                paint( g );
        }
        public void run()
        {
                while(true){
                        x++;
                        try{
                                Thread.sleep(100 );
                        }catch( InterruptedException e ) {}
                        repaint();
                }
        }
}
