import java.applet.Applet;
import java.io.*;
import java.net.*;
import java.awt.*;

// UNIXでは、appletviewerのpropertiesで、network: Unrestrictedにすれば
// server.class、client.classとも問題なく動く。
// windows95では、10000というポートをlistenしようとしたときに
// SecurityException が出る。 Windows がクライアントで UNIXがサーバーなら可能。
//

// それぞれのプレイヤーがキー操作したら、その情報を受けとって表示位置を変更するというもの。
// 同期させない。
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
	Player p[] = new Player[8];	// プレイヤーを8人分管理
	int player_num=0;  // プレイヤーが何人いるか
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

		// プレイヤー初期化
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
