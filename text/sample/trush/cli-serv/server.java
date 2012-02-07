import java.applet.Applet;
import java.io.*;
import java.net.*;
import java.awt.*;

// UNIXでは、appletviewerのpropertiesで、network: Unrestrictedにすれば
// server.class、client.classとも問題なく動く。
// windows95では、10000というポートをlistenしようとしたときに
// SecurityException が出る。 Windows がクライアントで UNIXがサーバーなら可能。
//
public class server extends Applet implements Runnable
{
	ServerSocket ss;
	Socket so = null;
	OutputStream out;
	InputStream in;
	
	Thread t;
	int port = 10000;

	int x , y;	// 四角の位置

	public void init()
	{
		port = Integer.parseInt(getParameter( "port") );
		x = size().width/2;		// 四角の最初の位置は真中
		y = size().height/2;

		try{
			ss = new ServerSocket( port );
			so = ss.accept();
			System.out.println( so.toString() );
			
			out = so.getOutputStream();
			in = so.getInputStream();
			
		} catch ( IOException e ){
			System.out.println("IOEx!");
		}
		t = new Thread( this );
		t.start();
	}
	public void paint( Graphics g )
	{
		g.fillRect( x ,y , 10 ,10 );
	}
	void shikakumove( int dx, int dy )	// 四角を動かすメソッド
	{
		x += dx;
		y += dy;
	}
	public boolean keyDown( Event ev , int c )
	{
		try{
			out.write( c);
		}catch( IOException e ){}
		return true;
	}
	public void run()
	{
		int c;
		while( true && ( so != null )){
			c = 0;
			try{
				Thread.sleep(100);
			}catch( InterruptedException e ){ }
			try{
				
				if( in.available()>0 ){  // もしも、読みこむことができればreadする
					c = in.read();
					//System.out.println( (char)c);
				}
			}catch( IOException e ){}
			switch( c )
			{
				case 'h': shikakumove( -10 , 0 );break;
				case 'j': shikakumove( 0 , 10 );break;
				case 'k': shikakumove( 0 , -10 );break;
				case 'l': shikakumove( 10 , 0);break;
				case 'q': end(); break;
			}
			repaint();
		}
	}
	void end()
	{
		try {
			so.close();
			so = null;
		} catch( IOException e ){}
	}
		
}
