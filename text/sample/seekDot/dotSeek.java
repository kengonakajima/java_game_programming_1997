import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;

public class dotSeek extends Applet implements Runnable
{
	Image ofscr;
	Graphics ofscr_g;

	Thread thread;


	int height =200, width=300;

	int snn = 20;
	double snx[] = new double[snn];
	double sny[] = new double[snn];
	double sndx[] = new double[snn];
	double sndy[] = new double[snn];

	Color snowcolor = new Color( 0xffffff );   // 雪の色
	Color bgcolor = new Color( 0x000011 );     // バックの色

	public void init()
	{
		ofscr = createImage( width , height );
		ofscr_g = ofscr.getGraphics();

		// 必要な分のメモリーを確保。要素数は (縦ドット数)×(横ドット数) です。 

		for( int i=0;i<snn;i++) initSnow( i );

		ofscr_g.setColor( bgcolor );
		ofscr_g.fillRect( 0 , 0 , width , height );
		ofscr_g.setColor( snowcolor );
		ofscr_g.fillRect( 0 , height-20,width,20);
		thread = new Thread( this );
		thread.start();
	}

	public void paint( Graphics g ){
		g.drawImage( ofscr , 0 , 0 , this );
		drawAllSnow(g);
	}
	// 画像のある点の色を求める。
	PixelGrabber pg;
	int getPixelColor( Image img , int x , int y )
	{

		try{
			int pix[] = new int[width*100];
			pg = new PixelGrabber( img , x,y,3,3,pix ,0, img.getWidth()  );
			System.out.println("fuck");
			pg.grabPixels();
			System.out.println("fuck");
			return pix[0];

		}catch( Throwable t ){
			System.out.println( t );
			return 0x00000000;
		}
	}
	void initSnow( int index )
	{
		snx[index] = width * Math.random();
		sny[index] = height * Math.random();
		sndx[index] = -2.0 + 4*Math.random();
		sndy[index] = 0.3 + 2*Math.random();
	}

	void drawSnow( Graphics g , int x, int y )
	{
		g.setColor( snowcolor );
		g.drawLine( x-3,y-3 , x+3,y+3 );
		g.drawLine( x-3,y+3 , x+3,y-3 );
		g.drawLine( x,y-5 , x,y+5 );
	}
	void drawAllSnow(Graphics g)
	{
		for(int i=0;i<snn; i++){
			drawSnow( g,(int)snx[i] , (int)sny[i] );
		}
	}
	void moveSnow()
	{
		for(int i=0;i<snn;i++){
			snx[i] += sndx[i];
			sny[i] += sndy[i];
			sndx[i] += ( -1 + 2*Math.random() );
			if( getPixelColor( ofscr , (int) snx[i] , (int) sny[i] ) ==
			    0xffffff ){
				drawSnow( ofscr_g , (int)snx[i] ,(int) sny[i] );
				initSnow(i);
			}
		}
	}
	public void run()
	{
		while(true)
		{
			System.out.println( snx[0] );
			try{
				Thread.sleep(80);
			}catch( InterruptedException e ){}
			moveSnow();
			repaint();
		}
	}

	public void destroy()
	{
		thread.stop();
	}

}
