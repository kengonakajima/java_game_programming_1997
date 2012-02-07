import java.applet.Applet;
import java.io.*;
import java.net.*;
import java.awt.*;

// UNIX$B$G$O!"(Bappletviewer$B$N(Bproperties$B$G!"(Bnetwork: Unrestricted$B$K$9$l$P(B
// server.class$B!"(Bclient.class$B$H$bLdBj$J$/F0$/!#(B
// windows95$B$G$O!"(B10000$B$H$$$&%]!<%H$r(Blisten$B$7$h$&$H$7$?$H$-$K(B
// SecurityException $B$,=P$k!#(B Windows $B$,%/%i%$%"%s%H$G(B UNIX$B$,%5!<%P!<$J$i2DG=!#(B
//
public class server extends Applet implements Runnable
{
	ServerSocket ss;
	Socket so = null;
	OutputStream out;
	InputStream in;
	
	Thread t;
	int port = 10000;

	int x , y;	// $B;M3Q$N0LCV(B

	public void init()
	{
		port = Integer.parseInt(getParameter( "port") );
		x = size().width/2;		// $B;M3Q$N:G=i$N0LCV$O??Cf(B
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
	void shikakumove( int dx, int dy )	// $B;M3Q$rF0$+$9%a%=%C%I(B
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
				
				if( in.available()>0 ){  // $B$b$7$b!"FI$_$3$`$3$H$,$G$-$l$P(Bread$B$9$k(B
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
