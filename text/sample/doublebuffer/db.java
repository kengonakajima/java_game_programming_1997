import java.applet.Applet;
import java.awt.*;

//常に動くようなアプレットは、Runnableをつけることが必要です。
public class db extends Applet implements Runnable
{
	Thread t;	
	public void init()
	{
		t = new Thread(this );
		t.start();
	}
	public void paint(Graphics g )
	{
	}

	public void run()
	{
		while( true )
		{
			try{
				Thread.sleep( 200 );
			}catch( InterrputedException e ) {}
			repaint();
		}

	}


}
