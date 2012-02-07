// client$B$N7hDj%P!<%8%g%s!#$9$G$KJ8>O$KKd$a$3$s$G$"$k$N$G!"JQ99$K$OCm0U$;$h!#(B

// $B%5!<%P!<$rMQ0U$9$k%?%$%W$NBP@o%2!<%`$N%5%s%W%k!#$3$N%"%W%l%C%H$O(B
// $BI=<($N$_$r9T$J$&!#(B
// $B%"%W%l%C%H$NCf$K!"J#?t$N%W%l%$%d!<$,F0$-$^$o$k$H$$$&$b$N!#(B
// $B%-!<F~NO$G(B4$BJ}8~$KF0$-$^$o$k$@$1$N$b$N!#(B
// $BMW5a$,Mh$7$@$$!"0LCV$rJQ99$9$k!#(B

import java.applet.Applet;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class client extends Applet implements Runnable
{
	Socket clisock;		// $B%/%i%$%"%s%HMQ(B
	Thread thread;
	Socket so;
	InputStream in;
	OutputStream out;
	DataInputStream din;

	Image offscr;
	Graphics offscrg;
	int width , height;

	int num;
	int x[] = new int[100];		// $B$?$/$5$sMQ0U$7$F$*$/(B
	int y[] = new int[100];
	Color colortable[] = new Color[100];

	public void init()
	{
		

		offscr = createImage( width = size().width , height = size().height );
		offscrg = offscr.getGraphics();

		// $B?'$r=i4|2=$7$F$*$/(B
		
		for(int i=0;i<100;i++){
			colortable[i] = new Color( (i*105)&255,(i*54)&255,(i*202)&255);
		}
		
		try{
			so = new Socket( getParameter("host") ,
				Integer.parseInt(getParameter("port")));
			in = so.getInputStream();
			din = new DataInputStream( in );
			out = so.getOutputStream();
		}catch( IOException e ){
			System.out.println("Network error. Server down? or bad hostname/port?");
		}
		thread = new Thread( this );
		thread.start();
	}

	public void paint( Graphics g )
	{
		g.drawImage( offscr , 0 , 0 ,this);
	}
	public void update( Graphics g)
	{
		paint(g);
	}
	void dispAll()
	{
		offscrg.setColor( Color.white);
		offscrg.fillRect( 0 , 0 , width , height );
		
		for(int i=0;i < num ; i++){
			offscrg.setColor( colortable[i] );	
			offscrg.fillRect( x[i] , y[i] , 8,8);
		}
	}
	public boolean keyDown(Event e , int c)
	{
		try{
			out.write( c);
		}catch( IOException ex ){}
		return true;
	}

	public void run()
	{
		
		while(true)
		{
			int counter = 0;
			while(true)
			{
				int tmpx=0,tmpy=0;
				try{
					tmpx = din.readShort();
					tmpy = din.readShort();
				}catch( IOException e ){}

				if( tmpx == -1 && tmpy == -1 ){
					dispAll();
					repaint();
					break;
				} else {
					x[counter] = tmpx;
					y[counter] = tmpy;
					counter++;
				}
			}
			num = counter;
			
		}
		
	}

}	
