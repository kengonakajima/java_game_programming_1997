import java.applet.Applet;
import java.awt.*;

public class thread extends Applet implements Runnable
{
	Thread th;

	public void init()
	{
		th = new Thread( this);
		th.start();
	}
	public void paint(Graphics g)
	{
		// $B?t;z$rJ8;z$KJQ49$9$k$K$O!"(BInteger$B%/%i%9$r;H$$$^$9!#(B
		g.drawString( Integer.toString( i ) , 100 , 100 );
	}
	int i=0;

	public void run()
	{
		while(true){       // $B$:$C$HB3$1$k>l9g$O!"%k!<%W$5$;$kI,MW$,$"$j$^$9!#(B
			i++;
			try{
				Thread.sleep(100 );
			}catch( InterruptedException e) {}
			repaint();
		}
	}
}
