import java.applet.Applet;
import java.net.*;
import java.io.*;
import java.awt.*;

public class http extends Applet
{
	URL u;
	URLConnection uc;
	OutputStream out;
	InputStream in;
	TextField tf;
	Button btn;

	public void init()
	{
		tf = new TextField( 30 );
		setLayout( new BorderLayout() );
		add("South" , tf );
		btn = new Button("load");
		add("Center" , btn );
	}
	public boolean action( Event ev , Object o )
	{
		
		if( o.toString() == "load" ){
			System.out.println( tf.getText());
			try{
				u = new URL( tf.getText());
			}catch( MalformedURLException e ){
				System.out.println("fuckin' malex.");
				return true;
			}

			try{
				uc = u.openConnection();
				uc.setDoInput(true);
			}catch( IOException e ){
				System.out.println("fuckin' ioex.");
				return true;
			}


			try{
				in = uc.getInputStream();
				int c;
				while(( c = in.read()) != -1)
				{
					System.out.print( (char)c );
				}
			}catch( IOException e ){
				System.out.println("fuckin' IOerror!");
			}
		}
		return true;

	}
}


