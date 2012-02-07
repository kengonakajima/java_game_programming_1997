
import java.applet.Applet;
import java.applet.AudioClip;  // AudioClip$B$r;H$&$?$a$N(Bimport
import java.awt.*;
import java.awt.image.*;
import java.util.*;


public class game extends Applet implements Runnable
{


	Thread thread;
	Image double_buffer;
	Graphics dg;

	int cron=0;

	// $BJQ992DG=8D=j!"?tCM$N$H$3$m$@$1?'$rJQ$($k!#(B 
	int width , height;

	final int holesize = 64;  // $B7j$N%5%$%:(B($B@5J}7A(B)

	// $B$b$0$iC!$-$rD4@0$9$k$?$a$NJQ99ItJ,(B 
	final int yokosize = 5; // $B7j$N?t(B
	final int tatesize = 4; 
	public final int interval = 100;	// $B%2!<%`$N?J9TB.EY(B
	final int mogtime = 40;   // $B%b%0%i$N<wL?(B
	final int misstime = 4;   // $B%_%9I=<($N;~4V(B
	final int hittime = 4;    // $B%R%C%H$7$?I=<($N;~4V(B
	final int mogblank = 8;   // $BF1$87j$KB3$1$F=P$k;~$N4V(B
	final int rate = 10;      // $B%b%0%i$,=P8=$9$kIQEY(B
	final int life_max = 10;  // 10$B2s%_%9$7$?$i=*$o$j(B
	final int levelunit = 10; // 10$BI$$d$C$D$1$k$4$H$K%l%Y%k%"%C%W(B


	int mog[][] = new int[yokosize][tatesize ]; // $B$b$0$i$r!"I,MW$J?t$@$1MQ0U$9$k(B
	int mogstate[][] = new int[yokosize][tatesize];  // $B%b%0%i$N>uBV(B

	final int ALIVE = 1;  // $B$b$0$i$N>uBV$O!"$3$l$i$NCM$rF~$l$F$*$/!#(B
	final int MISS = 2;
	final int HIT = 3;
	final int OUT = 0;


	int score,level,life, hitno;

	MediaTracker mt; // $B2hA|%m!<%I$N$?$a$K;H$&(BMediaTracker
	Image aliveimg;  // $B%b%0%i$N2hA|(B($B=P8=$7$F$$$k;~$N(B)
	Image hitimg;    // $B%b%0%i$N2hA|(B($BC!$$$?=V4V$N(B)
	AudioClip hitsound,hellosound; // $BC!$$$?2;$H=P$F$/$k2;(B
	

	int mode;  
	final int TITLE= 1;       //mode$B$KF~$l$kCM(B
	final int GAME = 2;
	final int GAMEOVER = 3;

	public void init()
	{

		//width = size().width;  //JDK1.0.2
		//height = size().height;  // JDK1.0.2
		width = getSize().width;  //JDK1.1
		height = getSize().height;  // JDK1.1
		thread = new Thread(this);
		double_buffer = createImage( width , height );
		dg = double_buffer.getGraphics();

		thread.start();

		/* $B$3$3$+$i$b$0$iC!$-$N$?$a$NJQ99ItJ,(B */
		dg.setColor( Color.black );
		dg.fillRect( 0 , 0 , width , height );


		// $B;H$&2hA|$r%m!<%I$9$k!#%m!<%I$,=*$o$k$^$G!"(BMediaTracker$B$r;H$C$FBT$D(B
		aliveimg = getImage( getDocumentBase() , "alive.gif" );
		hitimg = getImage( getDocumentBase() , "hit.gif" );
		
		mt = new MediaTracker( this ); // $BF1$8(BID$B$GBT$D(B
		mt.addImage( aliveimg, 1 );
		mt.addImage( hitimg , 1 );
		try{
			mt.waitForID( 1 ); // $B%m!<%I3+;O$7!";XDj$7$?(BID$B$N2hA|$,A4It%m!<%I$5$l$k$^$GBT$D!#(B
		}catch( InterruptedException ie ){}

		hitsound = getAudioClip( getDocumentBase() , "yahoo.au");
		hellosound = getAudioClip( getDocumentBase() , "hi.au");
		// $B%?%$%H%k2hLL$rI=<((B
		mode = TITLE;


	}

	public void paint( Graphics g )
	{
		g.drawImage( double_buffer , 0 , 0 , null );

	}
	public void update( Graphics g )
	{
		paint( g );
	}
	public void run()
	{
		while( true )
		{
			cron++;
			try{
				Thread.sleep( interval );
			} catch( InterruptedException e ){}

			doIt();
			repaint();
		}
	}

	void doIt()
	{

		if( mode == TITLE ){
			dg.setColor( Color.black );
			dg.fillRect( 0 , 0 , width , height );
			dg.setColor( Color.white );
			dg.drawString( "DestroMogura",30, height/2 );
			dg.drawString( "press mouse button" , 30 , height/2+50);
		} else if( mode == GAME ){

		/* $BJQ992DG=8D=j(B */
		
			// $B0lDj;~4V$4$H$K!"%b%0%i$r=P8=$5$;$k(B
			// $B%l%Y%k$K1~$8$F!"=P$F$/$k%b%0%i$,A}$($F$$$/!#(B

			if( ( cron % rate ) == 0 ){
				int kazu;
				kazu = new Random().nextInt() & 255;
				kazu = kazu % level;
				for(int i = 0 ; i <= kazu ; i++){

					putMogura();
				}
			}
			// $B%b%0%i$N<wL?$r8:$i$7$F$$$/!#(B
			moveMogura();

			// $BL?$,$J$/$J$C$?$i!"%2!<%`%*!<%P!<(B
			if( life < 0 ){
				mode = GAMEOVER;
			}
			
			// $BE@?t$J$I$rI=<(!">C$7$F$+$i=q$/$3$H$KCm0U(B
			dg.setColor( Color.black );
			dg.fillRect( yokosize * holesize+10,0, width - yokosize*holesize , 100);

			dg.setColor( Color.white );
			dg.drawString("Score " + score , yokosize * holesize +10 , 30);
			dg.drawString("Life " + life , yokosize * holesize+10,60);
			dg.drawString("Level " + level , yokosize * holesize+10,90);

		} else if( mode == GAMEOVER ){
			dg.setColor( Color.black );
			dg.fillRect( 0 , 0 , width , height );
			dg.setColor( Color.red );
			dg.drawString("Game Over",30, height/2 );
		}
	}
	void moveMogura( )
	{
		// $B;`$L$^$G$N;~4V$r(B1$B8:$i$9!#;`$s$@$b$N$O!">C$7$F$7$^$&!#(B
		for(int i = 0 ; i < tatesize ; i++){
			for(int j = 0 ; j < yokosize ;j++ ){
				mog[j][i]--;
				if( mog[j][i] < 0 ){
					mogstate[j][i] = OUT;
					outMogura( j , i );
				}
				if( mog[j][i] == misstime ){
					mogstate[j][i] = MISS;
					life--;
					missMogura( j , i );
				}
			}
		}
	}


	// $B$b$0$i$r0lI$=P8=$5$;$k(B
	void putMogura( ){
		
		int r = new Random().nextInt();
		r = r & 255; // $B@5$NCM$KLa$9!#$3$N7k2L!"(Br$B$K$O!"(B0$B$+$i(B255$B$NCM$,F~$k!#(B
		r = r % (yokosize * tatesize );
		int yoko = r % yokosize;
		int tate = r / yokosize;

		// $B%b%0%i$,>C$($F$+$i0lDj;~4V$?$?$J$$$HF1$87j$K$OEP>l$G$-$J$$(B
		if( mog[yoko][tate] > -mogblank ) return;
		hellosound.play(); // $B=P8=;~$N2;$r=P$9(B
		mogstate[ yoko ][ tate ] = ALIVE;
		mog[ yoko ][ tate ] = mogtime;

		dg.setColor( Color.white );
		dg.fillRect( yoko * holesize , tate*holesize , holesize , holesize );
		dg.drawImage( aliveimg , yoko * holesize , tate*holesize ,null);
	}
	// $B%b%0%i$r>C$9!#(B
	void outMogura( int x , int y ){
		
		dg.setColor( Color.black );
		dg.fillRect( x * holesize , y * holesize , holesize , holesize );
		dg.setColor( Color.white );
		dg.drawRect( x * holesize , y * holesize , holesize , holesize );
	}
	// $BF($2>uBV$N%b%0%i$rI=<($9$k!#(B
	void missMogura( int x , int y ){

		dg.setColor( Color.black );
		dg.fillRect( x * holesize+1 , y*holesize+1 , holesize-1 , holesize-1 );
		dg.setColor( Color.yellow );
		dg.drawString( "MISS" ,x*holesize , y*holesize+(holesize/2) );


	}
	//$B$d$i$l>uBV$N%b%0%i$rI=<($9$k!#(B
	void yarareMogura( int x , int y ){
		dg.setColor( Color.red );
		dg.drawLine( x * holesize , y* holesize , x*holesize + holesize , y*holesize + holesize );
		dg.drawLine( x * holesize + holesize , y * holesize , x * holesize , y * holesize + holesize );
		dg.drawImage( hitimg , x*holesize, y*holesize ,null);
	}
	// $B%^%&%9$,2!$5$l$?$H$-$N=hM}(B
	public boolean mouseDown( Event e , int x , int y )
	{
		if( mode == TITLE ){
		
			// $B$b$0$i$r=i4|2=(B
			for(int i = 0 ; i < yokosize ; i++){
				for(int j = 0 ; j < tatesize ; j++){
					mogstate[i][j] = OUT;
					mog[i][j] = 0;
				}
			}
			score = 0;
			level = 1;
			life = life_max;
			hitno = 0;
			mode = GAME;
		} else if( mode == GAME ){
			// $B$I$N%b%0%i$rC!$$$?$N$+$rH=Dj$9$k(B
			int yoko ,tate; // $B%b%0%i$N7j$N0LCV(B
			yoko = x / holesize;
			tate = y / holesize;
			
			// $B%b%0%i$,@8$-$F$$$k$H$-$@$1%R%C%H!#(B
		
			if( yoko>=0 && yoko < yokosize &&
			    tate>=0 && tate < tatesize &&
			   mogstate[yoko][tate] == ALIVE ){
				mog[yoko][tate] = hittime;
				mogstate[yoko][tate] = HIT;
				yarareMogura( yoko , tate );
				score = score + level;
				hitsound.play();  // $B2;$r$@$9(B
				hitno++;
				// $B%l%Y%k(B
				if( (hitno % levelunit ) == 0 ){
					level++;
				}
			}
		} else if( mode == GAMEOVER ){
			mode = TITLE;
		}
		return true;
	}

}

