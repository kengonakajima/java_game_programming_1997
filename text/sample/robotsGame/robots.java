import java.awt.*;
import java.util.*;
import java.net.*;
import java.awt.*;
import java.io.*;

public class robots extends java.applet.Applet
{



	Image double_buffer;
	Graphics dg;

	int cron=0;

	Image imgs[] = new Image[4];

	int width ,height;
	int interval = 50;	/* milli second */
	Image floorimg;
	Graphics floorimg_g;

	YourNameWindow win;

	public void init()
	{

		width = size().width;
		height = size().height;

		imgs[ROBOT] = getImage( getDocumentBase() , "robot.gif");
		imgs[PLAYER] = getImage( getDocumentBase() , "man.gif");
		imgs[JUNK] = getImage( getDocumentBase() , "junk.gif");

		double_buffer = createImage( width , height );
		dg = double_buffer.getGraphics();

		floorimg = createImage( width , height );  
		floorimg_g = floorimg.getGraphics();
		
		fxsiz = width / xtilesize;
		fysiz = height / ytilesize;

		field =  new int[fxsiz][fysiz];
		tmpfield = new int [fxsiz][fysiz];
		initFloorImage();
	
		initField();
		

	}

	public void paint( Graphics g )
	{
		drawAll();
		g.drawImage( double_buffer , 0 , 0 , null );

	}
	public void update( Graphics g )
	{
		paint( g );
	}

	int fxsiz;
	int fysiz;
	int field[][];
	int tmpfield[][];
	final int BLANK = 0;    // $B$9$Y$FCO7A$NMWAG$H$7$F4IM}(B
	final int ROBOT = 1;
	final int JUNK = 2;
	final int PLAYER = 3;
	int plx , ply;
	int stage=0;
	int wait_bonus=0;

	Color bg1 = new Color( 0x001028 );
	Color bg2 = new Color( 0x113322 );
	Color fg = new Color( 0xffee44 );

	int xtilesize =20,ytilesize=20;    // $B%?%$%k$N=D2#$NBg$-$5(B

	int score=0;
	String yourname = "input yourname";

	Random ran = new Random();

	void initFloorImage()
	{
		int c=0;
		for(int i=0;i<fxsiz;i++){
			c = i;
			for(int j=0;j<fysiz;j++){
				c++;
				if( (c%2)==0){
					floorimg_g.setColor( bg1 );
				} else {
					floorimg_g.setColor( bg2 );
				}
				floorimg_g.fillRect( i*xtilesize , j*ytilesize , 
									xtilesize , ytilesize );
			}
		}
	}

	void initField()
	{
		int num;

		// $BE($N?t$r7h$a$k(B
		if( stage < 5 ) num = stage * 5 ; else num = 25 + stage ;

		// $B%U%m%"$r=i4|2=(B
		for(int i=0;i<fxsiz;i++){
			for(int j=0;j<fysiz;j++){
				field[i][j] = BLANK;
			}
		}
		// $BE($rG[CV(B
		for( int i = 0 ; i < num ; i++ ){
			int x = ( ran.nextInt() & 0xffff ) % fxsiz;
			int y = ( ran.nextInt() & 0xffff ) % fysiz;
			field[x][y] = ROBOT;
		} 
		// $B%W%l%$%d!<$rG[CV(B
		for(;;){
			int x = ( ran.nextInt() & 0xffff ) % fxsiz;
			int y = ( ran.nextInt() & 0xffff ) % fysiz;
			if( field[x][y] != BLANK ) continue;
			field[x][y] = PLAYER;
			plx = x;
			ply = y;
			break;
		}
	}

	// $B$3$NJV$jCM$,(Btrue $B$N>l9g$O!"Jb$1$J$+$C$?!#(B

	boolean walk( int rx , int ry , boolean teleport )
	{
		// $BD4$Y$?$j!"?t$($?$j!#(B
		int robotno=0;


		for(int i=0;i<fxsiz;i++){
			for(int j=0;j<fysiz;j++){
				if( field[i][j] == ROBOT ) robotno++;
				if( field[i][j] == PLAYER ){ plx = i; ply = j; }
				if( field[i][j] == JUNK) tmpfield[i][j] = JUNK; 
				else tmpfield[i][j] =BLANK;
			}
		}
		
		// $B%U%#!<%k%I$N%3%T!<$r0l;~E*$K:n$k!#A4BN$,0l@F$K?7$7$$>uBV(B
		// $B$K0\9T$9$k$h$&$J%2!<%`$G$O!"$3$N$h$&$K%F%s%]%i%j$N%P%C%U%!$r(B
		// $B:n$kI,MW$,$"$k!#(B($B%i%$%U%2!<%`$J$I(B)
		if( (plx+rx) < 0 || (plx+rx) >=fxsiz ){
			return true;
		}
		if( (ply+ry) < 0 || (ply+ry) >=fysiz ){
			return true;
		}
		if( field[plx + rx][ply+ry] == JUNK ){
			return true;
		}

		tmpfield[plx+rx][ply+ry] = PLAYER;
		plx += rx; ply += ry;

		int score_store = score;

		for(int i=0;i<fxsiz;i++){
			for(int j=0;j<fysiz;j++){
				if( field[i][j] == ROBOT ){
					int relx = 0 , rely = 0;
					if( i > plx ) relx = -1;
					if( i < plx ) relx = 1;
					if( j > ply ) rely = -1;
					if( j < ply ) rely = 1;
					switch( tmpfield[i+relx][j+rely] ){
						case ROBOT:
						tmpfield[i+relx][j+rely] = JUNK;
						score += 20;
						break;
						case JUNK:
						score += 10;
						break;
						case PLAYER:
						if( teleport == true ){
							die();
						}
						score = score_store;
						plx -= rx;
						ply -= ry;
						
						return true;

						case BLANK:
						tmpfield[i+relx][j+rely] = ROBOT;
						break;
					}
				}
			}
		}

		// $B$G$-$"$,$C$?%P%C%U%!$r85$N%P%C%U%!$K%3%T!<$9$k!#(B
		
		for(int i=0;i<fxsiz;i++){
			for(int j=0;j<fysiz;j++){
				field[i][j] = tmpfield[i][j];
			}
		}

		// $BE($r?t$($FC/$b$$$J$+$C$?$i%/%j%"(B
		if( countEnemy(field) ==0 ){
			stage++;
			initField();
		}
		
		drawAll();
		repaint();
		
		return false;
	}
	// windows95$BMQ$N(BJDK1.1$B$N(Bbeta2$B$G$O!"2a5n$H$N8_49@-$rJ]$D$?$a$K(B
	// $BMQ0U$5$l$F$$$k(BkeyDown$B%a%=%C%I$P$+$j$G$O$J$/!"(B1.1$B$G2C$o$C$?(B
	// keyListener$B$r;H$C$?$d$j$+$?$G$b%-!<%$%Y%s%H$,Ht$s$G$3$J$$$N$G(B
	// $B$7$+$?$J$/!"%^%&%9$K$h$kA`:n$K$7$F$$$k!#(B
	int gridx,gridy;
	public boolean mouseMove( Event e , int x , int y )
	{
		gridx = x / xtilesize;
		gridy = y / ytilesize;
		repaint();
		return true;
	}
	// $B%-!<%\!<%I$G$bA`:n$G$-$k$h$&$K$7$F$*$/!#(B
	public boolean keyDown( Event e , int c )
	{
		switch( c ){
			case 'h': walk( -1 , 0 , false ); break;
			case 'j': walk( 0 , 1 , false );break;
			case 'k': walk( 0 ,-1 , false );break;
			case 'l': walk( 1 , 0 , false );break;
			case 'y': walk( -1,-1, false );break;
			case 'u': walk( 1,-1,false );break;
			case 'b': walk( -1,1,false );break;
			case 'n': walk( 1,1,false );break;
			case 't': 	
				  int destx = ( ran.nextInt() &0xff)%fxsiz ;
				  int desty = ( ran.nextInt() &0xff)%fysiz ;
				  walk( destx-plx , desty-ply , true );
				  break;
			case 'w':
				  waitRobots();
				  break;
			case ' ': walk(0,0,false );break;
			
		}
		return true;
	}
	public boolean mouseDown( Event e , int x , int y)
	{
		gridx = x / xtilesize;
		gridy = y / ytilesize;
		int dx =808, dy=808;
		boolean ret;

		if( gridx == ( plx + 1) ) dx = 1;
		else if( gridx == (plx -1 )) dx = -1;
		else if( gridx == plx ) dx = 0;
		if( gridy == ( ply + 1) ) dy = 1;
		else if( gridy == (ply -1 )) dy = -1; 
		else if( gridy == ply ) dy = 0;

		// $B<g?M8x$N6a$/$r%/%j%C%/$9$k$H!"IaDL$KJb$/!#(B
		// $B1s$/$r%/%j%C%/$9$k$H!"%F%l%]!<%H$9$k!#(B 
		// $B%7%U%H%-!<$r2!$7$J$,$i%/%j%C%/$9$k$H!"!VBT$D!W!#(B
		if( ( e.modifiers & Event.SHIFT_MASK ) != 0){
			waitRobots();
		} else
		if( dx >= -1 && dx <= 1 && dy >= -1 && dy <= 1 ){
			walk( dx,dy,false );
			
		} else {
			int destx = ( ran.nextInt() &0xff)%fxsiz ;
			int desty = ( ran.nextInt() &0xff)%fysiz ;
			walk( destx-plx , desty-ply , true );
		}
	
		return true;

	}
	void waitRobots()
	{

		int c;
		int total_robots = countEnemy(field);
		for(;;){
			// $B%$%Y%s%H=hM}$N%a%=%C%I$NCf$GIA2h$9$k$K$O$3$&$9$k(B
			paint( getGraphics() );    
			c = countEnemy(tmpfield);
			if( c == 0 ){
				wait_bonus = total_robots * stage;
				score += wait_bonus;
				initField();
				break;
			}

			if( walk( 0 , 0 , false ) == true ){
				// wait$B$7$F$k$N$KJb$1$J$$>l9g!"$=$l$O;`$@!#(B
				die();
				break;
			}

		}
	}

	void die()
	{

		win = new YourNameWindow( this , score , yourname);

		stage = score = 0;
		initField();
	}

	int countEnemy(int [][]f)
	{
		int counter=0;
		for(int i=0;i<fxsiz;i++){
			for(int j=0;j<fysiz;j++){
				if( f[i][j] == ROBOT ) counter++;
			}
		}
		return counter;
	}

	void drawAll()
	{
		// $B$^$:>C$9(B
		dg.drawImage( floorimg , 0 , 0 , null );

		// $B%U%#!<%k%I$rIA$/(B

		for(int i=0;i<fxsiz;i++){
			for(int j=0;j<fysiz;j++){
				if( field[i][j] != BLANK ) 
				dg.drawImage( imgs[field[i][j]] , i*xtilesize ,j*ytilesize,this);
			}
		}
		
		// $B%0%j%C%I$rIA$/(B

		dg.drawRect( gridx*xtilesize , gridy*ytilesize , 
					xtilesize-1,ytilesize-1 );
		// $BE@?tI=<((B
		dg.setColor( fg );
		dg.drawString( "SCORE " + score + "/STAGE " +
					  stage + "/Wait Bonus " + wait_bonus,10,30);
	}
}

class YourNameWindow extends Frame
{
	Button b;
	TextField t;
	Label l;
	Panel p;
	TextArea tarea;

	int score;
	robots parent ;

	String cgi_url = "http://ns2.titan.co.jp/cgi-bin/ringo/java/aho.cgi";

	YourNameWindow(robots parent , int score , String name)
	{
		super("Highscore table");
		this.score = score;
		this.parent = parent;
		
		setLayout( new BorderLayout() );
		p = new Panel();
		p.setLayout( new BorderLayout() );
		b = new Button("Send");
		t = new TextField(name );
		l = new Label( "your score: " + score );


		tarea = new TextArea( "now loading score list" ,10,20 );
		p.add( "West" , l );
		p.add( "Center" , t );
		p.add( "East" , b );
		add( "North" ,p );
		add( "Center" , tarea );
		setForeground( new Color( 0xffee44 ));
		setBackground( new Color( 0x001028 ));
		resize(320,250);
		show();

		// $B%O%$%9%3%"$NI=$r<h$C$F$/$k!#(B
		String args[] = { "filename=robots_score" , "method=read" };
		
		HTTPAccess ha=null;
		String listcontents=null;
		try{
			ha = new HTTPAccess( cgi_url , "POST" , args );
			//String listcontents  = new String( ha.getDocument() ); // JDK 1.1
			byte buf[] = ha.getDocument();  // JDK1.0.2
			listcontents = new String( buf , 0 , 0 , buf.length ); //JDK1.0.2
		}catch( Exception e ){
			System.out.println( e ); 
			listcontents = "Network Error.\nPlease reconfigure\n" + 
			"appletviewer network properties.\n";
		}
		tarea.setText( listcontents );

	}

	public boolean action(Event e, Object o )
	{
		if( e.target == b ){
			if( t.getText().length() == 0 ){
				return true;
			}
			String args[] = 
			{ "filename=robots_score" , 
			  "method=append" ,"value="+  t.getText()+":"+score};
			try{
				HTTPAccess ha = new HTTPAccess( cgi_url , "POST" , args );
				byte [] ret = ha.getDocument();
				System.out.println( new String(ret ,0,0,ret.length) ); //1.0.2
			}catch( Exception ex ){
				System.out.println( ex );
			}
			parent.yourname = t.getText();
			hide();
		}
		return true;
	}

	public String getName()
	{
		return t.getText();
	}


}




// $B$3$N%/%i%9$OHFMQE*$J!"(Bhttp$B%"%/%;%9$r<B8=$9$k!#FC$K(BCGI$B$KBP$7$FM-8z!#(B
// $B;H$$$+$?$O!"$^$:!"(BURL$B$NJ8;zNs$^$?$O(BURL$B$r;XDj$7$F!"=i4|2=$7!"$=$N$"$H(B
// getDocument$B$9$k$@$1!#(B
// HTTPAccess ha = new HTTPAccess( URL
class HTTPAccess
{
	URL url;
	URLConnection uc;
	String request_string;
	String method;

	int read_unit = 1000;


	HTTPAccess( String location , String method , String[] values ) 
	throws MalformedURLException
	{
		this( new URL( location) , method , values );
	}
	HTTPAccess( String location ) throws MalformedURLException
	{
		this( new URL( location ) );
	}
	// $BIaDL$N%3%M%/%7%g%s(B
	HTTPAccess( URL u )
	{
		method = "GET";
		url = u;
	}
	// CGI$B$r;H$&J}(B
	HTTPAccess( URL u , String method , String[] values )
	throws MalformedURLException
	{
		request_string = "";
		this.method = method;

		for(int i=0;i< values.length;i++){
			request_string += URLEncoder.encode( values[i] );
			if( i == (values.length-1) )break;
			request_string += "&";
		}
		if( method.equals("GET") ){
			url = new URL( u.toExternalForm() + "?" +  request_string );

		} else if( method.equals("POST") ){
			url = u;
		}

	}

	byte[] getDocument() throws Exception
	{
		
		ByteArrayOutputStream baout=null;
		try{
			uc = url.openConnection();
			uc.setUseCaches(false);
			uc.setDoInput(true);  // $B$3$l$r$d$i$J$$$H%@%a!#(B

			if( method.equals("POST") ){
					uc.setDoOutput(true);  // $B$3$l$b=EMW(B
					OutputStream out = uc.getOutputStream();
					//byte b[] = request_string.getBytes();  // JDK1.1

					byte b[] = new byte[10000]; //JDK1.0.2
					request_string.getBytes(
							0 , request_string.length(),b,0 );//JDK1.0.2
					
					out.write( b , 0 , b.length );
					out.write( '\n' );
					out.close();
			}



			DataInputStream in =
			new DataInputStream( uc.getInputStream() );
			baout = new ByteArrayOutputStream();

			byte  b[] = new byte[read_unit];
			while(true)
			{
				int nread = in.read( b, 0 , read_unit );
				if( nread == -1 ) break;
				baout.write( b , 0 , nread );
			}
			in.close();
			baout.close();
		
		}catch( Exception e ){	throw e; }
		return baout.toByteArray();
	}
}

// $B%F%9%HMQ%/%i%9(B

class test
{
	public static void main(String args[] ){
		try{ 
			HTTPAccess ha = new HTTPAccess("http://ns2.titan.co.jp/index.html");
			byte contents[] = ha.getDocument();
			// System.out.println( new String( contents ) );// JDK1.1
			
			String a[] = {"filename=hoge" , "method=append" , "value=808" } ;

			HTTPAccess ha2 = new HTTPAccess("http://ns2.titan.co.jp/cgi-bin/ringo/java/aho.cgi" , "POST" , a );
			byte contents2[] = ha2.getDocument();
			// System.out.println( new String( contents2 ));// JDK1.1
							   
										   
		} catch( Throwable th ){
			System.out.println( th );
		}
	}
}















