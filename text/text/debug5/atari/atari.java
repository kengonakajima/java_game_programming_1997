import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class atari extends java.applet.Applet implements Runnable
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
		//width = size().width; //JDK1.0.2
		//height = size().height;  //JDK1.0.2
		width = getSize().width; //JDK1.1
		height = getSize().height;  //JDK1.1
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
	
	int maxnum=100;							// 最大の箱の個数
	int nownum=1;							// 現在の箱の個数
	double bx[] = new double[maxnum];		// 位置のX座標
	double by[] = new double[maxnum];		// 位置のY座標
	double bxsiz[] = new double[maxnum];	// 箱の幅
	double bysiz[] = new double[maxnum];	// 箱の高さ
	double bdx[] = new double[maxnum];		// X方向の移動量
	double bdy[] = new double[maxnum];		// Y方向の移動量
	boolean bfill[] = new boolean[maxnum]; //何かに当たった時に点滅させるフラグ

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

	double react = 17;  // 大きいほど緩やかな反射になる。割り算の定数
	void moveBox(){
		double before_x , before_y;
		for(int i=0;i<nownum;i++){
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
				// 壁に当たったら、移動方向の符号を変える。
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

	// 当たり判定。心臓部です。正確に四角の範囲。
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
	public void destroy()
	{
		thread.stop();
	}
}
