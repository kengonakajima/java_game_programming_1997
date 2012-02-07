/*
  $BJ#?t$N;M3Q$,F0$$$F!"$=$l$>$l$,Ev$?$k$H$O$M$+$($k$N?^(B

  $B$3$N%W%m%0%i%`$b!"(Bgame.java$B$r%3%T!<$7$F;H$C$F$$$k!#(B


  $B$^$:Bg;v$J%]%$%s%H$O!"Ev$?$jH=Dj$NCf?4ItJ,$K$J$C$F$$$k(Bcheck
$B%a%=%C%I$G$9!#;M3Q7AF1;N$NEv$?$jH=Dj$O!"ITEy9f$r;H$($P4JC1$K(B
$B<B8=$G$-$kNc$G$9!#B>$K$b!"1_F1;N$NEv$?$jH=Dj$J$i5wN%$r7W;;(B
$B$9$k$H$+!"?'!9%P%j%(!<%7%g%s$O$"$k$G$7$g$&!#(B

  $B<!$K!"%-%c%i%/%?$NH?<M$NItJ,$G$9!#(B

*/
import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class atari extends Applet implements Runnable
{
	Thread thread;
	Image double_buffer;
	Graphics dg;
	int cron=0;

	// $BJQ992DG=8D=j(B

	int width ,height;
	int interval = 50;	// milli second 

	Choice cho;

	public void init(){
		thread = new Thread(this);
		width = size().width;
		height = size().height;
		double_buffer = createImage( width , height );
		dg = double_buffer.getGraphics();

		setLayout( new BorderLayout() );
		initBox();
		thread.start();
	}
	public void paint( Graphics g ){
		g.drawImage( double_buffer , 0 , 0 , null );
	}
	public void update( Graphics g ){
		paint( g );
	}
	public void run(){
		while( true ){
			try{ 
				Thread.sleep( interval );
			} catch( InterruptedException e ){}
			doIt();
			repaint();
		}
	}
	
	int maxnum=100;
	int nownum=1;
	double bx[] = new double[maxnum];
	double by[] = new double[maxnum];
	double bxsiz[] = new double[maxnum];
	double bysiz[] = new double[maxnum];
	double bdx[] = new double[maxnum];
	double bdy[] = new double[maxnum];
	boolean bfill[] = new boolean[maxnum];
	int bcount[] = new int[maxnum];

	void initBox(){
		for(int i=0;i<maxnum;i++){
			bx[i] = ( width * Math.random());
			by[i] = ( height * Math.random());
			bdx[i] = (-4.0 + 8.0*Math.random());
			bdy[i] = (-4.0 + 8.0*Math.random());
			bysiz[i] = (20.0 + 40.0*Math.random());
			bxsiz[i] = (20.0 + 40.0*Math.random());
			bfill[i] = false;
		}
	}

	double react = 17;  // $BBg$-$$$[$I4K$d$+$JH?<M$K$J$k!#(B
	void moveBox(){
		double before_x , before_y;
		for(int i=0;i<nownum;i++){
			bcount[i]++;
			before_x = bx[i];
			before_y = by[i];
			bx[i] += bdx[i];
			by[i] += bdy[i];
			for(int j=0;j<nownum;j++){
				if( i == j )continue;
				if( check( bx[i] , by[i], bxsiz[i] , bysiz[i] , 
						  bx[j] , by[j] , bxsiz[j] , bysiz[j] ) ){
					bfill[i] = true;
					double relx , rely;   // $BH"$NCf?4$NAjBP0LCV4X78!#(B
					relx = (bx[j]+bxsiz[j]/2) - (bx[i]+bxsiz[i]/2);
					rely = (by[j]+bysiz[j]/2) - (by[i]+bysiz[i]/2);

					bdx[i] = -relx / react;  // $B$*8_$$$rN%$9(B
					bdy[i] = -rely / react;
					bdx[j] = relx /  react;
					bdy[j] = rely /  react;
				}
				if( bx[i] < 0 ){ bx[i]=0;bdx[i]*=-1;bfill[i]=true;}
				if( bx[i]+bxsiz[i] > width ){
					bx[i]=width-bxsiz[i];bdx[i]*=-1;bfill[i]=true;
				}
				if( by[i] < 0 ){ by[i]=0;bdy[i]*=-1;bfill[i]=true;}
				if( by[i]+bysiz[i] > height ){
					by[i]=height-bysiz[i];bdy[i]*=-1;bfill[i]=true;
				}
			}
		}
	}

	// $BEv$?$jH=Dj!#?4B!It$G$9!#(B
	boolean check( double x1, double y1 , double x1size , double y1size ,
				  double x2 , double y2 , double x2size , double y2size ){
		return( x2 <= (x1+x1size) && x1 <= (x2+x2size ) &&
			    y2 <= (y1+y1size) && y1 <= (y2+y2size) );
	}
	// $B%^%&%9$N%\%?%s$r2!$9$HH"$,A}$($^$9!#(B
	public boolean mouseDown( Event e , int x , int y){
		bx[nownum] = x;
		by[nownum] = y;
		nownum++;
		return true;
	}
	void doIt(){

		// $BH"$rF0$+$9!#(B
		moveBox();
		
		dg.setColor( Color.white );
		dg.fillRect( 0 , 0 , width , height );
		dg.setColor( Color.black  );

		// $BA4ItIA2h$9$k!#(B
		for(int i=0;i<nownum;i++){
			if( bfill[i] ){
				dg.fillRect( (int)bx[i] , (int)by[i] ,
							(int)bxsiz[i] , (int)bysiz[i]);
				bfill[i] = false;
			} else {
				dg.drawRect( (int)bx[i] , (int)by[i] ,
							(int)bxsiz[i] , (int)bysiz[i]);
			}
		}
	}
}



