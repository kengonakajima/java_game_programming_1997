/*
  複数の四角が動いて、それぞれが当たるとはねかえるの図

  このプログラムも、game.javaをコピーして使っている。


  まず大事なポイントは、当たり判定の中心部分になっているcheck
メソッドです。四角形同士の当たり判定は、不等号を使えば簡単に
実現できる例です。他にも、円同士の当たり判定なら距離を計算
するとか、色々バリエーションはあるでしょう。

  次に、キャラクタの反射の部分です。

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

	// 変更可能個所

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

	double react = 17;  // 大きいほど緩やかな反射になる。
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
					double relx , rely;   // 箱の中心の相対位置関係。
					relx = (bx[j]+bxsiz[j]/2) - (bx[i]+bxsiz[i]/2);
					rely = (by[j]+bysiz[j]/2) - (by[i]+bysiz[i]/2);

					bdx[i] = -relx / react;  // お互いを離す
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

	// 当たり判定。心臓部です。
	boolean check( double x1, double y1 , double x1size , double y1size ,
				  double x2 , double y2 , double x2size , double y2size ){
		return( x2 <= (x1+x1size) && x1 <= (x2+x2size ) &&
			    y2 <= (y1+y1size) && y1 <= (y2+y2size) );
	}
	// マウスのボタンを押すと箱が増えます。
	public boolean mouseDown( Event e , int x , int y){
		bx[nownum] = x;
		by[nownum] = y;
		nownum++;
		return true;
	}
	void doIt(){

		// 箱を動かす。
		moveBox();
		
		dg.setColor( Color.white );
		dg.fillRect( 0 , 0 , width , height );
		dg.setColor( Color.black  );

		// 全部描画する。
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



