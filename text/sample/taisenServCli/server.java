// server$B$N7hDj%P!<%8%g%s!#$3$l$KJQ99$r2C$($?$i!"J8>O$NJ}$K$bJQ99$r2C$($kI,MW$,$"$k!#(B
// $BCm0U$;$h!#(B


// $B2?$G$b$h$$$+$i!"%W%m%H%3%k$r7h$a$kI,MW$,$"$k!#(B
// $BF~NO$O!"%-!<F~NO$@$+$i!"(B1$B%P%$%HAw?.$9$l$P$h$$!#$3$l$O?^$K$7$F@bL@$9$k$3$H!#(B
// $B=PNO$O!"(Bshort$B$NCM(B2$B8D%;%C%H$N:BI8$r?M?tJ,Aw?.$7$F!":G8e$K(B(-1,-1)$B$rAw$C$F=*$j$H$9$k!#(B
// $B%/%i%$%"%s%H$NB&$G$O!"(B-1$B$,$/$k$^$GBT$C$F!"Mh$?$iA4BN$r0l5$$K99?7$9$k!#(B
// $B%5!<%P$O!"C/$+$,2?$+$rF~NO$9$kEY$KA4BN$rAw?.$9$k!#$@$+$i?M?t$,B?$$$HBgJQ$@!#(B
// $BF~NO$@$1$r$d$j$H$j$9$k$h$&$J%G%6%$%s$b$"$k$H$$$&$3$H$r@bL@$9$k!#$7$+$7!"CY1d$NBg$-$$(B
// $B%7%9%F%`$G$O!"$=$l$O?I$$$H$$$&$3$H$b@bL@$9$k!#%5!<%P!<$O!"@$3&$NF14|$r<h$k$?$a$K(B
// $B;H$&$3$H$,$G$-$k$H$$$&$3$H$@!#(B

// $BF0:n<B83$O!"(BNetscape$B$O$b$A$m$s%@%a$G!"(Bappletviewer$B$@$H!"(B
/*

 server     client
 UNIX       UNIX	 .... totally ok
 UNIX       windows95    .... Totally ok
 windows95  UNIX	 .... TOtally ok
 windows95  windows95    .... totally ok

$B4D6-$O!"(BEtherNet$B$G(BIP$B@\B3$5$l$F$$$k%^%7%sF1;N!#%$%s%?!<%M%C%H>e$G$b(Bproxy$B$,$J$$8B$j(B
$BF1$87k2L$K$J$k$H;W$o$l$k!#(B

*/


import java.net.*;
import java.io.*;

class serverMain
{
	
	public static void main( String args[] )
	{
		server serv;
		serv = new server(9000);
	}
}
class acceptor implements Runnable
{
	ServerSocket servsock;
	server parent;
	Thread t;
	
	acceptor(ServerSocket ss, server parent)
	{
		servsock = ss;
		this.parent = parent;
		t = new Thread( this );
		t.start();
	}
	public void run()
	{
		System.out.println("wait for new connection.");
		while(true)
		{
			Socket tmpsock;
			try{
				tmpsock = servsock.accept();
			} catch( IOException e ){
				System.out.println("accept error.");
				tmpsock = null;
			}
			System.out.println("New socket. " + tmpsock);
			parent.addSocket( tmpsock );
		}
	}
}

class server implements Runnable
{

	int playerno = 5;
	Thread thread;
	ServerSocket servsock;
	Socket sock[] = new Socket[playerno];		// $B?M?tJ,$@$1MQ0U$9$k!#(B
	boolean using[] = new boolean[playerno];        // $B%=%1%C%H$,;HMQCf$+$I$&$+(B 
	InputStream in[] = new InputStream[playerno];
	OutputStream out[] = new OutputStream[playerno];
	int timeout[] = new int[playerno];		// $B0lDj;~4VA`:n$,$J$$;~$O%?%$%`%"%&%H$9$k!#(B
	int timeout_max = 300;

	acceptor ac;

	// $B%W%l%$%d!<$N>pJs(B
	int x[] = new int[playerno];
	int y[] = new int[playerno];
	
	server(int port)
	{
		try{
			ServerSocket servsock = new ServerSocket( port );
			ac = new acceptor( servsock , this );
		}catch( IOException e ){
			System.out.println("Can't make ServerSocket.");
		}
		
		for(int i = 0 ; i < playerno ; i++){
			using[i] = false;
			x[i] = y[i] = 50;  // $B=i4|0LCV$OE,Ev(B
			timeout[i] = 0;
		}
		thread = new Thread(this);
		thread.start();
	}
	
	// acceptor$B$+$i8F$S$@$5$l$k!#%=%1%C%H$N6u$-$rC5$7$FEPO?$9$k(B
	public void addSocket( Socket soc )
	{
		if( soc == null ) return;
		for(int i = 0 ; i < playerno ; i++){
			if( using[i] == false ){
				sock[i] = soc;
				try{
					in[i] = soc.getInputStream();
					out[i] = soc.getOutputStream();
				} catch( IOException e ){
					System.out.println( "getting IN/OUT stream error.");
					return;
				}
				using[i] = true;
				System.out.println( "player " + i + " is added.");
				return;
			}
		}
		System.out.println("player full.");
		try{
			soc.close();
		}catch( IOException e ){}

	}
	void deletePlayer(int index )
	{
		timeout[index] = 0;
		try{
			sock[index].close();
			in[index].close();
			out[index].close();
		}catch( IOException exc ){}
		using[index] = false;
		System.out.println("Closed socket. player="+index);
	}
	
	// $BF~NO$K1~$8$F0\F0$5$;$k(B
	void playerMove( int index , int key)
	{
		int dx , dy;

		switch( key)
		{
			case 'h': dx = -5; dy = 0; break;
			case 'j': dx = 0; dy = 5; break;
			case 'k': dx = 0; dy = -5; break;
			case 'l': dx = 5; dy = 0; break;
			case 'q': dx=dy=0;deletePlayer( index );return;
			default: dx = dy = 0; break;
		}
		x[index] += dx;
		if( x[index] < 0 ) x[index] = 0;
		if( x[index] > 200) x[index] = 200;
		y[index] += dy;
		if( y[index] < 0 ) y[index] = 0;
		if( y[index] > 200) y[index] = 200;
		timeout[index] = 0;			// $B%?%$%`%"%&%H$^$G$N;~4V$r85$KLa$9(B
	}
	// $BA40w$KBP$7$F0LCV$N>pJs$rAw?.$9$k(B
	void sendForAll()
	{
		// $B$^$:!"Aw$k>pJs$r:n$k!#%P%C%U%!$N%5%$%:$O%W%m%H%3%k$r;2>H(B
		
		short sendbuf[] = new short[(playerno+1)*2*2];
		int counter = 0;
		for(int i = 0 ;i < playerno ; i++){
			if( using[i] == true){
				sendbuf[counter++] = (short)x[i];
				sendbuf[counter++] = (short)y[i];
			}
		}
		sendbuf[counter++] = -1; // $B%G!<%?$N:G8e$H$$$&0UL#(B
		sendbuf[counter++] = -1;
		
		
		for(int i = 0 ;i < playerno ; i++){
			if( using[i] == true )
			{
				try{
					DataOutputStream dout;
					dout = new DataOutputStream( out[i] );
					for(int j=0; j < counter ; j++){
						dout.writeShort( sendbuf[j] );
					}
					dout.flush();
					//dout.close();
				}catch( IOException e ){
					// $B=q$-$3$_$,$&$^$/$$$+$J$+$C$?$i!"$=$N%W%l%$%d!<$rKu>C(B
					System.out.println("dout ex.");
					deletePlayer( i );
				}
			}
		}
	}
	public void run()
	{
		while(true)
		{
			try{
				Thread.sleep(200);
			}catch( InterruptedException e){}

			// $B%5!<%P!<$N>uBV$rI=<((B
			for(int i=0;i < playerno ;i++){
				
				if(using[i])System.out.print("ON"); else System.out.print("OFF");
				System.out.print(":"+x[i]);
				System.out.print(":"+y[i]);
				System.out.print(":"+timeout[i]);
				System.out.print(" ");
			}
			System.out.println("");
			
			// $B$=$l$>$l$N@\B3$N=hM}(B

			for(int i = 0  ; i < playerno ; i++){
				if( using[i] == true ){
					if( (++timeout[i]) > timeout_max){
						deletePlayer( i );
						continue;
					}
					
					try{
						if( in[i].available() > 0 ){
							// $B2?$+>pJs$rAw$C$F$-$?$>(B
							int r;
							
							r = in[i].read();
							playerMove( i, r );
							sendForAll();
							
						}
					}catch( IOException e)
					{
						// $B%=%1%C%H$,$b$&;H$($J$$(B
						deletePlayer( i );
					}
				}
			}
		}
	}
}




