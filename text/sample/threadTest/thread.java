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
		// 数字を文字に変換するには、Integerクラスを使います。
		g.drawString( Integer.toString( i ) , 100 , 100 );
	}
	int i=0;

	public void run()
	{
		while(true){       // ずっと続ける場合は、ループさせる必要があります。
			i++;
			try{
				Thread.sleep(100 );
			}catch( InterruptedException e) {}
			repaint();
		}
	}
}
