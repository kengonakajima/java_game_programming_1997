import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class game extends Applet implements Runnable
{
	Thread thread;
	Image double_buffer;
	Graphics dg;
	int cron=0;

	// 変更可能個所

	int width ,height;
	int interval = 500;	// milli second 

	public void init()
	{
		thread = new Thread(this);
		width = size().width;
		height = size().height;
		double_buffer = createImage( width , height );
		dg = double_buffer.getGraphics();

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
	void doIt()
	{
		dg.drawString( Integer.toString( cron ) , 50 ,cron*10  );
		
	}
}


























