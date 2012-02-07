import java.applet.Applet;
import java.io.*;
import java.net.*;
import java.awt.*;

// UNIX$B$G$O!"(Bappletviewer$B$N(Bproperties$B$G!"(Bnetwork: Unrestricted$B$K$9$l$P(B
// server.class$B!"(Bclient.class$B$H$bLdBj$J$/F0$/!#(B
// windows95$B$G$O!"(B10000$B$H$$$&%]!<%H$r(Blisten$B$7$h$&$H$7$?$H$-$K(B
// SecurityException $B$,=P$k!#(B Windows $B$,%/%i%$%"%s%H$G(B UNIX$B$,%5!<%P!<$J$i2DG=!#(B
//

// $B$=$l$>$l$N%W%l%$%d!<$,%-!<A`:n$7$?$i!"$=$N>pJs$r<u$1$H$C$FI=<(0LCV$rJQ99$9$k$H$$$&$b$N!#(B
// $BF14|$5$;$J$$!#(B
//
public class server extends Applet implements Runnable
{
	ServerSocket ss;
	Socket so = null;
	OutputStream out;
	InputStream in;
	
	Thread t;
	int port = 10000;
	String host;
	boolean server_mode = false;
	Player p[] = new Player[8];	// $B%W%l%$%d!<$r(B8$B?MJ,4IM}(B
	int player_num=0;  // $B%W%l%$%d!<$,2??M$$$k$+(B
	TextField hostfield , portfield;
	Checkbox servercheckbox;
	Button startbutton;

	public void init()
	{
		setLayout( new GridLayout( 4 , 1 ));

		startbutton = new Button("Start");
		hostfield = new TextField("server host");
		portfield = new TextField("server port");
		servercheckbox = new Checkbox( "Server mode");

		add( hostfield );
		add( portfield );
		add( servercheckbox );
		add( startbutton );

		// $B%W%l%$%d!<=i4|2=(B
		for(int i=0 ; i < 8 ; i++){
			p[i] = new Player( size().width/2 , (size().height/8)*i , Color.black);
		}

/*		if( server_mode ){
						
			try{
				ss = new ServerSocket( port );
				so = ss.accept();
				out = so.getOutputStream();
				in = so.getInputStream();
			} catch ( IOException e ){
				System.out.println("IOException!");
			}
			t = new Thread( this );
			t.start();
		} else {
			try{
				so = Socket( host , port );
				out = so.getOutputStream();
				in = so.getInputStream();
			}catch( IOException e ){
				System.out.println("IOException!");
			}
		}
*/
	}
	public void paint( Graphics g )
	{
		if( server_mode ){
			for(
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
		
}

class Player
{
	int x;
	int y;
	Color c;

	Player(int x, int y , Color c)
	{
		this.x = x;
		this.y = y;
		this.c = c;
	}
	void move(int dx , int dy )
	{
		x += dx;
		y += dy;
	}
	Point getPos()
	{
		return new Point( x , y );
	}
}
