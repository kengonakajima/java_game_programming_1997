import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import java.util.*;


public class shootChar extends Applet implements Runnable
{


	Thread thread;
	Image double_buffer;
	Graphics dg;

	int cron=0;

	int width ,height;
	int interval = 90;	/* milli second */

	public void init()
	{


		thread = new Thread(this);
		width = size().width;
		height = size().height;
		double_buffer = createImage( width , height );
		dg = double_buffer.getGraphics();

		

		thread.start();

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

	int enemy_num=20;
	boolean alive[] = new boolean[enemy_num];
	double enemy_x[] = new double[enemy_num];
	double enemy_y[] = new double[enemy_num];
	double enemy_dx[] = new double[enemy_num];
	double enemy_dy[] = new double[enemy_num];
	char enemy_char[] = new char[enemy_num];
	Color enemy_col[] = new Color[enemy_num];
	double enemy_xsiz = 20.0, enemy_ysiz = 20.0;

	int life;

	Color bg = new Color( 0x001629 );
	Color fg1 = new Color( 0xffee44 );
	Color fg2 = new Color( 0xffbb33 );
	Color fg3 = new Color( 0xcc7700 );

	int hit=0,score=0,hitatime=0;
	public boolean keyDown( Event e , int c )
	{
		int counter=0;
		for(int i = 0 ;  i < enemy_num;i++){
			if( alive[i] == true && enemy_char[i] == (char) c ){
				System.out.println("ok");
				alive[i] = false;
				hit++;
				counter++;
			}
		}
		if( counter > hitatime ) hitatime = counter;
		score += counter * counter;
		return true;
	
	}
	void initGame()
	{
		hit = score = hitatime = 0;
		for(int i=0;i<enemy_num;i++){
			alive[i] = false;
		}
	}
	void createEnemy()
	{
		for(int i=0;i<enemy_num;i++){
			if( alive[i] == false ){
				alive[i]=true;
				enemy_x[i] =  (double)width * Math.random() ;
				enemy_y[i] = 0;
				enemy_dx[i] = -4 + 8*Math.random();
				enemy_dy[i] = 3*Math.random()+0.3;
				enemy_char[i] = (char)( 'A' + (int)( ('z'-'A')*Math.random() ) );
				enemy_col[i] = new Color( (int)( 0xffffff * Math.random() ));
				break;
			}
		}
	}
	void moveEnemy()
	{
		for(int i=0;i<enemy_num;i++){
			if( alive[i] == false) continue;
			enemy_x[i] += enemy_dx[i];
			enemy_y[i] += enemy_dy[i];
			if( enemy_x[i] < 0 ){
				enemy_x[i] = 0;
				enemy_dx[i] *= -1;
			}
			if( enemy_x[i] > (width-enemy_xsiz) ){
				enemy_x[i] = (width-enemy_xsiz);
				enemy_dx[i] *= -1;
			}
			if( enemy_y[i] > height ){
				life--;
				alive[i] = false;
			}
			dg.setColor( enemy_col[i] );
			dg.drawString( "" + enemy_char[i] , (int)enemy_x[i] , (int)(enemy_y[i] + enemy_ysiz) );
		}
	}


	final int GAME = 1;
	final int IDLE = 2;
	int gamemode = IDLE;

	void doIt()
	{
		dg.setColor( bg );
		dg.fillRect( 0 , 0 , width , height );

		moveEnemy();

		if( ( cron % 10 ) == 0 )createEnemy();
		switch(gamemode )
		{
			case GAME:
			dg.setColor( fg1 );
			dg.drawString( "Score:" + score + " Hit:" + hit + " OneTime:" + hitatime ,0,26);
			break;
			case IDLE:
			gamemode = GAME;
			initGame();
			break;
		}
		
	}


}
// キーボードゲーム。降ってくる文字のキーを押すと消える。上で撃つほど高い点数。
// 3文字下まできたら死に。
