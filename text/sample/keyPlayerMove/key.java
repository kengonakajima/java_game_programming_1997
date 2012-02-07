// $B%-!<A`:n$G%-%c%i%/%?$rF0$+$9%5%s%W%k!#%+!<%=%k%-!<$GA`:n$9$k$3$H$,$G$-$k!#(B
// $B%+!<%=%k%-!<$,$J$$%-!<%\!<%I$N$?$a$K!"B?>/2~B$$7$?$[$&$,$h$$!#(B

import java.applet.Applet;
import java.awt.*;

public class key extends Applet implements Runnable
{
        Image offscr;
        Graphics og;
        int x , y;      // $BF0$/;M3Q$N0LCV(B
	boolean down[] = new boolean [4]; // down[0]$B$,>e!"0J2<1&2s$j(B

        Thread t;        // $B%-%c%i$r7QB3E*$KF0$+$9$?$a$K%9%l%C%I$r;H$&!#(B
        final int UP = 1004; // $BDj?t$O$3$N$h$&$K:G=i$KDj5A$7$F$*$-$^$7$g$&!#(B
        final int DOWN = 1005; // int$B$NA0$K(Bfinal$B$r$D$1$k$H!"!VJQ99$G$-$J$$!W$H$$$&0UL#$K$J$j$^$9!#(B
        final int LEFT = 1006;  // $B$3$&$9$k$3$H$G!"%W%m%0%i%`$N0UL#$,$o$+$j$d$9$/$J$j$^$9!#(B
        final int RIGHT = 1007;

        public void init( ){
                offscr = createImage( size().width , size().height );
                og = offscr.getGraphics();
                og.setColor( Color.white );
                og.fillRect( 0 , 0 , size().width , size().height);

                x = size().width/2;  // $B:G=i$O??$sCf$K$$$k!#(B
                y = size().height/2;

		for(int i = 0 ; i < 4 ; i++) down[i] = false;

                t = new Thread( this );
                t.start();
		
		
        }
        public boolean keyDown( Event e , int key){
		System.out.println( key );
		if( key == UP ) down[0] = true;
		if( key == RIGHT ) down[1] = true;
		if( key == DOWN ) down[2] = true;
		if( key == LEFT ) down[3] = true;
		
		return true;
        }
        public boolean keyUp( Event e , int key ){
		System.out.println( key );
		if( key == UP ) down[0] = false;
		if( key == RIGHT ) down[1] = false;
		if( key == DOWN ) down[2] = false;
		if( key == LEFT ) down[3] = false;

		return true;
        }
        public void run()
        {
                while(true){
               
                        if( down[0]) y = y - 4;
                        if( down[1]) x = x + 4;
                        if( down[2] ) y = y + 4;
                        if( down[3] ) x = x - 4;

                        og.setColor( Color.white );
                        og.fillRect( 0 , 0, size().width , size().height);
                        og.setColor( Color.black );
                        og.fillRect( x,y,10,10);

			repaint();
			
			try{
                                Thread.sleep(70);
                        }catch( InterruptedException e ){}
                }
        }
	public void update( Graphics g){
		paint(g);
	}
	public void paint( Graphics g ){
		try {
			g.drawImage(offscr , 0 , 0 , this );
		} catch( NullPointerException e ){
			System.out.println("null paint");
		}
	}



}



