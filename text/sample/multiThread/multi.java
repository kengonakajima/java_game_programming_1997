import java.awt.*;

class counter implements Runnable
{
	Thread t;
	int val=0;
	int milisec;
	counter(int i)
	{
		t = new Thread( this );
		t.start();
		milisec = i;
	}
	public void run()
	{
		while(true)
		{
			val++;
			try{
				Thread.sleep( milisec );
			}catch( InterruptedException e ){}
		}
	}
	public int getVal(){ 
		return val;
	}
}
public class multi extends java.applet.Applet
{
	counter ca[] = new counter[10];

	public void init()
	{
		for(int i=0;i<10;i++){
			ca[i] = new counter(i*100+100);
		}
	}
	public void paint(Graphics g)
	{
		for( int i=0;i < 10 ;i++ ){
			g.drawString( "counter " + i + ":" + ca[i].getVal() ,
						 20 , 20 + i*20  );
		}
		try{      
			Thread.sleep(50);    // ここで50ミリ秒待つ。
		}catch( InterruptedException e ){}

		repaint();   // 描画が終わったらもういちど描画するための手抜きコード
	}
	public boolean mouseDown( Event e , int x , int y)
	{
		repaint();
		return true;
	}
}
