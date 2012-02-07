import java.applet.Applet;
import java.awt.*;
import java.net.*;
import java.io.*;

public class client extends Applet implements Runnable
{
	Thread t;
	Socket so;
	OutputStream out;
	InputStream in;

	String host;
	int port;

	public void init()
	{
		host = getParameter("host");
		port = Integer.parseInt( getParameter("port") );
		System.out.println(host + "port " + port );

		try{
			so = new Socket( host , port );
			
			out = so.getOutputStream();
			in = so.getInputStream();
		}catch( IOException e ){
			System.out.println( "IOEx.");
		}
		

		t = new Thread( this );
	}
	public boolean keyDown( Event ev , int c )
	{
		try{
			out.write( c );
		} catch( IOException e ){}

		return true;
	}

	public void run()
	{
		int c=0;
		while( true )
		{
			try{
				Thread.sleep(100);
			}catch( InterruptedException e ){}
			try{
				if( in.available() > 0 ){
					c = in.read();
				}
				System.out.println( (char)c);
			} catch( IOException e ){}

		}
	}

}
