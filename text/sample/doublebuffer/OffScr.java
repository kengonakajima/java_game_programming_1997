import java.applet.Applet;
import java.awt.*;
public class OffScr extends Applet implements Runnable
{
        Thread t;
        int x = 0;
        Image ofscr;            // $B2>A[2hLL(B
        Graphics ofscr_g;       // $B2>A[2hLL$rA`:n$9$k$?$a$N(BGraphics

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
                // $B2?$b<j$r2C$($J$$$H!"$3$3$K>C5n$9$k%W%m%0%i%`$,4^$^$l$k$N$G$9!#(B
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
